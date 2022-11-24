package com.salesianostriana.dam.trianafy.controllers;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/artista")
public class ArtistaController {
    @Autowired
    private ArtistRepository repo;
    @GetMapping()
    public ResponseEntity<List<Artist>> getAllArtistas(){
        return ResponseEntity.ok(repo.findAll());
    }

}
