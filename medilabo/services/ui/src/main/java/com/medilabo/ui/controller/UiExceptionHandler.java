package com.medilabo.ui.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class UiExceptionHandler {

    private String errorMesage = "errorMessage";
    private String error = "error";

    @ExceptionHandler(RuntimeException.class)
    public String handleGlobalControllerException(Exception ex, Model model) {
	log.info("Use UiExceptionHandler : {}", ex.getMessage());
	model.addAttribute(errorMesage, ex.getMessage());
	return error;
    }

}