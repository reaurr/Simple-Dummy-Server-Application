package ceyhun.erturk.globals;

import ceyhun.erturk.repository.AccountDao;
import ceyhun.erturk.repository.BaseDao;
import ceyhun.erturk.repository.TransactionDao;
import ceyhun.erturk.repository.impl.AccountDaoImpl;
import ceyhun.erturk.repository.impl.TransactionDaoImpl;

public class DaoFactory {

    private static BaseDao baseDao;

    public enum DaoType {
        ACOOUNT,
        TRANSACTION
    }


    public static BaseDao getBaseDao(DaoType type) {
        if (type == DaoType.ACOOUNT) {
            if (baseDao == null) {
                baseDao =  AccountDaoImpl.getInstance();
                return baseDao;
            } else if (baseDao instanceof AccountDao) {
                return baseDao;
            } else {
                baseDao =  AccountDaoImpl.getInstance();
                return baseDao;
            }
        } else {
            if (baseDao == null) {
                baseDao =  TransactionDaoImpl.getInstance();
                return baseDao;
            } else if (baseDao instanceof TransactionDao) {
                return baseDao;
            } else {
                baseDao =  TransactionDaoImpl.getInstance();
                return baseDao;
            }
        }
    }

    // for closing server
    public static void setBaseDao(BaseDao baseDao) {
        DaoFactory.baseDao = baseDao;
    }
}
