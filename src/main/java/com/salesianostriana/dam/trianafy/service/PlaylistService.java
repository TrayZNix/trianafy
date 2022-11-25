package com.salesianostriana.dam.trianafy.service;


import com.salesianostriana.dam.trianafy.dto.PlaylistDtoIn;
import com.salesianostriana.dam.trianafy.dto.PlaylistDtoOut;
import com.salesianostriana.dam.trianafy.mappers.PlaylistMapper;
import com.salesianostriana.dam.trianafy.model.*;
import com.salesianostriana.dam.trianafy.repos.PlaylistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistMapper mapper;

    @Autowired
    private PlaylistRepository repoPlaylist;
    @Autowired
    private SongRepository repoSongs;

    public Playlist add(Playlist playlist) {
        return repoPlaylist.save(playlist);
    }

    public Optional<Playlist> findById(Long id) {
        return repoPlaylist.findById(id);
    }

    public List<Playlist> findAll() {
        return repoPlaylist.findAll();
    }

    public Playlist edit(Playlist playlist) {
        return repoPlaylist.save(playlist);
    }

    public void delete(Playlist playlist) {
        repoPlaylist.delete(playlist);
    }

    public void deleteById(Long id) {
        repoPlaylist.deleteById(id);
    }


    public ResponseEntity<PlaylistDtoOut> validateUpdate(Long id, PlaylistDtoIn playlist) {
        Optional<Playlist> optPlalist = repoPlaylist.findById(id);
        if(optPlalist.isPresent()) {
            Playlist inDbPlaylist = optPlalist.get();
            if(playlist.getName() != null){
                inDbPlaylist.setName(playlist.getName());
            }
            if(playlist.getDescription() != null){
                inDbPlaylist.setDescription(playlist.getDescription());
            }
            return ResponseEntity.status(HttpStatus.OK).body(mapper.toPlaylistDtoOut(repoPlaylist.save(inDbPlaylist)));
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<Playlist> validateAndAdd(Long id1, Long id2) {
        Optional<Song> optSong = repoSongs.findById(id2);
        if(optSong.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else{
            Optional<Playlist> optPlaylist = repoPlaylist.findById(id1);
            if(optPlaylist.isPresent()){
                Song s = optSong.get();
                Playlist p = optPlaylist.get();
                p.addSong(s);
                return ResponseEntity.status(HttpStatus.CREATED).body(repoPlaylist.save(p));
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
    }
    public ResponseEntity<Playlist> validateAndRemove(Long id1, Long id2) {
        //TODO CHECKEAR SI ES NECESARIO COMPROBAR SI LA CANCION EXISTE
        Optional<Playlist> optPlaylist = repoPlaylist.findById(id1);
        Optional<Song> optSong = repoSongs.findById(id2);
        if (optSong.isPresent() && optPlaylist.isPresent()) {
            Playlist p = optPlaylist.get();
            Song s = optSong.get();
            List<Song> songs = p.getSongs();
            p.deleteSong(s);
            repoPlaylist.save(p);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
