package com.salesianostriana.dam.trianafy.controllers;

import com.salesianostriana.dam.trianafy.dto.SongDtoIn;
import com.salesianostriana.dam.trianafy.dto.SongDtoModifiedArtist;
import com.salesianostriana.dam.trianafy.dto.SongDtoOut;
import com.salesianostriana.dam.trianafy.mappers.ArtistaMapper;
import com.salesianostriana.dam.trianafy.mappers.SongMapper;
import com.salesianostriana.dam.trianafy.model.*;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.repos.PlaylistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import com.salesianostriana.dam.trianafy.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

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
    private PlaylistRepository repoPlaylist;
    @Autowired
    private SongMapper songMapper;
    @Autowired
    private SongService songService;

    @Autowired
    private ArtistaMapper artistaMapper;

    @GetMapping()
    public ResponseEntity<List<SongDtoOut>> getSongs(){
        List<Song> canciones = repoSongs.findAll();
        List<SongDtoOut> cancionesDto = new ArrayList<SongDtoOut>();
        canciones.forEach(c -> cancionesDto.add(songMapper.toSongOut(c)));
        if(cancionesDto.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(cancionesDto);
    }
    @GetMapping("/{id}")
    public  ResponseEntity<SongDtoModifiedArtist> getSongConcreta(@PathVariable Long id){
        Optional<Song> s = repoSongs.findById(id);
        if(s.isPresent()){
        SongDtoModifiedArtist songToReturn = songMapper.toSongDtoModifiedArtist(s.get());
        return ResponseEntity.status(HttpStatus.OK).body(songToReturn);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping()
    public ResponseEntity<SongDtoOut> createSong(@RequestBody SongDtoIn song){
        //TODO preguntar si validar asi los datos es correcto
        if(song.getArtistId() == null || song.getTitle() == null){
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
                                .toSongOut(repoSongs
                                .save(s)));
            }
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<SongDtoOut> updateSong(@RequestBody SongDtoIn song, @PathVariable Long id){
        return songService.ValidateUpdate(song, id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Long id){
        //TODO FIX DELETE SONG
        Optional<Song> optSong = repoSongs.findById(id);
        if(optSong.isPresent()){
            Song s = optSong.get();
            List<Playlist> listaPlaylists = repoPlaylist.findAll();
            listaPlaylists.forEach(lista -> {
                List<Song> cancionesPlaylist = lista.getSongs();
                while (cancionesPlaylist.contains(s)){
                    cancionesPlaylist.remove(s);
                }
                lista.setSongs(cancionesPlaylist);
            });
            repoPlaylist.saveAll(listaPlaylists);
            repoSongs.deleteById(id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
