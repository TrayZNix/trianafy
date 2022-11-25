package com.salesianostriana.dam.trianafy.dto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ArtistaDtoOut {
    @Hidden()
    private Long id;
    @NotNull
    private String artist;
}
