package com.salesianostriana.dam.trianafy.model;

import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SongMapper {
    @Autowired
    private ArtistRepository repoArtist;

    public Song toSongIn(SongDtoIn dtoSong){
        Optional<Artista> artista = repoArtist.findById(dtoSong.getArtistId());
        if(artista.isEmpty()){
            return null;
        }
        else{
            Artista artistaObj = artista.get();
            return Song.builder().title(dtoSong.getTitle()).album(dtoSong.getAlbum()).year(dtoSong.getYear()).artista(artistaObj).build();
        }
    }
    public SongDtoOut toSongOut(Song song){
        if(song.getArtista() != null){
            return SongDtoOut.builder().id(song.getId()).title(song.getTitle()).album(song.getAlbum()).year(song.getYear()).artist(song.getArtista().getName()).build();
        }
        else{
            return SongDtoOut.builder().id(song.getId()).title(song.getTitle()).album(song.getAlbum()).year(song.getYear()).artist(null).build();
        }

    }
}
