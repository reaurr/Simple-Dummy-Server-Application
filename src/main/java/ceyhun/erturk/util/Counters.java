package ceyhun.erturk.util;

import java.util.concurrent.atomic.AtomicInteger;

public class Counters {

    public static AtomicInteger AccountIdCounter;
    public static AtomicInteger TransactionIdCounter;

    static {
        AccountIdCounter = new AtomicInteger();
        TransactionIdCounter = new AtomicInteger();
    }


    public static AtomicInteger getAccountIdCounter() {
        return AccountIdCounter;
    }

    public static AtomicInteger getTransactionIdCounter() {
        return TransactionIdCounter;
    }
}
