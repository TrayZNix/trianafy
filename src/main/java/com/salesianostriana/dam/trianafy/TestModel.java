package com.salesianostriana.dam.trianafy;

import com.salesianostriana.dam.trianafy.model.Artista;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestModel {

    private final ArtistService artistService;
    private final SongService songService;
    private final PlaylistService playlistService;

    @PostConstruct
    public void run() {

        Artista a1 = Artista.builder()
                .name("Joaquín Sabina")
                .build();

        Artista a2 = Artista.builder()
                .name("Dua Lipa")
                .build();

        Artista a3 = Artista.builder()
                .name("Metallica")
                .build();

        Artista a4 = Artista.builder()
                .name("Rels B")
                .build();

        Artista a5 = Artista.builder()
                .name("Toteking")
                .build();

        List<Artista> artistaList = List.of(a1, a2, a3, a4, a5);

        artistaList.forEach(artistService::add);

        Song s1a1 = Song.builder()
                .album("19 días y 500 noches")
                .artist(a1)
                .year("1999")
                .title("19 días y 500 noches")
                .build();

        Song s2a1 = Song.builder()
                .album("19 días y 500 noches")
                .artist(a1)
                .year("1999")
                .title("Donde habita el olvido")
                .build();

        Song s3a1 = Song.builder()
                .album("19 días y 500 noches")
                .artist(a1)
                .year("1999")
                .title("A mis cuarenta y diez")
                .build();

        Song s1a2 = Song.builder()
                .album("Future Nostalgia")
                .artist(a2)
                .year("2019")
                .title("Don't Start Now")
                .build();

        Song s2a2 = Song.builder()
                .album("Future Nostalgia")
                .artist(a2)
                .year("2021")
                .title("Love Again")
                .build();

        Song s1a3 = Song.builder()
                .album("Metallica")
                .artist(a3)
                .year("1991")
                .title("Enter Sandman")
                .build();

        Song s2a3 = Song.builder()
                .album("Metallica")
                .artist(a3)
                .year("1991")
                .title("Unforgiven")
                .build();

        Song s3a3 = Song.builder()
                .album("Metallica")
                .artist(a3)
                .year("1991")
                .title("Nothing Else Matters")
                .build();

        Song s1a4 = Song.builder()
                .album("A mi")
                .artist(a4)
                .year("2019")
                .title("A mi")
                .build();

        Song s2a4 = Song.builder()
                .album("La última canción")
                .artist(a4)
                .year("2020")
                .title("La isla EP")
                .build();

        Song s1a5 = Song.builder()
                .album("RapSinCorte")
                .artist(a5)
                .year("2021")
                .title("Rap sin corte L")
                .build();

        Song s2a5 = Song.builder()
                .album("Dios bendiga este EP")
                .artist(a5)
                .year("2022")
                .title("Man on the moon")
                .build();

        List<Song> songList = List.of(
                s1a1, s2a1, s3a1,
                s1a2, s2a2,
                s1a3, s2a3, s3a3,
                s1a4, s2a4,
                s1a5, s2a5
        );

        songList.forEach(songService::add);


        Playlist p1 = Playlist.builder()
                .name("Random")
                .description("Una lista muy loca")
                .build();

        Playlist p2 = Playlist.builder()
                .name("Rap y latino")
                .description("La musica de los jóvenes :)")
                .build();

        playlistService.add(p1);
        playlistService.add(p2);

        p1.addSong(s1a3);
        p1.addSong(s2a2);
        p1.addSong(s1a3);
        p1.addSong(s3a3);

        playlistService.edit(p1);

        p2.addSong(s1a4);
        p2.addSong(s2a5);
        p2.addSong(s1a5);
        p2.addSong(s2a4);

        playlistService.edit(p2);

        System.out.println(p1);
        System.out.println(p2);









    }

}
