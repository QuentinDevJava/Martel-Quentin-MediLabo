package com.medilabo.ui.exception;

public class PatientControllerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PatientControllerException(String message) {
	super(message);
    }

    public PatientControllerException(String message, Throwable cause) {
	super(message, cause);
    }
}