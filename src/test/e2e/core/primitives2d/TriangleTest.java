package test.e2e.core.primitives2d;

import static org.junit.Assert.fail;

import java.io.File;
import java.net.URL;

import org.junit.Test;

import rprocessing.Runner;
import test.e2e.util.ImageUtils;
import test.e2e.util.TempFile;

/**
 * 
 * @author github.com/gaocegege
 */
public class TriangleTest {
  private static String CORE_CODE = "processing$triangle(30, 75, 58, 20, 86, 75)";
  private static String SAVE_CODE_TEMPLATE =
      "processing$saveFrame(\"%s\")\nprocessing$exit()";

  @Test
  public void test() {
    try {
      File saveFile = TempFile.createTempImage();

      String code = assembleCode(saveFile.getCanonicalPath());
      System.out.println(code);
      File sketchFile = TempFile.createSketchTempFile(code);

      String[] args = new String[] {sketchFile.getCanonicalPath()};
      Runner.main(args);

      float diff =
          ImageUtils.DiffImage(saveFile, new URL(
              "https://processing.org/reference/images/triangle_.png"));
      System.out.println(diff);
    } catch (Exception exception) {
      System.err.println(exception);
      fail("Should not have thrown any exception");
    }
  }

  private String assembleCode(String filename) {
    return CORE_CODE + "\n" + String.format(SAVE_CODE_TEMPLATE, filename);
  }
}
