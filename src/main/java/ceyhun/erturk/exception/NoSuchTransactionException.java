package ceyhun.erturk.exception;


import ceyhun.erturk.util.LockMechanism;

public class NoSuchTransactionException extends Exception {
    public NoSuchTransactionException(long transactionId) {
        super("Transaction not found, id : " + transactionId);
        LockMechanism.unLockTransaction();
        LockMechanism.unLockAccount();
    }
}
