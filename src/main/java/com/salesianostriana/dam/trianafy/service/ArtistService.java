package com.salesianostriana.dam.trianafy.service;


import com.salesianostriana.dam.trianafy.dto.ArtistaDtoIn;
import com.salesianostriana.dam.trianafy.dto.ArtistaDtoOut;
import com.salesianostriana.dam.trianafy.exception.ArtistNotFoundException;
import com.salesianostriana.dam.trianafy.exception.EmptyArtistsListException;
import com.salesianostriana.dam.trianafy.mappers.ArtistaMapper;
import com.salesianostriana.dam.trianafy.model.Artista;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    @Autowired
    private ArtistaMapper mapperArtista;
    private final ArtistRepository repository;

    public Artista add(Artista artista) {
        return repository.save(artista);
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

    public Boolean existsById(Long id ) { return repository.existsById(id); }

    public ResponseEntity<List<Artista>> findAll(){
        List<Artista> artistas = repository.findAll();
        if (artistas.isEmpty()) throw new EmptyArtistsListException();
        else return ResponseEntity.ok(artistas);
    }

    public Artista findById(Long id){
       return repository.findById(id).orElseThrow(ArtistNotFoundException::new);
    }
    public ResponseEntity<ArtistaDtoOut> editArtist(ArtistaDtoIn artista, Long id){
        if(artista.getName()==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if(!repository.existsById(id)){
            throw new ArtistNotFoundException();
        }
        else{
            Artista artistaAEditar = this.findById(id);
            artistaAEditar.setName(artista.getName());
            return ResponseEntity.ok(mapperArtista.toArtistDto(this.edit(artistaAEditar)));
        }
    }

    public boolean existsByName(String name){
        return repository.existsByName(name);
    }
}
