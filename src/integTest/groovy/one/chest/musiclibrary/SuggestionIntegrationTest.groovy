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

import groovy.transform.CompileStatic
import org.junit.Test

@CompileStatic
class SuggestionIntegrationTest {

    @Test
    void testSuggestStartsWith() {
        MusicGuesser lib = new MusicGuesserImpl('https://suggest-music.yandex.ru')
        List<String> searchResult = lib.suggest("The Marvelettes")
        assert searchResult.sort().containsAll([
                'The marvelettes',
                'The marvelettes - forever: the complete motown albums, volume 1',
                'The marvelettes - playboy',
                'The marvelettes - please mr. Postman',
                'The marvelettes - the definitive collection',
                'The marvelettes: лучшее',
        ])
    }

    @Test
    void testGuess() {
        MusicGuesser lib = new MusicGuesserImpl('https://suggest-music.yandex.ru')
        def searchResult = lib.suggest("REM its the")
        assert searchResult == ['R.E.M. - its the end of the world as we know it and i feel fine']
    }

}
