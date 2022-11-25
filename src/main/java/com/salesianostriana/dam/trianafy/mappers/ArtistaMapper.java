package com.salesianostriana.dam.trianafy.mappers;

import com.salesianostriana.dam.trianafy.dto.ArtistaDto;
import com.salesianostriana.dam.trianafy.model.Artista;
import org.springframework.stereotype.Component;

@Component
public class ArtistaMapper {
    public ArtistaDto toArtistDto(Artista artista){
        return ArtistaDto.builder().id(artista.getId()).artist(artista.getName()).build();
    }
}
