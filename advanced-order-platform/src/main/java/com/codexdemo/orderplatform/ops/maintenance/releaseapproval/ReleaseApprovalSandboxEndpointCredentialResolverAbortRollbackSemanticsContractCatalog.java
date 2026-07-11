package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoRecords.RehearsalAbortRollbackSemanticsNoGoBoundary;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoRecords.RehearsalAbortRollbackSemanticsProhibitedField;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoRecords.RehearsalAbortRollbackSemanticsRejectionReason;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoRecords.RehearsalAbortRollbackSemanticsRequiredField;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoRecords.RehearsalAbortRollbackSemanticsUpstreamEchoRequest;
import java.util.List;

final class ReleaseApprovalSandboxEndpointCredentialResolverAbortRollbackSemanticsContractCatalog {

  static final String TARGET_PREREQUISITE_ID = "abort-rollback-semantics";
  static final String NODE_V326_CONTRACT_DIGEST =
      "fe05bcfd65aabf56ef170bf458837053a11edf0ae44ad203a88d4ecd284299f9";
  static final String SOURCE_NODE_V325_REVIEW_DIGEST =
      "5781245b6dd5b67d6e2985e7e6f70e942defcd4ea95a09dc516743abf7abf0ca";

  private static final List<RequiredFieldTemplate> REQUIRED_FIELDS =
      List.of(
          requiredField(
              "manual_abort_marker",
              "Manual abort marker",
              "stable non-secret manual abort marker",
              "Bind the future path to a human-visible abort marker."),
          requiredField(
              "rollback_runbook_reference",
              "Rollback runbook reference",
              "runbook id or immutable document reference",
              "Point operators to rollback instructions without executing them."),
          requiredField(
              "operator_confirmation_handle",
              "Operator confirmation handle",
              "operator confirmation handle, no credential value",
              "Bind abort/rollback review to an operator confirmation."),
          requiredField(
              "approval_correlation_id",
              "Approval correlation id",
              "stable non-secret correlation id",
              "Bind semantics to the approval chain."),
          requiredField(
              "cleanup_evidence_marker",
              "Cleanup evidence marker",
              "cleanup marker or evidence digest",
              "Declare how cleanup evidence will be recognized after a stopped attempt."),
          requiredField(
              "idempotent_noop_failure_policy",
              "Idempotent no-op failure policy",
              "policy id or semantic version",
              "Define how repeated abort/rollback requests remain safe no-ops."),
          requiredField(
              "rollback_authority_boundary",
              "Rollback authority boundary",
              "authority handle, no executable permission",
              "Describe who may authorize rollback without granting Node execution."),
          requiredField(
              "abort_reason_code",
              "Abort reason code",
              "stable reason code list",
              "Standardize operator-readable abort reasons."),
          requiredField(
              "recovery_checkpoint_reference",
              "Recovery checkpoint reference",
              "checkpoint handle or digest",
              "Describe the recovery checkpoint to inspect before future execution."),
          requiredField(
              "audit_digest",
              "Audit digest",
              "sha256 digest or equivalent stable digest",
              "Prove contract immutability without secret, endpoint, or command material."));

  private static final List<ProhibitedFieldTemplate> PROHIBITED_FIELDS =
      List.of(
          prohibitedField(
              "credential_value",
              "Credential values must not enter the abort/rollback semantics contract.",
              "CREDENTIAL_VALUE_PRESENT"),
          prohibitedField(
              "raw_endpoint_url",
              "Raw endpoint URLs must remain outside this contract.",
              "RAW_ENDPOINT_URL_PRESENT"),
          prohibitedField(
              "runtime_shell_command",
              "Runtime shell commands would turn this intake into implementation.",
              "RUNTIME_SHELL_COMMAND_PRESENT"),
          prohibitedField(
              "shell_script_body",
              "Shell scripts are prohibited in the contract-only intake.",
              "SHELL_SCRIPT_BODY_PRESENT"),
          prohibitedField(
              "secret_provider_config",
              "Provider configuration is not allowed before implementation candidate gates.",
              "SECRET_PROVIDER_CONFIG_PRESENT"),
          prohibitedField(
              "resolver_client_config",
              "Resolver client configuration is not allowed before implementation candidate gates.",
              "RESOLVER_CLIENT_CONFIG_PRESENT"),
          prohibitedField(
              "external_request_payload",
              "No HTTP/TCP payload may be prepared or sent by v326.",
              "EXTERNAL_REQUEST_PAYLOAD_PRESENT"),
          prohibitedField(
              "approval_ledger_mutation",
              "Approval ledger writes remain outside this Node contract.",
              "APPROVAL_LEDGER_MUTATION_PRESENT"),
          prohibitedField(
              "schema_migration_sql",
              "Schema migration SQL is prohibited in this intake.",
              "SCHEMA_MIGRATION_SQL_PRESENT"),
          prohibitedField(
              "deployment_action",
              "Deployment actions are prohibited during semantics intake.",
              "DEPLOYMENT_ACTION_PRESENT"),
          prohibitedField(
              "rollback_execution_action",
              "Rollback execution is prohibited during semantics intake.",
              "ROLLBACK_EXECUTION_ACTION_PRESENT"),
          prohibitedField(
              "upstream_process_start",
              "Starting Java, mini-kv, or external audit services is prohibited.",
              "UPSTREAM_PROCESS_START_PRESENT"),
          prohibitedField(
              "mini_kv_write_command",
              "mini-kv write commands are prohibited and mini-kv remains non-authoritative.",
              "MINI_KV_WRITE_COMMAND_PRESENT"),
          prohibitedField(
              "java_sql_execution",
              "Java SQL execution must not be triggered by this Node contract.",
              "JAVA_SQL_EXECUTION_PRESENT"));

