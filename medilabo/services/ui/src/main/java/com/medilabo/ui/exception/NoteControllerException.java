package com.medilabo.ui.exception;

public class NoteControllerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NoteControllerException(String message) {
	super(message);
    }

    public NoteControllerException(String message, Throwable cause) {
	super(message, cause);
    }
}
