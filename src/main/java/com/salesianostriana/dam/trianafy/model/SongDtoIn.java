package com.salesianostriana.dam.trianafy.model;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Null;

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
