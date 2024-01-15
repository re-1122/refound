package com.koyomiji.refound.asset;

import com.koyomiji.refound.ReFound;
import com.koyomiji.refound.util.DigestUtil;
import com.koyomiji.refound.util.IOUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public class AssetFetcher {
  private Path cacheDir;
  private SSLContext sslContext;

  public AssetFetcher(Path cacheDir) {
    this.cacheDir = cacheDir;

    try {
      this.sslContext = SSLContext.getInstance("SSL");
      this.sslContext.init(
          null, new TrustManager[] {new CertificateIgnorantTrustManager()},
          null);

    } catch (NoSuchAlgorithmException | KeyManagementException e) {
      throw new RuntimeException(e);
    }
  }

  public AssetFetcher(File cacheDir) { this(cacheDir.toPath()); }

  public AssetFetcher() { this(ReFound.proxy.getCacheDir()); }

  public Path getCachePath(AssetIdentifier identifier) {
    if (cacheDir == null) {
      throw new RuntimeException("Cache directory not set");
    }

    return cacheDir.resolve(identifier.getSHA1());
  }

  public void addCache(AssetIdentifier identifier, byte[] data) {
    if (cacheDir == null) {
      throw new RuntimeException("Cache directory not set");
    }

    Path path = cacheDir.resolve(identifier.getSHA1());

    try {
      // Assuming the digest is unique enough, just overwrite
      Files.createDirectories(path.getParent());
      Files.write(path, data);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public byte[] queryCache(AssetIdentifier identifier) {
    if (cacheDir == null) {
      return null;
    }

    Path path = cacheDir.resolve(identifier.getSHA1());

    if (!Files.exists(path)) {
      return null;
    }

    byte[] bytes = null;

    try {
      bytes = Files.readAllBytes(path);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    if (!DigestUtil.testSHA1(bytes, identifier.getSHA1()) ||
        !testSize(identifier, bytes)) {
      return null;
    }

    return bytes;
  }

  public byte[] fetch(AssetIdentifier identifier) {
    byte[] cached = queryCache(identifier);

    if (cached != null) {
      return cached;
    }

    HttpsURLConnection con = null;

    try {
      con = (HttpsURLConnection)identifier.url.openConnection();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    con.setSSLSocketFactory(sslContext.getSocketFactory());
    con.setAllowUserInteraction(false);
    con.setInstanceFollowRedirects(true);

    try {
      con.setRequestMethod("GET");
    } catch (ProtocolException e) {
      throw new RuntimeException(e);
    }

    try {
      con.connect();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    int code = 0;
    InputStream is = null;
    byte[] data = null;

    try {
      code = con.getResponseCode();

      if (code != HttpURLConnection.HTTP_OK) {
        throw new RuntimeException("Failed to fetch asset. Status code: " +
                                   code);
      }

      is = con.getInputStream();
      data = IOUtil.readAllBytes(is);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    assertSize(identifier, data);
    DigestUtil.assertSHA1(data, identifier.getSHA1());

    addCache(identifier, data);

    return data;
  }

  private void assertSize(AssetIdentifier identifier, byte[] data) {
    if (data.length != identifier.getSize()) {
      throw new RuntimeException(
          "Asset size mismatch. Expected: " + identifier.getSize() +
          ", Actual: " + data.length);
    }
  }

  private boolean testSize(AssetIdentifier identifier, byte[] data) {
    return data.length == identifier.getSize();
  }

  public Path fetchAndGetPath(AssetIdentifier identifier) {
    byte[] data = fetch(identifier);
    return getCachePath(identifier);
  }
}
