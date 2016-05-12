package ru.javawebinar.topjava.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ALovkov
 * 12.05.2016
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class EmailExistException extends RuntimeException {
    public EmailExistException(String message) {
        super(message);
    }
}
