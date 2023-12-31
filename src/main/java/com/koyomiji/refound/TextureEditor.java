package com.koyomiji.refound;

import java.awt.image.BufferedImage;

public class TextureEditor {
  public static BufferedImage offsetImage(BufferedImage image, int x, int y) {
    BufferedImage newImage = new BufferedImage(
        image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

    for (int i = 0; i < image.getWidth(); i++) {
      int newX = i + x;
      if (newX < 0 || newX >= image.getWidth()) {
        continue;
      }
      for (int j = 0; j < image.getHeight(); j++) {
        int newY = j + y;
        if (newY < 0 || newY >= image.getHeight()) {
          continue;
        }
        newImage.setRGB(newX, newY, image.getRGB(i, j));
      }
    }

    return newImage;
  }
}
