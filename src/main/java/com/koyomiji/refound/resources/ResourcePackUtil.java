package com.koyomiji.refound.resources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ResourcePackUtil {
  public static ByteArrayInputStream toPNGByteArrayInputStream(BufferedImage image) throws IOException {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    ImageIO.write(image, "png", os);
    return new ByteArrayInputStream(os.toByteArray());
  }
}
