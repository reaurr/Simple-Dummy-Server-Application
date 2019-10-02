package ceyhun.erturk.ServiceTests;


import ceyhun.erturk.InıtTest;
import ceyhun.erturk.exception.*;
import ceyhun.erturk.globals.DaoFactory;
import ceyhun.erturk.globals.ServerGlobal;
import ceyhun.erturk.model.Account;
import ceyhun.erturk.model.Transaction;
import ceyhun.erturk.util.CurrencyUtil;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TransactionServiceTest {

    @ClassRule
    public static final InıtTest inıtTest = new InıtTest();

    @Test
    public void testDoTransaction() throws WrongDaoException, ExecutionException, InterruptedException {
        Transaction transaction = new Transaction(1, 2, 50);
        transaction = ServerGlobal.getServerExecutor().submit(DaoFactory.getBaseDao(DaoFactory.DaoType.TRANSACTION).getTransaction().addTransaction(transaction)).get();
        assertTrue(transaction.getSenderId() == 1);
        assertTrue(transaction.getReceiver() == 2);
        assertTrue(transaction.getTransactionAmount() == 50);
    }


    @Test
    public void testTransactionGet() throws Exception {
        Transaction transaction = ServerGlobal.getServerExecutor().submit(DaoFactory.getBaseDao(DaoFactory.DaoType.TRANSACTION).getTransaction().getTransaction(1)).get();
        assertNull(transaction);
    }

    @Test
    public void testGetTransactions() throws WrongDaoException, ExecutionException, InterruptedException, NoSuchTransactionException {
        List<Transaction> transactions = ServerGlobal.getServerExecutor().submit(DaoFactory.getBaseDao(DaoFactory.DaoType.TRANSACTION).getTransaction().getAllTransactions()).get();
        transactions.stream().forEach(transaction -> {
            assertNotNull(transaction.getTransactionAmount());
            assertNotNull(transaction.getReceiver());
            assertNotNull(transaction.getSenderId());
        });
    }

}
