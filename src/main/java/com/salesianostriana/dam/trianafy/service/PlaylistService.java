package com.salesianostriana.dam.trianafy.service;


import com.salesianostriana.dam.trianafy.dto.PlaylistDtoIn;
import com.salesianostriana.dam.trianafy.dto.PlaylistDtoOut;
import com.salesianostriana.dam.trianafy.dto.PlaylistDtoOutPCreateWSongs;
import com.salesianostriana.dam.trianafy.exception.EmptyPlaylistListException;
import com.salesianostriana.dam.trianafy.exception.PlaylistNotFoundException;
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

    @Autowired
    private PlaylistMapper mapperPlaylist;

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

    public Boolean existsById(Long id ) { return repoPlaylist.existsById(id); }

    public ResponseEntity<List<PlaylistDtoOut>> findAllPlaylists(){
        List<Playlist> playlists = repoPlaylist.findAll();
        if(playlists.isEmpty()){
            throw new EmptyPlaylistListException();
        }
        else return ResponseEntity.ok(mapperPlaylist.toPlaylistListDtoOut(playlists));
    }
    public ResponseEntity<PlaylistDtoOutPCreateWSongs> findPlaylistById(Long id){
        Optional<Playlist> optPlaylist = this.findById(id);
        if (optPlaylist.isEmpty()) throw new PlaylistNotFoundException();
        return optPlaylist
                .map(playlist -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(mapperPlaylist.toPlaylistDtoOutPCreateWSongs(playlist)))
                .orElseGet(() ->
                        ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .build());
    }

}
