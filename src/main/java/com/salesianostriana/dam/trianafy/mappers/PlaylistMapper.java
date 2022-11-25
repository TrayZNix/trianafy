package com.salesianostriana.dam.trianafy.mappers;

import com.salesianostriana.dam.trianafy.dto.*;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlaylistMapper {
    @Autowired
    private SongMapper songMapper;
    public List<PlaylistDtoOut> toPlaylistListDtoOut(List<Playlist> listaPlaylists){
        List<PlaylistDtoOut> playlistListDto = new ArrayList<PlaylistDtoOut>();
        listaPlaylists.forEach(playlist -> {
            playlistListDto.add(PlaylistDtoOut.builder().id(playlist.getId()).name(playlist.getName()).numberOfSongs(playlist.getSongs().size()).build());
        });
        return playlistListDto;
    }

    public PlaylistDtoOut toPlaylistDtoOut(Playlist playlist){
        return PlaylistDtoOut.builder().id(playlist.getId()).name(playlist.getName()).build();
    }

    public Playlist toPlaylist(PlaylistDtoIn playlistDto){
        return Playlist.builder().name(playlistDto.getName()).description(playlistDto.getDescription()).build();
    }

    public PlaylistDtoOutPCreate toPlaylistDtoOutPostCreate(Playlist playlist){
        return PlaylistDtoOutPCreate.builder().id(playlist.getId()).name(playlist.getName()).description(playlist.getDescription()).build();
    }
    public PlaylistDtoOutPCreateWSongs toPlaylistDtoOutPCreateWSongs(Playlist playlist){
        List<Song> cancionesPlaylist = playlist.getSongs();
        List<SongDtoOut> cancionesDtoArtista = new ArrayList<SongDtoOut>();
        cancionesPlaylist.forEach(cancion -> {
            cancionesDtoArtista.add(songMapper.toSongOut(cancion));

        });
        return PlaylistDtoOutPCreateWSongs.builder().id(playlist.getId()).description(playlist.getDescription()).name(playlist.getName()).songs(cancionesDtoArtista).build();

    }
}
