package ru.practicum.moviehub.http;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class BaseHttpHandler implements HttpHandler {
    protected static final String CT_JSON = "application/json; charset=UTF-8";

    protected final void sendJson(HttpExchange ex, int status, String json) throws IOException {
        byte[] raw = json.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", CT_JSON);
        ex.sendResponseHeaders(status, raw.length);
        ex.getResponseBody().write(raw);
        ex.close();
    }

    protected final void sendNoContent(HttpExchange ex) throws IOException {
        ex.getResponseHeaders().set("Content-Type", CT_JSON);
        ex.sendResponseHeaders(204, -1);
        ex.close();
    }

    /* JSON-массив без внешней библиотеки */
    protected final String toJsonArray(java.util.Collection<?> items) {
        if (items.isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (Object o : items) sb.append(o.toString()).append(',');
        sb.setCharAt(sb.length() - 1, ']');
        return sb.toString();
    }
}

