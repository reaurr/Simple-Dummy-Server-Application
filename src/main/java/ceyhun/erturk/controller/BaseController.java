package ceyhun.erturk.controller;

import ceyhun.erturk.exception.WrongControllerException;

public abstract class BaseController {

    public abstract TransactionController getTransaction()  throws WrongControllerException;

    public abstract AccountController getAccount()  throws WrongControllerException;

}
