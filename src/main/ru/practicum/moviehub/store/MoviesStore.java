package ru.practicum.moviehub.store;

import ru.practicum.moviehub.model.Movie;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MoviesStore {
    private final Map<String, Movie> storage = new ConcurrentHashMap<>();

    public Collection<Movie> getAll() {
        return storage.values();
    }

    public Optional<Movie> getById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    public Movie add(Movie movie) {
        storage.put(movie.id(), movie);
        return movie;
    }

    public boolean delete(String id) {
        return storage.remove(id) != null;
    }

    /* вызываем в @BeforeEach – «чистый лист» для каждого теста */
    public void clear() {
        storage.clear();
    }
}