package ceyhun.erturk.repository;

import ceyhun.erturk.exception.WrongDaoException;

public abstract class BaseDao {

    public abstract TransactionDao getTransaction() throws WrongDaoException;

    public abstract AccountDao getAccount() throws WrongDaoException;

}
