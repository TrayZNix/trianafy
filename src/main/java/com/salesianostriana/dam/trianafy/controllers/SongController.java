package com.salesianostriana.dam.trianafy.controllers;

import com.salesianostriana.dam.trianafy.dto.ArtistaDtoIn;
import com.salesianostriana.dam.trianafy.dto.SongDtoIn;
import com.salesianostriana.dam.trianafy.dto.SongDtoModifiedArtist;
import com.salesianostriana.dam.trianafy.dto.SongDtoOut;
import com.salesianostriana.dam.trianafy.exception.SongNotFoundException;
import com.salesianostriana.dam.trianafy.mappers.ArtistaMapper;
import com.salesianostriana.dam.trianafy.mappers.SongMapper;
import com.salesianostriana.dam.trianafy.model.*;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.repos.PlaylistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Tags(@Tag(name = "Canciones", description = "SongController"))
@RequestMapping("/song")
public class SongController {
    @Autowired
    private SongMapper songMapper;
    @Autowired
    private SongService songService;
    @Autowired
    private PlaylistService playlistService;
    @Autowired
    private ArtistService artistService;

    @Operation(summary = "Devuelve todas las canciones guardadas en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se encontraron una o más canciones",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SongDtoOut.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                  "id": 1,
                                                  "title": "Crystalline",
                                                  "album": "Nocturnal",
                                                  "year": "2017",
                                                  "artist": "The Midnight"
                                                },
                                                {
                                                  "id": 2,
                                                  "title": "I remember",
                                                  "album": "I remember",
                                                  "year": "2009",
                                                  "artist": "deadmau5"
                                                }
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró ninguna canción",
                    content = {@Content})
    })
    @GetMapping()
    public ResponseEntity<List<SongDtoOut>> getSongs(){
        List<Song> canciones = songService.findAll();
        List<SongDtoOut> cancionesDto = new ArrayList<SongDtoOut>();
        canciones.forEach(c -> cancionesDto.add(songMapper.toSongOut(c)));
        if(cancionesDto.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(cancionesDto);
    }
    @Operation(summary = "Devuelve una canción según el id proporcionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se encontró la canción correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SongDtoModifiedArtist.class),
                            examples = {@ExampleObject(
                                    value = """
                                   {
                                   "id": 1,
                                   "title": "Jeremías 17-5",
                                   "album": "Muerte",
                                   "year": "2012"
                                   , "artist": {
                                                "id": 1,
                                                "name": "Canserbero"
                                                }
                                   }
                                   """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró ningún artista con el id proporcionado",
                    content = {@Content})
    })
    @GetMapping("/{idCancion}")
    public  ResponseEntity<SongDtoModifiedArtist> getSongConcreta( @Parameter(description = "Id de la canción a buscar")@PathVariable Long idCancion){
        Optional<Song> s = songService.findById(idCancion);
        if(s.isPresent()){
        SongDtoModifiedArtist songToReturn = songMapper.toSongDtoModifiedArtist(s.get());
        return ResponseEntity.status(HttpStatus.OK).body(songToReturn);
        }
        else{
            throw new SongNotFoundException();
        }
    }
    @Operation(summary = "Crea una canción nueva según el cuerpo proporcionado")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la canción a crear",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = SongDtoIn.class),
                    examples = {@ExampleObject(
                           value = """
                                   {
                                      "title": "Y Nos Dieron las Diez",
                                      "album": "Física y Química",
                                      "year": "1992",
                                      "artistId": 1
                                   }
                                   """
                    )}
            )})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se creó la canción correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SongDtoOut.class),
                            examples = {@ExampleObject(
                                    value = """
                                         {
                                          "id": 1,
                                          "title": "Y Nos Dieron las Diez",
                                          "album": "Física y Química",
                                          "year": "1992",
                                          "artist": "Joaquín Sabina"
                                        }
                                    """
                            )})}),
            @ApiResponse(responseCode = "400",
                    description = "Falta algún parámetro o el tipo de uno o más parámetros no es correcto",
                    content = {@Content})
    })
    @PostMapping()
    public ResponseEntity<SongDtoOut> createSong(@RequestBody SongDtoIn song){
        //TODO preguntar si validar asi los datos es correcto
        if(song.getTitle() == null || song.getYear() == null || song.getAlbum() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            Song s = songMapper.toSongIn(song);
            if(s == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            else{
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(songMapper
                                .toSongOut(songService
                                .add(s)));
            }
        }
    }
    @Operation(summary = "Modifica una canción según el id y cuerpo proporcionado")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Nuevos de la canción",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = SongDtoIn.class),
                    examples = {@ExampleObject(
                            value = """
                                    {
                                      "title": "Levitating",
                                      "album": "Future Nostalgia",
                                      "year": "2020",
                                      "artistId": 2
                                   }
                                    """
                    )})})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se creó la canción correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SongDtoOut.class),
                            examples = {@ExampleObject(
                                    value = """
                                    {
                                        "title": "Empire State Of Mind",
                                         "album": "The Blueprint 3",
                                         "year": "2009",
                                         "artist": "JAY-Z"
                                     }
                                    """
                            )})}),
            @ApiResponse(responseCode = "400",
                    description = "Falta algún parámetro o el tipo de uno o más parámetros no es correcto",
                    content = {@Content(mediaType = "application/json")})
    })
    @PutMapping("/{idCancion}")
    public ResponseEntity<SongDtoOut> updateSong(@RequestBody SongDtoIn song, @Parameter(description = "Id de la canción a editar") @PathVariable Long idCancion){
        Optional<Song> optSong = songService.findById(idCancion);
        if(optSong.isPresent()){
            Song inDbSong = optSong.get();
            //Validación
            if(song.getTitle() == null || song.getYear() == null || song.getAlbum() == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            else{
                if(song.getArtistId() != null){
                    Artista artista = artistService.findById(song.getArtistId());
                    inDbSong.setArtist(artista);
                }
                else{
                    inDbSong.setArtist(null);
                }
                inDbSong.setYear(song.getYear());
                inDbSong.setAlbum(song.getAlbum());
                inDbSong.setTitle(song.getTitle());

                return ResponseEntity.status(HttpStatus.OK).body(songMapper.toSongOut(songService.add(inDbSong)));
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @Operation(summary = "Elimina una canción según el id")
    @ApiResponse(responseCode = "204",
            description = "La canción se eliminó correctamente o no se encontró",
            content =  {@Content})
    @DeleteMapping("/{idCancion}")
    public ResponseEntity<?> deleteSong(@Parameter(description = "Id de la canción a borrar") @PathVariable Long idCancion){
        //TODO FIX DELETE SONG
        Optional<Song> optSong = songService.findById(idCancion);
        if(optSong.isPresent()){
            Song s = optSong.get();
            List<Playlist> listaPlaylists = playlistService.findAll();
            listaPlaylists.forEach(lista -> {
                List<Song> cancionesPlaylist = lista.getSongs();
                while (cancionesPlaylist.contains(s)){
                    cancionesPlaylist.remove(s);
                }
                lista.setSongs(cancionesPlaylist);
                playlistService.add(lista);
            });
            songService.deleteById(idCancion);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
