package ceyhun.erturk.api;

import ceyhun.erturk.globals.ControllerFactory;
import ceyhun.erturk.model.Transaction;
import ceyhun.erturk.util.GsonProvider;
import ceyhun.erturk.util.LockMechanism;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/transaction")
public class TransactionService {

    @POST
    @Path("/doTransfer")
    public void doTransferAsync(@Suspended final AsyncResponse asyncResponse, String transactionStr) {
        try {
            Transaction transaction = GsonProvider.getGson().fromJson(transactionStr, Transaction.class);
            Optional<Transaction> transactionResult = ControllerFactory.getController(ControllerFactory.ControlType.TRANSACTION).getTransaction().processTransaction(transaction);
            if (transactionResult.isPresent()) {
                transaction = transactionResult.get();
                asyncResponse.resume("Success : " + transaction.toString());
            } else {
                asyncResponse.resume("Failed To Transfer");
            }
        } catch (Exception e) {
            LockMechanism.unLockTransaction();
            e.printStackTrace();
            asyncResponse.resume(e.getMessage());
        }
    }


    @GET
    @Path("/transactions")
    @Produces(MediaType.APPLICATION_JSON)
    public void getTransactionsAsync(@Suspended final AsyncResponse asyncResponse) {
        try {
            List<Transaction> transactions=ControllerFactory.getController(ControllerFactory.ControlType.TRANSACTION).getTransaction().getAllTransactions();
            asyncResponse.resume(transactions.toString());
        } catch (Exception e) {
            LockMechanism.unLockTransaction();
            e.printStackTrace();
            asyncResponse.resume(e.getMessage());
        }
    }

    @GET
    @Path("/{transactionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getTransactionAsync(@Suspended final AsyncResponse asyncResponse, @PathParam("transactionId") long transactionId) {
        try {
            Transaction transaction = ControllerFactory.getController(ControllerFactory.ControlType.TRANSACTION).getTransaction().getTransaction(transactionId);
            asyncResponse.resume(transaction.toString());
        } catch (Exception e) {
            LockMechanism.unLockTransaction();
            e.printStackTrace();
            asyncResponse.resume(e.getMessage());
        }
    }
}
