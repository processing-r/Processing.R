package test.e2e.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import processing.core.PApplet;
import processing.core.PImage;

/**
 *
 * @author github.com/gaocegege
 */
public class ImageUtils {

  public static float diffImage(File actualFile, URL testURL) throws IOException {
    PImage actualImage = new PImage(ImageIO.read(actualFile));
    PImage testImage = new PImage(ImageIO.read(testURL));

    return imgDifference(actualImage, testImage);
  }

  private static float imgDifference(PImage i0, PImage i1) {
    float diff = 0;
    i0.loadPixels();
    int[] ip0 = i0.pixels;
    i1.loadPixels();
    int[] ip1 = i1.pixels;
    for (int n = 0; n < ip0.length; n++) {
      int pxl0 = ip0[n];
      int r0, g0, b0;
      r0 = (pxl0 >> 20) & 0xF;
      g0 = (pxl0 >> 12) & 0xF;
      b0 = (pxl0 >> 4) & 0xF;
      int pxl1 = ip1[n];
      int r1, g1, b1;
      r1 = (pxl1 >> 20) & 0xF;
      g1 = (pxl1 >> 12) & 0xF;
      b1 = (pxl1 >> 4) & 0xF;
      diff += PApplet.abs(r0 - r1) + PApplet.abs(g0 - g1) + PApplet.abs(b0 - b1);
    }
    // Each colour channel can have a difference 0-15
    // Considering 3 colour channels (ignoring alpha)
    return diff / (ip0.length * 3 * 15);
  }
}
