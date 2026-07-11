package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoRecords.RehearsalApprovalPrerequisiteArtifactNoGoBoundary;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoRecords.RehearsalApprovalPrerequisiteArtifactProhibitedField;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoRecords.RehearsalApprovalPrerequisiteArtifactRejectionReason;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoRecords.RehearsalApprovalPrerequisiteArtifactRequiredField;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoRecords.RehearsalApprovalPrerequisiteArtifactUpstreamEchoRequest;
import java.util.List;

final
class ReleaseApprovalSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeCatalog {

  private static final List<RequiredFieldTemplate> REQUIRED_FIELD_TEMPLATES =
      List.of(
          requiredField(
              "artifact_id",
              "Artifact id",
              "operator",
              "stable non-secret id",
              "Trace this intake artifact across Node, Java, and mini-kv evidence."),
          requiredField(
              "source_node_verification",
              "Source Node verification",
              "node-v305",
              "Node v305 digest + route reference",
              "Bind the artifact to the v305 upstream echo verification."),
          requiredField(
              "operator_approval_reference",
              "Operator approval reference",
              "operator",
              "non-secret approval ticket or review id",
              "Prove a human approval review exists before any runtime shell step."),
          requiredField(
              "credential_handle_review_status",
              "Credential handle review status",
              "audit-process",
              "credential handle + status, no value",
              "Show that only a credential handle was reviewed."),
          requiredField(
              "endpoint_handle_allowlist_review_status",
              "Endpoint handle allowlist review status",
              "audit-process",
              "endpoint handle + allowlist status, no raw URL",
              "Show that only an endpoint handle was reviewed."),
          requiredField(
              "no_network_safety_test_reference",
              "No-network safety test reference",
              "node-v305",
              "test or report id",
              "Prove the intake remains offline and does not send HTTP/TCP."),
          requiredField(
              "manual_abort_semantics_reference",
              "Manual abort semantics reference",
              "operator",
              "runbook or review id",
              "Document how an operator stops before any runtime shell invocation."),
          requiredField(
              "rollback_semantics_reference",
              "Rollback semantics reference",
              "operator",
              "runbook or review id",
              "Document rollback expectations without executing deployment or rollback."),
          requiredField(
              "java_echo_required_version",
              "Java echo required version",
              "java-v142",
              "Java v142 receipt reference",
              "Tell Java exactly which read-only artifact contract to echo."),
          requiredField(
              "mini_kv_receipt_required_version",
              "mini-kv receipt required version",
              "mini-kv-v135",
              "mini-kv v135 receipt reference",
              "Tell mini-kv exactly which non-participation receipt to emit."),
          requiredField(
              "created_by_operator_identity",
              "Created by operator identity",
              "operator",
              "operator id or identity handle",
              "Bind the artifact to a non-secret operator identity."),
          requiredField(
              "audit_correlation_id",
              "Audit correlation id",
              "audit-process",
              "stable correlation id",
              "Link all later evidence without embedding secret material."));

  private static final List<ProhibitedFieldTemplate> PROHIBITED_FIELD_TEMPLATES =
      List.of(
          prohibitedField(
              "credential_value",
              "Credential values are never accepted by Node, Java, or mini-kv in this stage.",
              "CREDENTIAL_VALUE_PRESENT"),
          prohibitedField(
              "raw_endpoint_url",
              "Raw endpoint URLs stay outside the artifact; only reviewed endpoint handles are allowed.",
              "RAW_ENDPOINT_URL_PRESENT"),
          prohibitedField(
              "secret_provider_config",
              "Secret provider configuration would imply implementation, not intake planning.",
              "PROVIDER_OR_CLIENT_CONFIG_PRESENT"),
          prohibitedField(
              "resolver_client_config",
              "Resolver client configuration would imply implementation, not intake planning.",
              "PROVIDER_OR_CLIENT_CONFIG_PRESENT"),
          prohibitedField(
              "external_request_payload",
              "External HTTP/TCP payloads are forbidden before a later explicit connection version.",
              "EXTERNAL_REQUEST_REQUESTED"),
          prohibitedField(
              "approval_ledger_mutation",
              "Approval ledger writes are Java-side production behavior and are blocked here.",
              "WRITE_OR_SCHEMA_MUTATION_REQUESTED"),
          prohibitedField(
              "schema_migration_sql",
              "Schema migration SQL is blocked in this artifact intake plan.",
              "WRITE_OR_SCHEMA_MUTATION_REQUESTED"),
          prohibitedField(
              "mini_kv_write_command",
              "mini-kv must remain non-authoritative and read-only.",
              "MINI_KV_WRITE_OR_AUTHORITY_REQUESTED"));

