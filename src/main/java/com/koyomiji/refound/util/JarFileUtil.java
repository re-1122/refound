package com.koyomiji.refound.util;

import java.io.IOException;
import java.util.jar.JarFile;

public class JarFileUtil {
  public static byte[] readFile(JarFile jarFile, String filename) {
    try {
      return IOUtil.readAllBytes(
          jarFile.getInputStream(jarFile.getJarEntry(filename)));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
