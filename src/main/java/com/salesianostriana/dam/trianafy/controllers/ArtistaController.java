package com.salesianostriana.dam.trianafy.controllers;

import com.salesianostriana.dam.trianafy.dto.ArtistaDtoIn;
import com.salesianostriana.dam.trianafy.mappers.ArtistaMapper;
import com.salesianostriana.dam.trianafy.model.Artista;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController()
@Tags(@Tag(name = "Artistas", description = "ArtistaController"))
@RequestMapping("/artist")
public class ArtistaController {
    @Autowired
    private ArtistaMapper artistaMapper;
    @Autowired
    private ArtistService serviceArtista;
    @Autowired
    private SongService songService;

    @Operation(summary = "Devuelve una lista de todos los artistas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se encontró uno o más artistas",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Artista.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 1, "nombre": "Kaze"},
                                                {"id": 2, "nombre": "Canserbero"}
                                            ]
                                            """
                    )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraron artistas",
                    content = {@Content})
    })
    @GetMapping()
    public ResponseEntity<List<Artista>> getAllArtistas(){
        List<Artista> artistas = serviceArtista.findAll();
        if (artistas.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else return ResponseEntity.ok(artistas);
    }
    @Operation(summary = "Devuelve una artistas según su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se encontró el artista",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artista.class),
                            examples = {@ExampleObject(
                                    value = """    
                                                {"id": 1, "nombre": "Kaze"}
                                            """
                    )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado un artista con el id proporcionado",
                    content = {@Content})
    })
    @GetMapping("/{idArtista}")
    public ResponseEntity<Artista> getArtista(@Parameter(description = "Id del artista a buscar") @PathVariable Long idArtista){
        return ResponseEntity.of(serviceArtista.findById(idArtista));
    }
    @Operation(summary = "Crea un artista nuevo segun el cuerpo proporcionado")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del artista a crear",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ArtistaDtoIn.class),
                    examples = @ExampleObject(value = """
                            { "name": "Kaze"}
                            """))})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se creó el artista correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artista.class),
                            examples = {@ExampleObject(
                                    value = """
                                        {"id": 1, "name": "Kaze"}
                                    """
                            )})}),
            @ApiResponse(responseCode = "400",
                    description = "Falta algún parámetro o el tipo de uno o más parámetros no es correcto",
                    content = {@Content})
    })
    @PostMapping()
    public ResponseEntity<Artista> createArtista(@RequestBody ArtistaDtoIn artista){
        if(artista.getName() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body((serviceArtista.add(artistaMapper.toArtist(artista))));
    }
    @Operation(summary = "Modifica un artista según el id y el cuerpo proporcionado")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevo nombre del artista",
                                                            content = {@Content(mediaType = "application/json",
                                                                    schema = @Schema(implementation = ArtistaDtoIn.class),
                                                                    examples = {@ExampleObject(value = """
                                                                                                        {"name": "Rels B"}
                                                                                                       """
                                                            )})})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se creó el artista correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artista.class),
                            examples = {@ExampleObject(
                                    value = """
                                        {"id": 1, "nombre": "Rels b"}
                                    """
                            )})}),
            @ApiResponse(responseCode = "400",
                    description = "Falta algún parámetro o el tipo de uno o más parámetros no es correcto",
                    content = {@Content}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró ningún artista con el id proporcionado",
                    content = {@Content})
    })
    @PutMapping("/{idArtista}")
    public ResponseEntity<Artista> editArtista(@RequestBody ArtistaDtoIn artista, @Parameter(description = "Id del artista a editar")@PathVariable Long idArtista){
        if(artista.getName()==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if(!serviceArtista.existsById(idArtista)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else{
        return ResponseEntity.of(serviceArtista.findById(idArtista).map(preEdit -> {
            preEdit.setName(artista.getName());
            return serviceArtista.edit(preEdit);
        }));
        }
    }
    @Operation(summary = "Elimina un artista según el id")
    @ApiResponse(responseCode = "204",
            description = "El artista se eliminó correctamente o no se encontró",
            content =  {@Content})
    @DeleteMapping("/{idArtista}")
    public ResponseEntity<?> deleteArtista(@Parameter(description = "Id del artista a borrar")@PathVariable Long idArtista){
        if(serviceArtista.existsById(idArtista)){
            List<Song> canciones = songService.findAll();
            canciones.forEach(song -> {
                if(song.getArtist()!=null){
                    if(song.getArtist().getId().equals(idArtista)){
                        song.setArtist(null);
                    }
                }
            });
            songService.addAll(canciones);
            serviceArtista.deleteById(idArtista);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
