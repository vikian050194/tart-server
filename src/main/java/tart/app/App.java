package tart.app;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import tart.app.api.Handler;
import tart.app.api.file.*;
import tart.app.api.ping.PingHandler;
import tart.app.api.user.RegistrationHandler;
import tart.app.dependency.*;

public final class App {

    public enum RunMode {
        DEV, PROD
    };

    private final RunMode mode;
    private final HttpServer server;

    public App(int httpPort, RunMode m) throws IOException {
        mode = m;
        if (mode == RunMode.PROD) {
            server = HttpServer.create(new InetSocketAddress("0.0.0.0", httpPort), 0);
        } else {
            server = HttpServer.create(new InetSocketAddress("127.0.0.1", httpPort), 0);
        }
    }

    public void init(DependencyFactory df) {
        var handlers = new ArrayList<Handler>();
        handlers.add(new PingHandler(df.getObjectMapper(),
                df.getErrorHandler()));
        handlers.add(new RegistrationHandler(df.getUserService(), df.getObjectMapper(),
                df.getErrorHandler()));
        handlers.add(new InfoHandler(df.getFileService(), df.getObjectMapper(),
                df.getErrorHandler()));
        handlers.add(new DataHandler(df.getFileService(), df.getObjectMapper(),
                df.getErrorHandler()));

        handlers.stream().forEach(h -> server.createContext(h.url(), h::handle));
    }

    public InetSocketAddress start() {
        server.start();
        // TODO use logger
        System.out.println("Server started on %s".formatted(server.getAddress()));
        return server.getAddress();
    }

    public void stop(int delay) {
        server.stop(delay);
        // TODO use logger
        System.out.println("Server stopped with code %s".formatted(delay));
    }

    public static void main(String[] args) throws IOException {
        var httpPort = Configuration.port();
        var app = new App(httpPort, RunMode.DEV);
        var dependencyFactory = new DefaultDependencyFactory();
        app.init(dependencyFactory);
        app.start();
    }
}
