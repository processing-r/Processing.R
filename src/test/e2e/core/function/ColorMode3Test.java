
// Generated by hack/generate-e2e-test.py
package test.e2e.core.function;

import static org.junit.Assert.fail;
import org.junit.Test;
import test.e2e.core.E2eTestBase;

public class ColorMode3Test extends E2eTestBase {

  public ColorMode3Test() {
    coreCodeTemplate = "# If the color is defined here, it won't be affected by the colorMode() in\n# setup().  Instead, just declare the variable here and assign the value after\n# the colorMode() in setup() color bg = color(180, 50, 50); # No\n\nbg <- NULL\n\nsetup <- function() {\n    size(100, 100)\n    colorMode(HSB, 360, 100, 100)\n    bg = color(180, 50, 50)\n}\n\ndraw <- function() {\n    background(bg)\n    saveFrame(\"%s\")\n    exit()\n}\n";
    referenceURI = "https://processing.org/reference/images/colorMode_2.png";
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
