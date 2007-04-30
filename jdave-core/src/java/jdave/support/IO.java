/*
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jdave.support;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Joni Freeman
 */
public class IO {
    public static String read(InputStream stream) throws IOException {
        BufferedInputStream in = new BufferedInputStream(stream);
        byte[] buf = new byte[4096];
        ByteArrayOutputStream contents = new ByteArrayOutputStream();
        int len = 0;
        while ((len = in.read(buf)) != -1) {
            contents.write(buf, 0, len);
        }
        return contents.toString();
    }

    public static void write(File file, String content) throws IOException {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            BufferedOutputStream contents = new BufferedOutputStream(out);
            contents.write(content.getBytes());
            contents.flush();
        } finally {
            close(out);
        }
    }

    private static void close(OutputStream out) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
            }
        }
    }
}
