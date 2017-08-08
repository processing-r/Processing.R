
// Generated by hack/generate-e2e-test.py
package test.e2e.core.function;

import static org.junit.Assert.fail;
import org.junit.Test;
import test.e2e.core.E2eTestBase;

public class Alpha1Test extends E2eTestBase {

  public Alpha1Test() {
    coreCodeTemplate = "noStroke()\nc = color(0, 126, 255, 102)\nfill(c)\nrect(15, 15, 35, 70)\nvalue = alpha(c) # Sets 'value' to 102\nfill(value)\nrect(50, 15, 35, 70)\n\nsaveFrame(\"%s\")\nexit()\n";
    referenceURI = "https://processing.org/reference/images/alpha_.png";
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
