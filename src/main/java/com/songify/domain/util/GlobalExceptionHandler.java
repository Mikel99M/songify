package com.songify.domain.util;

import com.songify.domain.album.AlbumNotFoundException;
import com.songify.domain.artist.ArtistNotFoundException;
import com.songify.domain.genre.GenreNotFoundException;
import com.songify.domain.genre.GenreWasNotDeletedException;
import com.songify.domain.song.SongNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(SongNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSongNotFound(
            SongNotFoundException ex,
            WebRequest request) {

        ErrorResponse body = new ErrorResponse(
                "NOT_FOUND",
                ex.getMessage(),
                Instant.now(),
                request.getDescription(false).replace("uri=", "")
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(GenreNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGenreNotFound(
            GenreNotFoundException ex,
            WebRequest request) {

        ErrorResponse body = new ErrorResponse(
                "NOT_FOUND",
                ex.getMessage(),
                Instant.now(),
                request.getDescription(false).replace("uri=", "")
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(ArtistNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleArtistNotFound(
            ArtistNotFoundException ex,
            WebRequest request) {

        ErrorResponse body = new ErrorResponse(
                "NOT_FOUND",
                ex.getMessage(),
                Instant.now(),
                request.getDescription(false).replace("uri=", "")
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(AlbumNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAlbumNotFound(
            AlbumNotFoundException ex,
            WebRequest request) {

        ErrorResponse body = new ErrorResponse(
                "NOT_FOUND",
                ex.getMessage(),
                Instant.now(),
                request.getDescription(false).replace("uri=", "")
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(GenreWasNotDeletedException.class)
    public ResponseEntity<ErrorResponse> handleGenreWasNotDeleted(
            GenreWasNotDeletedException ex,
            WebRequest request) {

        ErrorResponse body = new ErrorResponse(
                HttpStatus.CONFLICT.name(),
                ex.getMessage(),
                Instant.now(),
                request.getDescription(false).replace("uri=", "")
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body);
    }

    public record ErrorResponse(
            String error,
            String message,
            Instant timestamp,
            String path
    ) {
    }

}
