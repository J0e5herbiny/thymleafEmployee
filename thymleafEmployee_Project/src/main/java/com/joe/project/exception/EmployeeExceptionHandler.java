package com.joe.project.exception;

import org.springframework.beans.NotReadablePropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.thymeleaf.exceptions.TemplateInputException;

@ControllerAdvice
public class EmployeeExceptionHandler {

    @ExceptionHandler(value = TemplateInputException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String templateInputExceptionHandler(){
        return "/Xlogin-authPage";
    }

    @ExceptionHandler(value = NotReadablePropertyException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String NotReadablePropertyExceptionHandler(){
        return "/Xlogin-authPage";
    }

    @ExceptionHandler(value = Exception.class)
    public String anyExceptionException(){
        return "/error";
    }

}
