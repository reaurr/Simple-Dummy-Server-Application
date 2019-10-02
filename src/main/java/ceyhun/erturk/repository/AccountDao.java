package ceyhun.erturk.repository;


import ceyhun.erturk.exception.*;
import ceyhun.erturk.model.Account;
import ceyhun.erturk.util.CurrencyUtil;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;


public abstract class AccountDao extends BaseDao {

    /**
     * @param account:
     * @return Callable  account
     */
    public abstract Callable<Account> createAccount(Account account) throws DuplicateAccountException;

    /**
     * @param accountId:
     * @return Callable of optional account
     */
    public abstract Callable<Optional<Account>> getAccount(long accountId) throws NoSuchAccountException;

    /**
     * @param accountId,amount:
     * @return Callable Boolean
     */
    public abstract Callable<Boolean> withdraw(long accountId, double amount) throws NoSuchAccountException, IncorrectBalanceException;

    /**
     * @param accountId,amount,senderAccountId:
     * @return Callable Boolean
     */
    public abstract Callable<Boolean> deposit(long accountId, double amount, long senderAccountId) throws NoSuchAccountException, IncorrectBalanceException, CurrencyConvertException;

    /**
     * @param accountId:
     * @return Callable Double
     */
    public abstract Callable<Double> getBalance(long accountId) throws NoSuchAccountException;

    /**
     * @return Callable account list
     */
    public abstract Callable<List<Account>> getAllAccounts();


    @Override
    public AccountDao getAccount() {
        return this;
    }


    @Override
    public TransactionDao getTransaction() throws WrongDaoException {
        throw new WrongDaoException();
    }

}
