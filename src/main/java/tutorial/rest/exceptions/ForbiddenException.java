package tutorial.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Chris on 10/19/14.
 */
@ResponseStatus(value= HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {
}
