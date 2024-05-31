package kr.gyk.adobby.unsolved_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Refresh Token not Found")
public class RefreshTokenNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public RefreshTokenNotFoundException(String message) {
        super(message);
    }
}