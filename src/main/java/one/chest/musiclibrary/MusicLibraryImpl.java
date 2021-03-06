/*
 * MIT License
 *
 * Copyright (c) 2017 Mikhalev Ruslan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package one.chest.musiclibrary;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import one.chest.musiclibrary.exception.InvalidTrackLocationException;
import one.chest.musiclibrary.exception.MusicLibraryException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public final class MusicLibraryImpl implements MusicLibrary {

    private final String host;
    private final TrackLocationFetcher trackLocationFetcher;
    private final BasicCookieStore cookieStore = new BasicCookieStore();

    MusicLibraryImpl(String libraryHost) {
        this.host = libraryHost;
        this.trackLocationFetcher = new TrackLocationFetcher();
        Unirest.setHttpClient(createHttpClient());
        addCookie("UUID", UUID.randomUUID().toString());
    }

    private HttpClient createHttpClient() {
        return HttpClients.custom().setDefaultCookieStore(cookieStore).build();
    }

    private void addCookie(String name, String value) {
        BasicClientCookie cookie = new BasicClientCookie(name, value);
        cookie.setDomain(host);
        cookieStore.addCookie(cookie);
    }

    @Override
    public List<Track> searchTracks(String artist, String song) {
        try {
            HttpResponse<JsonNode> response = Unirest.get(host.concat("/handlers/music-search.jsx"))
                    .queryString("text", artist + " " + song)
                    .queryString("type", "track")
                    .queryString("nocookiesupport", "true")
                    .header("Accept-Language", "ru")
                    .header("X-Retpath-Y", host)
                    .asJson();
            JSONObject tracks = response.getBody().getObject().getJSONObject("tracks");
            return new TrackExtractor(artist).fromJSON(tracks);
        } catch (UnirestException e) {
            throw new MusicLibraryException("Error while search track", e);
        }
    }

    @Override
    public Optional<Track> searchTrack(String artist, String song) {
        List<Track> tracks = searchTracks(artist, song);
        return tracks.size() > 0 ? Optional.of(tracks.get(0)) : Optional.empty();
    }

    @Override
    public Track getTrack(TrackLocation trackLocation) {
        try {
            HttpResponse<JsonNode> response = Unirest.get(host.concat("/handlers/track.jsx?track={trackId}:{albumId}"))
                    .routeParam("trackId", String.valueOf(trackLocation.getTrackId()))
                    .routeParam("albumId", String.valueOf(trackLocation.getAlbumId()))
                    .header("Accept-Language", "ru")
                    .header("X-Retpath-Y", host)
                    .asJson();
            JSONObject track = response.getBody().getObject().getJSONObject("track");
            JSONArray artistNode = track.getJSONArray("artists");
            assert artistNode.length() == 1;
            String artistName = ((JSONObject) artistNode.get(0)).getString("name");
            return TrackImpl.fromJson(artistName, track);

        } catch (UnirestException e) {
            throw new MusicLibraryException("Error while fetching track metadata", e);
        }
    }

    @Override
    public InputStream fetchInputStream(Track track) {
        return fetchInputStream(track.getTrackLocation());
    }

    @Override
    public InputStream fetchInputStream(TrackLocation trackLocation) {
        try {
            String downloadLink = getDownloadLink(trackLocation);
            return Unirest.get(downloadLink)
                    .header("X-Retpath-Y", host)
                    .asBinary()
                    .getBody();

        } catch (UnirestException e) {
            throw new MusicLibraryException("Error while building track download URI", e);
        }
    }

    public String getDownloadLink(TrackLocation trackLocation) throws UnirestException {
        GetRequest request = Unirest.get(host.concat("/api/v2.1/handlers/track/{trackId}:{albumId}/web-feed-promotion-playlist-saved/download/m?hq=0"))
                .routeParam("trackId", String.valueOf(trackLocation.getTrackId()))
                .routeParam("albumId", String.valueOf(trackLocation.getAlbumId()))
                .header("Accept-Language", "ru")
                .header("X-Retpath-Y", host);

        HttpResponse<JsonNode> response = request.asJson();
        if (response.getStatus() == 403) {
            throw new InvalidTrackLocationException("Invalid location: " + request.getUrl());
        }

        if( response.getStatus() != 200) {
            throw new MusicLibraryException("Invalid response status: " + response.getStatus() + ". Reason: " + response.getStatusText());
        }

        String locationHolderURL = response.getBody().getObject().getString("src");
        JSONObject json = Unirest.get(locationHolderURL)
                .queryString("format", "json")
                .asJson()
                .getBody()
                .getObject();

        return trackLocationFetcher.createTrackURI(
                trackLocation.getTrackId(),
                json.getString("host"),
                json.getString("path"),
                json.getString("ts"),
                json.getString("s"));
    }

}
