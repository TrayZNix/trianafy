package com.salesianostriana.dam.trianafy.model;

import lombok.*;

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
