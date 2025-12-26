package com.songify.domain.album;

import com.songify.domain.artist.Artist;
import com.songify.domain.song.Song;
import com.songify.domain.util.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Album extends BaseEntity {

    public Album(final String title, final Instant releaseDate) {
        this.title = title;
        this.releaseDate = releaseDate;
    }

    @Id
    @GeneratedValue(generator = "album_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "album_id_seq",
            sequenceName = "album_id_seq",
            allocationSize = 1
    )
    private Long id;

    private String title;

    private Instant releaseDate;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "album_id")
    private Set<Song> songs = new HashSet<>();

    @ManyToMany(mappedBy = "albums")
    private Set<Artist> artists = new HashSet<>();

    public void addSongToAlbum(final Song song) {
        songs.add(song);
    }

    public void removeArtist(Artist artist) {
        artists.remove(artist);
        artist.removeAlbum(this);
    }

    public void addArtist(final Artist artist) {
        artists.add(artist);
    }

    @Builder
    Album(final Long id, final String title, final Instant releaseDate, final Set<Song> songs, final Set<Artist> artists) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        if (artists != null) this.artists = artists;
        if (songs != null) this.songs = songs;
    }
}
