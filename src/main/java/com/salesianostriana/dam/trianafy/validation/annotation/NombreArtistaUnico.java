package com.salesianostriana.dam.trianafy.validation.annotation;

import com.salesianostriana.dam.trianafy.validation.validator.ValidadorNombreUnico;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidadorNombreUnico.class)
@Documented
public @interface NombreArtistaUnico {

    String message() default "El artista ya existe";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}


