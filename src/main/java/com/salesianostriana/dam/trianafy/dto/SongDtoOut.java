package com.salesianostriana.dam.trianafy.dto;

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
