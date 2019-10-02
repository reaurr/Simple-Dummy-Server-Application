package ceyhun.erturk.model;

import ceyhun.erturk.util.CurrencyUtil;

import java.io.Serializable;
import java.util.Objects;

public class Account implements Serializable {
    private long accountId;
    private double balance;
    private String accountUsername, accountUserSurname;
    private CurrencyUtil.CurrencyType accountType;


    //accountId not defined by user manually
    public Account(double balance, String accountUsername, String accountUserSurname, CurrencyUtil.CurrencyType accountType) {
        this.balance = balance;
        this.accountUsername = accountUsername;
        this.accountUserSurname = accountUserSurname;
        this.accountType=accountType;
    }

    //accountId not defined by user manually and not define accountType so default is Dollar
    public Account(double balance, String accountUsername, String accountUserSurname) {
        this.balance = balance;
        this.accountUsername = accountUsername;
        this.accountUserSurname = accountUserSurname;
        this.accountType=CurrencyUtil.CurrencyType.DOLLAR;
    }

    // for auto generate test accounts
    public Account(long accountId, double balance, String accountUsername, String accountUserSurname,CurrencyUtil.CurrencyType accountType) {
        this.accountId = accountId;
        this.balance = balance;
        this.accountUsername = accountUsername;
        this.accountUserSurname = accountUserSurname;
        this.accountType=accountType;
    }

    public long getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountUsername() {
        return accountUsername;
    }


    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public CurrencyUtil.CurrencyType getAccountType() {
        return accountType;
    }

    // necessary for mapping
    @Override
    public int hashCode() {
        return Objects.hash(getAccountId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return getAccountId() == account.getAccountId();
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                ", accountUsername='" + accountUsername + '\'' +
                ", accountUserSurname='" + accountUserSurname + '\'' +
                ", accountType=" + accountType +
                '}';
    }
}
