package routingTask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoSuchCountryException extends RuntimeException {

    public NoSuchCountryException() {
        super("No such country");
    }
}
