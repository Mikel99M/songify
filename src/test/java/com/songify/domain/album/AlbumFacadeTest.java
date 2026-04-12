package com.songify.domain.album;

import com.songify.domain.album.dto.AlbumDto;
import com.songify.domain.album.dto.AlbumDtoWithArtistsAndSongs;
import com.songify.domain.album.dto.AlbumRequestDto;
import com.songify.domain.artist.ArtistMapper;
import com.songify.domain.song.SongMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlbumFacadeTest {

    @Mock
    AlbumRetriever albumRetriever;
    @Mock
    AlbumAdder albumAdder;
    @Mock
    AlbumDeleter albumDeleter;
    @Mock
    AlbumUpdater albumUpdater;
    @Mock
    AlbumMapper albumMapper;
    @Mock
    ArtistMapper artistMapper;
    @Mock
    SongMapper songMapper;

    @InjectMocks
    AlbumFacade albumFacade;

    @Test
    void addAlbumWithSong_should_delegate_to_AlbumAdder() {
        // given
        Instant now = Instant.parse("2026-04-12T20:00:00Z");
        AlbumRequestDto req = new AlbumRequestDto(1L, "title", now);

        Album album = Album.builder()
                .id(10L)
                .title("title")
                .songs(new HashSet<>())
                .artists(new HashSet<>())
                .build();

        AlbumDto albumDto = new AlbumDto(10L, "title");
        AlbumDtoWithArtistsAndSongs expected =
                new AlbumDtoWithArtistsAndSongs(albumDto, Set.of(), Set.of());

        when(albumAdder.addAlbum(eq(1L), eq("title"), any(Instant.class)))
                .thenReturn(album);

        when(albumMapper.mapAlbumToAlbumDto(album)).thenReturn(albumDto);

        // when
        AlbumDtoWithArtistsAndSongs result = albumFacade.addAlbumWithSong(req);

        // then
        assertThat(result).isEqualTo(expected);
        verify(albumAdder).addAlbum(req.songId(), req.title(), req.releaseDate());
    }

    @Test
    void addArtistToAlbum_should_delegate_to_AlbumAdder() {
        // given
        long artistId = 5L;
        long albumId = 10L;

        Album album = Album.builder().id(albumId).title("abc").build();
        AlbumDto albumDto = new AlbumDto(albumId, "abc");

        AlbumDtoWithArtistsAndSongs expected =
                new AlbumDtoWithArtistsAndSongs(albumDto, Set.of(), Set.of());

        when(albumAdder.addArtistToAlbum(artistId, albumId)).thenReturn(album);
        when(albumMapper.mapAlbumToAlbumDto(album)).thenReturn(albumDto);

        // when
        AlbumDtoWithArtistsAndSongs result = albumFacade.addArtistToAlbum(artistId, albumId);

        // then
        assertThat(result).isEqualTo(expected);
        verify(albumAdder).addArtistToAlbum(artistId, albumId);
    }

    @Test
    void addSongToAlbum_should_delegate_to_AlbumAdder() {
        // given
        long songId = 5L;
        long albumId = 10L;

        Album album = Album.builder().id(albumId).title("abc").build();
        AlbumDto albumDto = new AlbumDto(albumId, "abc");

        AlbumDtoWithArtistsAndSongs expected =
                new AlbumDtoWithArtistsAndSongs(albumDto, Set.of(), Set.of());

        when(albumAdder.addSongToAlbum(songId, albumId)).thenReturn(album);
        when(albumMapper.mapAlbumToAlbumDto(album)).thenReturn(albumDto);

        // when
        AlbumDtoWithArtistsAndSongs result = albumFacade.addSongToAlbum(songId, albumId);

        // then
        assertThat(result).isEqualTo(expected);
        verify(albumAdder).addSongToAlbum(songId, albumId);
    }

    @Test
    void findAllAlbums_should_delegate_to_AlbumRetriever() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        AlbumInfo album1 = mock(AlbumInfo.class);
        AlbumInfo album2 = mock(AlbumInfo.class);

        Page<AlbumInfo> expectedPage = new PageImpl<>(List.of(album1, album2), pageable, 2);

        when(albumRetriever.findAllAlbumsProjected(pageable)).thenReturn(expectedPage);

        // when
        Page<AlbumInfo> result = albumFacade.findAllAlbums(pageable);

        // then
        assertThat(result).isEqualTo(expectedPage);
        verify(albumRetriever).findAllAlbumsProjected(pageable);
    }

    @Test
    void findAlbumWithArtistsAndSongs_should_delegate_to_AlbumRetriever() {
        long albumId = 5L;

        Album album = Album.builder().id(albumId).title("t").build();
        AlbumDto albumDto = new AlbumDto(albumId, "t");

        AlbumDtoWithArtistsAndSongs expected =
                new AlbumDtoWithArtistsAndSongs(albumDto, Set.of(), Set.of());

        when(albumRetriever.findById(albumId)).thenReturn(album);
        when(albumMapper.mapAlbumToAlbumDto(album)).thenReturn(albumDto);

        // when
        AlbumDtoWithArtistsAndSongs result = albumFacade.findAlbumWithArtistsAndSongs(albumId);

        // then
        assertThat(result).isEqualTo(expected);
        verify(albumRetriever).findById(albumId);
    }

    @Test
    void updateAlbum_should_delegate_to_AlbumUpdater() {
        // given
        long albumId = 10L;

        AlbumRequestDto req = new AlbumRequestDto(1L, "updated", Instant.now());
        Album album = Album.builder().id(albumId).title("updated").build();
        AlbumDto expectedDto = new AlbumDto(albumId, "updated");

        when(albumUpdater.updateAlbum(albumId, req)).thenReturn(album);
        when(albumMapper.mapAlbumToAlbumDto(album)).thenReturn(expectedDto);

        // when
        AlbumDto result = albumFacade.updateAlbum(albumId, req);

        // then
        assertThat(result).isEqualTo(expectedDto);
        verify(albumUpdater).updateAlbum(albumId, req);
    }

    @Test
    void deleteAlbumById_should_delegate_to_AlbumDeleter() {
        // given
        long albumId = 100L;

        // when
        albumFacade.deleteAlbumById(albumId);

        // then
        verify(albumDeleter).deleteAlbumByIdWhenNoSongsAssigned(albumId);
    }
}
