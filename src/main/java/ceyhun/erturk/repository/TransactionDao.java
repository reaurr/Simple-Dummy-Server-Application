package ceyhun.erturk.repository;


import ceyhun.erturk.exception.NoSuchTransactionException;
import ceyhun.erturk.exception.WrongDaoException;
import ceyhun.erturk.model.Transaction;

import java.util.List;
import java.util.concurrent.Callable;


public abstract class TransactionDao extends BaseDao {

    /**
     * @param transaction:
     * @return Callable transaction
     */
    public abstract Callable<Transaction> addTransaction(Transaction transaction);

    /**
     * @return Callable transaction list
     */
    public abstract Callable<List<Transaction>> getAllTransactions() throws NoSuchTransactionException;

    /**
     * @param transactionId:
     * @return Callable transaction
     */
    public abstract Callable<Transaction> getTransaction(long transactionId) throws NoSuchTransactionException;

    @Override
    public AccountDao getAccount() throws WrongDaoException {
        throw new WrongDaoException();
    }

    @Override
    public TransactionDao getTransaction() {
        return this;
    }

}
