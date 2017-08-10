
// Generated by hack/generate-e2e-test.py
package test.e2e.core.function;

import static org.junit.Assert.fail;
import org.junit.Test;
import test.e2e.core.E2eTestBase;

public class BezierPoint1Test extends E2eTestBase {

  public BezierPoint1Test() {
    coreCodeTemplate = "# bezierPoint 1 https://processing.org/reference/bezierPoint_.html\n\nnoFill()\nbezier(85, 20, 10, 10, 90, 90, 15, 80)\nfill(255)\nsteps <- 10\nfor (i in 0:steps) {\n    t <- i/steps\n    x = bezierPoint(85, 10, 90, 15, t)\n    y = bezierPoint(20, 10, 90, 80, t)\n    ellipse(x, y, 5, 5)\n}\n\nsaveFrame(\"%s\")\nexit()\n";
    referenceURI = "https://processing.org/reference/images/bezierPoint_.png";
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