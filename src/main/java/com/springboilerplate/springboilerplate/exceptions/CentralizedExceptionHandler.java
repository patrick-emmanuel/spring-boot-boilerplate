package com.springboilerplate.springboilerplate.exceptions;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CentralizedExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException exception, HttpHeaders headers,
            HttpStatus status, WebRequest request){
        logger.error("Missing servlet request parameter exception.");
        String error = String.format("%s parameter is missing", exception.getParameterName());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request){
        logger.error("Method argument not valid.");
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        String error = fieldErrors.stream()
                .map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
                .collect(Collectors.joining());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage(), error);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler({ExpiredTokenException.class})
    public ResponseEntity<Object> handleExpiredTokenException(ExpiredTokenException ex, WebRequest webRequest){
        String error = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), error);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler({InvalidTokenException.class})
    public ResponseEntity<Object> handleInvalidTokenException(InvalidTokenException ex, WebRequest webRequest){
        String error = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), error);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler({InvalidPasswordResetToken.class})
    public ResponseEntity<Object> handleInvalidPasswordResetToken(InvalidPasswordResetToken ex, WebRequest webRequest){
        String error = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), error);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler({NoTokenException.class})
    public ResponseEntity<Object> handleNoTokenException(NoTokenException ex, WebRequest webRequest){
        String error = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), error);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler({SendingTokenException.class})
    public ResponseEntity<Object> handleSendingTokenException(SendingTokenException ex, WebRequest webRequest){
        String error = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), error);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler({RoleDoesNotExistException.class})
    public ResponseEntity<Object> handleRoleDoesNotExistException(RoleDoesNotExistException ex, WebRequest webRequest){
        String error = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), error);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
