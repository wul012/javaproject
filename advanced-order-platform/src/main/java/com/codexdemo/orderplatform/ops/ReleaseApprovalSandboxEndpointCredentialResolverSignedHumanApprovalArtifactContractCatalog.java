package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoRecords
        .RehearsalSignedHumanApprovalArtifactNoGoBoundary;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoRecords
        .RehearsalSignedHumanApprovalArtifactProhibitedField;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoRecords
        .RehearsalSignedHumanApprovalArtifactRejectionReason;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoRecords
        .RehearsalSignedHumanApprovalArtifactRequiredField;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoRecords
        .RehearsalSignedHumanApprovalArtifactUpstreamEchoRequest;
import java.util.List;

final class ReleaseApprovalSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractCatalog {

    static final String TARGET_PREREQUISITE_ID = "signed-human-approval-artifact";
    static final String NODE_V314_CONTRACT_DIGEST =
            "72498e59c086eadd4d44e80789120de195af1a0b70dd49346b837e2bc8ed4666";
    static final String SOURCE_NODE_V312_DECISION_DIGEST =
            "152d7517c07119df360446a29c508f5d3d9a78a28adfc6137ea0b0254508b0c6";
    static final String SOURCE_NODE_V312_VERIFICATION_DIGEST =
            "8292327cdb44e1d37ead67ff5a0444c08625860c62a3648846801a84f5a6f194";

    private static final List<RequiredFieldTemplate> REQUIRED_FIELDS = List.of(
            requiredField("artifact_id", "Artifact id", "stable non-secret artifact id",
                    "Trace the signed approval artifact across Node, Java, and mini-kv evidence."),
            requiredField("approval_correlation_id", "Approval correlation id",
                    "stable non-secret correlation id", "Bind the signed artifact to the approval review chain."),
            requiredField("operator_identity_handle", "Operator identity handle",
                    "operator identity handle, no credential value",
                    "Identify the requesting operator without embedding secret material."),
            requiredField("signer_identity_handle", "Signer identity handle",
                    "signer identity handle, no private key",
                    "Identify the human signer without carrying signing keys or credentials."),
            requiredField("policy_version", "Policy version", "policy id or semantic version",
                    "Bind the artifact to a known approval policy contract."),
            requiredField("artifact_digest", "Artifact digest", "sha256 digest or equivalent stable digest",
                    "Prove artifact immutability without embedding raw secret payloads."),
            requiredField("issued_at", "Issued at", "ISO-8601 timestamp",
                    "Declare when the human approval artifact was issued."),
            requiredField("expires_at", "Expires at", "ISO-8601 timestamp",
                    "Prevent stale approval artifacts from being treated as current."),
            requiredField("review_status", "Review status", "approved, rejected, expired, or revoked",
                    "Keep this version contract-only and status-based."),
            requiredField("no_network_assertion", "No-network assertion",
                    "boolean assertion plus evidence handle", "Assert this contract path sends no HTTP/TCP request."),
            requiredField("rollback_abort_reference", "Rollback/abort reference",
                    "runbook or evidence handle",
                    "Reference manual abort and rollback semantics without executing them.")
    );

    private static final List<ProhibitedFieldTemplate> PROHIBITED_FIELDS = List.of(
            prohibitedField("credential_value", "Credential values must not enter the signed approval artifact.",
                    "CREDENTIAL_VALUE_PRESENT"),
            prohibitedField("raw_endpoint_url", "The contract may reference endpoint handles, not raw endpoint URLs.",
                    "RAW_ENDPOINT_URL_PRESENT"),
            prohibitedField("signing_private_key", "A signed artifact may reference a signer handle, not a private key.",
                    "SIGNING_PRIVATE_KEY_PRESENT"),
            prohibitedField("secret_provider_config", "Provider configuration would turn this contract into implementation.",
                    "PROVIDER_CONFIG_PRESENT"),
            prohibitedField("resolver_client_config",
                    "Resolver client configuration is not allowed in a contract-only intake.",
                    "RESOLVER_CLIENT_CONFIG_PRESENT"),
            prohibitedField("external_request_payload", "No HTTP/TCP payload may be prepared or sent by v314.",
                    "EXTERNAL_REQUEST_PAYLOAD_PRESENT"),
            prohibitedField("approval_ledger_mutation", "Approval ledger writes remain outside this Node contract.",
                    "APPROVAL_LEDGER_MUTATION_PRESENT"),
            prohibitedField("schema_migration_sql", "Schema migration SQL is prohibited in this intake.",
                    "SCHEMA_MIGRATION_SQL_PRESENT")
    );

