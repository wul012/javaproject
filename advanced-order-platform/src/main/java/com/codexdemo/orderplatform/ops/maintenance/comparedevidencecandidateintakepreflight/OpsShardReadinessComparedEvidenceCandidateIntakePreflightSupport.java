package com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessComparedEvidenceCandidateIntakePreflightSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v1371";
  static final String SOURCE_NODE_CANDIDATE_BLUEPRINT_VERSION = "Node v1361";
  static final String SOURCE_JAVA_CANDIDATE_BLUEPRINT_VERSION = "Java v1064";
  static final String INTAKE_STATE =
      "waiting-for-real-compared-package-evidence-candidate-document";
  static final int REQUIRED_FIELD_COUNT = 20;
  static final int PASSED_GATE_COUNT = 36;

  private OpsShardReadinessComparedEvidenceCandidateIntakePreflightSupport() {}

  static OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse response(
      String version,
      String endpoint,
      String profile,
      List<OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeSlot> slots,
      List<OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeGuard> guards,
      List<String> gates,
      List<String> additionalChecks) {
    var slotCopy = List.copyOf(slots);
    var guardCopy = List.copyOf(guards);
    var gateCopy = List.copyOf(gates);
    int passedSlotCount =
        (int) slotCopy.stream().filter(slot -> "passed".equals(slot.status())).count();
    int passedGuardCount =
        (int) guardCopy.stream().filter(guard -> "passed".equals(guard.status())).count();
    List<String> checks = new ArrayList<>();
    checks.add("compared-evidence-candidate-intake-preflight-slot-count-" + slotCopy.size());
    checks.add("compared-evidence-candidate-intake-preflight-guard-count-" + guardCopy.size());
    checks.add(
        "compared-evidence-candidate-intake-preflight-required-field-count-"
            + REQUIRED_FIELD_COUNT);
    checks.add("compared-evidence-candidate-intake-preflight-passed-gate-count-" + gateCopy.size());
    checks.add("compared-evidence-candidate-intake-preflight-source-plan-" + SOURCE_PLAN);
    checks.add(
        "compared-evidence-candidate-intake-preflight-source-node-"
            + SOURCE_NODE_CANDIDATE_BLUEPRINT_VERSION);
    checks.add(
        "compared-evidence-candidate-intake-preflight-source-java-"
            + SOURCE_JAVA_CANDIDATE_BLUEPRINT_VERSION);
    checks.add("compared-evidence-candidate-intake-preflight-real-document-count-zero");
    checks.add("compared-evidence-candidate-intake-preflight-no-synthetic-document");
    checks.add("compared-evidence-candidate-intake-preflight-no-payload-import");
    checks.add("compared-evidence-candidate-intake-preflight-no-candidate-evaluation");
    checks.add("compared-evidence-candidate-intake-preflight-no-approval-grant");
    checks.add("compared-evidence-candidate-intake-preflight-no-signed-approval-capture");
    checks.add("compared-evidence-candidate-intake-preflight-no-runtime-payload");
    checks.add("compared-evidence-candidate-intake-preflight-no-write");
    checks.add("compared-evidence-candidate-intake-preflight-no-sibling-mutation");
    checks.addAll(additionalChecks);

    return new OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse(
        PROJECT,
        version,
        true,
        false,
        true,
        SOURCE_PLAN,
        SOURCE_NODE_CANDIDATE_BLUEPRINT_VERSION,
        SOURCE_JAVA_CANDIDATE_BLUEPRINT_VERSION,
        INTAKE_STATE,
        0,
        REQUIRED_FIELD_COUNT,
        gateCopy.size(),
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        endpoint,
        profile,
        slotCopy.size(),
        passedSlotCount,
        guardCopy.size(),
        passedGuardCount,
        slotCopy,
        guardCopy,
        gateCopy,
        List.copyOf(checks),
        passedSlotCount == slotCopy.size()
                && passedGuardCount == guardCopy.size()
                && gateCopy.size() == PASSED_GATE_COUNT
            ? "passed"
            : "blocked");
  }

  static OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeSlot slot(
      String code,
      String sourceBlueprintSection,
      String requiredFields,
      String documentRequirement,
      String missingDocumentGuard,
      String sourceEndpoint) {
    return new OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeSlot(
        code,
        sourceBlueprintSection,
        requiredFields,
        documentRequirement,
        missingDocumentGuard,
        sourceEndpoint,
        "passed");
  }

  static OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeGuard guard(
      String code, String category, String guard, String rejectionCode) {
    return new OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeGuard(
        code, category, guard, rejectionCode, "fail-closed", "passed");
  }
}
