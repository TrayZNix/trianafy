package com.salesianostriana.dam.trianafy.controllers;

import com.salesianostriana.dam.trianafy.model.*;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import com.salesianostriana.dam.trianafy.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/song")
public class SongsController {
    @Autowired
    private ArtistRepository repoArtista;
    @Autowired
    private SongRepository repoSongs;
    @Autowired
    private SongMapper songMapper;
    @Autowired
    private SongService songService;

    @GetMapping()
    public ResponseEntity<List<SongDtoOut>> getSongs(){
        List<Song> canciones = repoSongs.findAll();
        List<SongDtoOut> cancionesDto = new ArrayList<SongDtoOut>();
        canciones.forEach(cancion -> cancionesDto.add(songMapper.toSongOut(cancion)));
        if(cancionesDto.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(cancionesDto);
    }
    @GetMapping("/{id}")
    public  ResponseEntity<Song> getSongConcreta(@PathVariable Long id){
        return ResponseEntity.of(repoSongs.findById(id));
    }

    @PostMapping()
    public ResponseEntity<SongDtoOut> createSong(@RequestBody SongDtoIn song){
        //TODO preguntar si validar asi los datos es correcto
        if(song.getArtistId() == null || song.getTitle() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(songMapper
                        .toSongOut(repoSongs
                        .save(songMapper.toSongIn(song))));
    }
    @PutMapping("/{id}")
    public ResponseEntity<SongDtoOut> updateSong(@RequestBody SongDtoIn song, @PathVariable Long id){
        return songService.ValidateUpdate(song, id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Long id){
        if(repoSongs.existsById(id)){
            repoSongs.deleteById(id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
