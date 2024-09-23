package com.eso.socialmediaserver.exception.controller;

import com.eso.socialmediaserver.exception.dto.BusinessException;
import com.eso.socialmediaserver.exception.dto.ErrorCode;
import com.eso.socialmediaserver.exception.dto.ErrorModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorModel> customHandleBusinessException(BusinessException ex, WebRequest request) {
        LOGGER.info("Business Error: {}", ex.getMessage());
        ErrorModel error = ErrorModel.builder()
                .timestamp(ZonedDateTime.now())
                .status(ex.getStatusCode())
                .error(ex.getErrorCode())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.resolve(ex.getStatusCode()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorModel error = ErrorModel.builder()
                .timestamp(ZonedDateTime.now())
                .status(ErrorCode.validation.getHttpCode())
                .error(ErrorCode.validation.name())
                .message(String.join(", ", errors))
                .build();
        return new ResponseEntity<>(error, headers, HttpStatus.resolve(ErrorCode.validation.getHttpCode()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorModel> constraintViolationException(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        ErrorModel error = ErrorModel.builder()
                .timestamp(ZonedDateTime.now())
                .status(ErrorCode.validation.getHttpCode())
                .error(ErrorCode.validation.name())
                .message(String.join(", ", errors))
                .build();
        return new ResponseEntity<>(error, HttpStatus.resolve(ErrorCode.validation.getHttpCode()));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatusCode status,
                                                                     WebRequest request) {
        ErrorModel error = ErrorModel.builder()
                .timestamp(ZonedDateTime.now())
                .status(status.value())
                .error(ErrorCode.unknown.name())
                .message(ex.getRequestPartName() + " is missing!")
                .build();
        return new ResponseEntity<>(error, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        ErrorModel error = ErrorModel.builder()
                .timestamp(ZonedDateTime.now())
                .status(ErrorCode.validation.getHttpCode())
                .error(ErrorCode.validation.name())
                .message(ex.getLocalizedMessage())
                .build();
        return new ResponseEntity<>(error, headers, HttpStatus.resolve(ErrorCode.validation.getHttpCode()));
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatusCode status,
                                                                   WebRequest request) {

        ErrorModel error = ErrorModel.builder()
                .timestamp(ZonedDateTime.now())
                .status(ErrorCode.unknown.getHttpCode())
                .error(ErrorCode.unknown.name())
                .message(ex.getLocalizedMessage())
                .build();
        return new ResponseEntity<>(error, headers, HttpStatus.resolve(ErrorCode.unknown.getHttpCode()));
    }
}
