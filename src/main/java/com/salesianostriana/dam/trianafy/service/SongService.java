package com.salesianostriana.dam.trianafy.service;


import com.salesianostriana.dam.trianafy.dto.SongDtoIn;
import com.salesianostriana.dam.trianafy.dto.SongDtoOut;
import com.salesianostriana.dam.trianafy.mappers.SongMapper;
import com.salesianostriana.dam.trianafy.model.*;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongService {

    @Autowired
    private ArtistRepository repoArtista;
    @Autowired
    private SongRepository repoSongs;
    @Autowired
    private SongMapper songMapper;

    private final SongRepository repository;

    public Song add(Song song) {
        return repository.save(song);
    }

    public Optional<Song> findById(Long id) {
        return repository.findById(id);
    }

    public List<Song> findAll() {
        return repository.findAll();
    }

    public Song edit(Song song) {
        return repository.save(song);
    }

    public void delete(Song song) {
        repository.delete(song);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public ResponseEntity<SongDtoOut> ValidateUpdate(SongDtoIn song, Long id){
        if(repoSongs.existsById(id)){
            Song inDbSong = repoSongs.findById(id).get();
            //Validación
            if(song.getTitle() == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            else{
                if(song.getArtistId() != null){
                    Optional<Artista> artista = repoArtista.findById(song.getArtistId());
                    if(!artista.isEmpty()){
                        inDbSong.setArtist(artista.get());
                    }
                    else{
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                    }
                }
                if(song.getYear() != null){
                    inDbSong.setYear(song.getYear());
                }
                if(song.getAlbum() != null){
                    inDbSong.setAlbum(song.getAlbum());
                }
                inDbSong.setTitle(song.getTitle());

                return ResponseEntity.status(HttpStatus.OK).body(songMapper.toSongOut(repoSongs.save(inDbSong)));
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
