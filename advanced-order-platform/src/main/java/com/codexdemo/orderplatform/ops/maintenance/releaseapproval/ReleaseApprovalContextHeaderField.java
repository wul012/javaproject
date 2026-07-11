package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import java.util.List;

record ReleaseApprovalContextHeaderField(String value, String source, boolean echoed) {

  private static final String NOT_SUPPLIED_SOURCE = "NOT_SUPPLIED";

  static ReleaseApprovalContextHeaderField from(
      String value, String headerName, String placeholder) {
    String normalizedValue = normalizeValue(value);
    if (normalizedValue == null) {
      return new ReleaseApprovalContextHeaderField(placeholder, NOT_SUPPLIED_SOURCE, false);
    }
    return new ReleaseApprovalContextHeaderField(normalizedValue, headerName, true);
  }

  static ReleaseApprovalContextHeaderField normalized(
      List<String> warnings, String value, String headerName, String placeholder, String warning) {
    ReleaseApprovalContextHeaderField field = from(value, headerName, placeholder);
    field.addMissingWarning(warnings, warning);
    return field;
  }

  static String normalizeValue(String value) {
    if (value == null || value.isBlank()) {
      return null;
    }
    return value.trim();
  }

  private void addMissingWarning(List<String> warnings, String warning) {
    if (!echoed) {
      warnings.add(warning);
    }
  }

  static boolean allEchoed(ReleaseApprovalContextHeaderField... fields) {
    for (ReleaseApprovalContextHeaderField field : fields) {
      if (!field.echoed()) {
        return false;
      }
    }
    return true;
  }
}
