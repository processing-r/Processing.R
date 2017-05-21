package rprocessing.exception;

public class RMIRuntimeException extends Exception { // Ignore CheckstyleBear
                                                     // (AbbreviationAsWordInName)

  private static final long serialVersionUID = 1L;

  public RMIRuntimeException(final String message) {
    super(message);
  }

  public RMIRuntimeException(final Exception exception) {
    super(exception);
  }
}
