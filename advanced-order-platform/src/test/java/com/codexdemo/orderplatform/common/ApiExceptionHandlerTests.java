package com.codexdemo.orderplatform.common;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.MDC;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

@ExtendWith(OutputCaptureExtension.class)
class ApiExceptionHandlerTests {

  private final ApiExceptionHandler handler = new ApiExceptionHandler();
  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @AfterEach
  void clearMdc() {
    MDC.clear();
  }

  @Test
  void mapsConstraintViolationsToValidationProblemDetails(CapturedOutput output) {
    MDC.put("traceId", "trace-j4-validation");
    MDC.put("spanId", "span-j4-validation");
    ConstraintViolationException exception =
        new ConstraintViolationException(validator.validate(new SampleRequest(" ")));

    ProblemDetail detail = handler.handleConstraintViolation(exception);

    assertThat(detail.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(detail.getTitle()).isEqualTo("VALIDATION_FAILED");
    assertThat(detail.getProperties()).containsKey("fieldErrors");
    assertThat(detail.getProperties().get("fieldErrors").toString()).contains("value:");
    assertThat(output)
        .contains("constraint validation failed")
        .contains("traceId=trace-j4-validation")
        .contains("spanId=span-j4-validation");
  }

  private record SampleRequest(@NotBlank String value) {}
}
