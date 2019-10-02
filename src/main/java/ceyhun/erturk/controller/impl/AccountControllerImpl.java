package ceyhun.erturk.controller.impl;


import ceyhun.erturk.controller.AccountController;
import ceyhun.erturk.exception.DuplicateAccountException;
import ceyhun.erturk.exception.NoSuchAccountException;
import ceyhun.erturk.exception.WrongDaoException;
import ceyhun.erturk.globals.DaoFactory;
import ceyhun.erturk.globals.ServerGlobal;
import ceyhun.erturk.model.Account;
import ceyhun.erturk.util.LockMechanism;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public final class AccountControllerImpl extends AccountController {

    private static AccountControllerImpl accountController;

    private AccountControllerImpl() {
    }

    //No need to synchronized, requests are already in lock
    public final static AccountControllerImpl getInstance() {
        if (accountController == null) {
            accountController = new AccountControllerImpl();
        }
        return accountController;
    }

    /**
     * @param account user accountId
     * @return Account
     */
    @Override
    public Account createAccount(Account account) throws DuplicateAccountException, WrongDaoException, ExecutionException, InterruptedException {
        LockMechanism.getAccountLock().lock();
        Future<Account> completableAccount = ServerGlobal.getServerExecutor().submit(DaoFactory.getBaseDao(DaoFactory.DaoType.ACOOUNT).getAccount().createAccount(account));
        LockMechanism.unLockAccount();
        return completableAccount.get();
    }

    /**
     * @param accountId user accountId
     * @return Account
     */
    @Override
    public Account getAccount(long accountId) throws Exception {
        LockMechanism.getAccountLock().lock();
        Future<Optional<Account>> account = ServerGlobal.getServerExecutor().submit(DaoFactory.getBaseDao(DaoFactory.DaoType.ACOOUNT).getAccount().getAccount(accountId));
        LockMechanism.unLockAccount();
        return account.get().orElseThrow(() -> new NoSuchAccountException(accountId));
    }

    /**
     * @param accountId user accountId
     * @return Account
     */
    @Override
    public double getBalance(long accountId) throws Exception {
        LockMechanism.getAccountLock().lock();
        Future<Double> balance = ServerGlobal.getServerExecutor().submit(DaoFactory.getBaseDao(DaoFactory.DaoType.ACOOUNT).getAccount().getBalance(accountId));
        LockMechanism.unLockAccount();
        return balance.get();
    }

    /**
     * @return Account
     */
    @Override
    public List<Account> getAllAccounts() throws Exception {
        LockMechanism.getAccountLock().lock();
        Future<List<Account>> accounts = ServerGlobal.getServerExecutor().submit(DaoFactory.getBaseDao(DaoFactory.DaoType.ACOOUNT).getAccount().getAllAccounts());
        LockMechanism.unLockAccount();
        return accounts.get();
    }

}
