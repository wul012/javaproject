package com.codexdemo.orderplatform.common;

import jakarta.validation.ConstraintViolationException;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class ApiExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

  @ExceptionHandler(BusinessException.class)
  public ProblemDetail handleBusinessException(BusinessException exception) {
    RequestLogCorrelation.Correlation correlation = RequestLogCorrelation.current();
    log.warn(
        "business exception handled code={} status={} traceId={} spanId={}",
        exception.getCode(),
        exception.getStatus().value(),
        correlation.traceId(),
        correlation.spanId());
    ProblemDetail detail =
        ProblemDetail.forStatusAndDetail(exception.getStatus(), exception.getMessage());
    detail.setType(URI.create("https://advanced-order-platform/errors/" + exception.getCode()));
    detail.setTitle(exception.getCode());
    return detail;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleValidationException(MethodArgumentNotValidException exception) {
    RequestLogCorrelation.Correlation correlation = RequestLogCorrelation.current();
    log.warn(
        "request body validation failed fieldErrorCount={} traceId={} spanId={}",
        exception.getBindingResult().getFieldErrorCount(),
        correlation.traceId(),
        correlation.spanId());
    ProblemDetail detail =
        ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Request validation failed");
    detail.setTitle("VALIDATION_FAILED");
    detail.setType(URI.create("https://advanced-order-platform/errors/VALIDATION_FAILED"));
    detail.setProperty(
        "fieldErrors",
        exception.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .toList());
    return detail;
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ProblemDetail handleConstraintViolation(ConstraintViolationException exception) {
    RequestLogCorrelation.Correlation correlation = RequestLogCorrelation.current();
    log.warn(
        "constraint validation failed violationCount={} traceId={} spanId={}",
        exception.getConstraintViolations().size(),
        correlation.traceId(),
        correlation.spanId());
    ProblemDetail detail =
        ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Request validation failed");
    detail.setTitle("VALIDATION_FAILED");
    detail.setType(URI.create("https://advanced-order-platform/errors/VALIDATION_FAILED"));
    detail.setProperty(
        "fieldErrors",
        exception.getConstraintViolations().stream()
            .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
            .toList());
    return detail;
  }

  @ExceptionHandler(HandlerMethodValidationException.class)
  public ProblemDetail handleHandlerMethodValidation(HandlerMethodValidationException exception) {
    RequestLogCorrelation.Correlation correlation = RequestLogCorrelation.current();
    log.warn(
        "handler method validation failed resultCount={} traceId={} spanId={}",
        exception.getParameterValidationResults().size(),
        correlation.traceId(),
        correlation.spanId());
    ProblemDetail detail =
        ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Request validation failed");
    detail.setTitle("VALIDATION_FAILED");
    detail.setType(URI.create("https://advanced-order-platform/errors/VALIDATION_FAILED"));
    detail.setProperty(
        "fieldErrors",
        exception.getParameterValidationResults().stream()
            .flatMap(
                result ->
                    result.getResolvableErrors().stream()
                        .map(
                            error ->
                                result.getMethodParameter().getParameterName()
                                    + ": "
                                    + error.getDefaultMessage()))
            .toList());
    return detail;
  }

  @ExceptionHandler(MissingRequestHeaderException.class)
  public ProblemDetail handleMissingHeader(MissingRequestHeaderException exception) {
    RequestLogCorrelation.Correlation correlation = RequestLogCorrelation.current();
    log.warn(
        "missing request header headerName={} traceId={} spanId={}",
        exception.getHeaderName(),
        correlation.traceId(),
        correlation.spanId());
    ProblemDetail detail =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST, "Missing required header: " + exception.getHeaderName());
    detail.setTitle("MISSING_HEADER");
    detail.setType(URI.create("https://advanced-order-platform/errors/MISSING_HEADER"));
    return detail;
  }
}
