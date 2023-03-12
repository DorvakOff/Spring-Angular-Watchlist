package com.dorvak.webapp.moteur.exception;

public class ServletExecutionException extends RuntimeException {
    public ServletExecutionException(String message, Object... args) {
        super(String.format(message, args));
    }
}
