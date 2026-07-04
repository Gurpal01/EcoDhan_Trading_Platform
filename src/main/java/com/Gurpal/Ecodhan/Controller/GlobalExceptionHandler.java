package com.Gurpal.Ecodhan.Controller;

import com.Gurpal.Ecodhan.Exception.ExceptionResponse;
import com.Gurpal.Ecodhan.Exception.NotExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotExistException.class)
    public ResponseEntity<?> exceptionThrow(NotExistException e)
    {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        return ResponseEntity.ok(exceptionResponse);
    }

}
