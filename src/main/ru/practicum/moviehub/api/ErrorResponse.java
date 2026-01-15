package ru.practicum.moviehub.api;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class ErrorResponse {
    private final String code;
    private final String message;
    private final String details;

    public ErrorResponse(String code, String message, String details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }

    public String toJson() {
        String d = details == null ? "null" : '"' + escape(details) + '"';
        return "{\"code\":\"" + escape(code) +
                "\",\"message\":\"" + escape(message) +
                "\",\"details\":" + d + '}';
    }

    public void send(HttpExchange ex, int httpStatus) throws IOException {
        byte[] raw = toJson().getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        ex.sendResponseHeaders(httpStatus, raw.length);
        ex.getResponseBody().write(raw);
        ex.close();
    }

    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}