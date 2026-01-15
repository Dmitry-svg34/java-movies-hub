package ru.practicum.moviehub.model;

public record Movie(
        String id,
        String title,
        String director,
        double ticketPrice,
        java.time.LocalDate releaseDate) {

    @Override
    public String toString() {
        return String.format(
                "{\"id\":\"%s\",\"title\":\"%s\",\"director\":\"%s\",\"ticketPrice\":%.2f,\"releaseDate\":\"%s\"}",
                escape(id), escape(title), escape(director), ticketPrice, releaseDate);
    }

    private String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
