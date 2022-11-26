package com.salesianostriana.dam.trianafy.controllers;

import com.salesianostriana.dam.trianafy.dto.*;
import com.salesianostriana.dam.trianafy.mappers.PlaylistMapper;
import com.salesianostriana.dam.trianafy.mappers.SongMapper;
import com.salesianostriana.dam.trianafy.model.*;
import com.salesianostriana.dam.trianafy.repos.PlaylistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
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

import java.util.List;
import java.util.Optional;

@RestController
@Tags(@Tag(name = "Playlist", description = "PlaylistController"))
@RequestMapping("/list")
public class PlaylistController {
    @Autowired
    private PlaylistRepository repoPlaylist;
    @Autowired
    private PlaylistService playlistService;
    @Autowired
    private SongService songService;
    @Autowired
    private PlaylistService servicePlaylist;
    @Autowired
    private PlaylistMapper mapperPlaylist;
    @Autowired
    private SongMapper songMapper;

    @Operation(summary = "Devuelve todas las playlists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se encontraron una o más playlists",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PlaylistDtoOut.class)),
                            examples = {@ExampleObject(
                                    value = """
                                   [
                                    {
                                        "id": 1,
                                        "name": "Rap",
                                        "numberOfSongs": 12
                                    },
                                    {
                                        "id": 2,
                                        "name": "Electrónica",
                                        "numberOfSongs": 26
                                    }
                                   ]
                                   """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró ninguna playlist",
                    content = {@Content})
    })
    @GetMapping()
    public ResponseEntity<List<PlaylistDtoOut>> getPlaylists(){
        return ResponseEntity.ok(mapperPlaylist.toPlaylistListDtoOut(playlistService.findAll()));
    }

    @Operation(summary = "Devuelve una playlist según el id proporcionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se encontró la playlist correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlaylistDtoOutPCreateWSongs.class),
                            examples = {@ExampleObject(
                                    value = """
                                        {
                                            "id": 1,
                                            "name": "Hip-Hop",
                                            "description": "Esta es una lista de Hip-Hop",
                                            "songs": [
                                                        {
                                                            "id": 1,
                                                            "title": "Wonderful",
                                                            "album": "R.U.L.E",
                                                            "year": "2004",
                                                            "artist": "Ja Rule"
                                                         },
                                                         {
                                                            "id": 2,
                                                            "title": "In Da Club",
                                                            "album": "Get Rich Or Die Tryin'",
                                                            "year": "2003",
                                                            "artist": "50 Cent"
                                                         }
                                                     ]
                                        }
                                   """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró ninguna playlist",
                    content = {@Content})
    })
    @GetMapping("/{idPlaylist}")
    public ResponseEntity<PlaylistDtoOutPCreateWSongs> getListaConcreta(@Parameter(description = "Id de la playlist a buscar") @PathVariable Long idPlaylist){
        Optional<Playlist> optPlaylist = playlistService.findById(idPlaylist);
        return optPlaylist
                .map(playlist -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(mapperPlaylist.toPlaylistDtoOutPCreateWSongs(playlist)))
                .orElseGet(() ->
                        ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .build());

    }

    @Operation(summary = "Crea una playlist según el cuerpo proporcionado")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la playlist a crear",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlaylistDtoIn.class),
                    examples = {@ExampleObject(
                            value = """
                                   {
                                      "name": "Drill",
                                      "description": "Drill británico"
                                   }
                                   """
                    )}
            )})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "La playlist se creó correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlaylistDtoOutPCreate.class),
                            examples = {@ExampleObject(
                                    value = """
                                   {
                                      "name": "Drill",
                                      "description": "Drill británico"
                                   }
                                   """
                            )})}),
            @ApiResponse(responseCode = "400",
                    description = "Falta algún parámetro o el tipo de uno o más parámetros no es correcto",
                    content = {@Content})
    })
    @PostMapping()
    public ResponseEntity<PlaylistDtoOutPCreate> createPlaylist(@RequestBody PlaylistDtoIn playlist){
        if(playlist.getName() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else return ResponseEntity.status(HttpStatus.CREATED).body(mapperPlaylist.toPlaylistDtoOutPostCreate(playlistService.add(mapperPlaylist.toPlaylist(playlist))));
    }

    @Operation(summary = "Modifica una playlist según el id y el cuerpo proporcionado")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Nuevos datos para la playlist",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlaylistDtoIn.class),
                    examples = {@ExampleObject(
                            value = """
                                        {
                                            "name": "Musica para estudiar",
                                            "description": "Playlist de musica que ayuda a concentrarte"
                                        }
                                   """
                    )}
            )})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "La playlist se modificó correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlaylistDtoOut.class),
                            examples = {@ExampleObject(
                                    value = """
                                   {
                                      "name": "Musica para estudiar",
                                      "description": "Playlist de musica que ayuda a concentrarte",
                                      "numberOfSongs": 7
                                   }
                                   """
                            )})}),
            @ApiResponse(responseCode = "400",
                    description = "Falta algún parámetro o el tipo de uno o más parámetros no es correcto",
                    content = {@Content})
    })
    @PutMapping("/{idPlaylist}")
    public ResponseEntity<PlaylistDtoOut> editPlaylist(@Parameter(description = "Id de la playlist a editar") @PathVariable Long idPlaylist,
                                                       @RequestBody PlaylistDtoIn playlist){
        return servicePlaylist.validateUpdate(idPlaylist, playlist);
    }

    @Operation(summary = "Elimina una playlist según el id")
    @ApiResponse(responseCode = "204",
            description = "La playlist se eliminó correctamente o no se encontró",
            content =  {@Content})

    @DeleteMapping("/{idPlaylist}")
    public ResponseEntity<?> deletePlaylist(@Parameter(description = "Id de la playlist a eliminar") @PathVariable Long idPlaylist ){
        if(repoPlaylist.existsById(idPlaylist)){
            playlistService.deleteById(idPlaylist);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //AÑADIR Y ELIMINAR CANCIONES

    @Operation(summary = "Devuelve todas las canciones de una playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se encontraron las canciones de la playlist indicada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlaylistDtoOutPCreateWSongs.class),
                            examples = {@ExampleObject(
                                    value = """
                                                     {
                                                       "id": 1,
                                                       "name": "Para conducir de noche 🌙",
                                                       "description": "Musica electronica y monótona",
                                                       "songs": [
                                                         {
                                                           "id": 1,
                                                           "title": "Blood Rage",
                                                           "album": "Beware of the Humans",
                                                           "year": "2017",
                                                           "artist": {
                                                                        "id": 1,
                                                                        "name": "Nightcrawler"
                                                                     }
                                                         },
                                                         {
                                                           "id": 2,
                                                           "title": "Aleph",
                                                           "album": "Maryland",
                                                           "year": "2011",
                                                           "artist": {
                                                                        "id": 2,
                                                                        "name": "Gesaffelstein"
                                                                     }
                                                         }
                                                       ]
                                                     }
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró ninguna playlist",
                    content = {@Content})
    })
    @GetMapping("/{idPlaylist}/song")
    public ResponseEntity<PlaylistDtoOutPCreateWSongs> getFullPlaylist(@Parameter(description = "Id de la playlist donde buscar las canciones") @PathVariable Long idPlaylist){
        Optional<Playlist> p = playlistService.findById(idPlaylist);
        return p.map(playlist -> ResponseEntity.ok(mapperPlaylist.toPlaylistDtoOutPCreateWSongs(playlist))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @Operation(summary = "Devuelve la canción según el id indicado en la playlist indicada en el primer id")
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
                    description = "No se encontró la canción indicada",
                    content = {@Content})
    })
    @GetMapping("/{idPlaylist}/song/{idCancion}")
    public ResponseEntity<SongDtoModifiedArtist> getPlaylistSongData(@Parameter(description = "Id de la playlist donde buscar la canción") @PathVariable Long idPlaylist,
                                                                     @Parameter(description = "Id de la canción")@PathVariable Long idCancion){
        if(repoPlaylist.existsById(idPlaylist)) {
            Optional<Song> s = songService.findById(idCancion);
            if (s.isPresent()) {
                SongDtoModifiedArtist songToReturn = songMapper.toSongDtoModifiedArtist(s.get());
                return ResponseEntity.status(HttpStatus.OK).body(songToReturn);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            }
        }
        else{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @Operation(summary = "Añade la canción de id indicado en la playlist indicada en el primer id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se encontró la canción correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class),
                            examples = {@ExampleObject(
                                    value = """
                                   {
                                   "id": 1,
                                   "name": "Lista para entrenar",
                                   "description": "Lista para ponerte fuerte",
                                   "songs": [
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
                                           ]
                                   }
                                   """
                            )})}),
            @ApiResponse(responseCode = "400",
                    description = "No se encontró la canción o playlist indicada",
                    content = {@Content})
    })
    @PostMapping("/{idPlaylist}/song/{idCancion}")
    public ResponseEntity<Playlist> addSongToPlaylist(@Parameter(description = "Id de la playlist donde añadir la canción") @PathVariable Long idPlaylist,@Parameter(description = "Id de la canción a añadir") @PathVariable Long idCancion){
        return servicePlaylist.validateAndAdd(idPlaylist, idCancion);
    }

    @Operation(summary = "Elimina una de todas las canción de id indicado, en la playlist indicada en el primer id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se eliminó la canción correctamente", content = @Content())})
    @DeleteMapping("/{idPlaylist}/song/{idCancion}")
    public ResponseEntity<Playlist> deleteSongFromPlaylist(@Parameter(description = "Id de la playlist donde eliminar la canción") @PathVariable Long idPlaylist, @Parameter(description = "Id de la canción a borrar") @PathVariable Long idCancion){
        return servicePlaylist.validateAndRemove(idPlaylist, idCancion);
    }

}
