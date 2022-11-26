package com.salesianostriana.dam.trianafy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

/**
 Esta clase se usará para devolver una playlist.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PlaylistDtoOut {
    @Id
    private Long id;
    private String name;
    private Integer numberOfSongs;
}
