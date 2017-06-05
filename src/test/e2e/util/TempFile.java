package test.e2e.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;

/**
 * TempFile creates the tempfile and delete it when exits.
 * 
 * @author github.com/gaocegege
 */
public class TempFile {
  private static String SKETCH_PREFIX = "Processing.R.";
  private static String SKETCH_SUFFIX = ".tmp.rpde";
  private static String PNG_SUFFIX = ".tmp.png";

  public static File createTempImage() throws IOException {
    // this temporary file remains after the jvm exits.
    File tempFile = File.createTempFile(SKETCH_PREFIX, PNG_SUFFIX);
    tempFile.deleteOnExit();

    return tempFile;
  }

  public static File createSketchTempFile(String code) throws IOException {
    // this temporary file remains after the jvm exits.
    File tempFile = File.createTempFile(SKETCH_PREFIX, SKETCH_SUFFIX);
    tempFile.deleteOnExit();

    FileUtils.writeStringToFile(tempFile, code, Charset.defaultCharset());
    return tempFile;
  }
}
