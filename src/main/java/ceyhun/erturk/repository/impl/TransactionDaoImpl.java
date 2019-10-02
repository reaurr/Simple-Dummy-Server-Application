package ceyhun.erturk.repository.impl;


import ceyhun.erturk.model.Transaction;
import ceyhun.erturk.repository.TransactionDao;

import java.util.*;
import java.util.concurrent.Callable;


public final class TransactionDaoImpl extends TransactionDao {

    private List<Transaction> transactionList;

    private static TransactionDaoImpl transactionDao;

    private TransactionDaoImpl() {
        transactionList = new ArrayList();
    }

    //No need to synchronized, requests are already in lock
    public final static TransactionDaoImpl getInstance() {
        if (transactionDao == null) {
            transactionDao = new TransactionDaoImpl();
        }
        return transactionDao;
    }

    /**
     * @param transaction:
     * @return Callable transaction
     */
    @Override
    public Callable<Transaction> addTransaction(Transaction transaction) {
        return () -> {
            transactionList.add(transaction);
            return transaction;
        };
    }

    /**
     * @return Callable transaction list
     */
    @Override
    public Callable<List<Transaction>> getAllTransactions() {
        return () -> transactionList;
    }

    /**
     * @param transactionId:
     * @return Callable transaction
     */
    @Override
    public Callable<Transaction> getTransaction(long transactionId) {
        return () -> transactionList.stream()
                .filter(transaction -> transactionId == (transaction.getTransactionId()))
                .findAny().get();
    }
}