  private static final List<RejectionReasonTemplate> REJECTION_REASON_TEMPLATES =
      List.of(
          rejectionReason(
              "MISSING_OPERATOR_APPROVAL_REFERENCE",
              "operator-approval",
              "Reject artifacts without a non-secret operator approval reference."),
          rejectionReason(
              "CREDENTIAL_VALUE_PRESENT",
              "credential-boundary",
              "Reject artifacts that include credential values instead of handles."),
          rejectionReason(
              "RAW_ENDPOINT_URL_PRESENT",
              "endpoint-boundary",
              "Reject artifacts that include raw endpoint URLs instead of endpoint handles."),
          rejectionReason(
              "NO_NETWORK_SAFETY_TEST_MISSING",
              "runtime-boundary",
              "Reject artifacts that do not cite a no-network safety check."),
          rejectionReason(
              "ABORT_ROLLBACK_SEMANTICS_MISSING",
              "runtime-boundary",
              "Reject artifacts missing abort or rollback semantics."),
          rejectionReason(
              "JAVA_OR_MINIKV_ECHO_MISSING",
              "upstream-echo",
              "Reject completion claims until Java v142 and mini-kv v135 echo this contract."),
          rejectionReason(
              "RUNTIME_SHELL_IMPLEMENTATION_REQUESTED",
              "runtime-boundary",
              "Reject requests to implement, enable, or invoke a runtime shell."),
          rejectionReason(
              "EXTERNAL_REQUEST_REQUESTED",
              "runtime-boundary",
              "Reject requests to send HTTP/TCP or instantiate providers/clients."),
          rejectionReason(
              "WRITE_OR_SCHEMA_MUTATION_REQUESTED",
              "write-boundary",
              "Reject ledger writes, SQL migrations, deployments, rollbacks, and mini-kv write/admin commands."));

  private static final List<NoGoBoundaryTemplate> NO_GO_BOUNDARY_TEMPLATES =
      List.of(
          noGoBoundary(
              "runtime_shell_implemented", "No runtime shell implementation belongs in v306."),
          noGoBoundary(
              "runtime_shell_invocation_allowed", "No runtime shell invocation is allowed."),
          noGoBoundary("execution_allowed", "No execution path is opened by an intake plan."),
          noGoBoundary("connects_managed_audit", "No managed audit connection is opened."),
          noGoBoundary("credential_value_read", "No credential value is read or stored."),
          noGoBoundary("raw_endpoint_url_parsed", "No raw endpoint URL is parsed or rendered."),
          noGoBoundary("external_request_sent", "No HTTP/TCP request is sent."),
          noGoBoundary(
              "provider_or_client_instantiated",
              "No secret provider, resolver client, fake provider, or fake client is instantiated."),
          noGoBoundary("schema_migration_executed", "No schema migration is executed."),
          noGoBoundary("approval_ledger_written", "No approval ledger write is performed."),
          noGoBoundary(
              "mini_kv_write_or_authority", "mini-kv remains read-only and non-authoritative."),
          noGoBoundary(
              "automatic_upstream_start",
              "Node does not start Java, mini-kv, or external audit services."));

  private static final List<String> PROOF_CLAIMS =
      List.of(
          "managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt.consumedByNodeApprovalPrerequisiteArtifactIntakePlanState=approval-prerequisite-artifact-intake-plan-ready",
          "managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt.artifactIntakePlan.requiredFieldCount=12",
          "managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt.artifactIntakePlan.prohibitedFieldCount=8",
          "managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt.artifactIntakePlan.rejectionReasonCount=9",
          "managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt.artifactIntakePlan.noGoBoundaryCount=12",
          "managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt.artifactIntakePlan.javaMiniKvEchoCanRunInParallel=true",
          "managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt.sideEffectBoundary.credentialValueRead=false",
          "managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt.sideEffectBoundary.approvalLedgerWritten=false",
          "managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt.readyForNodeV307ApprovalPrerequisiteArtifactUpstreamEchoVerification=true");

