package net.leanelephant;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/transactionservice")
public class TransactionService {

    private static TransactionStore transactionStore = new TransactionStore();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello")
    public String getMessage() {
        return "Hello world!";
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
        transactionStore.addTransaction(transaction);

        return Response.ok("{ 'status': 'ok' }").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/types/{type}")
    public Response getTransactionsByType(@PathParam("type") String type) {
        List<Long> typeList = transactionStore.getTransactionsByType(type);
        return Response.ok(typeList.toArray()).build();
    }
}
