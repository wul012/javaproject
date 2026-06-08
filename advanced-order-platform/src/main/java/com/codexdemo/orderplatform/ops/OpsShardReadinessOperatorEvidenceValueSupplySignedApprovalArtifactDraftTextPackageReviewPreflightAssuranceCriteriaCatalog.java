package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightAssuranceCriteriaCatalog {

    static final int ASSURANCE_CRITERION_COUNT = 14;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightAssuranceCriteriaCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
            .ReviewCriterion> assuranceCriteria() {
        return List.of(
                criterion("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_SOURCE_PLAN_CRITERION", "Node v1248-v1250",
                        "sourcePlanVersion names Node v1261.", "Does the source version match review preflight?",
                        "reject missing sourcePlanVersion",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSourceEvidenceService
                                .ENDPOINT),
                criterion("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_SOURCE_FILES_CRITERION", "Node v1248-v1250",
                        "sourceFileReferences are present and redacted.", "Are source files reviewable by reference?",
                        "reject raw source file payload",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSourceEvidenceService
                                .ENDPOINT),
                criterion("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_SOURCE_SNIPPET_CRITERION", "Node v1248-v1250",
                        "sourceSnippetDigest is present.", "Can snippet evidence be checked by digest?",
                        "reject raw snippet text",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSourceEvidenceService
                                .ENDPOINT),
                criterion("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_VALUE_HANDLE_CRITERION", "Node v1251-v1252",
                        "operatorValueHandle is redacted and stable.", "Is the value handle reviewable without secret value?",
                        "reject raw operator value",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeOperatorValueHandleService
                                .ENDPOINT),
                criterion("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_REDACTED_VALUE_DIGEST_CRITERION", "Node v1251-v1252",
                        "redactedValueDigest matches expected shape.", "Does value evidence stay redacted?",
                        "reject value digest mismatch",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeOperatorValueHandleService
                                .ENDPOINT),
                criterion("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_REDACTION_POLICY_CRITERION", "Node v1253-v1255",
                        "redactionPolicy is reviewable.", "Does policy block raw secret leakage?",
                        "reject missing redaction policy",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakePolicyReviewStateService
                                .ENDPOINT),
                criterion("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_PROVENANCE_POLICY_CRITERION", "Node v1253-v1255",
                        "provenancePolicy is reviewable.", "Does provenance remain pinned?",
                        "reject missing provenance policy",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakePolicyReviewStateService
                                .ENDPOINT),
                criterion("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_REVIEW_STATE_CRITERION", "Node v1253-v1255",
                        "reviewState remains pre-acceptance.", "Is package state still before acceptance?",
                        "reject approved or accepted state",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakePolicyReviewStateService
                                .ENDPOINT),
                criterion("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_WRITE_ROUTE_LOCK_CRITERION", "Node v1256-v1260",
                        "writeRouteLock remains closed.", "Is write routing closed?",
                        "reject open write route",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeExecutionLockService
                                .ENDPOINT),
                criterion("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_RUNTIME_PAYLOAD_LOCK_CRITERION", "Node v1256-v1260",
                        "runtimePayloadLock remains closed.", "Is runtime payload creation closed?",
                        "reject runtime payload material",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeExecutionLockService
                                .ENDPOINT),
                criterion("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_JAVA_STARTUP_LOCK_CRITERION", "Node v1256-v1260",
                        "javaStartupLock remains closed.", "Does review avoid starting Java?",
                        "reject Java startup request",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeExecutionLockService
                                .ENDPOINT),
                criterion("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_MINI_KV_STARTUP_LOCK_CRITERION", "Node v1256-v1260",
                        "miniKvStartupLock remains closed.", "Does review avoid starting mini-kv?",
                        "reject mini-kv startup request",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeExecutionLockService
                                .ENDPOINT),
                criterion("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_SIBLING_MUTATION_LOCK_CRITERION", "Node v1256-v1260",
                        "siblingMutationLock remains closed.", "Is sibling mutation still blocked?",
                        "reject sibling mutation request",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeExecutionLockService
                                .ENDPOINT),
                criterion("DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_ARCHIVE_CLOSEOUT_CRITERION", "Node v1261",
                        "archiveCloseoutManifest summarizes review controls.", "Can the reviewer close without acceptance?",
                        "reject missing archive closeout manifest",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeArchiveCloseoutService
                                .ENDPOINT)
        );
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
            .ReviewCriterion criterion(
                    String code,
                    String versionRange,
                    String reviewCriterion,
                    String reviewQuestion,
                    String materialRejectionControl,
                    String sourceEndpoint
    ) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightSupport
                .criterion(code, versionRange, reviewCriterion, reviewQuestion, materialRejectionControl,
                        sourceEndpoint);
    }
}