  private static final List<RejectionTemplate> REJECTION_REASONS =
      List.of(
          rejection(
              "MANUAL_ABORT_MARKER_MISSING",
              "abort-rollback-semantics-contract",
              "The manual abort marker is missing."),
          rejection(
              "ROLLBACK_RUNBOOK_REFERENCE_MISSING",
              "abort-rollback-semantics-contract",
              "The rollback runbook reference or recovery checkpoint is missing."),
          rejection(
              "CREDENTIAL_OR_RAW_ENDPOINT_PRESENT",
              "credential-boundary",
              "Credential values and raw endpoint URLs are not allowed in abort/rollback semantics."),
          rejection(
              "RUNTIME_SHELL_COMMAND_PRESENT",
              "runtime-shell-boundary",
              "Runtime shell commands and shell script bodies are prohibited."),
          rejection(
              "NETWORK_OR_PROVIDER_ACTION_PRESENT",
              "network-boundary",
              "Network execution, provider/client config, HTTP requests, and TCP attempts are prohibited."),
          rejection(
              "WRITE_OR_ROLLBACK_ACTION_PRESENT",
              "write-boundary",
              "Ledger writes, schema migration, deployment, rollback execution, upstream start, Java SQL, and mini-kv writes are prohibited."));

  private static final List<NoGoTemplate> NO_GO_BOUNDARIES =
      List.of(
          noGo("credential_value_read", "v326 must not read managed audit credential values."),
          noGo("raw_endpoint_url_parse", "v326 must not parse or render raw endpoint URLs."),
          noGo(
              "runtime_shell_command_render",
              "v326 must not render or invoke runtime shell commands."),
          noGo("secret_provider_instantiation", "v326 must not instantiate secret providers."),
          noGo("resolver_client_instantiation", "v326 must not instantiate resolver clients."),
          noGo(
              "http_or_tcp_execution",
              "v326 must not send HTTP/HTTPS requests or open TCP/TLS sockets."),
          noGo("rollback_execution", "v326 must not execute deployment or rollback operations."),
          noGo("java_sql_execution", "v326 must not trigger Java SQL execution."),
          noGo(
              "mini_kv_write_command",
              "v326 must not request mini-kv write commands or authority."),
          noGo("ledger_or_schema_write", "v326 must not write approval ledger or schema state."),
          noGo(
              "automatic_upstream_start",
              "v326 must not automatically start Java, mini-kv, or external audit services."));

  private static final List<EchoRequestTemplate> UPSTREAM_ECHO_REQUESTS =
      List.of(
          echoRequest(
              "java",
              "Java v150",
              "Read-only echo of the Node v326 abort/rollback semantics contract, confirming Java will not execute SQL, deployment, rollback, ledger writes, or external network calls."),
          echoRequest(
              "mini-kv",
              "mini-kv v142",
              "Non-participation receipt proving mini-kv does not execute LOAD/COMPACT/RESTORE/SETNXEX, write commands, or become abort/rollback authority."));

  private static final List<String> SOURCE_COMPLETED_PREREQUISITE_IDS =
      List.of(
          "java-mini-kv-decision-echo",
          "signed-human-approval-artifact",
          "credential-handle-approval",
          "endpoint-handle-allowlist-approval",
          "no-network-safety-fixture");

  private static final List<String> SOURCE_REMAINING_PREREQUISITE_IDS =
      List.of("abort-rollback-semantics");

  private static final List<String> PROOF_CLAIMS =
      List.of(
          "managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.consumedByNodeAbortRollbackSemanticsContractState=abort-rollback-semantics-contract-intake-ready",
          "managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.abortRollbackSemanticsContract.requiredFieldCount=10",
          "managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.abortRollbackSemanticsContract.prohibitedFieldCount=14",
          "managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.abortRollbackSemanticsContract.noGoBoundaryCount=11",
          "managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.prerequisiteTransition.afterV326=contract-intake-defined",
          "managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.abortRollbackSemanticsContract.abortRollbackExecutionAllowed=false",
          "managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.sideEffectBoundary.httpRequestSent=false",
          "managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.sideEffectBoundary.tcpConnectionAttempted=false",
          "managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.sideEffectBoundary.externalRequestSent=false",
          "managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.sideEffectBoundary.approvalLedgerWritten=false",
          "managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.readyForNodeV327AbortRollbackSemanticsUpstreamEchoVerification=true");

