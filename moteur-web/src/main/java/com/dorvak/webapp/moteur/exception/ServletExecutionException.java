package com.dorvak.webapp.moteur.exception;

public class ServletExecutionException extends ApplicationException {

    public ServletExecutionException(String message, Object... args) {
        super(message, args);
    }
}
