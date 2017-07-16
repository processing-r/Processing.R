
// Generated by hack/generate-e2e-test.py
package test.e2e.core.function;

import static org.junit.Assert.fail;
import org.junit.Test;
import test.e2e.core.E2eTestBase;

public class ApplyMatrix0Test extends E2eTestBase {

  public ApplyMatrix0Test() {
    coreCodeTemplate = "settings <- function() {\n    size(100, 100, P3D)\n}\n\ndraw <- function() {\n    noFill()\n    translate(50, 50, 0)\n    rotateY(PI/6)\n    stroke(153)\n    box(35)\n    # Set rotation angles\n    ct = cos(PI/9)\n    st = sin(PI/9)\n    # Matrix for rotation around the Y axis\n    applyMatrix(ct, 0, st, 0, 0, 1, 0, 0, -st, 0, ct, 0, 0, 0, 0, 1)\n    stroke(255)\n    box(50)\n    saveFrame(\"%s\")\n    exit()\n}\n";
    referenceURI = "https://processing.org/reference/images/applyMatrix_.png";
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
