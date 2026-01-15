package ru.practicum.moviehub;
import ru.practicum.moviehub.api.ErrorResponse;
import ru.practicum.moviehub.http.BaseHttpHandler;
import com.sun.net.httpserver.HttpExchange;
import ru.practicum.moviehub.store.MoviesStore;

import java.io.IOException;

public class MoviesHandler extends BaseHttpHandler {

    private final MoviesStore store;

    public MoviesHandler(MoviesStore store) {
        this.store = store;
    }

    @Override
    public void handle(HttpExchange ex) throws IOException {
        if ("GET".equalsIgnoreCase(ex.getRequestMethod())) {
            /* возвращаем реальные фильмы, а не хардкод */
            String json = toJsonArray(store.getAll());
            sendJson(ex, 200, json);
            return;
        }
        new ErrorResponse("405", "Method Not Allowed", null).send(ex, 405);
    }
}