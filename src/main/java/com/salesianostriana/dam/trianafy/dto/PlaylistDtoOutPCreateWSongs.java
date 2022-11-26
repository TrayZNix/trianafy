package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.List;

/**
  Esta clase se usará para devolver una playlist. Se diferencia del DTO "PlaylistDtoOutPCreate.class" en que este dto
  si devuelve las canciones de la playlist
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PlaylistDtoOutPCreateWSongs {
    @Id
    private Long id;
    private String name;
    private String description;
    private List<SongDtoOut> songs;
}
