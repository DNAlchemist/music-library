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

import org.apache.tika.Tika
import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.ParseContext
import org.apache.tika.parser.mp3.Mp3Parser
import org.junit.Test
import org.xml.sax.helpers.DefaultHandler

public class GetTrackLocationIntegrationTest {

    @Test
    void getDownloadTrackFromLocation() {
        def musicLibrary = new MusicLibraryImpl('https://music.yandex.ru');
        def trackLocation = new TrackLocation(4766, 57703)

        musicLibrary.fetchInputStream(trackLocation).withCloseable { is ->
            assert new Tika().detect(is) == "audio/mpeg"

            new Metadata().with { metadata ->
                new Mp3Parser().parse(is, new DefaultHandler(), metadata, new ParseContext())
                assert metadata['channels'] == '2'
                assert metadata['samplerate'] == '44100'
                assert metadata['version'] == 'MPEG 3 Layer III Version 1'
            }

        }
    }

}
