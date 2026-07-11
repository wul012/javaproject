package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.function.BooleanSupplier;

final class ReleaseApprovalDigestSupport {

  private ReleaseApprovalDigestSupport() {}

  static String digest(List<String> lines) {
    String canonical = String.join("\n", lines) + "\n";
    try {
      byte[] bytes =
          MessageDigest.getInstance("SHA-256").digest(canonical.getBytes(StandardCharsets.UTF_8));
      return "sha256:" + HexFormat.of().formatHex(bytes);
    } catch (NoSuchAlgorithmException ex) {
      throw new IllegalStateException("SHA-256 digest algorithm is not available", ex);
    }
  }

  static String line(String key, Object value) {
    return key + "=" + value(value);
  }

  static List<String> warningInputNames(String markerWarningsInputName) {
    return List.of(markerWarningsInputName);
  }

  static List<String> boundaryInputNames(String... boundaryInputNames) {
    return List.of(boundaryInputNames);
  }

  static List<String> warningLines(String markerWarningsInputName, List<String> markerWarnings) {
    return List.of(line(markerWarningsInputName, markerWarnings));
  }

  static BoundaryDigestInput boundaryInput(String name, Object value) {
    return new BoundaryDigestInput(name, value);
  }

  static List<String> boundaryLines(BoundaryDigestInput... inputs) {
    List<String> lines = new ArrayList<>();
    for (BoundaryDigestInput input : inputs) {
      lines.add(line(input.name(), input.value()));
    }
    return List.copyOf(lines);
  }

  static WarningCondition warningIf(boolean condition, String warningCode) {
    return new WarningCondition(condition, warningCode);
  }

  static List<String> warnings(WarningCondition... conditions) {
    List<String> warnings = new ArrayList<>();
    for (WarningCondition condition : conditions) {
      if (condition.condition()) {
        warnings.add(condition.warningCode());
      }
    }
    return List.copyOf(warnings);
  }

  static EchoWorkflowStep workflowStep(String name, boolean ready) {
    return new EchoWorkflowStep(name, ready);
  }

  static EchoWorkflowStep workflowStep(String name, BooleanSupplier ready) {
    return new EchoWorkflowStep(name, ready.getAsBoolean());
  }

  static EchoWorkflowReadiness workflowReadiness(EchoWorkflowStep... steps) {
    return new EchoWorkflowReadiness(List.of(steps));
  }

  private static String value(Object value) {
    if (value == null) {
      return "<null>";
    }
    if (value instanceof List<?> list) {
      return "["
          + String.join(",", list.stream().map(ReleaseApprovalDigestSupport::value).toList())
          + "]";
    }
    return String.valueOf(value);
  }

  record BoundaryDigestInput(String name, Object value) {}

  record WarningCondition(boolean condition, String warningCode) {}

  record EchoWorkflowStep(String name, boolean ready) {}

  record EchoWorkflowReadiness(List<EchoWorkflowStep> steps) {

    EchoWorkflowReadiness {
      steps = List.copyOf(steps);
    }

    boolean ready(String stepName) {
      return steps.stream()
          .filter(step -> step.name().equals(stepName))
          .findFirst()
          .map(EchoWorkflowStep::ready)
          .orElse(false);
    }

    boolean allReady() {
      return steps.stream().allMatch(EchoWorkflowStep::ready);
    }

    List<String> readyStepNames() {
      return steps.stream().filter(EchoWorkflowStep::ready).map(EchoWorkflowStep::name).toList();
    }

    List<String> missingStepNames() {
      return steps.stream().filter(step -> !step.ready()).map(EchoWorkflowStep::name).toList();
    }

    WarningCondition warningIfMissing(String stepName, String warningCode) {
      return warningIf(!ready(stepName), warningCode);
    }
  }
}
