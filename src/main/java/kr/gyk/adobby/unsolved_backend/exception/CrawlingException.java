package kr.gyk.adobby.unsolved_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Exception occurred while crawling")
public class CrawlingException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public CrawlingException (String message) {
        super(message);
    }

    public CrawlingException () {
        super("Unknown Exception occurred while crawling");
    }
}
