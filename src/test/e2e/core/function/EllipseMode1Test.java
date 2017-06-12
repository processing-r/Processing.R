
// Generated by hack/generate-e2e-test.py
package test.e2e.core.function;

import static org.junit.Assert.fail;
import org.junit.Test;
import test.e2e.core.E2eTestBase;

public class EllipseMode1Test extends E2eTestBase {

  public EllipseMode1Test() {
    coreCodeTemplate = "CORNERS <- as.integer(1)\nCORNER <- as.integer(0)\n\nellipseMode(CORNER)  # Set ellipseMode is CORNER\nfill(255)  # Set fill to white\nellipse(25, 25, 50, 50)  # Draw white ellipse using CORNER mode\n\nellipseMode(CORNERS)  # Set ellipseMode to CORNERS\nfill(100)  # Set fill to gray\nellipse(25, 25, 50, 50)  # Draw gray ellipse using CORNERS mode\n\nsaveFrame(\"%s\")\nexit()\n";
    referenceURI = "https://processing.org/reference/images/ellipseMode_1.png";
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
