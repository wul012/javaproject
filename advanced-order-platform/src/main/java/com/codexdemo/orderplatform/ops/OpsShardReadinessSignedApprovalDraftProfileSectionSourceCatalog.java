package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse;
import java.util.List;

final class OpsShardReadinessSignedApprovalDraftProfileSectionSourceCatalog {

  private OpsShardReadinessSignedApprovalDraftProfileSectionSourceCatalog() {}

  static List<OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.DraftSectionSource>
      sources(
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
              preflight,
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
              readiness,
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
              reviewPackagePreflight,
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
              authoringReadiness,
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
              instructionPreflight) {
    return List.of(
        source(
            1,
            "signed-approval-artifact-draft-preflight",
            preflight.version(),
            "Node v1111",
            preflight.endpoint(),
            preflight.profile(),
            preflight.gateCount(),
            preflight.status()),
        source(
            2,
            "signed-approval-artifact-draft-readiness",
            readiness.version(),
            "Node v1136",
            readiness.endpoint(),
            readiness.profile(),
            readiness.gateCount(),
            readiness.status()),
        source(
            3,
            "signed-approval-artifact-draft-review-package-preflight",
            reviewPackagePreflight.version(),
            "Node v1161",
            reviewPackagePreflight.endpoint(),
            reviewPackagePreflight.profile(),
            reviewPackagePreflight.gateCount(),
            reviewPackagePreflight.status()),
        source(
            4,
            "signed-approval-artifact-draft-authoring-readiness",
            authoringReadiness.version(),
            "Node v1186",
            authoringReadiness.endpoint(),
            authoringReadiness.profile(),
            authoringReadiness.gateCount(),
            authoringReadiness.status()),
        source(
            5,
            "signed-approval-artifact-draft-instruction-preflight",
            instructionPreflight.version(),
            "Node v1211",
            instructionPreflight.endpoint(),
            instructionPreflight.profile(),
            instructionPreflight.gateCount(),
            instructionPreflight.status()));
  }

  private static OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse
          .DraftSectionSource
      source(
          int order,
          String code,
          String javaVersion,
          String nodeVersionMarker,
          String endpoint,
          String profile,
          int sourceGateCount,
          String sourceStatus) {
    return new OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse
        .DraftSectionSource(
        order,
        code,
        javaVersion,
        nodeVersionMarker,
        endpoint,
        profile,
        sourceGateCount,
        sourceStatus,
        "passed");
  }
}
