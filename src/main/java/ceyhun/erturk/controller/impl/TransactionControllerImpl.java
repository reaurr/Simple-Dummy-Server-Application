package ceyhun.erturk.controller.impl;


import ceyhun.erturk.controller.TransactionController;

import ceyhun.erturk.exception.*;
import ceyhun.erturk.globals.ServerGlobal;
import ceyhun.erturk.util.Counters;
import ceyhun.erturk.util.LockMechanism;
import ceyhun.erturk.globals.DaoFactory;
import ceyhun.erturk.model.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public final class TransactionControllerImpl extends TransactionController {

    private static TransactionControllerImpl transactionController;

    private TransactionControllerImpl() {
    }

    //No need to synchronized, requests are already in lock
    public final static TransactionControllerImpl getInstance() {
        if (transactionController == null) {
            transactionController = new TransactionControllerImpl();
        }
        return transactionController;
    }

    /**
     * @param transaction user accountId
     * @return Optional Transaction
     */
    @Override
    public Optional<Transaction> processTransaction(Transaction transaction) throws IncorrectBalanceException, NoSuchAccountException, WrongDaoException, ExecutionException, InterruptedException, CurrencyConvertException {
        LockMechanism.getTransactionLock().lock();
        if (transaction.getReceiver() != transaction.getSenderId()) {
            Future<Boolean> withdrawResult = ServerGlobal.getServerExecutor().submit(DaoFactory.getBaseDao(DaoFactory.DaoType.ACOOUNT).getAccount().withdraw(transaction.getSenderId(), transaction.getTransactionAmount()));
            Future<Boolean> depositResult = ServerGlobal.getServerExecutor().submit(DaoFactory.getBaseDao(DaoFactory.DaoType.ACOOUNT).getAccount().deposit(transaction.getReceiver(), transaction.getTransactionAmount(), transaction.getSenderId()));
            if (withdrawResult.get() && depositResult.get()) {
                transaction.setTransactionId(Counters.getTransactionIdCounter().incrementAndGet());
                transaction = ServerGlobal.getServerExecutor().submit(DaoFactory.getBaseDao(DaoFactory.DaoType.TRANSACTION).getTransaction().addTransaction(transaction)).get();
                LockMechanism.unLockTransaction();
                return Optional.of(transaction);
            } else {
                LockMechanism.unLockTransaction();
                return Optional.empty();
            }
        } else {
            LockMechanism.unLockTransaction();
            return Optional.empty();
        }
    }

    /**
     * @return Transaction list
     */
    @Override
    public List<Transaction> getAllTransactions() throws NoSuchTransactionException, WrongDaoException, ExecutionException, InterruptedException {
        LockMechanism.getTransactionLock().lock();
        Future<List<Transaction>> transactions = ServerGlobal.getServerExecutor().submit(DaoFactory.getBaseDao(DaoFactory.DaoType.TRANSACTION).getTransaction().getAllTransactions());
        LockMechanism.unLockTransaction();
        return transactions.get();
    }

    /**
     * @param transactionId user accountId
     * @return Transaction
     */
    @Override
    public Transaction getTransaction(long transactionId) throws NoSuchTransactionException, WrongDaoException, ExecutionException, InterruptedException {
        LockMechanism.getTransactionLock().lock();
        Future<Transaction> transactionFuture = ServerGlobal.getServerExecutor().submit(DaoFactory.getBaseDao(DaoFactory.DaoType.TRANSACTION).getTransaction().getTransaction(transactionId));
        LockMechanism.unLockTransaction();
        return transactionFuture.get();
    }


}
