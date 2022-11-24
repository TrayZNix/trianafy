package com.salesianostriana.dam.trianafy.model;

import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SongMapper {
    @Autowired
    private ArtistRepository repoArtist;

    public Song toSong(SongDto dtoSong){
        Optional<Artista> artista = repoArtist.findById(dtoSong.getArtistaId());
        if(artista.isEmpty()){
            return null;
        }
        else{
            Artista artistaObj = artista.get();
            return Song.builder().title(dtoSong.getTitle()).album(dtoSong.getAlbum()).year(dtoSong.getYear()).artista(artistaObj).build();
        }
    }
}
