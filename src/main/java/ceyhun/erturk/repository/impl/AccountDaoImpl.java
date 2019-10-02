package ceyhun.erturk.repository.impl;


import ceyhun.erturk.exception.DuplicateAccountException;
import ceyhun.erturk.exception.IncorrectBalanceException;
import ceyhun.erturk.exception.NoSuchAccountException;
import ceyhun.erturk.model.Account;
import ceyhun.erturk.repository.AccountDao;
import ceyhun.erturk.util.CurrencyUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;


public final class AccountDaoImpl extends AccountDao {

    //No need to Concurrent HashMap, requests already in lock
    private Map<Long, Optional<Account>> accounts;

    private static AccountDaoImpl accountDao;


    private AccountDaoImpl() {
        accounts = new HashMap();
    }

    //No need to synchronized, requests are already in lock
    public final static AccountDaoImpl getInstance() {
        if (accountDao == null) {
            accountDao = new AccountDaoImpl();
        }
        return accountDao;
    }

    /**
     * @param account:
     * @return Callable account
     */
    @Override
    public Callable<Account> createAccount(Account account) {
        return () -> {
            if (accounts.containsKey(account.getAccountId())) {
                throw new DuplicateAccountException(account.getAccountId());
            } else {
                accounts.put(account.getAccountId(), Optional.of(account));
                return account;
            }
        };
    }

    /**
     * @param accountId:
     * @return Callable of optional account
     */
    @Override
    public Callable<Optional<Account>> getAccount(long accountId) {
        return () -> {
            if (accounts.containsKey(accountId)) {
                return accounts.get(accountId);
            } else {
                throw new NoSuchAccountException(accountId);
            }
        };
    }

    /**
     * @param accountId,amount:
     * @return Callable Boolean
     */
    @Override
    public Callable<Boolean> withdraw(long accountId, double amount) {
        return () -> {
            Optional<Account> account = accounts.get(accountId);
            if (account.isPresent()) {
                if (account.get().getBalance() > amount) {
                    account.get().setBalance(account.get().getBalance() - amount);
                    accounts.put(accountId, account);
                    return true;
                } else {
                    throw new IncorrectBalanceException(accountId);
                }
            } else {
                throw new NoSuchAccountException(accountId);
            }
        };

    }

    /**
     * @param accountId,amount,senderAccountId:
     * @return Callable Boolean
     */
    @Override
    public Callable<Boolean> deposit(long accountId, double amount, long senderAccountId) {
        return () -> {
            Optional<Account> receiverAccount = accounts.get(accountId);
            Optional<Account> senderAccount = accounts.get(senderAccountId);
            if (receiverAccount.isPresent() && senderAccount.isPresent()) {
                double currencyDiff = CurrencyUtil.GetCurrencyDiff(receiverAccount.get().getAccountType(), senderAccount.get().getAccountType());
                double newAmout = amount * currencyDiff;
                receiverAccount.get().setBalance(receiverAccount.get().getBalance() + newAmout);
                accounts.put(accountId, receiverAccount);
                return true;
            } else {
                throw new NoSuchAccountException(accountId);
            }
        };
    }

    /**
     * @param accountId:
     * @return Callable Double
     */
    @Override
    public Callable<Double> getBalance(long accountId) {
        return () -> accounts.get(accountId).orElseThrow(() -> new NoSuchAccountException(accountId)).getBalance();
    }

    /**
     * @return Callable Account list
     */
    @Override
    public Callable<List<Account>> getAllAccounts() {
        return () -> accounts.values().stream().map(account -> account.get()).collect(Collectors.toList());
    }


}
