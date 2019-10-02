package ceyhun.erturk.exception;


import ceyhun.erturk.util.LockMechanism;

public class WrongControllerException extends Exception {
    public WrongControllerException() {
        super("Wrong Controller");
        LockMechanism.unLockTransaction();
        LockMechanism.unLockAccount();
    }
}
