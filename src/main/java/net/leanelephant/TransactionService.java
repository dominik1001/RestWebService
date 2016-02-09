package net.leanelephant;

import com.owlike.genson.Genson;
import net.leanelephant.exception.ParentTransactionDoesNotExistException;
import net.leanelephant.exception.TransactionExistsException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

@Path("/transactionservice")
public class TransactionService {

    // This can probably be improved by creating a singleton bean for the in-memory store.
    private static TransactionStore transactionStore = new TransactionStore();

    private final String statusOkJson;

    private final Genson genson;

    public TransactionService() {
        genson = new Genson();
        statusOkJson = genson.serialize(new HashMap<String, Object>() {{
            put("status", "ok");
        }});
    }

    /**
     * Adds a transaction
     *
     * @param transaction         the transaction to add
     * @param transactionIdString the transaction's id
     * @return returns a 200 when everything went well
     * Returns a 400 when
     * - a transaction with the id already exists
     * - the transaction's id is not null and does not equal the parameter id
     * - the transaction has a parent transaction id that does not exist
     * <p/>
     * Runtime analysis: O(1)
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/transaction/{transaction_id}")
    public Response addTransaction(Transaction transaction, @PathParam("transaction_id") String transactionIdString) {
        Long transactionId;
        try {
            transactionId = Long.parseLong(transactionIdString);
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (transaction.getId() != null && !transaction.getId().equals(transactionId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        transaction.setId(transactionId);
        try {
            transactionStore.addTransaction(transaction);
        } catch (TransactionExistsException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (ParentTransactionDoesNotExistException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(statusOkJson).build();
    }

    /**
     * A json list of all transaction ids that share the same type $type.
     *
     * @param type the type to search for
     * @return returns a json array with all the ids
     * <p/>
     * Runtime analysis: O(1)
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/types/{type}")
    public Response getTransactionsByType(@PathParam("type") String type) {
        List<Long> typeList = transactionStore.getTransactionsByType(type);
        return Response.ok(typeList.toArray()).build();
    }

    /**
     * A sum of all transactions that are transitively linked by their parent_id to $transaction_id.
     *
     * @param transactionIdString the transaction's id
     * @return returns a json map with the sum
     * <p/>
     * Runtime analysis: O(m) (Worst Case; m is the depth of the transaction tree)
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/sum/{transaction_id}")
    public Response getTransactionSum(@PathParam("transaction_id") String transactionIdString) {
        Long transactionId;
        try {
            transactionId = Long.parseLong(transactionIdString);
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final double sum = transactionStore.getTransactionSum(transactionId);
        String jsonSum = genson.serialize(new HashMap<String, Object>() {{
            put("sum", sum);
        }});
        return Response.ok(jsonSum).build();
    }
}
