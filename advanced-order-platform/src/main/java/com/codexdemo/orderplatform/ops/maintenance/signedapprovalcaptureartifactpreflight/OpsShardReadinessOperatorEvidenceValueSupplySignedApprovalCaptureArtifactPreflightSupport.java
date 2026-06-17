package com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight;

import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v1086";
  static final String SOURCE_CAPTURE_PREFLIGHT_VERSION = "Node v1061";
  static final String SOURCE_JAVA_CAPTURE_PREFLIGHT_VERSION = "Java v734";
  static final String SOURCE_TEMPLATE_VERSION = "Node v1036";
  static final String SOURCE_APPROVAL_PACKET_REVIEW_VERSION = "Node v1011";
  static final String ARTIFACT_PREFLIGHT_STATE = "fragment-map-only";
  static final String ARTIFACT_MATERIALIZATION_STATE = "not-materialized";
  static final String SIGNED_APPROVAL_CAPTURE_STATE = "not-captured";
  static final String APPROVAL_GRANT_STATE = "not-emitted";
  static final String VALUE_IMPORT_STATE = "locked";
  static final String RUNTIME_STATE = "locked";
  static final String SIBLING_MUTATION_STATE = "locked";

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSupport() {}

  static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
      response(
          String version,
          String endpoint,
          String profile,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
                      .ArtifactFragment>
              fragments,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
                      .ArtifactSeal>
              seals,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
                      .ArtifactGate>
              gates,
          List<String> additionalChecks) {
    List<
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
                .ArtifactFragment>
        fragmentCopy = List.copyOf(fragments);
    List<
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
                .ArtifactSeal>
        sealCopy = List.copyOf(seals);
    List<
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
                .ArtifactGate>
        gateCopy = List.copyOf(gates);
    int passedFragmentCount =
        (int) fragmentCopy.stream().filter(fragment -> "passed".equals(fragment.status())).count();
    int passedSealCount =
        (int) sealCopy.stream().filter(seal -> "passed".equals(seal.status())).count();
    List<String> checks = new ArrayList<>();
    checks.add("signed-approval-capture-artifact-preflight-fragment-count-" + fragmentCopy.size());
    checks.add(
        "signed-approval-capture-artifact-preflight-passed-fragment-count-" + passedFragmentCount);
    checks.add("signed-approval-capture-artifact-preflight-seal-count-" + sealCopy.size());
    checks.add("signed-approval-capture-artifact-preflight-passed-seal-count-" + passedSealCount);
    checks.add("signed-approval-capture-artifact-preflight-gate-count-" + gateCopy.size());
    checks.add("signed-approval-capture-artifact-preflight-source-plan-" + SOURCE_PLAN);
    checks.add(
        "signed-approval-capture-artifact-preflight-source-capture-"
            + SOURCE_CAPTURE_PREFLIGHT_VERSION);
    checks.add(
        "signed-approval-capture-artifact-preflight-source-java-"
            + SOURCE_JAVA_CAPTURE_PREFLIGHT_VERSION);
    checks.add(
        "signed-approval-capture-artifact-preflight-source-template-" + SOURCE_TEMPLATE_VERSION);
    checks.add(
        "signed-approval-capture-artifact-preflight-source-review-"
            + SOURCE_APPROVAL_PACKET_REVIEW_VERSION);
    checks.add("signed-approval-capture-artifact-preflight-no-artifact-materialization");
    checks.add("signed-approval-capture-artifact-preflight-no-signed-approval-capture");
    checks.add("signed-approval-capture-artifact-preflight-no-approval-grant");
    checks.add("signed-approval-capture-artifact-preflight-no-value-import");
    checks.add("signed-approval-capture-artifact-preflight-no-runtime-payload");
    checks.add("signed-approval-capture-artifact-preflight-no-sibling-mutation");
    checks.addAll(additionalChecks);

    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse(
        PROJECT,
        version,
        true,
        false,
        true,
        SOURCE_PLAN,
        SOURCE_CAPTURE_PREFLIGHT_VERSION,
        SOURCE_JAVA_CAPTURE_PREFLIGHT_VERSION,
        SOURCE_TEMPLATE_VERSION,
        SOURCE_APPROVAL_PACKET_REVIEW_VERSION,
        ARTIFACT_PREFLIGHT_STATE,
        ARTIFACT_MATERIALIZATION_STATE,
        SIGNED_APPROVAL_CAPTURE_STATE,
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
        fragmentCopy.size(),
        passedFragmentCount,
        sealCopy.size(),
        passedSealCount,
        gateCopy.size(),
        fragmentCopy,
        sealCopy,
        gateCopy,
        List.copyOf(checks),
        passedFragmentCount == fragmentCopy.size() && passedSealCount == sealCopy.size()
            ? "passed"
            : "blocked");
  }

  static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
          .ArtifactFragment
      fragment(
          String code,
          String sourceCaptureInput,
          String artifactStage,
          String fragmentRequirement,
          String materializationBlocker,
          String sealCode,
          String evidenceFileId,
          String evidenceSnippetId,
          String sourceEndpoint) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
        .ArtifactFragment(
        code,
        sourceCaptureInput,
        artifactStage,
        fragmentRequirement,
        materializationBlocker,
        sealCode,
        evidenceFileId,
        evidenceSnippetId,
        sourceEndpoint,
        "passed");
  }

  static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
          .ArtifactSeal
      seal(
          String code,
          String category,
          String sealRequirement,
          String rejectionCode,
          String enforcement) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
        .ArtifactSeal(code, category, sealRequirement, rejectionCode, enforcement, "passed");
  }

  static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
          .ArtifactGate
      gate(String code, String category, String gate, String enforcement) {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
        .ArtifactGate(code, category, gate, enforcement);
  }
}
