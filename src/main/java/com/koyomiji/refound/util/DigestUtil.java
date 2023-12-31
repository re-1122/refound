package com.koyomiji.refound.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestUtil {
  public static String sha1(byte[] data) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-1");
      byte[] digest = md.digest(data);
      return String.format("%x", new BigInteger(1, digest));
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  public static void assertSHA1(byte[] data, String expected) {
    String actual = sha1(data);
    if (!actual.equals(expected)) {
      throw new RuntimeException("SHA-1 mismatch: expected " + expected +
                                 ", got " + actual);
    }
  }

  public static boolean testSHA1(byte[] data, String expected) {
    String actual = sha1(data);
    return actual.equals(expected);
  }
}
