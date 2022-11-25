package com.salesianostriana.dam.trianafy.controllers;

import com.salesianostriana.dam.trianafy.model.Artista;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistaController {
    @Autowired
    private ArtistRepository repoArtist;

    @Autowired
    private SongRepository repoSongs;
    @GetMapping()
    public ResponseEntity<List<Artista>> getAllArtistas(){
        List<Artista> artistas = repoArtist.findAll();
        if (artistas.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else return ResponseEntity.ok(artistas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artista> getArtista(@PathVariable Long id){
        return ResponseEntity.of(repoArtist.findById(id));
    }

    @PostMapping()
    public ResponseEntity<Artista> createArtista(@RequestBody Artista artista){
        return ResponseEntity.status(HttpStatus.CREATED).body(repoArtist.save(artista));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artista> editArtista(@RequestBody Artista artista, @PathVariable Long id){
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
