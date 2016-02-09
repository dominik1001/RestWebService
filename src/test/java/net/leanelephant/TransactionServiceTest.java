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

    private void putTransaction(Transaction transaction) {
        target("transactionservice/transaction/" + transaction.getId()).request().post(Entity.entity(transaction, MediaType.APPLICATION_JSON_TYPE));
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(TransactionService.class);
    }

    @Test
    public void testAddValidTransaction() {
        Transaction transaction = new Transaction(5000, "cars");
        Entity<Transaction> transactionEntity = Entity.entity(transaction, MediaType.APPLICATION_JSON_TYPE);

        Response response = target("transactionservice/transaction/10").request().post(transactionEntity);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("{\"status\":\"ok\"}", response.readEntity(String.class));
    }

    @Test
    public void testAddInvalidTransaction() {
        Transaction transaction = new Transaction(11, 5000, "cars");
        Entity<Transaction> transactionEntity = Entity.entity(transaction, MediaType.APPLICATION_JSON_TYPE);

        Response response = target("transactionservice/transaction/10").request().post(transactionEntity);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testAddInvalidTransactionIdNotANumber() {
        Transaction transaction = new Transaction(11, 5000, "cars");
        Entity<Transaction> transactionEntity = Entity.entity(transaction, MediaType.APPLICATION_JSON_TYPE);

        Response response = target("transactionservice/transaction/invalid").request().post(transactionEntity);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetTransactionsByTypeNoTransactions() {
        Response response = target("transactionservice/types/cars").request().get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("[]", response.readEntity(String.class));
    }

    @Test
    public void testGetTransactionsByType() {
        putTransaction(new Transaction(12, 5000, "cars"));
        putTransaction(new Transaction(13, 10000, "shopping"));

        Response response = target("transactionservice/types/cars").request().get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("[10]", response.readEntity(String.class));
    }

    @Test
    public void testGetTransactionSum() {
        putTransaction(new Transaction(14, 5000, "cars"));
        putTransaction(new Transaction(15, 10000, "shopping"));

        Response response = target("transactionservice/sum/10").request().get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("{\"sum\":5000.0}", response.readEntity(String.class));
    }

}