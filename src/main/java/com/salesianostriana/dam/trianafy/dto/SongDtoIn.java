package com.salesianostriana.dam.trianafy.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
  Esta clase se usará para registrar una canción.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongDtoIn {
    @NotNull
    private String title;
    @NotNull
    private String album;
    @NotNull
    private String year;
    private Long artistId;
}
