package ceyhun.erturk.model;

import java.io.Serializable;

public class Transaction implements Serializable {
    private long transactionId;
    private long senderId;
    private long receiverId;
    private double transactionAmount;


    public Transaction(long senderId, long receiverId, double transactionAmount) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.transactionAmount = transactionAmount;
    }

    public long getSenderId() {
        return senderId;
    }

    public long getReceiver() {
        return receiverId;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "Transaction : {" +
                "transactionId=" + transactionId +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", transactionAmount=" + transactionAmount +
                '}';
    }
}
