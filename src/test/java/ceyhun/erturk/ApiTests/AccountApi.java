package ceyhun.erturk.ApiTests;

import ceyhun.erturk.InıtTest;
import ceyhun.erturk.model.Account;
import ceyhun.erturk.util.CurrencyUtil;
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

import static ceyhun.erturk.globals.Constants.ACCOUNT_URI;
import static org.junit.Assert.assertTrue;

public class AccountApi {

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
    public void testCreateAccount() throws Exception {
        Account account = new Account(500, "Ceyhun", "Erturk", CurrencyUtil.CurrencyType.EURO);
        String jsonInString = mapper.writeValueAsString(account);
        StringEntity entity = new StringEntity(jsonInString);
        HttpPut request = new HttpPut(ACCOUNT_URI+"create");
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
        String jsonString = EntityUtils.toString(response.getEntity());
        Account accountRead = mapper.readValue(jsonString, Account.class);
        assertTrue(accountRead.getAccountUsername().equals("Ceyhun"));
        assertTrue(accountRead.getAccountType() == CurrencyUtil.CurrencyType.EURO);
    }

    @Test
    public void testGetAccount() throws IOException {
        HttpPut request = new HttpPut(ACCOUNT_URI + 55);
        request.setHeader("Content-type", "application/json");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
        String jsonString = EntityUtils.toString(response.getEntity());
        Account accountRead = mapper.readValue(jsonString, Account.class);
        assertTrue(accountRead.getAccountType() == CurrencyUtil.CurrencyType.DOLLAR);
    }

    @Test
    public void testGetAccountBalance() throws IOException {
        HttpPut request = new HttpPut(ACCOUNT_URI+"balance/" + 55);
        request.setHeader("Content-type", "text/plain");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
        String jsonString = EntityUtils.toString(response.getEntity());
        Account accountRead = mapper.readValue(jsonString, Account.class);
        assertTrue(accountRead.getBalance() > 0.0);
    }

    @Test
    public void testGetAllAccounts() throws IOException {
        HttpPut request = new HttpPut(ACCOUNT_URI+"accounts");
        request.setHeader("Content-type", "application/json");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
        String jsonString = EntityUtils.toString(response.getEntity());
        List<Account> accounts = mapper.readValue(jsonString, new TypeReference<List<Account>>() {
        });
        accounts.stream().forEach(account -> {
            assertTrue(!account.getAccountUsername().isEmpty());
            assertTrue(account.getAccountType() != null);
            assertTrue(account.getBalance() > 0.0);
        });
    }


}
