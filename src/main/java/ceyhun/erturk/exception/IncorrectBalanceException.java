package ceyhun.erturk.exception;


import ceyhun.erturk.util.LockMechanism;

public class IncorrectBalanceException extends Exception{
    public IncorrectBalanceException(long accountId){
        super("Incorrect Balance, id : " + accountId);
        LockMechanism.unLockTransaction();
        LockMechanism.unLockAccount();
    }
}
