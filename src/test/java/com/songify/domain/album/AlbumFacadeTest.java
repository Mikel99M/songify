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
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    ArtistMapper artistMapper;
    @Mock
    SongMapper songMapper;
    @Mock
    AlbumMapper albumMapper;

    @InjectMocks
    AlbumFacade albumFacade;

    @Test
    void addAlbumWithSong_should_delegate_to_AlbumAdder() {
        // given
        AlbumRequestDto req = new AlbumRequestDto(1L, "title", Instant.now());
        Album album = Album.builder().id(10L).title("title").build();

        AlbumDto albumDto = new AlbumDto(10L, "title");
        AlbumDtoWithArtistsAndSongs expected =
                new AlbumDtoWithArtistsAndSongs(albumDto, Set.of(), Set.of());

        when(albumAdder.addAlbum(req.songId(), req.title(), req.releaseDate()))
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
        Pageable pageable = PageRequest.of(0, 10);

        List<Album> albumList = List.of(
                Album.builder().id(1L).title("A").build(),
                Album.builder().id(2L).title("B").build()
        );

        Page<Album> albums = new PageImpl<>(albumList, pageable, albumList.size());

        List<AlbumDto> expectedDtosList = List.of(
                new AlbumDto(1L, "A"),
                new AlbumDto(2L, "B")
        );

        Page<AlbumDto> expectedDtos = new PageImpl<>(expectedDtosList, pageable, expectedDtosList.size());

        when(albumRetriever.findAllAlbums(pageable)).thenReturn(albums);
        when(albumMapper.mapAlbumsToAlbumDtos(albums)).thenReturn(expectedDtos);

        // when
        Page<AlbumDto> result = albumFacade.findAllAlbums(pageable);

        // then
        assertThat(result).isEqualTo(expectedDtos);
        verify(albumRetriever).findAllAlbums(pageable);
        verify(albumMapper).mapAlbumsToAlbumDtos(albums);
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
        long albumId = 100L;

        // when
        albumFacade.deleteAlbumById(albumId);

        // then
        verify(albumDeleter).deleteAlbumByIdWhenNoSongsAssigned(albumId);
    }
}
