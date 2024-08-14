package com.JustHealth.Health.Exception;

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
