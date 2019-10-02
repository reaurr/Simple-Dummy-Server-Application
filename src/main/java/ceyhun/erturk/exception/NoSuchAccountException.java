package ceyhun.erturk.exception;


import ceyhun.erturk.util.LockMechanism;

public class NoSuchAccountException extends Exception {
    public NoSuchAccountException(long accountId) {
        super("Account not found, id : " + accountId);
        LockMechanism.unLockTransaction();
        LockMechanism.unLockAccount();
    }
}
