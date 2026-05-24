package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoRecords
        .RehearsalEndpointHandleAllowlistApprovalNoGoBoundary;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoRecords
        .RehearsalEndpointHandleAllowlistApprovalProhibitedField;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoRecords
        .RehearsalEndpointHandleAllowlistApprovalRejectionReason;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoRecords
        .RehearsalEndpointHandleAllowlistApprovalRequiredField;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoRecords
        .RehearsalEndpointHandleAllowlistApprovalUpstreamEchoRequest;
import java.util.List;

final class ReleaseApprovalSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractCatalog {

    static final String TARGET_PREREQUISITE_ID = "endpoint-handle-allowlist-approval";
    static final String NODE_V320_CONTRACT_DIGEST =
            "4657f89caa6866bad87db284dc98efd8d09a6538d8d735535e6a7e6d4d6c33e5";
    static final String SOURCE_NODE_V319_REVIEW_DIGEST =
            "59888d94ccd996aeb2f126c25291a8f5ba6f37d6d93cdf190fc656c0121bc7e5";

    private static final List<RequiredFieldTemplate> REQUIRED_FIELDS = List.of(
            requiredField("endpoint_handle", "Endpoint handle", "stable non-secret endpoint handle",
                    "Identify which endpoint handle was reviewed without exposing the raw endpoint URL."),
            requiredField("approval_correlation_id", "Approval correlation id",
                    "stable non-secret correlation id",
                    "Bind the endpoint allowlist review to the signed approval chain."),
            requiredField("operator_identity_handle", "Operator identity handle",
                    "operator identity handle, no credential value",
                    "Identify the requesting operator without embedding secret material."),
            requiredField("reviewer_identity_handle", "Reviewer identity handle",
                    "reviewer identity handle, no private key",
                    "Identify the human reviewer without carrying credentials or signing keys."),
            requiredField("policy_version", "Policy version", "policy id or semantic version",
                    "Bind the endpoint allowlist approval to a known review policy contract."),
            requiredField("approval_status", "Approval status", "approved, rejected, expired, or revoked",
                    "Keep this version contract-only and status-based."),
            requiredField("issued_at", "Issued at", "ISO-8601 timestamp",
                    "Declare when the endpoint handle allowlist approval was issued."),
            requiredField("expires_at", "Expires at", "ISO-8601 timestamp",
                    "Prevent stale endpoint allowlist approvals from being treated as current."),
            requiredField("revocation_marker", "Revocation marker",
                    "boolean marker plus optional evidence handle",
                    "Make revocation explicit without reading any secret provider state."),
            requiredField("audit_digest", "Audit digest", "sha256 digest or equivalent stable digest",
                    "Prove contract immutability without embedding raw credential or endpoint material.")
    );

    private static final List<ProhibitedFieldTemplate> PROHIBITED_FIELDS = List.of(
            prohibitedField("credential_value",
                    "Credential values must not enter the endpoint allowlist approval contract.",
                    "CREDENTIAL_VALUE_PRESENT"),
            prohibitedField("raw_endpoint_url",
                    "Raw endpoint URLs must not enter this contract; only endpoint handles and allowlist review status are allowed.",
                    "RAW_ENDPOINT_URL_PRESENT"),
            prohibitedField("secret_provider_config",
                    "Provider configuration would turn this contract into implementation.",
                    "SECRET_PROVIDER_CONFIG_PRESENT"),
            prohibitedField("resolver_client_config",
                    "Resolver client configuration is not allowed in a contract-only intake.",
                    "RESOLVER_CLIENT_CONFIG_PRESENT"),
            prohibitedField("provider_client_runtime_binding",
                    "Runtime bindings for providers or clients remain out of scope.",
                    "PROVIDER_CLIENT_RUNTIME_BINDING_PRESENT"),
            prohibitedField("external_request_payload",
                    "No HTTP/TCP payload may be prepared or sent by v320.",
                    "EXTERNAL_REQUEST_PAYLOAD_PRESENT"),
            prohibitedField("approval_ledger_mutation",
                    "Approval ledger writes remain outside this Node contract.",
                    "APPROVAL_LEDGER_MUTATION_PRESENT"),
            prohibitedField("schema_migration_sql",
                    "Schema migration SQL is prohibited in this intake.",
                    "SCHEMA_MIGRATION_SQL_PRESENT")
    );

    private static final List<RejectionTemplate> REJECTION_REASONS = List.of(
            rejection("ENDPOINT_HANDLE_MISSING", "endpoint-handle-contract",
                    "The endpoint handle allowlist approval contract fields are missing."),
            rejection("CREDENTIAL_VALUE_PRESENT", "credential-boundary",
                    "Credential values are not allowed; only handles and review statuses are allowed."),
            rejection("RAW_ENDPOINT_URL_PRESENT", "endpoint-boundary",
                    "Raw endpoint URLs are not allowed; this intake accepts only endpoint handle and allowlist review metadata."),
            rejection("PROVIDER_CLIENT_CONFIG_PRESENT", "provider-client-boundary",
                    "Secret provider and resolver client config are prohibited in this intake."),
            rejection("WRITE_OR_MIGRATION_PRESENT", "write-boundary",
                    "Ledger writes, schema migration, deployment, and rollback execution are prohibited.")
    );

    private static final List<NoGoTemplate> NO_GO_BOUNDARIES = List.of(
            noGo("credential_value_read", "v320 must not read managed audit credential values."),
            noGo("raw_endpoint_url_parse", "v320 must not parse or render raw endpoint URLs."),
            noGo("secret_provider_instantiation", "v320 must not instantiate secret providers."),
            noGo("resolver_client_instantiation", "v320 must not instantiate resolver clients."),
            noGo("external_request", "v320 must not send HTTP/TCP requests."),
            noGo("ledger_or_schema_write", "v320 must not write approval ledger or schema state."),
            noGo("automatic_upstream_start",
                    "v320 must not automatically start Java, mini-kv, or external audit services."),
            noGo("runtime_shell_implementation", "v320 must not implement a runtime shell."),
            noGo("runtime_shell_invocation", "v320 must not invoke a runtime shell.")
    );

