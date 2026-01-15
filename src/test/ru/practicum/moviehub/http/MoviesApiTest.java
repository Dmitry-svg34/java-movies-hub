package ru.practicum.moviehub.http;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.moviehub.MoviesHandler;
import ru.practicum.moviehub.store.MoviesStore;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoviesApiTest {

    private static final String BASE = "http://localhost:8080";
    private static MoviesServer server;
    /* 1. Единое хранилище на весь класс */
    private static final MoviesStore store = new MoviesStore();
    private static HttpClient client;

    @BeforeAll
    static void beforeAll() {
        MoviesHandler handler = new MoviesHandler(store);
        server = new MoviesServer(handler);
        server.start();
        client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    @AfterAll
    static void afterAll() {
        server.stop();
    }

    @BeforeEach
    void cleanStore() {
        store.clear();          // 2. «чистый лист» перед каждым тестом
    }

    /* уже существующий тест – всё ещё проходит */
    @Test
    void getMovies_whenEmpty_returnsEmptyArray() throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE + "/movies"))
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();

        HttpResponse<String> resp =
                client.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        assertEquals(200, resp.statusCode());
        assertEquals("application/json; charset=UTF-8",
                resp.headers().firstValue("Content-Type").orElse(""));
        assertEquals("[]", resp.body().trim());   // пустой массив – зелёный тест

    }
}