package com.JustHealth.Health.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MedicineNotFoundException extends Exception{


    public MedicineNotFoundException() {
        super();
    }

    public MedicineNotFoundException(String message) {
        super(message);
    }

    public MedicineNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MedicineNotFoundException(Throwable cause) {
        super(cause);
    }

    protected MedicineNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
