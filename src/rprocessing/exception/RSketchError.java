package rprocessing.exception;

import org.renjin.eval.EvalException;

/**
 * Error type for Processing.R
 * 
 * @author github.com/gaocegege
 */
public class RSketchError extends Exception {

  private static final long serialVersionUID = 6563629093797155634L;
  public final String fileName;
  public final int line;
  public final int column;

  private static final boolean VERBOSE = Boolean.parseBoolean(System.getenv("VERBOSE_RLANG_MODE"));

  private static void log(String msg) {
    if (!VERBOSE) {
      return;
    }
    System.err.println(RSketchError.class.getSimpleName() + ": " + msg);
  }

  public RSketchError(final String message) {
    this(message, null);
  }

  public RSketchError(final String message, final String fileName) {
    this(message, fileName, -1, -1);
  }

  public RSketchError(final String message, final String fileName, final int line) {
    this(message, fileName, line, 0);
  }

  public RSketchError(final String message, final String fileName, final int line,
      final int column) {
    super(message);

    this.fileName = fileName;
    this.line = line;
    this.column = column;
  }

  @Override
  public String toString() {
    if (fileName == null) {
      return getMessage();
    }
    if (line == -1) {
      return getMessage() + " in " + fileName;
    }
    if (column == -1) {
      return getMessage() + " at line " + line + " of " + fileName;
    }
    return getMessage() + " at " + line + ":" + column + " in " + fileName;
  }

  public static RSketchError toSketchException(Throwable t) {
    if (t instanceof RuntimeException && t.getCause() != null) {
      t = t.getCause();
    }
    if (t instanceof RSketchError) {
      return (RSketchError) t;
    }
    if (t instanceof EvalException) {
      final RSketchError e = (RSketchError) t;
      return e;
    }
    if (t instanceof ClassCastException) {
      final RSketchError e = (RSketchError) t;
      return e;
    }
    log("No exception type detected.");
    return null;
  }
}
