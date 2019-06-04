package com.example.muslibry5k.services.integrations;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.albums.GetAlbumRequest;
import com.wrapper.spotify.requests.data.albums.GetAlbumsTracksRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchAlbumsRequest;
import com.wrapper.spotify.requests.data.tracks.GetSeveralTracksRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SpotifyService {

    private final String CLIENT_ID = "gain-your-client-id-from-spotify-for-developers";
    private final String CLIENT_SECRET = "gain-your-client-secret-from-spotify-for-developers";

    private final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(CLIENT_ID)
            .setClientSecret(CLIENT_SECRET)
            .build();

    private final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();

    private ClientCredentials clientCredentials = null;

    public Album getAlbum(String id){

        getOrRefreshToken();
        GetAlbumRequest getAlbumRequest = spotifyApi.getAlbum(id).build();
        Album album = null;

        try {
            album = getAlbumRequest.execute();

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return album;
    }

    public Track[] getAlbumsTracks(String albumId){

        getOrRefreshToken();
        GetAlbumsTracksRequest getAlbumsTracksRequest = spotifyApi.getAlbumsTracks(albumId).build();
        TrackSimplified[] albumsTracksSimplified = new TrackSimplified[0];
        Track[] tracks = new Track[0];

        try {
            final Paging<TrackSimplified> trackSimplifiedPaging = getAlbumsTracksRequest.execute();
            if (trackSimplifiedPaging.getTotal() > 0) {
                albumsTracksSimplified = trackSimplifiedPaging.getItems();

                String[] ids = new String[albumsTracksSimplified.length];

                for (int i = 0; i < ids.length; i++) {
                    ids[i] = albumsTracksSimplified[i].getId();
                }

                GetSeveralTracksRequest getSeveralTracksRequest = spotifyApi.getSeveralTracks(ids).build();
                tracks = getSeveralTracksRequest.execute();

            }
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return tracks;
    }

    public AlbumSimplified[] getAlbums(String query) {

        getOrRefreshToken();
        SearchAlbumsRequest searchAlbumsRequest = spotifyApi.searchAlbums(query).build();
        Paging<AlbumSimplified> albumSimplifiedPaging = null;

        try {
            albumSimplifiedPaging = searchAlbumsRequest.execute();

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return albumSimplifiedPaging.getItems();
    }

    public void getOrRefreshToken() {
        if (clientCredentials == null) {
            try {
                clientCredentials = clientCredentialsRequest.execute();
            } catch (IOException | SpotifyWebApiException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        if (spotifyApi.getAccessToken() == null || clientCredentials.getExpiresIn() < 1) {
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
        }
    }

}
