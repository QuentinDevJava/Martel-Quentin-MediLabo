package com.medilabo.ui.exceptions;

public class AuthServiceUnavailableException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public AuthServiceUnavailableException(String message) {
        super(message);
    }
}

