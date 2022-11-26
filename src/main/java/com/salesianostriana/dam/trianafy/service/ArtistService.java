package com.salesianostriana.dam.trianafy.service;


import com.salesianostriana.dam.trianafy.model.Artista;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository repository;


    public Artista add(Artista artista) {
        return repository.save(artista);
    }

    public Optional<Artista> findById(Long id) {
        return repository.findById(id);
    }

    public List<Artista> findAll() {
        return repository.findAll();
    }

    public Artista edit(Artista artista) {
        return repository.save(artista);
    }

    public void delete(Artista artista) {
        repository.delete(artista);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
