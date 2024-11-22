package com.JustHealth.Health.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MedicineCompositionNotFoundException extends Exception{
    public MedicineCompositionNotFoundException() {
        super();
    }

    public MedicineCompositionNotFoundException(String message) {
        super(message);
    }

    public MedicineCompositionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MedicineCompositionNotFoundException(Throwable cause) {
        super(cause);
    }

    protected MedicineCompositionNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
