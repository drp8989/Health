package com.JustHealth.Health.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class medicineNotFoundException extends Exception{


    public medicineNotFoundException() {
        super();
    }

    public medicineNotFoundException(String message) {
        super(message);
    }

    public medicineNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public medicineNotFoundException(Throwable cause) {
        super(cause);
    }

    protected medicineNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
