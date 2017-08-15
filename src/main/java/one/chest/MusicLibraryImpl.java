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
package one.chest;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.util.List;
import java.util.UUID;


public final class MusicLibraryImpl implements MusicLibrary {

    private final String host;

    MusicLibraryImpl(String libraryHost) {
        this.host = libraryHost;
    }

    @Override
    public List<Track> searchTracks(String artist, String song) {
        try {
            HttpResponse<JsonNode> js = Unirest.get(host.concat("/handlers/music-search.jsx"))
                    .queryString("text", artist + " " + song)
                    .queryString("type", "track")
                    .header("Accept-Language", "ru")
                    .header("Cookie", UUID.randomUUID().toString())
                    .asJson();
            JSONObject tracks = js.getBody().getObject().getJSONObject("tracks");
            return new TrackExtractor(artist).fromJSON(tracks);
        } catch (UnirestException e) {
            throw new MusicLibraryInternalException("Error while search track", e);
        }
    }

}
