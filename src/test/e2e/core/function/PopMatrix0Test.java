
// Generated by hack/generate-e2e-test.py
package test.e2e.core.function;

import static org.junit.Assert.fail;
import org.junit.Test;
import test.e2e.core.E2eTestBase;

public class PopMatrix0Test extends E2eTestBase {

  public PopMatrix0Test() {
    coreCodeTemplate = "fill(255)\nrect(0, 0, 50, 50)  # White rectangle\n\npushMatrix()\ntranslate(30, 20)\nfill(0)  \nrect(0, 0, 50, 50)  # Black rectangle\npopMatrix()\n\nfill(100)  \nrect(15, 10, 50, 50)  # Gray rectangle\n\nsaveFrame(\"%s\")\nexit()\n";
    referenceURI = "https://processing.org/reference/images/popMatrix_.png";
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
