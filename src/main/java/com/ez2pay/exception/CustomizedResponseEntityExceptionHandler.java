package com.ez2pay.exception;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ResponseEntity<ExceptionResponse> handleAllException (Exception ex, WebRequest request){
        ex.printStackTrace();
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Hidden
    public final ResponseEntity<ExceptionResponse> handleNotFoundException (Exception ex, WebRequest request){
        ex.printStackTrace();
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({InvalidInputException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Hidden
    public final ResponseEntity<ExceptionResponse> handleBadRequestException (Exception ex, WebRequest request){
        ex.printStackTrace();
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceConflictException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @Hidden
    public final ResponseEntity<ExceptionResponse> handleConflictException (Exception ex, WebRequest request){
        ex.printStackTrace();
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

}
