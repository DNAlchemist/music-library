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

import one.chest.musiclibrary.exception.MusicLibraryInternalException;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TrackLocationFetcher {

    private final MessageDigest signer;
    HexBinaryAdapter hexBinaryAdapter = new HexBinaryAdapter();

    TrackLocationFetcher() {
        try {
            this.signer = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new MusicLibraryInternalException("Signer initialization error", e);
        }
    }

    String createTrackURI(int trackId, String host, String path, String ts, String s) {
        String hash = md5("XGRlBW9FXlekgbPrRHuSiA" + path.substring(1) + s);
        return "https://" + host + "/get-mp3/" + hash + "/" + ts + path + "?track-id=" + trackId;
    }

    private String md5(String source) {
        try {
            return hexBinaryAdapter.marshal(signer.digest(source.getBytes("utf8"))).toLowerCase();
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("UTF8 encoding is unsupported!");
        }
    }
}
