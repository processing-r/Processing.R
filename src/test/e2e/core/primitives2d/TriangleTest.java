package test.e2e.core.primitives2d;

import static org.junit.Assert.fail;
import org.junit.Test;
import test.e2e.core.E2eTestBase;

/**
 * 
 * @author github.com/gaocegege
 */
public class TriangleTest extends E2eTestBase {

  public TriangleTest() {
    coreCode = "processing$triangle(30, 75, 58, 20, 86, 75)";
    referenceURI = "triangle_.png";
  }

  @Test
  public void test() {
    try {
      defaultOperation();
    } catch (Exception exception) {
      System.err.println(exception);
      fail("Should not have thrown any exception");
    }
  }
}
