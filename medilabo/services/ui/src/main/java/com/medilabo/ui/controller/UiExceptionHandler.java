package com.medilabo.ui.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.medilabo.ui.exception.EvaluationRisqueControllerException;
import com.medilabo.ui.exception.NoteControllerException;
import com.medilabo.ui.exception.PatientControllerException;

@ControllerAdvice
public class UiExceptionHandler {

    private String errorMesage = "errorMessage";
    private String error = "error";

    @ExceptionHandler(PatientControllerException.class)
    public String handlePatientControllerException(PatientControllerException ex, Model model) {
	model.addAttribute(errorMesage, ex.getMessage());
	return error;
    }

    @ExceptionHandler(NoteControllerException.class)
    public String handlePatientControllerException(NoteControllerException ex, Model model) {
	model.addAttribute(errorMesage, ex.getMessage());
	return error;
    }

    @ExceptionHandler(EvaluationRisqueControllerException.class)
    public String handlePatientControllerException(EvaluationRisqueControllerException ex, Model model) {
	model.addAttribute(errorMesage, ex.getMessage());
	return error;
    }
}