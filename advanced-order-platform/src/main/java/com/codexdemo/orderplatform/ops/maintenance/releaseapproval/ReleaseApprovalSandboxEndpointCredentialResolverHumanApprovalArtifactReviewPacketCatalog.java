package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoRecords.RehearsalHumanApprovalArtifactReviewMissingFieldCheck;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoRecords.RehearsalHumanApprovalArtifactReviewNoGoBoundary;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoRecords.RehearsalHumanApprovalArtifactReviewProhibitedField;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoRecords.RehearsalHumanApprovalArtifactReviewRejectionReason;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoRecords.RehearsalHumanApprovalArtifactReviewRequiredField;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoRecords.RehearsalHumanApprovalArtifactReviewUpstreamEchoRequest;
import java.util.List;

final
class ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketCatalog {

  private static final List<RequiredFieldTemplate> REQUIRED_FIELD_TEMPLATES =
      List.of(
          requiredField(
              "artifact_id", "Artifact id", "Stable artifact id bound to the human review packet."),
          requiredField(
              "operator_approval_reference",
              "Operator approval reference",
              "Human approval reference or ticket handle."),
          requiredField(
              "credential_handle_review_status",
              "Credential handle review status",
              "Review status for the credential handle, not credential value."),
          requiredField(
              "endpoint_handle_allowlist_review_status",
              "Endpoint handle allowlist review status",
              "Allowlist review status for endpoint handle, not raw URL."),
          requiredField(
              "no_network_safety_test_reference",
              "No-network safety test reference",
              "Evidence that review packet validation performs no HTTP/TCP request."),
          requiredField(
              "manual_abort_semantics_reference",
              "Manual abort semantics reference",
              "Documented operator abort behavior before runtime discussion."),
          requiredField(
              "rollback_semantics_reference",
              "Rollback semantics reference",
              "Documented rollback behavior without executing rollback."),
          requiredField(
              "created_by_operator_identity",
              "Created by operator identity",
              "Verified operator identity for the review artifact."),
          requiredField(
              "audit_correlation_id",
              "Audit correlation id",
              "Correlation id linking artifact review, audit trail, and later echo verification."));

  private static final List<ProhibitedFieldTemplate> PROHIBITED_FIELD_TEMPLATES =
      List.of(
          prohibitedField(
              "credential_value",
              "Credential values must never enter Node, Java, or mini-kv evidence.",
              "CREDENTIAL_VALUE_PRESENT"),
          prohibitedField(
              "raw_endpoint_url",
              "The review packet may carry endpoint handle status, not raw endpoint URLs.",
              "RAW_ENDPOINT_URL_PRESENT"),
          prohibitedField(
              "secret_provider_config",
              "Provider config would move v308 from contract review into implementation.",
              "PROVIDER_CONFIG_PRESENT"),
          prohibitedField(
              "resolver_client_config",
              "Resolver client config would instantiate the runtime path too early.",
              "RESOLVER_CLIENT_CONFIG_PRESENT"),
          prohibitedField(
              "external_request_payload",
              "v308 must not prepare or send HTTP/TCP payloads.",
              "EXTERNAL_REQUEST_PAYLOAD_PRESENT"),
          prohibitedField(
              "approval_ledger_mutation",
              "Ledger writes are outside this read-only packet contract.",
              "APPROVAL_LEDGER_MUTATION_PRESENT"),
          prohibitedField(
              "schema_migration_sql",
              "Schema migration SQL is prohibited in the review packet.",
              "SCHEMA_MIGRATION_SQL_PRESENT"),
          prohibitedField(
              "mini_kv_write_command",
              "mini-kv remains non-authoritative and must not receive write/admin commands.",
              "MINI_KV_WRITE_COMMAND_PRESENT"),
          prohibitedField(
              "runtime_shell_invocation_request",
              "Runtime shell invocation is still blocked after v308.",
              "RUNTIME_SHELL_INVOCATION_REQUEST_PRESENT"));

