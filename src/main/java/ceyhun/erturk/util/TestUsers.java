package ceyhun.erturk.util;

import ceyhun.erturk.globals.ControllerFactory;
import ceyhun.erturk.model.Account;
import ceyhun.erturk.repository.AccountDao;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.stream.IntStream;

public class TestUsers {
    public static void createTestAccounts(AccountDao accountDao) {
        IntStream.range(1, 500).mapToObj(i -> new Account(Counters.getAccountIdCounter().incrementAndGet(), Double.parseDouble(RandomStringUtils.randomNumeric(2, 5)), RandomStringUtils.randomAlphabetic(3, 8), RandomStringUtils.randomAlphabetic(8, 15), CurrencyUtil.CurrencyType.DOLLAR)).forEach(account -> {
            try {
                account = ControllerFactory.getController(ControllerFactory.ControlType.ACOOUNT).getAccount().createAccount(account);
                accountDao.createAccount(account);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
