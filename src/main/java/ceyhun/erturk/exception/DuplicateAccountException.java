package ceyhun.erturk.exception;


import ceyhun.erturk.util.LockMechanism;

public class DuplicateAccountException extends Exception {
    public DuplicateAccountException(long accountId) {
        super("Duplicate Account, id : " + accountId);
        LockMechanism.unLockTransaction();
        LockMechanism.unLockAccount();
    }
}
