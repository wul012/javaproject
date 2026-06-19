package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightEvidenceValueService;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightAssuranceLaneCatalog {

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightAssuranceLaneCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
              .ComparisonLane>
      evidenceValuePolicyLanes() {
    return List.of(
        lane(
            "comparison-lane-source-evidence-handle-set",
            "v1298",
            "compare source evidence handle set against submitted material",
            "Are source evidence handles comparable without importing evidence?",
            "reject-package-source-evidence-handle-missing"),
        lane(
            "comparison-lane-source-evidence-freeze",
            "v1299",
            "compare source evidence freeze state against closeout handoff",
            "Does the submitted package preserve frozen sibling evidence state?",
            "reject-package-source-evidence-freeze-missing"),
        lane(
            "comparison-lane-source-evidence-lineage",
            "v1300",
            "compare source evidence lineage against read-only route evidence",
            "Is lineage present without starting sibling services?",
            "reject-package-source-evidence-lineage-untraceable"),
        lane(
            "comparison-lane-operator-value-handle",
            "v1301",
            "compare operator value handle against expected submission lane",
            "Is the operator value represented as a handle and not a credential?",
            "reject-package-operator-value-handle-missing"),
        lane(
            "comparison-lane-operator-value-redaction",
            "v1302",
            "compare operator value redaction handle against policy controls",
            "Can the value handle be compared without value import?",
            "reject-package-operator-value-redaction-unbound"),
        lane(
            "comparison-lane-policy-assertion",
            "v1303",
            "compare policy assertion material against submission preflight policy slots",
            "Are policy assertions present without emitting approval grant?",
            "reject-package-policy-assertion-missing"),
        lane(
            "comparison-lane-review-state",
            "v1304",
            "compare review-state bridge against Java v959 review preflight",
            "Does the package preserve separate-reviewer state before acceptance?",
            "reject-package-review-state-missing"),
        lane(
            "comparison-lane-approval-grant-absence",
            "v1305",
            "compare approval grant absence against closeout guardrails",
            "Does the package avoid implying an approval grant?",
            "reject-package-approval-grant-present"));
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
              .ComparisonLane>
      executionCloseoutLanes() {
    return List.of(
        lane(
            "comparison-lane-execution-lock",
            "v1306",
            "compare execution lock proof against submission closeout",
            "Does the package keep execution locked before acceptance?",
            "reject-package-execution-lock-missing"),
        lane(
            "comparison-lane-no-runtime-payload",
            "v1307",
            "compare runtime payload absence against closeout evidence",
            "Does the package avoid carrying runtime payload material?",
            "reject-package-runtime-payload-present"),
        lane(
            "comparison-lane-no-write-routing",
            "v1308",
            "compare no-write routing lock against acceptance controls",
            "Does the package avoid opening write routing?",
            "reject-package-write-routing-open"),
        lane(
            "comparison-lane-no-active-shard-router",
            "v1309",
            "compare active shard router absence against closeout evidence",
            "Does the package avoid implying an active shard router?",
            "reject-package-active-shard-router-open"),
        lane(
            "comparison-lane-no-sibling-mutation",
            "v1310",
            "compare sibling mutation lock against Java and mini-kv boundaries",
            "Does the package leave sibling state untouched?",
            "reject-package-sibling-mutation-open"),
        lane(
            "comparison-lane-archive-closeout",
            "v1311",
            "compare archive closeout lane against final acceptance-control summary",
            "Is archive closeout present as evidence without writing files?",
            "reject-package-archive-closeout-missing"));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
          .ComparisonLane
      lane(
          String code,
          String versionRange,
          String comparisonLane,
          String comparisonQuestion,
          String acceptanceControl) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightSupport
        .lane(
            code,
            versionRange,
            comparisonLane,
            comparisonQuestion,
            acceptanceControl,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightEvidenceValueService
                .ENDPOINT);
  }
}
