package ceyhun.erturk.globals;

import ceyhun.erturk.controller.AccountController;
import ceyhun.erturk.controller.BaseController;
import ceyhun.erturk.controller.TransactionController;
import ceyhun.erturk.controller.impl.AccountControllerImpl;
import ceyhun.erturk.controller.impl.TransactionControllerImpl;

public class ControllerFactory {

    private static BaseController baseController;

    public enum ControlType {
        ACOOUNT,
        TRANSACTION
    }


    public static BaseController getController(ControlType type) {
        if (type == ControlType.ACOOUNT) {
            if (baseController == null) {
                baseController =  AccountControllerImpl.getInstance();
                return baseController;
            } else if (baseController instanceof AccountController) {
                return baseController;
            } else {
                baseController =  AccountControllerImpl.getInstance();
                return baseController;
            }
        } else {
            if (baseController == null) {
                baseController =  TransactionControllerImpl.getInstance();
                return baseController;
            } else if (baseController instanceof TransactionController) {
                return baseController;
            } else {
                baseController =  TransactionControllerImpl.getInstance();
                return baseController;
            }
        }
    }

    // for closing server
    public static void setBaseController(BaseController baseController) {
        ControllerFactory.baseController = baseController;
    }
}
