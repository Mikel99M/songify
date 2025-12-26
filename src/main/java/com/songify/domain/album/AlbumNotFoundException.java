package com.songify.domain.album;

public class AlbumNotFoundException extends RuntimeException {
    public AlbumNotFoundException(final String message) {
        super(message);
    }
}