  private static final List<RejectionReasonTemplate> REJECTION_REASON_TEMPLATES =
      List.of(
          rejectionReason("MISSING_ARTIFACT_ID", "Reject when artifact_id is absent or blank."),
          rejectionReason(
              "MISSING_OPERATOR_APPROVAL_REFERENCE",
              "Reject when the operator approval reference is absent."),
          rejectionReason(
              "MISSING_CREDENTIAL_HANDLE_REVIEW_STATUS",
              "Reject when credential handle review status is absent."),
          rejectionReason(
              "MISSING_ENDPOINT_HANDLE_ALLOWLIST_REVIEW_STATUS",
              "Reject when endpoint handle allowlist review status is absent."),
          rejectionReason(
              "MISSING_NO_NETWORK_SAFETY_TEST_REFERENCE",
              "Reject when no-network safety test reference is absent."),
          rejectionReason(
              "MISSING_ABORT_OR_ROLLBACK_SEMANTICS",
              "Reject when abort or rollback semantics are missing."),
          rejectionReason(
              "CREDENTIAL_VALUE_PRESENT", "Reject any artifact that includes credential values."),
          rejectionReason(
              "RAW_ENDPOINT_URL_PRESENT", "Reject any artifact that includes a raw endpoint URL."),
          rejectionReason(
              "PROVIDER_OR_CLIENT_CONFIG_PRESENT", "Reject provider or resolver client config."),
          rejectionReason(
              "EXTERNAL_REQUEST_REQUESTED",
              "Reject external request payloads or execution requests."),
          rejectionReason(
              "WRITE_OR_SCHEMA_MUTATION_REQUESTED",
              "Reject ledger writes and schema migration requests."),
          rejectionReason(
              "MINI_KV_WRITE_OR_AUTHORITY_REQUESTED",
              "Reject mini-kv writes, admin commands, and authority claims."),
          rejectionReason(
              "RUNTIME_SHELL_IMPLEMENTATION_REQUESTED",
              "Reject runtime shell implementation or invocation requests."));

  private static final List<NoGoBoundaryTemplate> NO_GO_BOUNDARY_TEMPLATES =
      List.of(
          noGoBoundary("credential_value_read", "Review only credential handle status."),
          noGoBoundary("raw_endpoint_url_parse", "Review only endpoint handle allowlist status."),
          noGoBoundary(
              "secret_provider_instantiation", "No secret provider can be instantiated in v308."),
          noGoBoundary(
              "resolver_client_instantiation", "No resolver client can be instantiated in v308."),
          noGoBoundary(
              "fake_provider_or_client", "No fake provider/client is introduced as a shortcut."),
          noGoBoundary("external_http_or_tcp_request", "No HTTP/TCP request is prepared or sent."),
          noGoBoundary(
              "runtime_shell_implementation",
              "Disabled runtime shell implementation remains blocked."),
          noGoBoundary("runtime_shell_invocation", "Runtime shell invocation remains blocked."),
          noGoBoundary("approval_ledger_write", "Approval ledger writes remain blocked."),
          noGoBoundary("schema_migration", "Schema migration remains blocked."),
          noGoBoundary(
              "mini_kv_write_or_authority", "mini-kv stays a read-only evidence provider."),
          noGoBoundary(
              "automatic_upstream_start",
              "Node must not start Java, mini-kv, or external audit services."));

  private static final List<String> PROOF_CLAIMS =
      List.of(
          "managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt.consumedByNodeHumanApprovalArtifactReviewPacketState=human-approval-artifact-review-packet-ready",
          "managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt.reviewPacket.requiredFieldCount=9",
          "managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt.reviewPacket.prohibitedFieldCount=9",
          "managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt.reviewPacket.rejectionReasonCount=13",
          "managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt.reviewPacket.missingFieldCheckCount=9",
          "managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt.reviewPacket.noGoBoundaryCount=12",
          "managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt.sideEffectBoundary.credentialValueRead=false",
          "managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt.sideEffectBoundary.approvalLedgerWritten=false",
          "managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt.readyForNodeV309HumanApprovalArtifactReviewPacketUpstreamEchoVerification=true");

  private static final List<String> NODE_VERIFICATION_ACTIONS =
      List.of(
          "Compare managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt.consumedByNodeHumanApprovalArtifactReviewPacketProfile with Node v308",
          "Require managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt.reviewPacket.requiredFieldCount=9 before Node v309",
          "Require managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt.reviewPacket.prohibitedFieldCount=9 before Node v309",
          "Require managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt.reviewPacket.rejectionReasonCount=13 before Node v309",
          "Require managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt.reviewPacket.missingFieldCheckCount=9 before Node v309",
          "Require managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt.reviewPacket.noGoBoundaryCount=12 before Node v309",
          "Keep managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt.sideEffectBoundary.credentialValueRead=false",
          "Keep managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt.sideEffectBoundary.runtimeShellInvocationAllowed=false");

  private static final List<String> NODE_WARNING_CODES =
      List.of("REVIEW_PACKET_DOES_NOT_AUTHORIZE_RUNTIME_SHELL");

  private static final List<String> NODE_RECOMMENDATION_CODES =
      List.of(
          "RUN_JAVA_V143_AND_MINI_KV_V136_IN_PARALLEL", "VERIFY_REVIEW_PACKET_ECHO_WITH_NODE_V309");

  private static final List<String> NEXT_REQUIRED_ECHO_VERSIONS =
      List.of(
          "mini-kv v136 human approval artifact review non-participation receipt",
          "Node v309 human approval artifact review upstream echo verification");

  private
  ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketCatalog() {}

