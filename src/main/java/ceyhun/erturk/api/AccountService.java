package ceyhun.erturk.api;

import ceyhun.erturk.exception.NoSuchAccountException;
import ceyhun.erturk.globals.ControllerFactory;
import ceyhun.erturk.util.Counters;
import ceyhun.erturk.model.Account;
import ceyhun.erturk.util.GsonProvider;
import ceyhun.erturk.util.LockMechanism;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/account")
public class AccountService {

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public void createAccountAsync(@Suspended final AsyncResponse asyncResponse, String accountStr) {
        try {
            Account account = GsonProvider.getGson().fromJson(accountStr, Account.class);
            account.setAccountId(Counters.getAccountIdCounter().incrementAndGet());
            account = ControllerFactory.getController(ControllerFactory.ControlType.ACOOUNT).getAccount().createAccount(account);
            asyncResponse.resume(account.toString());
        } catch (Exception e) {
            LockMechanism.unLockAccount();
            e.printStackTrace();
            asyncResponse.resume(e.getMessage());
        }
    }

    @GET
    @Path("/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getAccountAsync(@Suspended final AsyncResponse asyncResponse, @PathParam("accountId") long accountId) {
        try {
            asyncResponse.resume(Optional.of(ControllerFactory.getController(ControllerFactory.ControlType.ACOOUNT).getAccount().getAccount(accountId).toString()).orElseThrow(() -> new NoSuchAccountException(accountId)));
        } catch (Exception e) {
            LockMechanism.unLockAccount();
            e.printStackTrace();
            asyncResponse.resume(e.getMessage());
        }

    }

    @GET
    @Path("balance/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getBalanceAsync(@Suspended final AsyncResponse asyncResponse, @PathParam("accountId") long accountId) {
        try {
            Double balance = ControllerFactory.getController(ControllerFactory.ControlType.ACOOUNT).getAccount().getBalance(accountId);
            asyncResponse.resume(balance.toString());
        } catch (Exception e) {
            LockMechanism.unLockAccount();
            e.printStackTrace();
            asyncResponse.resume(e.getMessage());
        }
    }

    @GET
    @Path("/accounts")
    @Produces(MediaType.APPLICATION_JSON)
    public void getTransactionsAsync(@Suspended final AsyncResponse asyncResponse) {
        try {
            List<Account> accounts = ControllerFactory.getController(ControllerFactory.ControlType.ACOOUNT).getAccount().getAllAccounts();
            asyncResponse.resume(accounts.toString());
        } catch (Exception e) {
            LockMechanism.unLockAccount();
            e.printStackTrace();
            asyncResponse.resume(e.getMessage());
        }
    }
}
