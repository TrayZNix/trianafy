package com.salesianostriana.dam.trianafy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 Esta clase se usará para devolver la canción con el artista como objeto, y no como un String del nombre del artista.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SongDtoModifiedArtist {

    private Long id;
    private String title;
    private String album;
    private String year;
    private ArtistaDtoOut artist;


}
