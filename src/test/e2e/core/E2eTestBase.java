package test.e2e.core;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import rprocessing.Runner;
import test.e2e.util.ImageUtils;
import test.e2e.util.TempFile;

public abstract class E2eTestBase {

  protected static float THRESHOLD = (float) 0.01;
  protected static String SAVE_CODE_TEMPLATE = "processing$saveFrame(\"%s\")\nprocessing$exit()";

  protected String code;
  protected String coreCodeTemplate;
  protected String referenceURI;

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

  protected void defaultOperation() throws Exception {
    // Create temp sketch file and image file.
    File saveFile = TempFile.createTempImage();
    code = assembleCode(saveFile.getCanonicalPath());
    File sketchFile = TempFile.createSketchTempFile(code);
    System.out.println("## Code to test\n");
    System.out.println(code);

    // Run the sketch and save the image to saveFile.
    this.runSketch(sketchFile.getCanonicalPath());

    assert (diffImage(saveFile, referenceURI) < THRESHOLD);
  }

  protected String assembleCode(String filename) {
    return String.format(coreCodeTemplate, filename);
  }
}
