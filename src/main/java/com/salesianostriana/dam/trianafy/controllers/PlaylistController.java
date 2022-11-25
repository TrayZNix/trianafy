package com.salesianostriana.dam.trianafy.controllers;

import com.salesianostriana.dam.trianafy.dto.PlaylistDtoIn;
import com.salesianostriana.dam.trianafy.dto.PlaylistDtoOut;
import com.salesianostriana.dam.trianafy.dto.PlaylistDtoOutPCreate;
import com.salesianostriana.dam.trianafy.dto.SongDtoModifiedArtist;
import com.salesianostriana.dam.trianafy.mappers.PlaylistMapper;
import com.salesianostriana.dam.trianafy.mappers.SongMapper;
import com.salesianostriana.dam.trianafy.model.*;
import com.salesianostriana.dam.trianafy.repos.PlaylistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/list")
public class PlaylistController {
    @Autowired
    private PlaylistRepository repoPlaylist;
    @Autowired
    private SongRepository repoSongs;
    @Autowired
    private PlaylistMapper mapperPlaylist;
    @Autowired
    private PlaylistService servicePlaylist;
    @Autowired
    private SongMapper songMapper;

    @GetMapping()
    public ResponseEntity<List<PlaylistDtoOut>> getPlaylists(){
        return ResponseEntity.ok(mapperPlaylist.toPlaylistListDtoOut(repoPlaylist.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getListaConcreta(@PathVariable Long id){
        return ResponseEntity.of(repoPlaylist.findById(id));
    }

    @PostMapping()
    public ResponseEntity<PlaylistDtoOutPCreate> createPlaylist(@RequestBody PlaylistDtoIn playlist){
        return ResponseEntity.status(HttpStatus.CREATED).body(mapperPlaylist.toPlaylistDtoOutPostCreate(repoPlaylist.save(mapperPlaylist.toPlaylist(playlist))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistDtoOut> editPlaylist(@PathVariable Long id, @RequestBody PlaylistDtoIn playlist){
        return servicePlaylist.validateUpdate(id, playlist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlaylist(@PathVariable Long id ){
        if(repoPlaylist.existsById(id)){
            repoPlaylist.deleteById(id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //AÑADIR Y ELIMINAR CANCIONES

    @GetMapping("/{id}/song")
    public ResponseEntity<Playlist> getFullPlaylist(@PathVariable Long id){
        return ResponseEntity.of(repoPlaylist.findById(id));
    }
    @GetMapping("/{idPlaylist}/song/{id}")
    public ResponseEntity<SongDtoModifiedArtist> getPlaylistSongData(@PathVariable Long idPlaylist, @PathVariable Long id){
        if(repoPlaylist.existsById(idPlaylist)) {
            Optional<Song> s = repoSongs.findById(id);
            if (s.isPresent()) { //ES CORRECTO COMPROBAR ASI SI EXISTE Y LUEGO HACER GET
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

    @PostMapping("/{id1}/song/{id2}")
    public ResponseEntity<Playlist> addSongToPlaylist(@PathVariable Long id1, @PathVariable Long id2){
        return servicePlaylist.validateAndAdd(id1, id2);
    }

    @DeleteMapping("/{id1}/song/{id2}")
    public ResponseEntity<Playlist> deleteSongFromPlaylist(@PathVariable Long id1, @PathVariable Long id2){
        return servicePlaylist.validateAndRemove(id1, id2);
    }

}
