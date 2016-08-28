/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package rprocessing.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * RScriptReader
 * Read the script from r package.
 * 
 * @author github.com/gaocegege
 */
public class RScriptReader {

    private static final Charset UTF8 = Charset.forName("utf-8");

    public static class ResourceReader {
        private final Class<?> clazz;

        public ResourceReader(final Class<?> clazz) {
            this.clazz = clazz;
        }

        public String readText(final String resource) {
            return RScriptReader.readResourceAsText(clazz, resource);
        }
    }

    public static String readResourceAsText(final Class<?> clazz, final String resource) {
        try (final InputStream in = clazz.getResourceAsStream(resource)) {
            return readText(in);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readText(final InputStream in) throws IOException {
        return new String(readFully(in), UTF8);
    }

    public static byte[] readFully(final InputStream in) throws IOException {
        try (final ByteArrayOutputStream bytes = new ByteArrayOutputStream(1024)) {
            final byte[] buf = new byte[1024];
            int n;
            while ((n = in.read(buf)) != -1) {
                bytes.write(buf, 0, n);
            }
            return bytes.toByteArray();
        }
    }
}
