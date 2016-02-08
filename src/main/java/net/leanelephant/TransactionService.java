package net.leanelephant;

import com.owlike.genson.Genson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

@Path("/transactionservice")
public class TransactionService {

    private static TransactionStore transactionStore = new TransactionStore();

    private final String statusOkJson;

    private final Genson genson;

    public TransactionService() {
        genson = new Genson();
        statusOkJson = genson.serialize(new HashMap<String, Object>() {{
            put("status", "ok");
        }});
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
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
        }

        return Response.ok(statusOkJson).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/types/{type}")
    public Response getTransactionsByType(@PathParam("type") String type) {
        List<Long> typeList = transactionStore.getTransactionsByType(type);
        return Response.ok(typeList.toArray()).build();
    }

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
