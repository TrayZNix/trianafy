package com.salesianostriana.dam.trianafy.exception;

import javax.persistence.EntityNotFoundException;

public class EmptyArtistsListException extends EntityNotFoundException {

    public EmptyArtistsListException() {
        super("No se ha encontrado ningun artista");
    }



}
