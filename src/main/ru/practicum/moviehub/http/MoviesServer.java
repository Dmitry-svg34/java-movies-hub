package ru.practicum.moviehub.http;
import com.sun.net.httpserver.HttpServer;
import ru.practicum.moviehub.MoviesHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MoviesServer {

    private final HttpServer server;

    public MoviesServer(MoviesHandler handler) {
        try {
            server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/movies", handler);
        } catch (IOException e) {
            throw new RuntimeException("Cannot start server", e);
        }
    }

    public void start() {
        server.start();
        System.out.println("Server started on :8080");
    }

    public void stop() {
        server.stop(0);
        System.out.println("Server stopped");
    }
}

