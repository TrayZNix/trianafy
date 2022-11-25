package com.salesianostriana.dam.trianafy.mappers;

import com.salesianostriana.dam.trianafy.dto.PlaylistDtoIn;
import com.salesianostriana.dam.trianafy.dto.PlaylistDtoOut;
import com.salesianostriana.dam.trianafy.dto.PlaylistDtoOutPCreate;
import com.salesianostriana.dam.trianafy.model.Playlist;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlaylistMapper {
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
}
