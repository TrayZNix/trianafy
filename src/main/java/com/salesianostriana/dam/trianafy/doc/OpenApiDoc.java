package com.salesianostriana.dam.trianafy.doc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiDoc {
    @Bean
    public OpenAPI getOpenAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .version("1.0")
                        .contact(new Contact()
                        .name("Roberto Carvajal Velasco")
                        .email("carvajal.verob22@triana.salesianos.edu")
                        .url("https://www.github.com/TrayzNix"))
                        .license(new License().name("MIT License").url("https://github.com/TrayZNix/trianafy/blame/main/LICENSE"))
                        .title("Trianafy")
                        .description("API de una aplicación de música similar a Spotify, en la que " +
                                "podemos realizar peticiones CRUD a artistas, canciones y playlist, " +
                                "estando estos datos conectados entre si,"));

    }

}
