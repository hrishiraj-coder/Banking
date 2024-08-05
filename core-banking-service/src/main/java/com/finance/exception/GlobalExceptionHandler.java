package com.finance.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SimpleBankingGlobalException.class)
    public ResponseEntity handleSimpleGlobalException(SimpleBankingGlobalException exception){
         return ResponseEntity.badRequest().body(ErrorResponse.builder().code(exception.getCode())
                 .message(exception.getMessage()).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleGlobalException(Exception exception){
        return ResponseEntity.badRequest().body("Exception occur inside api"+exception);
    }

}
