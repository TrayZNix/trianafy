package com.salesianostriana.dam.trianafy.exception;

import javax.persistence.EntityNotFoundException;

public class PlaylistNotFoundException extends EntityNotFoundException {

    public PlaylistNotFoundException() {
        super("No se encontró la playlist");
    }

    public PlaylistNotFoundException(Long id) {
        super(String.format("No se encontró la playlist con id  '%d'", id));
    }

}
