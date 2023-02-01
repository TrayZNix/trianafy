package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.validation.annotation.NombreArtistaUnico;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 Esta clase se usará para registrar un artista
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ArtistaDtoIn {
    @NombreArtistaUnico(message = "{ArtistaDtoIn.name.unique}")
    @NotNull
    private String name;
}
