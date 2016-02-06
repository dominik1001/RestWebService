package net.leanelephant;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/transactionservice")
public class TransactionService {
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
    public String addTransaction(Transaction transaction, @PathParam("transaction_id") String transactionId) throws Exception {
        System.out.println(transactionId);
        System.out.println(transaction);

        return "ok";
    }

}
