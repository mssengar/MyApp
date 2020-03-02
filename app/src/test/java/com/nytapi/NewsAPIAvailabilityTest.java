package com.nytapi;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class NewsAPIAvailabilityTest {
    @Test
    public void testAPIAvailability() throws Exception {
        URLConnection connection = new URL(BuildConfig.BASE_URL+"1.json?api-key="+"aTve5PkTXY09Impz3enmjMP0A4dQz2iT").openConnection();
        InputStream response = connection.getInputStream();
        StringBuffer buffer = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response, Charset.defaultCharset()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                buffer.append(line);
            }
        }
        assert buffer.length() > 0;
    }
}