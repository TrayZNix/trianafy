package com.salesianostriana.dam.trianafy.controllers;

import com.salesianostriana.dam.trianafy.dto.ArtistaDtoIn;
import com.salesianostriana.dam.trianafy.mappers.ArtistaMapper;
import com.salesianostriana.dam.trianafy.model.Artista;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
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


@RestController()
@Tags(@Tag(name = "Controller de artistas", description = "ArtistaController"))
@RequestMapping("/artist")
public class ArtistaController {
    @Autowired
    private ArtistRepository repoArtist;
    @Autowired
    private SongRepository repoSongs;
    @Autowired
    private ArtistaMapper artistaMapper;
    @Operation(summary = "Devuelve una lista de todos los artistas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se encontró uno o más artistas",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Artista.class)))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraron artistas",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping()
    public ResponseEntity<List<Artista>> getAllArtistas(){
        List<Artista> artistas = repoArtist.findAll();
        if (artistas.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else return ResponseEntity.ok(artistas);
    }
    @Operation(summary = "Devuelve una artistas según su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se encontró el artista",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artista.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado un artista con el id proporcionado",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/{id}")
    public ResponseEntity<Artista> getArtista(@PathVariable Long id){
        return ResponseEntity.of(repoArtist.findById(id));
    }
    @Operation(summary = "Crea un artista nuevo segun el cuerpo proporcionado")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ArtistaDtoIn.class))})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se creó el artista correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artista.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Falta algún parámetro o el tipo de uno o más parámetros no es correcto",
                    content = {@Content(mediaType = "application/json")})
    })
    @PostMapping()
    public ResponseEntity<Artista> createArtista(@RequestBody ArtistaDtoIn artista){
        if(artista.getName() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body((repoArtist.save(artistaMapper.toArtist(artista))));
    }
    @Operation(summary = "Modifica un artista según el id y el cuerpo proporcionado")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ArtistaDtoIn.class))})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se creó el artista correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artista.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Falta algún parámetro o el tipo de uno o más parámetros no es correcto",
                    content = {@Content(mediaType = "application/json")})
    })
    @PutMapping("/{id}")
    public ResponseEntity<Artista> editArtista(@RequestBody Artista artista, @PathVariable Long id){
        if(artista.getName()==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if(!repoArtist.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else{
        return ResponseEntity.of(repoArtist.findById(id).map(preEdit -> {
            preEdit.setName(artista.getName());
            return repoArtist.save(preEdit);
        }));
        }
    }
    @Operation(summary = "Elimina un artista según el id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "El artista se eliminó correctamente o no se encontró")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArtista(@PathVariable Long id){
        if(repoArtist.existsById(id)){
            List<Song> canciones = repoSongs.findAll();
            canciones.forEach(song -> {
                if(song.getArtist()!=null){
                    if(song.getArtist().getId().equals(id)){
                        song.setArtist(null);
                    }
                }
            });
            repoSongs.saveAll(canciones);
            repoArtist.deleteById(id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
