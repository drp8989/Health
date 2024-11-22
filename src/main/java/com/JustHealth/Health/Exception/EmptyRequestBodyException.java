package com.JustHealth.Health.Exception;

    public class EmptyRequestBodyException extends RuntimeException{

        public EmptyRequestBodyException(String message) {
            super(message);
        }

        public EmptyRequestBodyException() {
            super();
        }

        public EmptyRequestBodyException(String message, Throwable cause) {
            super(message, cause);
        }

        public EmptyRequestBodyException(Throwable cause) {
            super(cause);
        }

        protected EmptyRequestBodyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
