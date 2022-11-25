package com.salesianostriana.dam.trianafy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SongDtoModifiedArtist {

    private Long id;
    private String title;
    private String album;
    private String year;
    private ArtistaDto artist;


}
