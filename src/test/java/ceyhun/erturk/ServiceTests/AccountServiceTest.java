package ceyhun.erturk.ServiceTests;


import ceyhun.erturk.InıtTest;
import ceyhun.erturk.exception.CurrencyConvertException;
import ceyhun.erturk.exception.IncorrectBalanceException;
import ceyhun.erturk.exception.NoSuchAccountException;
import ceyhun.erturk.exception.WrongDaoException;
import ceyhun.erturk.globals.DaoFactory;
import ceyhun.erturk.globals.ServerGlobal;
import ceyhun.erturk.model.Account;
import ceyhun.erturk.util.CurrencyUtil;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

public class AccountServiceTest {

    @ClassRule
    public static final InıtTest inıtTest = new InıtTest();

    @Test
    public void testCreateAccount() throws Exception {
        Account account = new Account(500, "Ceyhun", "Erturk", CurrencyUtil.CurrencyType.EURO);
        account = ServerGlobal.getServerExecutor().submit(DaoFactory.getBaseDao(DaoFactory.DaoType.ACOOUNT).getAccount().createAccount(account)).get();
        assertTrue(account.getAccountUsername().equals("Ceyhun"));
        assertTrue(account.getAccountType() == CurrencyUtil.CurrencyType.EURO);
    }

    @Test
    public void testWithdraw() throws WrongDaoException, IncorrectBalanceException, NoSuchAccountException, ExecutionException, InterruptedException {
        boolean result = ServerGlobal.getServerExecutor().submit(DaoFactory.getBaseDao(DaoFactory.DaoType.ACOOUNT).getAccount().withdraw(21, 100)).get();
        Assert.assertTrue(result);
    }

    @Test
    public void testDeposit() throws WrongDaoException, IncorrectBalanceException, NoSuchAccountException, ExecutionException, InterruptedException, CurrencyConvertException {
        boolean result = ServerGlobal.getServerExecutor().submit(DaoFactory.getBaseDao(DaoFactory.DaoType.ACOOUNT).getAccount().deposit(21, 23, 25)).get();
        Assert.assertTrue(result);
    }

    @Test
    public void testGetAccount() throws WrongDaoException, NoSuchAccountException, ExecutionException, InterruptedException {
        Optional<Account> account = ServerGlobal.getServerExecutor().submit(DaoFactory.getBaseDao(DaoFactory.DaoType.ACOOUNT).getAccount().getAccount(23)).get();
        Assert.assertTrue(account.isPresent());
    }

    @Test
    public void testGetAllAccount() throws WrongDaoException, ExecutionException, InterruptedException {
        List<Account> accounts = ServerGlobal.getServerExecutor().submit(DaoFactory.getBaseDao(DaoFactory.DaoType.ACOOUNT).getAccount().getAllAccounts()).get();
        accounts.stream().forEach(account -> {
            assertTrue(!account.getAccountUsername().isEmpty());
            assertTrue(account.getAccountType() != null);
            assertTrue(account.getBalance() > 0.0);
        });
    }

    @Test
    public void testGetAccountBalance() throws WrongDaoException, NoSuchAccountException, ExecutionException, InterruptedException {
        Double balanceResult = ServerGlobal.getServerExecutor().submit(DaoFactory.getBaseDao(DaoFactory.DaoType.ACOOUNT).getAccount().getBalance(21)).get();
        Assert.assertNotNull(balanceResult);
    }


}
