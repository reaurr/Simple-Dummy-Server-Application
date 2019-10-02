package ceyhun.erturk.exception;


import ceyhun.erturk.util.LockMechanism;

public class CurrencyConvertException extends Exception {
    public CurrencyConvertException() {
        super("Wrong Currency Convert");
        LockMechanism.unLockTransaction();
        LockMechanism.unLockAccount();
    }
}
