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
    private ArtistService artistService;
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

    public Boolean existsById(Long id ) { return repository.existsById(id); }

    public List<Song> addAll(List<Song> list) { return repository.saveAll(list);}

    public ResponseEntity<SongDtoOut> ValidateUpdate(SongDtoIn song, Long id){
        Optional<Song> optSong = findById(id);
        if(optSong.isPresent()){
            Song inDbSong = optSong.get();
            //Validación
            if(song.getTitle() == null || song.getYear() == null || song.getAlbum() == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            else{
                if(song.getArtistId() != null){
                    Optional<Artista> artista = artistService.findById(song.getArtistId());
                    artista.ifPresent(inDbSong::setArtist);
                }
                else{
                    inDbSong.setArtist(null);
                }
                inDbSong.setYear(song.getYear());
                inDbSong.setAlbum(song.getAlbum());
                inDbSong.setTitle(song.getTitle());

                return ResponseEntity.status(HttpStatus.OK).body(songMapper.toSongOut(add(inDbSong)));
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