    private static final List<RejectionTemplate> REJECTION_REASONS = List.of(
            rejection("SIGNED_ARTIFACT_MISSING", "artifact-contract",
                    "The signed approval artifact contract fields are missing."),
            rejection("CREDENTIAL_VALUE_PRESENT", "credential-boundary",
                    "Credential values are not allowed; only handles and review statuses are allowed."),
            rejection("RAW_ENDPOINT_URL_PRESENT", "endpoint-boundary",
                    "Raw endpoint URLs are not allowed; only endpoint handles may appear later."),
            rejection("RUNTIME_IMPLEMENTATION_PRESENT", "runtime-boundary",
                    "Runtime shell implementation or invocation details are prohibited."),
            rejection("WRITE_OR_MIGRATION_PRESENT", "write-boundary",
                    "Ledger writes, schema migration, deployment, and rollback execution are prohibited.")
    );

    private static final List<NoGoTemplate> NO_GO_BOUNDARIES = List.of(
            noGo("runtime_shell_implementation", "v314 must not implement a runtime shell."),
            noGo("runtime_shell_invocation", "v314 must not invoke a runtime shell."),
            noGo("credential_value_read", "v314 must not read managed audit credential values."),
            noGo("raw_endpoint_url_parse", "v314 must not parse or render raw endpoint URLs."),
            noGo("provider_client_instantiation", "v314 must not instantiate secret providers or resolver clients."),
            noGo("external_request", "v314 must not send HTTP/TCP requests."),
            noGo("ledger_or_schema_write", "v314 must not write approval ledger or schema state."),
            noGo("automatic_upstream_start",
                    "v314 must not automatically start Java, mini-kv, or external audit services.")
    );

    private static final List<EchoRequestTemplate> UPSTREAM_ECHO_REQUESTS = List.of(
            echoRequest("java", "Java v145",
                    "Read-only echo of the Node v314 signed human approval artifact contract."),
            echoRequest("mini-kv", "mini-kv v138",
                    "Non-participation receipt proving mini-kv does not store, validate, or become authority for signed approval artifacts.")
    );

    private static final List<String> SOURCE_COMPLETED_PREREQUISITE_IDS = List.of(
            "java-mini-kv-decision-echo"
    );

    private static final List<String> SOURCE_REMAINING_PREREQUISITE_IDS = List.of(
            TARGET_PREREQUISITE_ID,
            "credential-handle-approval",
            "endpoint-handle-allowlist-approval",
            "no-network-safety-fixture",
            "abort-rollback-semantics"
    );

    private static final List<String> PROOF_CLAIMS = List.of(
            "managedAuditSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceipt.consumedByNodeSignedHumanApprovalArtifactContractState=signed-human-approval-artifact-contract-intake-ready",
            "managedAuditSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceipt.signedArtifactContract.requiredFieldCount=11",
            "managedAuditSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceipt.signedArtifactContract.prohibitedFieldCount=8",
            "managedAuditSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceipt.signedArtifactContract.noGoBoundaryCount=8",
            "managedAuditSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceipt.prerequisiteTransition.afterV314=contract-intake-defined",
            "managedAuditSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceipt.sideEffectBoundary.approvalLedgerWritten=false",
            "managedAuditSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceipt.sideEffectBoundary.signedArtifactStoredByJava=false",
            "managedAuditSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceipt.readyForNodeV315SignedHumanApprovalArtifactContractUpstreamEchoVerification=true"
    );

    private static final List<String> NODE_VERIFICATION_ACTIONS = List.of(
            "Compare managedAuditSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceipt.consumedByNodeSignedHumanApprovalArtifactContractProfile with Node v314",
            "Require managedAuditSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceipt.signedArtifactContract.requiredFieldCount=11 before Node v315",
            "Require managedAuditSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceipt.signedArtifactContract.prohibitedFieldCount=8 before Node v315",
            "Require managedAuditSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceipt.readyForNodeV315SignedHumanApprovalArtifactContractUpstreamEchoVerification=true before Node v315",
            "Keep managedAuditSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceipt.sideEffectBoundary.credentialValueRead=false",
            "Keep managedAuditSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceipt.sideEffectBoundary.approvalLedgerWritten=false",
            "Keep managedAuditSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceipt.sideEffectBoundary.signedArtifactAuthorityClaimedByJava=false"
    );

    private static final List<String> WARNING_CODES = List.of(
            "SIGNED_ARTIFACT_CONTRACT_DOES_NOT_CLOSE_ALL_PREREQUISITES"
    );

