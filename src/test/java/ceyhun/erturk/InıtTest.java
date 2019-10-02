package ceyhun.erturk;

import ceyhun.erturk.globals.DaoFactory;
import ceyhun.erturk.globals.ServerGlobal;
import ceyhun.erturk.repository.AccountDao;
import ceyhun.erturk.util.TestUsers;
import org.junit.rules.ExternalResource;

public class InıtTest extends ExternalResource {

    @Override
    protected void before() throws Exception {
        TestUsers.createTestAccounts(DaoFactory.getBaseDao(DaoFactory.DaoType.ACOOUNT).getAccount());
        ServerGlobal.StartServer();
    }

    @Override
    protected void after()   {
        ServerGlobal.DestroyServer();
    }
}
