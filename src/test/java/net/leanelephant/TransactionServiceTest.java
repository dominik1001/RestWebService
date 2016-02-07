package net.leanelephant;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class TransactionServiceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(TransactionService.class);
    }

    @Test
    public void testHelloWorld() {
        String response = target("transactionservice/hello").request().get(String.class);
        assertEquals("Hello world!", response);
    }

    @Test
    public void testAddTransaction() {
        Transaction transaction = new Transaction(10, 5000, "cars");
        Entity<Transaction> transactionEntity = Entity.entity(transaction, MediaType.APPLICATION_JSON_TYPE);

        Response response = target("transactionservice/transaction/10").request().post(transactionEntity);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}