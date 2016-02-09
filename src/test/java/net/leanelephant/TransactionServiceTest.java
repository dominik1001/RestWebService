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

    public void setUp() throws Exception {
        super.setUp();
        putTransaction(new Transaction(10L, 5000.0, "cars"));
        putTransaction(new Transaction(11L, 10000.0, "shopping", 10L));
    }

    @Test
    public void testAddTransaction_Valid() {
        Transaction transaction = new Transaction(5000, "cars");
        Entity<Transaction> transactionEntity = Entity.entity(transaction, MediaType.APPLICATION_JSON_TYPE);

        Response response = target("transactionservice/transaction/12").request().post(transactionEntity);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("{\"status\":\"ok\"}", response.readEntity(String.class));
    }

    @Test
    public void testAddTransaction_Invalid() {
        Transaction transaction = new Transaction(13, 5000, "cars");
        Entity<Transaction> transactionEntity = Entity.entity(transaction, MediaType.APPLICATION_JSON_TYPE);

        Response response = target("transactionservice/transaction/14").request().post(transactionEntity);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testAddTransaction_InvalidIdNotANumber() {
        Transaction transaction = new Transaction(15, 5000, "cars");
        Entity<Transaction> transactionEntity = Entity.entity(transaction, MediaType.APPLICATION_JSON_TYPE);

        Response response = target("transactionservice/transaction/invalid").request().post(transactionEntity);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetTransactionsByType_NoTransactions() {
        Response response = target("transactionservice/types/books").request().get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("[]", response.readEntity(String.class));
    }

    @Test
    public void testGetTransactionsByType() {
        Response response = target("transactionservice/types/cars").request().get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("[10]", response.readEntity(String.class));
    }

    @Test
    public void testGetTransactionSum() {
        Response response = target("transactionservice/sum/10").request().get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("{\"sum\":15000.0}", response.readEntity(String.class));
    }

    @Test
    public void testGetTransactionSum_SingleTransaction() {
        Response response = target("transactionservice/sum/11").request().get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("{\"sum\":10000.0}", response.readEntity(String.class));
    }

    @Test
    public void testGetTransactionSum_NoTransactions() {
        Response response = target("transactionservice/sum/9").request().get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("{\"sum\":0.0}", response.readEntity(String.class));
    }

}