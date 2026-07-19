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
  void mapsBusinessFailures(CapturedOutput output) {
    MDC.put("traceId", "trace-v1870");
    MDC.put("spanId", "span-v1870");

    assertProblem(BusinessException.invalidInput("INVALID_INPUT", "invalid"), 400);
    assertProblem(BusinessException.conflict("CONFLICT", "conflict"), 409);
    assertProblem(BusinessException.notFound("NOT_FOUND", "missing"), 404);

    assertThat(output)
        .contains("code=INVALID_INPUT")
        .contains("status=400")
        .contains("code=CONFLICT")
        .contains("status=409")
        .contains("code=NOT_FOUND")
        .contains("status=404")
        .contains("traceId=trace-v1870")
        .contains("spanId=span-v1870");
  }

  @Test
  void mapsConstraintViolations(CapturedOutput output) {
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

  private void assertProblem(BusinessException exception, int expectedStatus) {
    ProblemDetail detail = handler.handleBusinessException(exception);

    assertThat(detail.getStatus()).isEqualTo(expectedStatus);
    assertThat(detail.getTitle()).isEqualTo(exception.getCode());
    assertThat(detail.getDetail()).isEqualTo(exception.getMessage());
    assertThat(detail.getType().toString()).endsWith("/" + exception.getCode());
  }

  private record SampleRequest(@NotBlank String value) {}
}
