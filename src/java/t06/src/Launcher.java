package t06.src;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * Created by arsenykogan on 27/05/14.
 */
public class Launcher {

    public static void main(String[] args) {

        final Server server = new Server(8080);
        final ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(QuadsServer.class, "/squares/*");
        server.setHandler(handler);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
