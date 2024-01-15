package com.koyomiji.refound.resources;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ResourcePackUtil {
  public static ByteArrayInputStream
  toPNGByteArrayInputStream(BufferedImage image) throws IOException {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    ImageIO.write(image, "png", os);
    return new ByteArrayInputStream(os.toByteArray());
  }
}
