package com.songify.domain.song;

import com.songify.domain.album.Album;
import com.songify.domain.genre.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {

    @EntityGraph(attributePaths = "genre")
    Page<Song> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "genre")
    Optional<Song> findById(Long id);

    @Modifying
    @Query("delete from Song s where s.id in :ids")
    int deleteByIdIn(Collection<Long> ids);

    boolean existsByAlbum(Album album);

    boolean existsByGenre(Genre genre);
}

