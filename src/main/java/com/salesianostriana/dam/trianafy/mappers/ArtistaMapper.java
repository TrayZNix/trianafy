package com.salesianostriana.dam.trianafy.mappers;

import com.salesianostriana.dam.trianafy.dto.ArtistaDtoIn;
import com.salesianostriana.dam.trianafy.dto.ArtistaDtoOut;
import com.salesianostriana.dam.trianafy.model.Artista;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArtistaMapper {
    @Autowired
    private ArtistRepository repoArtistas;
    public ArtistaDtoOut toArtistDto(Artista artista){
        if(artista == null){
            return null;
        }
        else return ArtistaDtoOut.builder().id(artista.getId()).artist(artista.getName()).build();
    }

    public Artista toArtist(ArtistaDtoIn artista){
        return Artista.builder().name(artista.getName()).build();
    }
}
