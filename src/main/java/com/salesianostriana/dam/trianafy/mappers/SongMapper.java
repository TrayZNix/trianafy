package com.salesianostriana.dam.trianafy.mappers;

import com.salesianostriana.dam.trianafy.dto.SongDtoIn;
import com.salesianostriana.dam.trianafy.dto.SongDtoModifiedArtist;
import com.salesianostriana.dam.trianafy.dto.SongDtoOut;
import com.salesianostriana.dam.trianafy.model.Artista;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SongMapper {
    @Autowired
    private ArtistRepository repoArtist;
    @Autowired
    private ArtistaMapper mapperArtista;
    public Song toSongIn(SongDtoIn dtoSong){
        if(dtoSong.getArtistId() == null){
            return Song.builder().title(dtoSong.getTitle()).album(dtoSong.getAlbum()).year(dtoSong.getYear()).build();
        }
        else{
            Optional<Artista> artista = repoArtist.findById(dtoSong.getArtistId());
            if(artista.isPresent()){
                Artista artistaObj = artista.get();
                return Song.builder().title(dtoSong.getTitle()).album(dtoSong.getAlbum()).year(dtoSong.getYear()).artist(artistaObj).build();
            }
            else{
                return  null;
            }
        }
    }
    public SongDtoOut toSongOut(Song song){
        if(song.getArtist() != null){
            return SongDtoOut.builder().id(song.getId()).title(song.getTitle()).album(song.getAlbum()).year(song.getYear()).artist(song.getArtist().getName()).build();
        }
        else{
            return SongDtoOut.builder().id(song.getId()).title(song.getTitle()).album(song.getAlbum()).year(song.getYear()).artist(null).build();
        }

    }

    public SongDtoModifiedArtist toSongDtoModifiedArtist(Song s){
        return SongDtoModifiedArtist.builder().album(s.getAlbum()).title(s.getTitle()).year(s.getYear()).id(s.getId()).artist(mapperArtista.toArtistDto(s.getArtist())).build();
    }
}
