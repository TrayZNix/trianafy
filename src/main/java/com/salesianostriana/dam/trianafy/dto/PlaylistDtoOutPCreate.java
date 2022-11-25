package com.salesianostriana.dam.trianafy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PlaylistDtoOutPCreate {
    @Id
    private Long id;
    private String name;
    private String description;
}
