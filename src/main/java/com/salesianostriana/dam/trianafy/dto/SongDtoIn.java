package com.salesianostriana.dam.trianafy.dto;

import lombok.*;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongDtoIn {
    @Column(nullable = false)
    private String title;
    private String album;
    private String year;
    private Long artistId;
}