    private static final List<String> RECOMMENDATION_CODES = List.of(
            "RUN_JAVA_V145_AND_MINI_KV_V138_AFTER_V314_ARCHIVE",
            "KEEP_SIGNED_ARTIFACT_CONTRACT_NON_SECRET"
    );

    private static final List<String> NEXT_REQUIRED_ECHO_VERSIONS = List.of(
            "mini-kv v138 signed human approval artifact non-participation receipt",
            "Node v315 signed human approval artifact contract upstream echo verification"
    );

    private ReleaseApprovalSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractCatalog() {
    }

    static List<RehearsalSignedHumanApprovalArtifactRequiredField> requiredFields() {
        return REQUIRED_FIELDS.stream()
                .map(template -> new RehearsalSignedHumanApprovalArtifactRequiredField(
                        template.id(), template.label(), true, template.acceptedShape(), template.purpose()
                ))
                .toList();
    }

    static List<RehearsalSignedHumanApprovalArtifactProhibitedField> prohibitedFields() {
        return PROHIBITED_FIELDS.stream()
                .map(template -> new RehearsalSignedHumanApprovalArtifactProhibitedField(
                        template.id(), template.reason(), template.rejectionCode()
                ))
                .toList();
    }

    static List<RehearsalSignedHumanApprovalArtifactRejectionReason> rejectionReasons() {
        return REJECTION_REASONS.stream()
                .map(template -> new RehearsalSignedHumanApprovalArtifactRejectionReason(
                        template.code(), template.source(), template.message()
                ))
                .toList();
    }

    static List<RehearsalSignedHumanApprovalArtifactNoGoBoundary> noGoBoundaries() {
        return NO_GO_BOUNDARIES.stream()
                .map(template -> new RehearsalSignedHumanApprovalArtifactNoGoBoundary(
                        template.id(), false, template.message()
                ))
                .toList();
    }

    static List<RehearsalSignedHumanApprovalArtifactUpstreamEchoRequest> upstreamEchoRequests() {
        return UPSTREAM_ECHO_REQUESTS.stream()
                .map(template -> new RehearsalSignedHumanApprovalArtifactUpstreamEchoRequest(
                        template.project(), template.version(), template.requestedEcho(), true, true
                ))
                .toList();
    }

    static List<String> requiredFieldIds() {
        return REQUIRED_FIELDS.stream().map(RequiredFieldTemplate::id).toList();
    }

    static List<String> prohibitedFieldIds() {
        return PROHIBITED_FIELDS.stream().map(ProhibitedFieldTemplate::id).toList();
    }

    static List<String> noGoBoundaryIds() {
        return NO_GO_BOUNDARIES.stream().map(NoGoTemplate::id).toList();
    }

    static List<String> sourceCompletedPrerequisiteIds() {
        return SOURCE_COMPLETED_PREREQUISITE_IDS;
    }

    static List<String> sourceRemainingPrerequisiteIds() {
        return SOURCE_REMAINING_PREREQUISITE_IDS;
    }

    static List<String> proofClaims() {
        return PROOF_CLAIMS;
    }

    static List<String> nodeVerificationActions() {
        return NODE_VERIFICATION_ACTIONS;
    }

    static List<String> warningCodes() {
        return WARNING_CODES;
    }

    static List<String> recommendationCodes() {
        return RECOMMENDATION_CODES;
    }

    static List<String> nextRequiredEchoVersions() {
        return NEXT_REQUIRED_ECHO_VERSIONS;
    }

    private static RequiredFieldTemplate requiredField(
            String id,
            String label,
            String acceptedShape,
            String purpose
    ) {
        return new RequiredFieldTemplate(id, label, acceptedShape, purpose);
    }

    private static ProhibitedFieldTemplate prohibitedField(String id, String reason, String rejectionCode) {
        return new ProhibitedFieldTemplate(id, reason, rejectionCode);
    }

    private static RejectionTemplate rejection(String code, String source, String message) {
        return new RejectionTemplate(code, source, message);
    }

    private static NoGoTemplate noGo(String id, String message) {
        return new NoGoTemplate(id, message);
    }

    private static EchoRequestTemplate echoRequest(String project, String version, String requestedEcho) {
        return new EchoRequestTemplate(project, version, requestedEcho);
    }

    private record RequiredFieldTemplate(String id, String label, String acceptedShape, String purpose) {
    }

    private record ProhibitedFieldTemplate(String id, String reason, String rejectionCode) {
    }

    private record RejectionTemplate(String code, String source, String message) {
    }

    private record NoGoTemplate(String id, String message) {
    }

    private record EchoRequestTemplate(String project, String version, String requestedEcho) {
    }
}
