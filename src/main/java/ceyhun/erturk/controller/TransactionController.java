package ceyhun.erturk.controller;

import ceyhun.erturk.exception.*;
import ceyhun.erturk.model.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;


public abstract class TransactionController extends BaseController {

    /**
     * @param transaction:
     * @return Optional transaction
     */
    public abstract Optional<Transaction> processTransaction(Transaction transaction) throws IncorrectBalanceException, NoSuchAccountException, WrongDaoException, ExecutionException, InterruptedException, CurrencyConvertException;

    /**
     * @return transaction list
     */
    public abstract List<Transaction> getAllTransactions() throws NoSuchTransactionException, WrongDaoException, ExecutionException, InterruptedException;

    /**
     * @param transactionId:
     * @return  transaction
     */
    public abstract Transaction getTransaction(long transactionId) throws NoSuchTransactionException, WrongDaoException, ExecutionException, InterruptedException;

    @Override
    public TransactionController getTransaction() {
        return this;
    }


    @Override
    public AccountController getAccount() throws WrongControllerException {
        throw new WrongControllerException();
    }

}
