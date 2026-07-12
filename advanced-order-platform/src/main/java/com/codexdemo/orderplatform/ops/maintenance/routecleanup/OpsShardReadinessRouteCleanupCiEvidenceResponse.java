package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupCiEvidenceResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String ciEvidenceEndpoint,
    String ciProfile,
    int validationStepCount,
    List<ValidationStep> validationSteps,
    String releaseRequirement,
    String status) {

  public record ValidationStep(
      String name, String commandOrCheck, String owner, boolean required, String status) {}
}
