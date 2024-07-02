package kr.gyk.adobby.unsolved_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "request params not valid")
public class RequestParamsNotValidException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public RequestParamsNotValidException(String message) {
        super(message);
    }
}
