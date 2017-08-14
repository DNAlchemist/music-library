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

import org.intellij.lang.annotations.Language;
import org.junit.Test;

public class JSResponseHandlerTest {

    @Test
    public void parseSuggestionToList() {
        def jsHandler = new JSResponseHandler()

        @Language('js')
        String js = '''
            suggest.apply([
                "robert johnson",
                [
                    "robert johnson",
                    "robert johnson - from four until late",
                    "robert johnson - crossroad blues",
                    "robert johnson - sweet home chicago",
                    "robert johnson - love in vain",
                    "robert johnson - cross road blues",
                    "robert johnson - come on in my kitchen",
                    "robert johnson - me and the devil blues",
                    "robert johnson - the complete recordings",
                    "robert johnson - little queen of spades"
                ], []
            ],[])
        '''

        assert jsHandler.parseSuggestionToList(js).sort() == [
                'robert johnson',
                'robert johnson - come on in my kitchen',
                'robert johnson - cross road blues',
                'robert johnson - crossroad blues',
                'robert johnson - from four until late',
                'robert johnson - little queen of spades',
                'robert johnson - love in vain',
                'robert johnson - me and the devil blues',
                'robert johnson - sweet home chicago',
                'robert johnson - the complete recordings'
        ]
    }

    @Test
    public void parseEmptySuggestionToList() {
        def jsHandler = new JSResponseHandler()

        @Language('js')
        String js = 'suggest.apply(["impossible value", [], []], [])'

        assert jsHandler.parseSuggestionToList(js) == []
    }

    @Test(expected = MusicLibraryInternalException)
    public void illegalArgumentListSize() {
        def jsHandler = new JSResponseHandler()

        @Language('js')
        String js = 'suggest.apply(["impossible value", [], []])'

        jsHandler.parseSuggestionToList(js)
    }

    @Test(expected = MusicLibraryInternalException)
    public void missingSuggestionArgument() {
        def jsHandler = new JSResponseHandler()

        @Language('js')
        String js = 'suggest.apply(["impossible value"], [])'

        jsHandler.parseSuggestionToList(js)
    }
    
}
