package com.songify.domain.artist;

import com.songify.domain.artist.dto.ArtistPatchRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
class ArtistUpdater {

    private final ArtistRetriever artistRetriever;

    @Transactional
    Artist updateName(Long id, String name) {
        Artist artist = artistRetriever.findById(id);
        artist.setName(name);
        return artist;
    }

    @Transactional
    Artist patch(Long id, ArtistPatchRequestDto req) {
        Artist artist = artistRetriever.findById(id);

        if (req.name() != null) artist.setName(req.name());

        return artist;
    }
}

