package feature;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class HappyPathIntegrationTest extends BaseIntegrationTest {

    @Test
    void f() throws Exception {

//        1. when I go to /song then I can see no songs
        mockMvc.perform(get("/songs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(0))
                .andExpect(jsonPath("$.totalPages").value(0))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.last").value(true))
                .andExpect(jsonPath("$.empty").value(true));

//        2. when I post to /songs with Song "test song 1" then Song "test song 1" is returned with id 1
        ResultActions postSongAction = mockMvc.perform(post("/songs")
                        .content("""
                                {
                                    "name": "test song 1",
                                    "releaseDate": "2025-10-12T08:54:34.017Z",
                                    "duration": 0,
                                    "language": "ENGLISH"
                                }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("test song 1")));

        Integer song1Id = JsonPath.read(
                postSongAction.andReturn().getResponse().getContentAsString(), "$.id"
        );

//        3. when I post to /songs with Song "test song 2" then Song "test song 2" is returned with id 2
        ResultActions postSongAction2 = mockMvc.perform(post("/songs")
                        .content("""
                                {
                                    "name": "test song 2",
                                    "releaseDate": "2025-10-12T08:54:34.017Z",
                                    "duration": 0,
                                    "language": "POLISH"
                                }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("test song 2")));

        Integer song2Id = JsonPath.read(
                postSongAction2.andReturn().getResponse().getContentAsString(), "$.id"
        );

//        4. when I go to /genres then I can see no genres
        mockMvc.perform(get("/genres")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("default")));

//        5. when I post to /genre with Genre "rock" then Genre "rock" is returned with id 2
        ResultActions postGenreAction = mockMvc.perform(post("/genres")
                        .content("""
                                {
                                    "name": "rock"
                                }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("rock")));

        Integer genre1Id = JsonPath.read(
                postGenreAction.andReturn().getResponse().getContentAsString(), "$.id"
        );

//        6. when I go to /song/1 then I can see default genre
        mockMvc.perform(get("/songs/%d".formatted(song1Id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(song1Id)))
                .andExpect(jsonPath("$.name", is("test song 1")))
                .andExpect(jsonPath("$.genre", is("default")));

//        7. when I put to /songs/1/genre/1 then Genre with id 2 ("rock") is added to Song with id 1 ("test song 1")
        mockMvc.perform(put("/songs/%d/genre/%d".formatted(song1Id, genre1Id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(song1Id)))
                .andExpect(jsonPath("$.genre", is("rock")));

//        8. when I go to /songs/1 then I can see "rock" genre
        mockMvc.perform(get("/songs/%d".formatted(song1Id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(song1Id)))
                .andExpect(jsonPath("$.name", is("test song 1")))
                .andExpect(jsonPath("$.genre", is("rock")));

//        9. when I put to /song/2/genre/1 then Genre with id 2 ("rock") is added to Song with id 2 ("test song 2")
        mockMvc.perform(put("/songs/%d/genre/%d".formatted(song2Id, genre1Id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(song2Id)))
                .andExpect(jsonPath("$.genre", is("rock")));

//        10. when I go to /albums then I can see no albums
        mockMvc.perform(get("/albums")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").isEmpty());

//        11. when I post to /albums with Album "test album 1" then Album "test album 1" is returned with id 1
        ResultActions albumPostAction = mockMvc.perform(post("/albums")
                        .content("""
                                {
                                  "songId": 1,
                                  "title": "test album 1",
                                  "releaseDate": "2025-11-23T23:24:17.172Z"
                                }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.album.id", is(1)))
                .andExpect(jsonPath("$.album.title", is("test album 1")))
                .andExpect(jsonPath("$.songs[0].id", is(1)))
                .andExpect(jsonPath("$.songs[0].name", is("test song 1")))
                .andExpect(jsonPath("$.songs[0].genre", is("rock")));

        Integer albumId = JsonPath.read(
                albumPostAction.andReturn().getResponse().getContentAsString(), "$.album.id"
        );

//        12. when I go to /albums/1 then I can see no songs added to album
        mockMvc.perform(get("/albums/%d".formatted(albumId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs").isArray())
                .andExpect(jsonPath("$.songs[1]").doesNotExist())
                .andExpect(jsonPath("$.songs[0].id", is(song1Id)));

//        13. when I put to /album/1/songs/2 then Song with id 2 ("test song 2") is added to Album with id 1 ("test album 1")
        mockMvc.perform(put("/albums/%d/songs/%d".formatted(albumId, song2Id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.album.id").value(albumId))
                .andExpect(jsonPath("$.album.title").value("test album 1"))
                .andExpect(jsonPath("$.songs").isArray())
                .andExpect(jsonPath("$.songs.length()").value(2))
                .andExpect(jsonPath("$.songs[*].id", containsInAnyOrder(song1Id, song2Id)))
                .andExpect(jsonPath("$.songs[*].name", containsInAnyOrder("test song 1", "test song 2")))
                .andExpect(jsonPath("$.artists").isArray())
                .andExpect(jsonPath("$.artists").isEmpty());

//        14. when I put to /album/1/songs/2 then Song with id 2 ("test song 2") is added to Album with id 1 ("test album 1")
        mockMvc.perform(put("/albums/%d/songs/%d".formatted(albumId, song2Id))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.album.id", is(albumId)))
                .andExpect(jsonPath("$.album.title", is("test album 1")))
                .andExpect(jsonPath("$.songs[*].id", containsInAnyOrder(song1Id, song2Id)))
                .andExpect(jsonPath("$.songs[*].name", containsInAnyOrder("test song 1", "test song 2")));

//        15. when I go to /album/1/songs then I can see 2 songs (id 1, id 2)
        mockMvc.perform(get("/albums/%d/songs".formatted(albumId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(song1Id, song2Id)));

//        16. when I post to /artist with Artist "test artist 1" then Artist "test artist 1" is returned with id 1
        ResultActions artistPostAction = mockMvc.perform(post("/artists")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "name": "test artist 1"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("test artist 1")));

        Integer artistId = JsonPath.read(
                artistPostAction.andReturn().getResponse().getContentAsString(), "$.id"
        );

//        17. when I put to /albums/1/artists/1 then Artist with id 1 ("test artist 1") is added to Album with id 1 ("test album 1")
        mockMvc.perform(put("/albums/%d/artists/%d".formatted(albumId, artistId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.album.id", is(albumId)))
                .andExpect(jsonPath("$.artists[*].id", containsInAnyOrder(artistId)))
                .andExpect(jsonPath("$.artists[*].name", containsInAnyOrder("test artist 1")))
                .andExpect(jsonPath("$.songs[*].name", containsInAnyOrder("test song 1", "test song 2")));

    }

}
