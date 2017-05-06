package rprocessing.exception;

/**
 * NotFoundException is the exception for not found resources.
 * 
 * @author github.com/gaocegege
 */
public class NotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public NotFoundException(final String message) {
        super(message);
    }
}
