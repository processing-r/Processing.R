
// Generated by hack/generate-e2e-test.py
package test.e2e.core.function;

import static org.junit.Assert.fail;
import org.junit.Test;
import test.e2e.core.E2eTestBase;

public class Blue1Test extends E2eTestBase {

  public Blue1Test() {
    coreCodeTemplate = "c = color(175, 100, 220)  # Define color 'c'\nfill(c)  # Use color variable 'c' as fill color\nrect(15, 20, 35, 60)  # Draw left rectangle\n\nblueValue = blue(c)  # Get blue in 'c'\nprintln(blueValue)  # Prints '220.0'\nfill(0, 0, blueValue)  # Use 'blueValue' in new fill\nrect(50, 20, 35, 60)  # Draw right rectangle\n\nsaveFrame(\"%s\")\nexit()\n";
    referenceURI = "https://processing.org/reference/images/blue_.png";
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
