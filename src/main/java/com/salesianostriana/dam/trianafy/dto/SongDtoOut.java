package com.salesianostriana.dam.trianafy.dto;

import lombok.*;
/**
 Esta clase se usará para devolver la canción, devolviendo el artista como un string de su nombre
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongDtoOut {
    private Long id;
    private String title;
    private String album;
    private String year;
    private String artist;
}
