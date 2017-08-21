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
package one.chest.musiclibrary

import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import org.json.JSONObject
import org.junit.Test

@CompileStatic
public class TrackExtractorTest {

    @Test
    public void fromJSON() {
        def trackExtractor = new TrackExtractor("Kasabian")
        def track = new JsonSlurper().parseText(getClass().classLoader.getResource("track.json").text) as JSONObject
        assert trackExtractor.filterByArtistName(track)
    }

    @Test
    public void fromJSONInvalidArtist() {
        def trackExtractor = new TrackExtractor("Queen of the stone age")
        def track = new JsonSlurper().parseText(getClass().classLoader.getResource("track.json").text) as JSONObject
        assert !trackExtractor.filterByArtistName(track)
    }

    @Test
    public void filterByArtistName() {
        def trackExtractor = new TrackExtractor("Kasabian")
        def tracks = new JsonSlurper().parseText(getClass().classLoader.getResource("tracks.json").text) as JSONObject
        assert trackExtractor.fromJSON(tracks) == [
                new TrackImpl(new TrackLocation(70149, 651152), "Kasabian", "Underdog")
        ]
    }
}
