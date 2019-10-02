package ceyhun.erturk.globals;

import ceyhun.erturk.util.GsonProvider;
import ceyhun.erturk.util.LockMechanism;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerGlobal {

    private static Server server;
    private static ExecutorService serverExecutor;

    public static void StartServer() throws Exception {
        if (server == null) {
            server = new Server(Constants.PORT);
            ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.SESSIONS);
            ctx.setContextPath(Constants.BASE_URI);
            server.setHandler(ctx);
            ServletHolder serHol = ctx.addServlet(ServletContainer.class, "/*");
            serHol.setInitParameter("jersey.config.server.provider.packages", Constants.PACKAGE);
            server.start();
            server.join();
        } else {
            server.start();
            server.join();
        }
    }

    //Assume that, server has 4 core cpu
    public static ExecutorService getServerExecutor() {
        if (serverExecutor == null) {
            serverExecutor = Executors.newFixedThreadPool(4);
        }
        return serverExecutor;
    }


    public static void DestroyServer() {
        server.destroy();
        serverExecutor.shutdown();
        ControllerFactory.setBaseController(null);
        DaoFactory.setBaseDao(null);
        LockMechanism.setTransactionLock(null);
        LockMechanism.setAccountLock(null);
        GsonProvider.setGson(null);
        server = null;
        serverExecutor = null;
    }

}
