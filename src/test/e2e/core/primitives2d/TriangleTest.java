package test.e2e.core.primitives2d;

import static org.junit.Assert.fail;
import java.io.File;
import org.junit.Test;
import test.e2e.core.E2eTestBase;
import test.e2e.util.TempFile;

/**
 * 
 * @author github.com/gaocegege
 */
public class TriangleTest extends E2eTestBase {
  private static String CORE_CODE = "processing$triangle(30, 75, 58, 20, 86, 75)";
  private static String SAVE_CODE_TEMPLATE = "processing$saveFrame(\"%s\")\nprocessing$exit()";

  @Override
  protected void testInternal() throws Exception {
    File saveFile = TempFile.createTempImage();
    code = assembleCode(saveFile.getCanonicalPath());
    File sketchFile = TempFile.createSketchTempFile(code);

    this.runSketch(sketchFile.getCanonicalPath());
    System.out.println(diffImageWithProcessingReference(saveFile, "triangle_.png"));
  }

  private String assembleCode(String filename) {
    return CORE_CODE + "\n" + String.format(SAVE_CODE_TEMPLATE, filename);
  }

  @Test
  public void test() {
    try {
      operation();
    } catch (Exception exception) {
      System.err.println(exception);
      fail("Should not have thrown any exception");
    }
  }
}
