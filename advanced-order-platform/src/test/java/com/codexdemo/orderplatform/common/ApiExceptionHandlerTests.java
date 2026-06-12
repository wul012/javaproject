package com.codexdemo.orderplatform.common;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

class ApiExceptionHandlerTests {

  private final ApiExceptionHandler handler = new ApiExceptionHandler();
  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void mapsConstraintViolationsToValidationProblemDetails() {
    ConstraintViolationException exception =
        new ConstraintViolationException(validator.validate(new SampleRequest(" ")));

    ProblemDetail detail = handler.handleConstraintViolation(exception);

    assertThat(detail.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(detail.getTitle()).isEqualTo("VALIDATION_FAILED");
    assertThat(detail.getProperties()).containsKey("fieldErrors");
    assertThat(detail.getProperties().get("fieldErrors").toString()).contains("value:");
  }

  private record SampleRequest(@NotBlank String value) {}
}
