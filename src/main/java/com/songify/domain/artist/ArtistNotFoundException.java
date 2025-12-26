package com.songify.domain.artist;

public class ArtistNotFoundException extends RuntimeException {
    public ArtistNotFoundException(final String message) {
        super("artist with id " + message + " not found");
    }
}
