package ceyhun.erturk.controller;

import ceyhun.erturk.exception.DuplicateAccountException;
import ceyhun.erturk.exception.WrongControllerException;
import ceyhun.erturk.exception.WrongDaoException;
import ceyhun.erturk.model.Account;

import java.util.List;
import java.util.concurrent.ExecutionException;


public abstract class AccountController extends BaseController {

    /**
     * @param account:
     * @return account from creation.
     */
    public abstract Account createAccount(Account account) throws DuplicateAccountException, WrongDaoException, ExecutionException, InterruptedException;


    /**
     * @param accountId:
     * @return account
     */
    public abstract Account getAccount(long accountId) throws Exception;


    /**
     * @param accountId:
     * @return balance
     */
    public abstract double getBalance(long accountId) throws Exception;

    /**
     * @return account list
     */
    public abstract List<Account> getAllAccounts() throws Exception;

    @Override
    public AccountController getAccount() {
        return this;
    }


    @Override
    public TransactionController getTransaction() throws WrongControllerException {
        throw new WrongControllerException();
    }

}
