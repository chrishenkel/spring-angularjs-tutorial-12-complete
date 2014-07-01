package tutorial.core.services.exceptions;

/**
 * Created by Chris on 6/30/14.
 */
public class BlogExistsException extends RuntimeException {
    public BlogExistsException() {
    }

    public BlogExistsException(String message) {
        super(message);
    }

    public BlogExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlogExistsException(Throwable cause) {
        super(cause);
    }
}
