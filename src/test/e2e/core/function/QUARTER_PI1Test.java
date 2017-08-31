
// Generated by hack/generate-e2e-test.py
package test.e2e.core.function;

import static org.junit.Assert.fail;
import org.junit.Test;
import test.e2e.core.E2eTestBase;

public class QUARTER_PI1Test extends E2eTestBase {

  public QUARTER_PI1Test() {
    coreCodeTemplate = "x = width/2\ny = height/2\nd = width * 0.8\narc(x, y, d, d, 0, QUARTER_PI)\narc(x, y, d - 20, d - 20, 0, HALF_PI)\narc(x, y, d - 40, d - 40, 0, PI)\narc(x, y, d - 60, d - 60, 0, TWO_PI)\n\nsaveFrame(\"%s\")\nexit()\n";
    referenceURI = "https://processing.org/reference/images/QUARTER_PI.png";
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