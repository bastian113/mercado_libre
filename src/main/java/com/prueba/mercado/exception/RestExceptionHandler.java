package com.prueba.mercado.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DNASequenceFormatException.class)
    protected ResponseEntity<Object> handleDnaSequenceFormatException(DNASequenceFormatException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), getFormattedDate());

        log.error(ex.getMessage());

        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), getFormattedDate());

        log.error(ex.getMessage());

        return buildResponseEntity(errorResponse);
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    private String getFormattedDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

}
