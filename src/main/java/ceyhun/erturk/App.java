package ceyhun.erturk;

import ceyhun.erturk.globals.DaoFactory;
import ceyhun.erturk.globals.ServerGlobal;
import ceyhun.erturk.util.TestUsers;

public class App {
    public static void main(String[] args) {
        try {
            TestUsers.createTestAccounts(DaoFactory.getBaseDao(DaoFactory.DaoType.ACOOUNT).getAccount());
            ServerGlobal.StartServer();
        } catch (Exception e) {
            e.printStackTrace();
            ServerGlobal.DestroyServer();
        }
    }
}