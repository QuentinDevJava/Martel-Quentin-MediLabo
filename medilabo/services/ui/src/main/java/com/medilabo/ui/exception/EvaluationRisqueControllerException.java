package com.medilabo.ui.exception;

public class EvaluationRisqueControllerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EvaluationRisqueControllerException(String message) {
	super(message);
    }

    public EvaluationRisqueControllerException(String message, Throwable cause) {
	super(message, cause);
    }
}
