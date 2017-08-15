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
package one.chest

import org.junit.Test

public class GetTrackLocationIntegrationTest {

    @Test
    void getTrackLocation() {
        def musicLibrary = new MusicLibraryImpl('https://music.yandex.ru');
        def trackLocation = new TrackLocation(4766, 57703)
        def uri = musicLibrary.getTrackURI(trackLocation)
        assert uri && uri.toString().startsWith("https://storage.mds.yandex.net/file-download-info/53090_49160231.49166739.1.57703/2?sign=")
    }

    @Test(expected = InvalidTrackLocationException)
    void getTrackInvalidLocation() {
        def musicLibrary = new MusicLibraryImpl('https://music.yandex.ru');
        def trackLocation = new TrackLocation(57703, 4766)
        musicLibrary.getTrackURI(trackLocation)
    }

}
