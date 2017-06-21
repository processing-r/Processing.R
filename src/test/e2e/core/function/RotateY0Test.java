
// Generated by hack/generate-e2e-test.py
package test.e2e.core.function;

import static org.junit.Assert.fail;
import org.junit.Test;
import test.e2e.core.E2eTestBase;

public class RotateY0Test extends E2eTestBase {

  public RotateY0Test() {
    coreCodeTemplate = "P3D <- \"processing.opengl.PGraphics3D\"\nPI <- pi\n\nsettings <- function() {\n    size(100, 100, P3D)\n}\n\ndraw <- function() {\n    translate(width/2, height/2)\n    rotateY(PI/3.0)\n    rect(-26, -26, 52, 52)\n    saveFrame(\"%s\")\n    exit()\n}\n";
    referenceURI = "https://processing.org/reference/images/rotateY_0.png";
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
