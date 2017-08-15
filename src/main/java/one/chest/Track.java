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

import org.json.JSONObject;

import java.util.Objects;

public class Track {

    private final TrackLocation trackLocation;
    private final String artist;
    private final String name;

    Track(TrackLocation trackLocation, String artist, String song) {
        this.trackLocation = Objects.requireNonNull(trackLocation);
        this.artist = Objects.requireNonNull(artist);
        this.name = Objects.requireNonNull(song);
    }

    public static Track fromJson(String artist, JSONObject i) {
        int albumId = ((JSONObject) i.getJSONArray("albums").get(0)).getInt("id");
        int trackId = i.getInt("id");
        TrackLocation trackLocation = new TrackLocation(albumId, trackId);
        return new Track(trackLocation, artist, i.getString("title"));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Track)) {
            return false;
        }
        Track other = (Track) obj;
        return ((artist != null && artist.equals(other.artist)) || other.artist == null) &&
                ((name != null && name.equals(other.name)) || other.name == null) &&
                (trackLocation != null && trackLocation.equals(other.trackLocation) || other.trackLocation == null);
    }

    @Override
    public String toString() {
        return String.format("[%s]%s - %s", trackLocation, artist, name);
    }
}
