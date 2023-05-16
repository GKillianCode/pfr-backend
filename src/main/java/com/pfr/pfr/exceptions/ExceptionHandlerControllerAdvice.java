package com.pfr.pfr.exceptions;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.management.InstanceAlreadyExistsException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionMessage> nullPointerExceptionHandler(HttpServletRequest request, NullPointerException exception) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(request.getRequestURI(), exception.getStackTrace()[0].toString(), exception.getClass().getName());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionMessage> entityNotFoundExceptionHandler(HttpServletRequest request, EntityNotFoundException exception) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(request.getRequestURI(), exception.getMessage(), exception.getClass().getName());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionMessage> constraintViolationExceptionHandler(HttpServletRequest request, ConstraintViolationException exception) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(request.getRequestURI(), exception.getMessage(), exception.getClass().getName());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InstanceAlreadyExistsException.class)
    public ResponseEntity<ExceptionMessage> instanceAlreadyExistsException(HttpServletRequest request,InstanceAlreadyExistsException exception) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(request.getRequestURI(), exception.getMessage(), exception.getClass().getName());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionMessage> illegalArgumentException(HttpServletRequest request,IllegalArgumentException exception) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(request.getRequestURI(), exception.getMessage(), exception.getClass().getName());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ExceptionMessage> entityExistsException(HttpServletRequest request,EntityExistsException exception) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(request.getRequestURI(), exception.getMessage(), exception.getClass().getName());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.CONFLICT);
    }
}