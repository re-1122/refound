package com.koyomiji.refound.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtil {
  public static byte[] readAllBytes(InputStream is) throws IOException {
    final int bufSize = 1024;
    byte[] buf = new byte[bufSize];
    int read = 0;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    while ((read = is.read(buf, 0, bufSize)) != -1) {
      baos.write(buf, 0, read);
    }

    return baos.toByteArray();
  }
}