  private static final List<String> NODE_VERIFICATION_ACTIONS =
      List.of(
          "Compare managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.consumedByNodeAbortRollbackSemanticsContractProfile with Node v326",
          "Require managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.abortRollbackSemanticsContract.requiredFieldCount=10 before Node v327",
          "Require managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.abortRollbackSemanticsContract.prohibitedFieldCount=14 before Node v327",
          "Require managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.readyForNodeV327AbortRollbackSemanticsUpstreamEchoVerification=true before Node v327",
          "Keep managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.sideEffectBoundary.rollbackExecuted=false",
          "Keep managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.sideEffectBoundary.httpRequestSent=false",
          "Keep managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.sideEffectBoundary.tcpConnectionAttempted=false",
          "Keep managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.sideEffectBoundary.approvalLedgerWritten=false");

  private static final List<String> WARNING_CODES =
      List.of(
          "ABORT_ROLLBACK_SEMANTICS_CONTRACT_DOES_NOT_EXECUTE_ROLLBACK",
          "FINAL_PREREQUISITE_CONTRACT_DOES_NOT_APPROVE_RUNTIME");

  private static final List<String> RECOMMENDATION_CODES =
      List.of(
          "RUN_JAVA_V150_AND_MINI_KV_V142_AFTER_V326_ARCHIVE",
          "KEEP_ABORT_ROLLBACK_SEMANTICS_CONTRACT_NON_EXECUTING");

  private static final List<String> NEXT_REQUIRED_ECHO_VERSIONS =
      List.of(
          "mini-kv v142 abort/rollback semantics non-participation receipt",
          "Node v327 abort/rollback semantics upstream echo verification");

  private ReleaseApprovalSandboxEndpointCredentialResolverAbortRollbackSemanticsContractCatalog() {}

  static List<RehearsalAbortRollbackSemanticsRequiredField> requiredFields() {
    return REQUIRED_FIELDS.stream()
        .map(
            template ->
                new RehearsalAbortRollbackSemanticsRequiredField(
                    template.id(),
                    template.label(),
                    true,
                    template.acceptedShape(),
                    template.purpose()))
        .toList();
  }

  static List<RehearsalAbortRollbackSemanticsProhibitedField> prohibitedFields() {
    return PROHIBITED_FIELDS.stream()
        .map(
            template ->
                new RehearsalAbortRollbackSemanticsProhibitedField(
                    template.id(), template.reason(), template.rejectionCode()))
        .toList();
  }

  static List<RehearsalAbortRollbackSemanticsRejectionReason> rejectionReasons() {
    return REJECTION_REASONS.stream()
        .map(
            template ->
                new RehearsalAbortRollbackSemanticsRejectionReason(
                    template.code(), template.source(), template.message()))
        .toList();
  }

  static List<RehearsalAbortRollbackSemanticsNoGoBoundary> noGoBoundaries() {
    return NO_GO_BOUNDARIES.stream()
        .map(
            template ->
                new RehearsalAbortRollbackSemanticsNoGoBoundary(
                    template.id(), false, template.message()))
        .toList();
  }

  static List<RehearsalAbortRollbackSemanticsUpstreamEchoRequest> upstreamEchoRequests() {
    return UPSTREAM_ECHO_REQUESTS.stream()
        .map(
            template ->
                new RehearsalAbortRollbackSemanticsUpstreamEchoRequest(
                    template.project(), template.version(), template.requestedEcho(), true, true))
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
      String id, String label, String acceptedShape, String purpose) {
    return new RequiredFieldTemplate(id, label, acceptedShape, purpose);
  }

  private static ProhibitedFieldTemplate prohibitedField(
      String id, String reason, String rejectionCode) {
    return new ProhibitedFieldTemplate(id, reason, rejectionCode);
  }

  private static RejectionTemplate rejection(String code, String source, String message) {
    return new RejectionTemplate(code, source, message);
  }

  private static NoGoTemplate noGo(String id, String message) {
    return new NoGoTemplate(id, message);
  }

  private static EchoRequestTemplate echoRequest(
      String project, String version, String requestedEcho) {
    return new EchoRequestTemplate(project, version, requestedEcho);
  }

  private record RequiredFieldTemplate(
      String id, String label, String acceptedShape, String purpose) {}

  private record ProhibitedFieldTemplate(String id, String reason, String rejectionCode) {}

  private record RejectionTemplate(String code, String source, String message) {}

  private record NoGoTemplate(String id, String message) {}

  private record EchoRequestTemplate(String project, String version, String requestedEcho) {}
}
