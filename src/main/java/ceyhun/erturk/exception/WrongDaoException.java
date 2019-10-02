package ceyhun.erturk.exception;


import ceyhun.erturk.util.LockMechanism;

public class WrongDaoException extends Exception {
    public WrongDaoException() {
        super("Wrong Dao");
        LockMechanism.unLockTransaction();
        LockMechanism.unLockAccount();
    }
}
