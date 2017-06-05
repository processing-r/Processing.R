package test.e2e.core;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import rprocessing.Runner;
import test.e2e.util.ImageUtils;

public abstract class E2eTestBase {

  protected String code;

  protected abstract void testInternal() throws Exception;

  protected String referenceImage(String path) throws MalformedURLException {
    return "https://processing.org/reference/images/" + path;
  }

  protected float diffImage(File file, String fileURL) throws MalformedURLException, IOException {
    return ImageUtils.DiffImage(file, new URL(fileURL));
  }

  protected float diffImageWithProcessingReference(File file, String referenceURL)
      throws MalformedURLException, IOException {
    return diffImage(file, referenceImage(referenceURL));
  }

  protected void runSketch(String path) throws Exception {
    String[] args = new String[] {path};
    Runner.main(args);
  }

  protected void operation() throws Exception {
    testInternal();
    System.out.println("\n## Code to test\n");
    System.out.println(code);
  }
}
