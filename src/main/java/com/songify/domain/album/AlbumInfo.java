package com.songify.domain.album;
//
import com.songify.domain.genre.Genre;
import com.songify.domain.artist.Artist;
import com.songify.domain.song.Song;

import java.time.Instant;
import java.util.Set;

/**
 * Projection for {@link Album}
 */
public interface AlbumInfo {
    Long getId();

    String getTitle();

    Instant getReleaseDate();

    Set<SongInfo> getSongs();

    Set<ArtistInfo> getArtists();

    /**
     * Projection for {@link Song}
     */
    interface SongInfo {
        Long getId();

        String getName();

        Instant getReleaseDate();

        Long getDuration();

        GenreInfo getGenre();

        /**
         * Projection for {@link Genre}
         */
        interface GenreInfo {
            Long getId();

            String getName();
        }
    }

    /**
     * Projection for {@link Artist}
     */
    interface ArtistInfo {
        Long getId();

        String getName();
    }
}