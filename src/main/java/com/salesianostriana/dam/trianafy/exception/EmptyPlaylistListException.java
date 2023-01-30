package com.salesianostriana.dam.trianafy.exception;

import javax.persistence.EntityNotFoundException;

public class EmptyPlaylistListException extends EntityNotFoundException {

    public EmptyPlaylistListException() {
        super("No se ha encontrado ninguna playlist");
    }



}
