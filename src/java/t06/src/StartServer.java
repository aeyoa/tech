package t06.src;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * Программа подключает сервлет и стартует сервер.
 */
public class StartServer {
    public static void main(String[] args) {
        Server server = new Server(8080);
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(StudServlet.class, "/students/*");
        server.setHandler(handler);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
