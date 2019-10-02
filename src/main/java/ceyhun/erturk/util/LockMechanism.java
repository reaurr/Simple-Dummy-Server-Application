package ceyhun.erturk.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class LockMechanism {

    private static ReentrantLock transactionLock, accountLock;

    static {
        transactionLock = new ReentrantLock();
        accountLock = new ReentrantLock();
    }


    public static void unLockTransaction() {
        if (transactionLock.isHeldByCurrentThread()) {
            transactionLock.unlock();
        }
    }

    public static void unLockAccount() {
        if (accountLock.isHeldByCurrentThread()) {
            accountLock.unlock();
        }
    }

    public static void setTransactionLock(ReentrantLock transactionLock) {
        LockMechanism.transactionLock = transactionLock;
    }


    public static void setAccountLock(ReentrantLock accountLock) {
        LockMechanism.accountLock = accountLock;
    }


    public static ReentrantLock getTransactionLock() {
        if (transactionLock==null){
            transactionLock = new ReentrantLock();
        }
        return transactionLock;
    }

    public static ReentrantLock getAccountLock() {
        if (accountLock==null){
            accountLock = new ReentrantLock();
        }
        return accountLock;
    }
}