    private static final List<EchoRequestTemplate> UPSTREAM_ECHO_REQUESTS = List.of(
            echoRequest("java", "Java v147",
                    "Read-only echo of the Node v320 endpoint-handle allowlist approval contract."),
            echoRequest("mini-kv", "mini-kv v140",
                    "Non-participation receipt proving mini-kv does not store raw endpoint URLs, validate allowlist authority, or become authority for endpoint handles.")
    );

    private static final List<String> SOURCE_COMPLETED_PREREQUISITE_IDS = List.of(
            "java-mini-kv-decision-echo",
            "signed-human-approval-artifact",
            "credential-handle-approval"
    );

    private static final List<String> SOURCE_REMAINING_PREREQUISITE_IDS = List.of(
            "endpoint-handle-allowlist-approval",
            "no-network-safety-fixture",
            "abort-rollback-semantics"
    );

    private static final List<String> PROOF_CLAIMS = List.of(
            "managedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceipt.consumedByNodeEndpointHandleAllowlistApprovalContractState=endpoint-handle-allowlist-approval-contract-intake-ready",
            "managedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceipt.endpointHandleAllowlistApprovalContract.requiredFieldCount=10",
            "managedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceipt.endpointHandleAllowlistApprovalContract.prohibitedFieldCount=8",
            "managedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceipt.endpointHandleAllowlistApprovalContract.noGoBoundaryCount=9",
            "managedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceipt.prerequisiteTransition.afterV320=contract-intake-defined",
            "managedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceipt.sideEffectBoundary.credentialValueRead=false",
            "managedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceipt.sideEffectBoundary.rawEndpointUrlParsed=false",
            "managedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceipt.sideEffectBoundary.endpointHandleAuthorityClaimedByJava=false",
            "managedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceipt.readyForNodeV321EndpointHandleAllowlistApprovalContractUpstreamEchoVerification=true"
    );

    private static final List<String> NODE_VERIFICATION_ACTIONS = List.of(
            "Compare managedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceipt.consumedByNodeEndpointHandleAllowlistApprovalContractProfile with Node v320",
            "Require managedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceipt.endpointHandleAllowlistApprovalContract.requiredFieldCount=10 before Node v321",
            "Require managedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceipt.endpointHandleAllowlistApprovalContract.prohibitedFieldCount=8 before Node v321",
            "Require managedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceipt.readyForNodeV321EndpointHandleAllowlistApprovalContractUpstreamEchoVerification=true before Node v321",
            "Keep managedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceipt.sideEffectBoundary.credentialValueRead=false",
            "Keep managedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceipt.sideEffectBoundary.rawEndpointUrlParsed=false",
            "Keep managedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceipt.sideEffectBoundary.endpointHandleAuthorityClaimedByJava=false"
    );

    private static final List<String> WARNING_CODES = List.of(
            "ENDPOINT_HANDLE_ALLOWLIST_CONTRACT_DOES_NOT_CLOSE_ALL_PREREQUISITES",
            "ENDPOINT_HANDLE_ALLOWLIST_APPROVAL_IS_NOT_CONNECTION_PERMISSION"
    );

    private static final List<String> RECOMMENDATION_CODES = List.of(
            "RUN_JAVA_V147_AND_MINI_KV_V140_AFTER_V320_ARCHIVE",
            "KEEP_ENDPOINT_HANDLE_ALLOWLIST_APPROVAL_NON_SECRET"
    );

    private static final List<String> NEXT_REQUIRED_ECHO_VERSIONS = List.of(
            "mini-kv v140 endpoint-handle allowlist approval non-participation receipt",
            "Node v321 endpoint-handle allowlist approval contract upstream echo verification"
    );

    private ReleaseApprovalSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractCatalog() {
    }

    static List<RehearsalEndpointHandleAllowlistApprovalRequiredField> requiredFields() {
        return REQUIRED_FIELDS.stream()
                .map(template -> new RehearsalEndpointHandleAllowlistApprovalRequiredField(
                        template.id(), template.label(), true, template.acceptedShape(), template.purpose()
                ))
                .toList();
    }

    static List<RehearsalEndpointHandleAllowlistApprovalProhibitedField> prohibitedFields() {
        return PROHIBITED_FIELDS.stream()
                .map(template -> new RehearsalEndpointHandleAllowlistApprovalProhibitedField(
                        template.id(), template.reason(), template.rejectionCode()
                ))
                .toList();
    }

    static List<RehearsalEndpointHandleAllowlistApprovalRejectionReason> rejectionReasons() {
        return REJECTION_REASONS.stream()
                .map(template -> new RehearsalEndpointHandleAllowlistApprovalRejectionReason(
                        template.code(), template.source(), template.message()
                ))
                .toList();
    }

    static List<RehearsalEndpointHandleAllowlistApprovalNoGoBoundary> noGoBoundaries() {
        return NO_GO_BOUNDARIES.stream()
                .map(template -> new RehearsalEndpointHandleAllowlistApprovalNoGoBoundary(
                        template.id(), false, template.message()
                ))
                .toList();
    }

    static List<RehearsalEndpointHandleAllowlistApprovalUpstreamEchoRequest> upstreamEchoRequests() {
        return UPSTREAM_ECHO_REQUESTS.stream()
                .map(template -> new RehearsalEndpointHandleAllowlistApprovalUpstreamEchoRequest(
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
