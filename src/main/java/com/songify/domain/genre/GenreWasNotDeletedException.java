package com.songify.domain.genre;

public class GenreWasNotDeletedException extends RuntimeException {
    public GenreWasNotDeletedException(final String message) {
        super(message);
    }
}
