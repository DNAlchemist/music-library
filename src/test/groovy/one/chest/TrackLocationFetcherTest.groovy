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

public class TrackLocationFetcherTest {

    @Test
    public void createTrackLocation() {
        def fetcher = new TrackLocationFetcher();
        assert fetcher.createTrackURI(
                57703,
                "s131f.storage.library.net",
                "/music/34/3/data-0.14:44307316719:7952090",
                "000556ce28f05a33",
                "0f622ee89ed744f3c31828c6d6f8f357f7582e5c7024fa332166085a9313a361"
        ) == "https://s131f.storage.library.net/get-mp3/9eff23a9ae8aedc1e5e4b55080e3df1e/000556ce28f05a33/music/34/3/data-0.14:44307316719:7952090?track-id=57703"
    }
}
