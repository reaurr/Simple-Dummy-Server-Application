package ceyhun.erturk.ApiTests;

import ceyhun.erturk.InıtTest;
import ceyhun.erturk.globals.Constants;
import ceyhun.erturk.model.Transaction;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.*;

import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TransactionApi {

    private static ObjectMapper mapper;

    @ClassRule
    public static final InıtTest inıtTest = new InıtTest();

    @BeforeClass
    public static void setUp() {
        mapper = new ObjectMapper();
    }

    @AfterClass
    public static void tearDown() {
        mapper = null;
    }


    @Test
    public void testTransaction() throws IOException {
        Transaction transaction = new Transaction(1, 2, 50);
        String jsonInString = mapper.writeValueAsString(transaction);
        StringEntity entity = new StringEntity(jsonInString);
        HttpPut request = new HttpPut(Constants.TRANSACTION_URI + "doTransfer");
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction transactionRead = mapper.readValue(jsonString, Transaction.class);
        assertTrue(transactionRead.getSenderId() == 1);
        assertTrue(transactionRead.getReceiver() == 2);
        assertTrue(transactionRead.getTransactionAmount() == 50);
    }

    @Test
    public void testGetTransaction() throws IOException {
        HttpPut request = new HttpPut(Constants.TRANSACTION_URI + 1);
        request.setHeader("Content-type", "application/json");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
        String jsonString = EntityUtils.toString(response.getEntity());
        Transaction transactionRead = mapper.readValue(jsonString, Transaction.class);
        assertNotNull(transactionRead.getTransactionAmount());
    }


    @Test
    public void testGetAllTransactions() throws IOException {
        HttpPut request = new HttpPut(Constants.TRANSACTION_URI + "transactions");
        request.setHeader("Content-type", "application/json");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
        String jsonString = EntityUtils.toString(response.getEntity());
        List<Transaction> transactions = mapper.readValue(jsonString, new TypeReference<List<Transaction>>() {
        });
        transactions.stream().forEach(transaction -> {
            assertNotNull(transaction.getTransactionAmount());
            assertNotNull(transaction.getReceiver());
            assertNotNull(transaction.getSenderId());
        });
    }

}
