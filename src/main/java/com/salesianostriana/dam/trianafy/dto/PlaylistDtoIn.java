package com.salesianostriana.dam.trianafy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 Esta clase se usará para registrar una playlist
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PlaylistDtoIn {
    @NotNull
    private String name;
    @NotNull
    private String description;
}
