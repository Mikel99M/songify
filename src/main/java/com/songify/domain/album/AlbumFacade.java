package com.songify.domain.album;

import com.songify.domain.album.dto.AlbumDto;
import com.songify.domain.album.dto.AlbumDtoWithArtistsAndSongs;
import com.songify.domain.album.dto.AlbumRequestDto;
import com.songify.domain.artist.ArtistMapper;
import com.songify.domain.song.SongMapper;
import com.songify.domain.song.dto.SongDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class AlbumFacade {

    private final AlbumRetriever albumRetriever;
    private final AlbumAdder albumAdder;
    private final AlbumDeleter albumDeleter;
    private final AlbumUpdater albumUpdater;

    private final ArtistMapper artistMapper;
    private final SongMapper songMapper;
    private final AlbumMapper albumMapper;

    public AlbumDtoWithArtistsAndSongs addAlbumWithSong(AlbumRequestDto dto) {
        Album saved = albumAdder.addAlbum(dto.songId(), dto.title(), dto.releaseDate());
        return toRichDto(saved);
    }

    public AlbumDtoWithArtistsAndSongs addArtistToAlbum(Long artistId, Long albumId) {
        Album saved = albumAdder.addArtistToAlbum(artistId, albumId);
        return toRichDto(saved);
    }

    public AlbumDtoWithArtistsAndSongs addSongToAlbum(Long songId, Long albumId) {
        Album saved = albumAdder.addSongToAlbum(songId, albumId);
        return toRichDto(saved);
    }

    public Page<AlbumDto> findAllAlbums(Pageable pageable) {
        return albumMapper.mapAlbumsToAlbumDtos(albumRetriever.findAllAlbums(pageable));
    }

    public AlbumDtoWithArtistsAndSongs findAlbumWithArtistsAndSongs(Long albumId) {
        Album album = albumRetriever.findById(albumId);
        return toRichDto(album);
    }

    public AlbumDto updateAlbum(Long albumId, AlbumRequestDto dto) {
        Album album = albumUpdater.updateAlbum(albumId, dto);
        return albumMapper.mapAlbumToAlbumDto(album);
    }

    public void deleteAlbumById(Long albumId) {
        albumDeleter.deleteAlbumByIdWhenNoSongsAssigned(albumId);
    }

    private AlbumDtoWithArtistsAndSongs toRichDto(Album album) {
        return new AlbumDtoWithArtistsAndSongs(
                albumMapper.mapAlbumToAlbumDto(album),
                album.getArtists().stream()
                        .map(artistMapper::mapArtistToArtistDto)
                        .collect(Collectors.toSet()),
                album.getSongs().stream()
                        .map(songMapper::mapSongToSongDto)
                        .collect(Collectors.toSet())
        );
    }

    public Set<SongDto> findAlbumsSongs(final Long albumId) {
        return albumRetriever.findAllSongInAlbum(albumId).stream().map(songMapper::mapSongToSongDto).collect(Collectors.toSet());
    }
}
