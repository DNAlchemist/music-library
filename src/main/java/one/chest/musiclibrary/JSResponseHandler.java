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

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.api.scripting.ScriptUtils;
import one.chest.musiclibrary.exception.MusicLibraryInternalException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

class JSResponseHandler {

    private ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

    JSResponseHandler() {
        BiFunction<ScriptObjectMirror, ScriptObjectMirror, Object[]> consumer = JSResponseHandler::suggest;
        engine.put("suggest", consumer);
    }

    private static Object[] suggest(ScriptObjectMirror arr, ScriptObjectMirror junk) {
        return (Object[]) ScriptUtils.convert(arr.getSlot(1), Object[].class);
    }

    List<String> parseSuggestionToList(String js) {
        try {
            Object[] scriptResponse = (Object[]) engine.eval(js);

            return Arrays.stream(scriptResponse)
                    .filter(Objects::nonNull)
                    .map(String::valueOf)
                    .collect(Collectors.toList());

        } catch (ScriptException | ClassCastException e) {
            throw new MusicLibraryInternalException("Can't handle js: " + js, e);
        }
    }

}
