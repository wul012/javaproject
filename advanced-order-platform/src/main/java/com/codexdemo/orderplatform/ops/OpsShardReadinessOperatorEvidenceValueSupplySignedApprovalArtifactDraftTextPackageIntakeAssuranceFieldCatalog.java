package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeAssuranceFieldCatalog {

    static final int ASSURANCE_FIELD_COUNT = 14;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeAssuranceFieldCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
            .IntakeField> assuranceFields() {
        return List.of(
                field("DRAFT_TEXT_PACKAGE_INTAKE_SOURCE_PLAN_VERSION_FIELD", "Node v1223-v1225",
                        "sourcePlanVersion", "Bind source evidence to Node v1236.",
                        "Source version does not import evidence.", "DRAFT_TEXT_PACKAGE_INTAKE_SOURCE_PLAN_VERSION_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEvidenceInstructionService
                                .ENDPOINT),
                field("DRAFT_TEXT_PACKAGE_INTAKE_SOURCE_FILE_REFERENCES_FIELD", "Node v1223-v1225",
                        "sourceFileReferences", "List expected source files for later review.",
                        "File references are not read as runtime payload.",
                        "DRAFT_TEXT_PACKAGE_INTAKE_SOURCE_FILE_REFERENCES_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEvidenceInstructionService
                                .ENDPOINT),
                field("DRAFT_TEXT_PACKAGE_INTAKE_SOURCE_SNIPPET_DIGEST_FIELD", "Node v1223-v1225",
                        "sourceSnippetDigest", "Pin source snippets without copying raw evidence.",
                        "Snippet digest cannot import source text.", "DRAFT_TEXT_PACKAGE_INTAKE_SOURCE_SNIPPET_DIGEST_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEvidenceInstructionService
                                .ENDPOINT),
                field("DRAFT_TEXT_PACKAGE_INTAKE_OPERATOR_VALUE_HANDLE_FIELD", "Node v1226-v1227",
                        "operatorValueHandle", "Bind future value evidence by handle.",
                        "Handle cannot expose credential value.", "DRAFT_TEXT_PACKAGE_INTAKE_OPERATOR_VALUE_HANDLE_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightValuePolicyInstructionService
                                .ENDPOINT),
                field("DRAFT_TEXT_PACKAGE_INTAKE_REDACTED_VALUE_DIGEST_FIELD", "Node v1226-v1227",
                        "redactedValueDigest", "Pin redacted value shape for review.",
                        "Digest cannot import operator value.", "DRAFT_TEXT_PACKAGE_INTAKE_REDACTED_VALUE_DIGEST_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightValuePolicyInstructionService
                                .ENDPOINT),
                field("DRAFT_TEXT_PACKAGE_INTAKE_REDACTION_POLICY_FIELD", "Node v1228-v1230",
                        "redactionPolicy", "Declare redaction policy for future review.",
                        "Policy cannot reveal raw secret values.", "DRAFT_TEXT_PACKAGE_INTAKE_REDACTION_POLICY_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightValuePolicyInstructionService
                                .ENDPOINT),
                field("DRAFT_TEXT_PACKAGE_INTAKE_PROVENANCE_POLICY_FIELD", "Node v1228-v1230",
                        "provenancePolicy", "Declare source provenance expectations.",
                        "Policy cannot mutate sibling evidence.", "DRAFT_TEXT_PACKAGE_INTAKE_PROVENANCE_POLICY_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightValuePolicyInstructionService
                                .ENDPOINT),
                field("DRAFT_TEXT_PACKAGE_INTAKE_REVIEW_STATE_FIELD", "Node v1228-v1230",
                        "reviewState", "Hold package state before any approval consideration.",
                        "Review state cannot grant approval.", "DRAFT_TEXT_PACKAGE_INTAKE_REVIEW_STATE_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightDraftTextLockService
                                .ENDPOINT),
                field("DRAFT_TEXT_PACKAGE_INTAKE_WRITE_ROUTE_LOCK_FIELD", "Node v1231-v1235",
                        "writeRouteLock", "Confirm write routing remains disabled.",
                        "Lock field cannot open write routing.", "DRAFT_TEXT_PACKAGE_INTAKE_WRITE_ROUTE_LOCK_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEmbargoInstructionService
                                .ENDPOINT),
                field("DRAFT_TEXT_PACKAGE_INTAKE_RUNTIME_PAYLOAD_LOCK_FIELD", "Node v1231-v1235",
                        "runtimePayloadLock", "Confirm runtime payload remains unavailable.",
                        "Lock field cannot create payload.", "DRAFT_TEXT_PACKAGE_INTAKE_RUNTIME_PAYLOAD_LOCK_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEmbargoInstructionService
                                .ENDPOINT),
                field("DRAFT_TEXT_PACKAGE_INTAKE_JAVA_STARTUP_LOCK_FIELD", "Node v1231-v1235",
                        "javaStartupLock", "Confirm Java service startup is out of scope.",
                        "Lock field cannot start Java.", "DRAFT_TEXT_PACKAGE_INTAKE_JAVA_STARTUP_LOCK_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEmbargoInstructionService
                                .ENDPOINT),
                field("DRAFT_TEXT_PACKAGE_INTAKE_MINI_KV_STARTUP_LOCK_FIELD", "Node v1231-v1235",
                        "miniKvStartupLock", "Confirm mini-kv startup is out of scope.",
                        "Lock field cannot start mini-kv.", "DRAFT_TEXT_PACKAGE_INTAKE_MINI_KV_STARTUP_LOCK_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEmbargoInstructionService
                                .ENDPOINT),
                field("DRAFT_TEXT_PACKAGE_INTAKE_SIBLING_MUTATION_LOCK_FIELD", "Node v1231-v1235",
                        "siblingMutationLock", "Confirm sibling mutation remains blocked.",
                        "Lock field cannot mutate sibling state.", "DRAFT_TEXT_PACKAGE_INTAKE_SIBLING_MUTATION_LOCK_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEmbargoInstructionService
                                .ENDPOINT),
                field("DRAFT_TEXT_PACKAGE_INTAKE_ARCHIVE_CLOSEOUT_MANIFEST_FIELD", "Node v1236",
                        "archiveCloseoutManifest", "Close the intake contract with archive evidence expectations.",
                        "Closeout cannot accept signed package text.", "DRAFT_TEXT_PACKAGE_INTAKE_ARCHIVE_CLOSEOUT_MANIFEST_GUARD",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCloseoutService
                                .ENDPOINT)
        );
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
            .IntakeField field(
                    String code,
                    String versionRange,
                    String expectedField,
                    String intakePurpose,
                    String materializationBlocker,
                    String guardCode,
                    String sourceEndpoint
    ) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSupport
                .field(code, versionRange, expectedField, intakePurpose, materializationBlocker, guardCode,
                        sourceEndpoint);
    }
}
