package com.songify.domain.album;

import com.songify.domain.song.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query("""
            select distinct a from Album a
            left join fetch a.songs songs
            left join fetch a.artists artists
            where a.id = :id""")
    Optional<Album> findAlbumByIdWithSongsAndArtists(Long id);

    @Query("""
            select a from Album a 
            inner join a.artists artists 
            where artists.id = :id
            """)
    Set<Album> findAllAlbumsByArtistId(@Param("id") Long id);

    @Modifying
    @Query("delete from Album a where a.id in :ids")
    int deleteByIdIn(Collection<Long> ids);

    @Query("""
        select distinct s from Album a
        join a.songs s
        where a.id in :id
    """)
    Set<Song> findAllSongInAlbum(Long id);
}
