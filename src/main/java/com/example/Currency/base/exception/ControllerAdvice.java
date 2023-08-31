package com.example.Currency.base.exception;

import com.example.Currency.model.response.error.FailureResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public FailureResponse handleEmergencyError(HttpServletRequest request, Exception e) {

        return FailureResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .title("Exception")
                .detail(e.getMessage())
                .instance(String.valueOf(request.getRequestURL()))
                .build();


    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public FailureResponse handleBadRequest(HttpServletRequest request, Exception e) {

        return FailureResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Bad Request")
                .detail(e.getMessage())
                .instance(String.valueOf(request.getRequestURL()))
                .build();
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoHandlerFoundException.class, ResourceNotFoundException.class})
    public FailureResponse handleNotFound(HttpServletRequest request, Exception e) {

        return FailureResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .title("Not Found")
                .detail(e.getMessage())
                .instance(String.valueOf(request.getRequestURL()))
                .build();


    }
}
