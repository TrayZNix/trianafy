package com.salesianostriana.dam.trianafy.exception;

import javax.persistence.EntityNotFoundException;

public class SongNotFoundException extends EntityNotFoundException {

    public SongNotFoundException() {
        super("No se encontró la canción");
    }

    public SongNotFoundException(Long id) {
        super(String.format("No se encontró la canción con id  '%d'", id));
    }

}
