package net.leanelephant;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/transactionservice")
public class TransactionService {

    private Map<Long, Transaction> transactionMap = new HashMap<Long, Transaction>();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello")
    public String getMessage() {
        return "Hello world!";
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/transaction/{transaction_id}")
    public Response addTransaction(Transaction transaction, @PathParam("transaction_id") String transactionIdString) {
        Long transactionId = Long.parseLong(transactionIdString);
        transactionMap.put(transactionId, transaction);

        return Response.ok().build();
    }

}
