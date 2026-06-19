package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackageintake;

import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v1236";
  static final String SOURCE_NODE_INSTRUCTION_PREFLIGHT_VERSION = "Node v1211";
  static final String SOURCE_JAVA_INSTRUCTION_PREFLIGHT_VERSION = "Java v909";
  static final String DRAFT_TEXT_PACKAGE_INTAKE_STATE = "expected-fields-only";
  static final String DRAFT_TEXT_ARTIFACT_STATE = "not-accepted";
  static final String SIGNED_DRAFT_STATE = "not-accepted";
  static final String SIGNATURE_ENVELOPE_STATE = "not-accepted";
  static final String APPROVAL_GRANT_STATE = "not-emitted";
  static final String VALUE_IMPORT_STATE = "locked";
  static final String RUNTIME_STATE = "locked";
  static final String SIBLING_MUTATION_STATE = "locked";

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSupport() {}

  static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
      response(
          String version,
          String endpoint,
          String profile,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
                      .IntakeField>
              fields,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
                      .IntakeGuard>
              guards,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
                      .IntakeGate>
              gates,
          List<String> additionalChecks) {
    var fieldCopy = List.copyOf(fields);
    var guardCopy = List.copyOf(guards);
    var gateCopy = List.copyOf(gates);
    int passedFieldCount =
        (int) fieldCopy.stream().filter(field -> "passed".equals(field.status())).count();
    int passedGuardCount =
        (int) guardCopy.stream().filter(guard -> "passed".equals(guard.status())).count();
    List<String> checks = new ArrayList<>();
    checks.add(
        "signed-approval-artifact-draft-text-package-intake-field-count-" + fieldCopy.size());
    checks.add(
        "signed-approval-artifact-draft-text-package-intake-passed-field-count-"
            + passedFieldCount);
    checks.add(
        "signed-approval-artifact-draft-text-package-intake-guard-count-" + guardCopy.size());
    checks.add(
        "signed-approval-artifact-draft-text-package-intake-passed-guard-count-"
            + passedGuardCount);
    checks.add("signed-approval-artifact-draft-text-package-intake-gate-count-" + gateCopy.size());
    checks.add("signed-approval-artifact-draft-text-package-intake-source-plan-" + SOURCE_PLAN);
    checks.add(
        "signed-approval-artifact-draft-text-package-intake-source-node-instruction-preflight-"
            + SOURCE_NODE_INSTRUCTION_PREFLIGHT_VERSION);
    checks.add(
        "signed-approval-artifact-draft-text-package-intake-source-java-instruction-preflight-"
            + SOURCE_JAVA_INSTRUCTION_PREFLIGHT_VERSION);
    checks.add("signed-approval-artifact-draft-text-package-intake-no-draft-text-acceptance");
    checks.add(
        "signed-approval-artifact-draft-text-package-intake-no-detached-signature-acceptance");
    checks.add("signed-approval-artifact-draft-text-package-intake-no-approval-grant");
    checks.add("signed-approval-artifact-draft-text-package-intake-no-value-import");
    checks.add("signed-approval-artifact-draft-text-package-intake-no-runtime-or-sibling-mutation");
    checks.addAll(additionalChecks);

    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse(
        PROJECT,
        version,
        true,
        false,
        true,
        SOURCE_PLAN,
        SOURCE_NODE_INSTRUCTION_PREFLIGHT_VERSION,
        SOURCE_JAVA_INSTRUCTION_PREFLIGHT_VERSION,
        DRAFT_TEXT_PACKAGE_INTAKE_STATE,
        DRAFT_TEXT_ARTIFACT_STATE,
        SIGNED_DRAFT_STATE,
        SIGNATURE_ENVELOPE_STATE,
        APPROVAL_GRANT_STATE,
        VALUE_IMPORT_STATE,
        RUNTIME_STATE,
        SIBLING_MUTATION_STATE,
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
        fieldCopy.size(),
        passedFieldCount,
        guardCopy.size(),
        passedGuardCount,
        gateCopy.size(),
        fieldCopy,
        guardCopy,
        gateCopy,
        List.copyOf(checks),
        passedFieldCount == fieldCopy.size() && passedGuardCount == guardCopy.size()
            ? "passed"
            : "blocked");
  }

  static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
          .IntakeField
      field(
          String code,
          String versionRange,
          String expectedField,
          String intakePurpose,
          String materializationBlocker,
          String guardCode,
          String sourceEndpoint) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
        .IntakeField(
        code,
        versionRange,
        expectedField,
        intakePurpose,
        materializationBlocker,
        guardCode,
        sourceEndpoint,
        "passed");
  }

  static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
          .IntakeGuard
      guard(String code, String category, String guard, String rejectionCode, String enforcement) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
        .IntakeGuard(code, category, guard, rejectionCode, enforcement, "passed");
  }

  static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
          .IntakeGate
      gate(String code, String category, String gate, String enforcement) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
        .IntakeGate(code, category, gate, enforcement);
  }
}
