package com.salesianostriana.dam.trianafy.exception;

import javax.persistence.EntityNotFoundException;

public class ArtistNotFoundException extends EntityNotFoundException {

    public ArtistNotFoundException() {
        super("No se encontró el artista");
    }

    public ArtistNotFoundException(Long id) {
        super(String.format("No se encontró el artista con id  '%d'", id));
    }

}
