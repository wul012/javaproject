package com.codexdemo.orderplatform.common;

public final class BusinessException extends RuntimeException {

  public enum Kind {
    INVALID_INPUT,
    CONFLICT,
    NOT_FOUND
  }

  private final Kind kind;
  private final String code;

  private BusinessException(Kind kind, String code, String message) {
    super(message);
    this.kind = kind;
    this.code = code;
  }

  public static BusinessException invalidInput(String code, String message) {
    return new BusinessException(Kind.INVALID_INPUT, code, message);
  }

  public static BusinessException conflict(String code, String message) {
    return new BusinessException(Kind.CONFLICT, code, message);
  }

  public static BusinessException notFound(String code, String message) {
    return new BusinessException(Kind.NOT_FOUND, code, message);
  }

  public Kind getKind() {
    return kind;
  }

  public String getCode() {
    return code;
  }
}
