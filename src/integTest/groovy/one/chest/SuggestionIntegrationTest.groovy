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

import groovy.transform.CompileStatic
import org.junit.Test

@CompileStatic
class SuggestionIntegrationTest {

    @Test
    void testSuggestStartsWith() {
        MusicLibrary lib = new MusicLibraryImpl('https://suggest-music.yandex.ru')
        def searchResult = lib.suggest("Robert Johnson")
        assert searchResult.sort() == [
                'Robert johnson',
                'Robert johnson - come on in my kitchen',
                'Robert johnson - cross road blues',
                'Robert johnson - crossroad blues',
                'Robert johnson - from four until late',
                'Robert johnson - little queen of spades',
                'Robert johnson - love in vain',
                'Robert johnson - me and the devil blues',
                'Robert johnson - sweet home chicago',
                'Robert johnson - the complete recordings'
        ]
    }

    @Test
    void testGuess() {
        MusicLibrary lib = new MusicLibraryImpl('https://suggest-music.yandex.ru')
        def searchResult = lib.suggest("REM its the")
        assert searchResult == ['R.e.m. - its the end of the world as we know it and i feel fine']
    }

}
