package net.leanelephant;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Application;

public class TransactionServiceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(TransactionService.class);
    }

    @Test
    public void test() {
        String response = target("hello").request().get(String.class);
        Assert.assertEquals("Hello world!", response);
    }
}