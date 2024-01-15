package com.koyomiji.refound.asset;

import com.koyomiji.refound.util.IOUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class FileInjector {
  private File archiveFile;
  private Map<String, byte[]> filesToAdd = new HashMap<>();

  public FileInjector(File archiveFile) { this.archiveFile = archiveFile; }

  public void add(String path, byte[] data) { filesToAdd.put(path, data); }

  public boolean exists(String path) {
    if (archiveFile.isFile()) {
      try {
        JarFile jarFile = new JarFile(archiveFile);
        JarEntry entry = jarFile.getJarEntry(path);
        jarFile.close();
        return entry != null;
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else {
      return archiveFile.toPath().resolve(path).toFile().exists();
    }
  }

  public void commit() {
    if (archiveFile.isFile()) {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();

      try {
        JarFile jarFile = new JarFile(archiveFile);
        JarOutputStream jos = new JarOutputStream(baos);

        Enumeration<JarEntry> entries = jarFile.entries();

        while (entries.hasMoreElements()) {
          JarEntry entry = entries.nextElement();
          if (!entry.isDirectory() &&
              !filesToAdd.containsKey(entry.getName())) {
            jos.putNextEntry(entry);
            jos.write(IOUtil.readAllBytes(jarFile.getInputStream(entry)));
            jos.closeEntry();
          }
        }

        for (Map.Entry<String, byte[]> asset : filesToAdd.entrySet()) {
          jos.putNextEntry(new JarEntry(asset.getKey()));
          jos.write(asset.getValue());
          jos.closeEntry();
        }

        jos.close();
        jarFile.close();

        Files.write(archiveFile.toPath(), baos.toByteArray());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else {
      // Dev env

      for (Map.Entry<String, byte[]> asset : filesToAdd.entrySet()) {
        try {
          Path dest = archiveFile.toPath().resolve(asset.getKey());
          Files.createDirectories(dest.getParent());
          Files.write(dest, asset.getValue());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }

    filesToAdd.clear();
  }
}
