


# Trianafy
**Trianafy** es una *API* usada para almacenar datos sobre música, siendo esto artistas, canciones y playlist
## Metodología de trabajo
Para trabajar de una manera eficiente, se ha decidido trabajar mediante historias de usuario. Estas se encuentran en el mismo repo.


[![](https://raw.githubusercontent.com/TrayZNix/trianafy/main/historiasDeUsuario.png)](https://github.com/TrayZNix/trianafy/blob/main/historiasDeUsuario.png)

## Requisitos
Para la ejecución de este proyecto, es necesario tener instalado [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html), y [Apache Maven](https://maven.apache.org/download.cgi).
Además, para poder probar todas las peticiones más fácilmente, se recomienda el uso de [Postman](https://www.postman.com).

## Ejecución 
Para ejecutar el programa, debemos abrir CMD, ubicarnos en la carpeta del proyecto, y ejecutar el siguiente comando

    mvn spring-boot:run
**IMPORTANTE:**
Si el puerto 8080 se encuentra ocupado, la API no se iniciará. De ocurrir esto, debes introducir la siguiente linea en el archivo *application.properties* ubicado en *./src/main/resources*

	server.port=9000
*Si el puerto 9000 tampoco estuviese libre, puedes probar con otros*
## Peticiones

### Testing con Postman
Gracias a Postman, puedes probar las peticiones de una manera fácil y directa. Para ello, dentro del programa, solo debes importar el archivo [TRIANAFY-REQUESTS-POSTMAN.json](https://github.com/TrayZNix/trianafy/blob/main/TRIANAFY-REQUESTS-POSTMAN.json) ubicado en la carpeta raiz de *Trianafy*

### Documentación de la API
Tras ejecutar la app, podemos acceder a su documentación accediendo a la siguiente URL

    http://localhost:8080/swagger-ui/index.html

Por otro lado, también es posible acceder al JSON que contiene toda la documentación mediante la siguiente URL

    http://localhost:8080/v3/api-docs

## Restricciones

### Restricciones en cuerpos de peticiones *POST* 

> #### Crear un artista: 	
> `http://localhost:8080/artist` 
> 
> El siguiente parámetro es **obligatorio**:
> 
>     {
> 	    "name": "nombreDeArtista"
>     }
>   ⠀
 
>#### Crear una canción:
>`http://localhost:8080/song`	
>
>Los siguientes parámetros son **obligatorios**:
>
>	   {
>		  "title": "Y Nos Dieron las Diez",
>		  "album": "Física y Química",
>		  "year": "1992"
>		}
>*El parametro "artistId" pasa a ser opcional. Si el artista de tu canción es anónimo, no tienes que indicar el atributo "artistId".  en cambio, si tiene artista, debes realizar la petición de la siguiente manera:*
>
>	   {
>		  "title": "Y Nos Dieron las Diez",
>		  "album": "Física y Química",
>		  "year": "1992",
>		  "artistId": 1
>		}
>⠀

> #### Crear una playlist: 	
> `http://localhost:8080/list` 
> 
> Los siguientes parámetros son **obligatorios**:
> 
>     {
> 	    	"name": "Drill",
>			"description": "Drill britanico"
>     }
>   ⠀

### Restricciones en cuerpos de peticiones *PUT*

> #### Modificar artista: 
> 	`http://localhost:8080/artist/{id}` 
> 
> El siguiente parámetro es **obligatorio**:
> 
>     {
> 	    "name": "nombreDeArtista"
>     }
>   ⠀

>#### Modificar una canción:
>`http://localhost:8080/song/{id}`	
>
>Los siguientes parámetros son **obligatorios**:
>
>	   {
>		  "title": "Y Nos Dieron las Diez",
>		  "album": "Física y Química",
>		  "year": "1992"
>		}
>*El parametro "artistId" es opcional. Si el artista de tu canción es anónimo, no tienes que indicar el atributo "artistId".  en cambio, si tiene artista, debes realizar la petición de la siguiente manera:*
>
>	   {
>		  "title": "Y Nos Dieron las Diez",
>		  "album": "Física y Química",
>		  "year": "1992",
>		  "artistId": 1
>		}
>⠀

> #### Modificar una playlist: 	
> `http://localhost:8080/list/{id}` 
> 
> Los siguientes parámetros son **obligatorios**:
> 
>     {
> 	    	"name": "Música para estudiar",
>			"description": "Playlist de música que ayuda concentrarte"
>     }
>   

### A tener en cuenta en las peticiones *DELETE*

>#### Borrado de un artista
>Al borrar un artista con la petición *DELETE* en la URL `localhost:8080/artist`, sus canciones se conservarán, cambiando artistId a null.
>⠀

>#### Borrado de una canción
>Al borrar una canción con la petición *DELETE* en la URL `localhost:8080/song`, se borrarán **TODAS** las entradas en las playlist donde exista esa canción.
>⠀

>#### Borrado de una canción en una playlist
>Al  eliminar una canción de una playlist ejecutando la peticion *DELETE* en la URL `localhost:8080/list/{idPlaylist}/song/{idSong}` se eliminarán todas las entradas de esa canción en esa playlist	 
>⠀
