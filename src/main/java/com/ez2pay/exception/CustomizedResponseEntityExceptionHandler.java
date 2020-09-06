package com.ez2pay.exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ResponseEntity<ExceptionResponse> handleAllException(Exception ex, WebRequest request){
        ex.printStackTrace();

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Timestamp(new Date().getTime()),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ResourceNotFoundException.class, UsernameNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Hidden
    public final ResponseEntity<ExceptionResponse> handleNotFoundException(Exception ex, WebRequest request) {
        ex.printStackTrace();

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Timestamp(new Date().getTime()),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequestException.class, InvalidJwtAuthenticationException.class, BadCredentialsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Hidden
    public final ResponseEntity<ExceptionResponse> handleBadRequestException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Timestamp(new Date().getTime()),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @Hidden
    public final ResponseEntity<ExceptionResponse> handleAccessDeniedException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Timestamp(new Date().getTime()),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler({ResourceConflictException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @Hidden
    public final ResponseEntity<ExceptionResponse> handleConflictException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Timestamp(new Date().getTime()),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> (fieldError.getField() + " " + fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ex.printStackTrace();

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Timestamp(new Date().getTime()),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errorList.toString(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