  private static final List<String> NODE_VERIFICATION_ACTIONS =
      List.of(
          "Compare managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt.consumedByNodeApprovalPrerequisiteArtifactIntakePlanProfile with Node v306",
          "Require managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt.artifactIntakePlan.requiredFieldCount=12 before Node v307",
          "Require managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt.artifactIntakePlan.prohibitedFieldCount=8 before Node v307",
          "Require managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt.artifactIntakePlan.rejectionReasonCount=9 before Node v307",
          "Require managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt.artifactIntakePlan.noGoBoundaryCount=12 before Node v307",
          "Require managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt.artifactIntakePlan.javaMiniKvEchoCanRunInParallel=true before Node v307",
          "Keep managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt.sideEffectBoundary.credentialValueRead=false",
          "Keep managedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt.sideEffectBoundary.miniKvWriteOrAuthorityCommandExecuted=false");

  private static final List<String> NODE_WARNING_CODES =
      List.of("ARTIFACT_INTAKE_PLAN_DOES_NOT_AUTHORIZE_RUNTIME");

  private static final List<String> NODE_RECOMMENDATION_CODES =
      List.of(
          "RUN_JAVA_V142_AND_MINI_KV_V135_IN_PARALLEL_AFTER_V306",
          "VERIFY_ARTIFACT_ECHO_WITH_NODE_V307");

  private static final List<String> NEXT_REQUIRED_ECHO_VERSIONS =
      List.of(
          "mini-kv v135 approval prerequisite artifact non-participation receipt",
          "Node v307 approval prerequisite artifact upstream echo verification");

  private
  ReleaseApprovalSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeCatalog() {}

  static List<RehearsalApprovalPrerequisiteArtifactRequiredField> requiredFields() {
    return REQUIRED_FIELD_TEMPLATES.stream()
        .map(
            template ->
                new RehearsalApprovalPrerequisiteArtifactRequiredField(
                    template.id(),
                    template.label(),
                    true,
                    template.source(),
                    template.acceptedShape(),
                    template.purpose()))
        .toList();
  }

  static List<RehearsalApprovalPrerequisiteArtifactProhibitedField> prohibitedFields() {
    return PROHIBITED_FIELD_TEMPLATES.stream()
        .map(
            template ->
                new RehearsalApprovalPrerequisiteArtifactProhibitedField(
                    template.id(), template.reason(), template.rejectionCode()))
        .toList();
  }

  static List<RehearsalApprovalPrerequisiteArtifactRejectionReason> rejectionReasons() {
    return REJECTION_REASON_TEMPLATES.stream()
        .map(
            template ->
                new RehearsalApprovalPrerequisiteArtifactRejectionReason(
                    template.code(), template.source(), template.message()))
        .toList();
  }

  static List<RehearsalApprovalPrerequisiteArtifactNoGoBoundary> noGoBoundaries() {
    return NO_GO_BOUNDARY_TEMPLATES.stream()
        .map(
            template ->
                new RehearsalApprovalPrerequisiteArtifactNoGoBoundary(
                    template.id(), false, template.message()))
        .toList();
  }

  static List<RehearsalApprovalPrerequisiteArtifactUpstreamEchoRequest> upstreamEchoRequests() {
    return List.of(
        new RehearsalApprovalPrerequisiteArtifactUpstreamEchoRequest(
            "java",
            "Java v142",
            "Echo the v306 artifact schema, required fields, rejection reasons, and no-go boundaries without ledger writes, SQL, deployment, rollback, or external managed audit calls.",
            true,
            true),
        new RehearsalApprovalPrerequisiteArtifactUpstreamEchoRequest(
            "mini-kv",
            "mini-kv v135",
            "Emit a read-only non-participation receipt for the v306 artifact schema without LOAD, COMPACT, RESTORE, SETNXEX, writes, or authority claims.",
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
      String id, String label, String source, String acceptedShape, String purpose) {
    return new RequiredFieldTemplate(id, label, source, acceptedShape, purpose);
  }

  private static ProhibitedFieldTemplate prohibitedField(
      String id, String reason, String rejectionCode) {
    return new ProhibitedFieldTemplate(id, reason, rejectionCode);
  }

  private static RejectionReasonTemplate rejectionReason(
      String code, String source, String message) {
    return new RejectionReasonTemplate(code, source, message);
  }

  private static NoGoBoundaryTemplate noGoBoundary(String id, String message) {
    return new NoGoBoundaryTemplate(id, message);
  }

  private record RequiredFieldTemplate(
      String id, String label, String source, String acceptedShape, String purpose) {}

  private record ProhibitedFieldTemplate(String id, String reason, String rejectionCode) {}

  private record RejectionReasonTemplate(String code, String source, String message) {}

  private record NoGoBoundaryTemplate(String id, String message) {}
}
