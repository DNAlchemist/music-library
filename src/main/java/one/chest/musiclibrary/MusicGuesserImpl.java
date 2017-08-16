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
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.List;
import java.util.stream.Collectors;

public final class MusicGuesserImpl implements MusicGuesser {

    private final String host;
    private final JSResponseHandler jsResponseHandler = new JSResponseHandler();

    MusicGuesserImpl(String libraryHost) {
        this.host = libraryHost;
    }

    static String formatSong(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    @Override
    public List<String> suggest(String part) {
        try {
            HttpResponse<String> js = Unirest.get(host.concat("/suggest-ya.cgi"))
                    .queryString("part", part)
                    .asString();
            return jsResponseHandler.parseSuggestionToList(js.getBody())
                    .stream()
                    .filter(s -> s.length() > 1)
                    .map(MusicGuesserImpl::formatSong)
                    .collect(Collectors.toList());

        } catch (UnirestException e) {
            throw new MusicLibraryInternalException("Error while suggest track", e);
        }
    }

}
