package com.koyomiji.refound.asset;

import java.net.MalformedURLException;
import java.net.URL;

public class AssetIdentifier {
  public URL url;
  public String sha1;
  public int size;

  public AssetIdentifier(String url, String sha1, int size) {
    try {
      this.url = new URL(url);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }

    this.sha1 = sha1;
    this.size = size;
  }

  public URL getURL() { return url; }

  public String getSHA1() { return sha1; }

  public int getSize() { return size; }
}