  static List<RehearsalHumanApprovalArtifactReviewRequiredField> requiredFields() {
    return REQUIRED_FIELD_TEMPLATES.stream()
        .map(
            template ->
                new RehearsalHumanApprovalArtifactReviewRequiredField(
                    template.id(),
                    template.label(),
                    template.requiredEvidence(),
                    template.missingFieldCode()))
        .toList();
  }

  static List<RehearsalHumanApprovalArtifactReviewProhibitedField> prohibitedFields() {
    return PROHIBITED_FIELD_TEMPLATES.stream()
        .map(
            template ->
                new RehearsalHumanApprovalArtifactReviewProhibitedField(
                    template.id(), template.reason(), template.rejectionCode()))
        .toList();
  }

  static List<RehearsalHumanApprovalArtifactReviewRejectionReason> rejectionReasons() {
    return REJECTION_REASON_TEMPLATES.stream()
        .map(
            template ->
                new RehearsalHumanApprovalArtifactReviewRejectionReason(
                    template.code(), template.message()))
        .toList();
  }

  static List<RehearsalHumanApprovalArtifactReviewMissingFieldCheck> missingFieldChecks(
      List<RehearsalHumanApprovalArtifactReviewRequiredField> requiredFields) {
    return requiredFields.stream()
        .map(
            field ->
                new RehearsalHumanApprovalArtifactReviewMissingFieldCheck(
                    field.id(), field.missingFieldCode()))
        .toList();
  }

  static List<RehearsalHumanApprovalArtifactReviewNoGoBoundary> noGoBoundaries() {
    return NO_GO_BOUNDARY_TEMPLATES.stream()
        .map(
            template ->
                new RehearsalHumanApprovalArtifactReviewNoGoBoundary(
                    template.id(), true, template.reason()))
        .toList();
  }

  static List<RehearsalHumanApprovalArtifactReviewUpstreamEchoRequest> upstreamEchoRequests() {
    return List.of(
        new RehearsalHumanApprovalArtifactReviewUpstreamEchoRequest(
            "java",
            "Java v143",
            "read-only-human-approval-artifact-review-packet-echo",
            true,
            true),
        new RehearsalHumanApprovalArtifactReviewUpstreamEchoRequest(
            "mini-kv",
            "mini-kv v136",
            "read-only-human-approval-artifact-review-non-participation-receipt",
            true,
            true));
  }

  static List<String> requiredFieldIds() {
    return REQUIRED_FIELD_TEMPLATES.stream().map(RequiredFieldTemplate::id).toList();
  }

  static List<String> prohibitedFieldIds() {
    return PROHIBITED_FIELD_TEMPLATES.stream().map(ProhibitedFieldTemplate::id).toList();
  }

  static List<String> rejectionReasonCodes() {
    return REJECTION_REASON_TEMPLATES.stream().map(RejectionReasonTemplate::code).toList();
  }

  static List<String> missingFieldCheckCodes() {
    return REQUIRED_FIELD_TEMPLATES.stream().map(RequiredFieldTemplate::missingFieldCode).toList();
  }

  static List<String> noGoBoundaryIds() {
    return NO_GO_BOUNDARY_TEMPLATES.stream().map(NoGoBoundaryTemplate::id).toList();
  }

  static List<String> proofClaims() {
    return PROOF_CLAIMS;
  }

  static List<String> nodeVerificationActions() {
    return NODE_VERIFICATION_ACTIONS;
  }

  static List<String> nodeWarningCodes() {
    return NODE_WARNING_CODES;
  }

  static List<String> nodeRecommendationCodes() {
    return NODE_RECOMMENDATION_CODES;
  }

  static List<String> nextRequiredEchoVersions() {
    return NEXT_REQUIRED_ECHO_VERSIONS;
  }

  private static RequiredFieldTemplate requiredField(
      String id, String label, String requiredEvidence) {
    return new RequiredFieldTemplate(id, label, requiredEvidence, "MISSING_" + id.toUpperCase());
  }

  private static ProhibitedFieldTemplate prohibitedField(
      String id, String reason, String rejectionCode) {
    return new ProhibitedFieldTemplate(id, reason, rejectionCode);
  }

  private static RejectionReasonTemplate rejectionReason(String code, String message) {
    return new RejectionReasonTemplate(code, message);
  }

  private static NoGoBoundaryTemplate noGoBoundary(String id, String reason) {
    return new NoGoBoundaryTemplate(id, reason);
  }

  private record RequiredFieldTemplate(
      String id, String label, String requiredEvidence, String missingFieldCode) {}

  private record ProhibitedFieldTemplate(String id, String reason, String rejectionCode) {}

  private record RejectionReasonTemplate(String code, String message) {}

  private record NoGoBoundaryTemplate(String id, String reason) {}
}
