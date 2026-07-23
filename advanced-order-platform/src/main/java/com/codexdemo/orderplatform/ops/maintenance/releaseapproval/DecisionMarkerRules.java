package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import java.util.List;

final class DecisionMarkerRules {

  static final String RECORD_MODE = "sandbox-endpoint-credential-resolver-decision-record-only";
  static final String DECISION_SCOPE = "managed-audit-sandbox-endpoint-credential-resolver";
  static final String DECISION_STATUS = "human-review-required-before-credential-resolution";
  static final String SOURCE_SPAN = "Node v259 sandbox endpoint handle upstream echo verification";
  static final String ENDPOINT_HANDLE = "ORDEROPS_MANAGED_AUDIT_SANDBOX_ENDPOINT_HANDLE";
  static final String CREDENTIAL_HANDLE = "ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE";
  static final String RESOLVER_POLICY_HANDLE =
      "ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_RESOLVER_POLICY_HANDLE";
  static final String APPROVAL_MARKER =
      "ORDEROPS_MANAGED_AUDIT_CREDENTIAL_RESOLVER_APPROVAL_MARKER";
  static final String RESOLVER_MODE = "policy-record-only-no-value-read";
  static final String RESOLVER_CANDIDATE_IMPLEMENTATION = "not-implemented";
  static final int REQUIRED_DECISION_FIELD_COUNT = 8;
  static final int EXPLICIT_NO_GO_CONDITION_COUNT = 9;
  static final int SOURCE_EVIDENCE_FILE_COUNT = 6;
  static final int SOURCE_MATCHED_SNIPPET_COUNT = 39;
  static final int SOURCE_CHECK_COUNT = 19;
  static final int SOURCE_PASSED_CHECK_COUNT = 19;
  static final int SOURCE_PRODUCTION_BLOCKER_COUNT = 0;
  static final int SOURCE_WARNING_COUNT = 2;
  static final int SOURCE_RECOMMENDATION_COUNT = 2;

  static final List<String> REQUIRED_DECISION_FIELD_IDS =
      List.of(
          "endpoint-handle",
          "credential-handle",
          "resolver-policy-handle",
          "approval-marker",
          "operator-identity",
          "approval-correlation",
          "redaction-policy",
          "fallback-rotation-plan");

  static final List<String> EXPLICIT_NO_GO_CONDITION_CODES =
      List.of(
          "CREDENTIAL_VALUE_REQUIRED",
          "RAW_ENDPOINT_URL_REQUIRED",
          "REAL_CONNECTION_REQUIRED",
          "EXTERNAL_REQUEST_REQUIRED",
          "SCHEMA_MIGRATION_REQUIRED",
          "UPSTREAM_WRITE_REQUIRED",
          "AUTO_START_REQUIRED",
          "MINI_KV_BACKEND_REQUIRED",
          "PRODUCTION_WINDOW_REQUIRED");

  static final List<String> NODE_WARNING_CODES =
      List.of("DECISION_RECORD_ONLY", "REAL_CREDENTIAL_STILL_ABSENT");

  static final List<String> NODE_RECOMMENDATION_CODES =
      List.of("START_POST_V260_PLAN", "DESIGN_DISABLED_RESOLVER_PRECHECK_LATER");

  static final List<String> NEXT_REQUIRED_ECHO_VERSIONS =
      List.of(
          "Java v105 sandbox endpoint credential resolver decision echo marker",
          "mini-kv v114 sandbox endpoint credential resolver non-participation receipt");

  static boolean sourceMarkerAccepted(
      ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
              .RehearsalManagedAuditSandboxEndpointHandlePreflightEchoMarker
          marker) {
    return marker.readyForNodeV259SandboxEndpointHandleUpstreamEchoVerification()
        && marker.markerWarnings().isEmpty()
        && marker.sourceNodeV257Echoed()
        && marker.endpointHandleReviewEchoed()
        && marker.credentialHandleReviewEchoed()
        && marker.networkAllowlistReviewEchoed()
        && marker.tlsPolicyReviewEchoed()
        && marker.redactionPolicyEchoed()
        && marker.operatorWindowReviewEchoed()
        && marker.sideEffectBoundaryEchoed()
        && !marker.readyForManagedAuditSandboxAdapterConnection();
  }

  static ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
          .RehearsalSandboxEndpointCredentialResolverSourceEcho
      sourceNodeV259(boolean readyForNodeV260CredentialResolverDecisionRecord) {
    return new ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
        .RehearsalSandboxEndpointCredentialResolverSourceEcho(
        ReleaseApprovalUpstreamContractConstants
            .NODE_V259_SANDBOX_ENDPOINT_HANDLE_UPSTREAM_ECHO_VERIFICATION_VERSION,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V259_SANDBOX_ENDPOINT_HANDLE_UPSTREAM_ECHO_VERIFICATION_PROFILE,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V259_SANDBOX_ENDPOINT_HANDLE_UPSTREAM_ECHO_VERIFICATION_STATE,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        SOURCE_EVIDENCE_FILE_COUNT,
        SOURCE_MATCHED_SNIPPET_COUNT,
        SOURCE_CHECK_COUNT,
        SOURCE_PASSED_CHECK_COUNT,
        SOURCE_PRODUCTION_BLOCKER_COUNT,
        SOURCE_WARNING_COUNT,
        SOURCE_RECOMMENDATION_COUNT,
        true,
        readyForNodeV260CredentialResolverDecisionRecord,
        true,
        readyForNodeV260CredentialResolverDecisionRecord);
  }

  static ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
          .RehearsalSandboxEndpointCredentialResolverDecisionRecord
      decisionRecord() {
    List<
            ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
                .RehearsalSandboxEndpointCredentialResolverDecisionField>
        requiredDecisionFields = requiredDecisionFields();
    List<
            ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
                .RehearsalSandboxEndpointCredentialResolverNoGoCondition>
        explicitNoGoConditions = explicitNoGoConditions();
    String decisionDigest =
        ReleaseApprovalDigestSupport.digest(
            List.of(
                ReleaseApprovalDigestSupport.line("recordMode", RECORD_MODE),
                ReleaseApprovalDigestSupport.line("decisionScope", DECISION_SCOPE),
                ReleaseApprovalDigestSupport.line("decisionStatus", DECISION_STATUS),
                ReleaseApprovalDigestSupport.line("endpointHandle", ENDPOINT_HANDLE),
                ReleaseApprovalDigestSupport.line("credentialHandle", CREDENTIAL_HANDLE),
                ReleaseApprovalDigestSupport.line("resolverPolicyHandle", RESOLVER_POLICY_HANDLE),
                ReleaseApprovalDigestSupport.line("approvalMarker", APPROVAL_MARKER),
                ReleaseApprovalDigestSupport.line("resolverMode", RESOLVER_MODE),
                ReleaseApprovalDigestSupport.line(
                    "resolverCandidateImplementation", RESOLVER_CANDIDATE_IMPLEMENTATION),
                ReleaseApprovalDigestSupport.line("requiredDecisionFields", requiredDecisionFields),
                ReleaseApprovalDigestSupport.line(
                    "explicitNoGoConditions", explicitNoGoConditions)));
    return new ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
        .RehearsalSandboxEndpointCredentialResolverDecisionRecord(
        decisionDigest,
        RECORD_MODE,
        DECISION_SCOPE,
        DECISION_STATUS,
        SOURCE_SPAN,
        ENDPOINT_HANDLE,
        CREDENTIAL_HANDLE,
        RESOLVER_POLICY_HANDLE,
        APPROVAL_MARKER,
        true,
        true,
        RESOLVER_MODE,
        RESOLVER_CANDIDATE_IMPLEMENTATION,
        REQUIRED_DECISION_FIELD_COUNT,
        EXPLICIT_NO_GO_CONDITION_COUNT,
        requiredDecisionFields,
        explicitNoGoConditions,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false);
  }

  static List<
          ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
              .RehearsalSandboxEndpointCredentialResolverDecisionField>
      requiredDecisionFields() {
    return List.of(
        decisionField(
            "endpoint-handle",
            "Confirm sandbox endpoint handle",
            "Node v259 upstream echo",
            "handle-aligned"),
        decisionField(
            "credential-handle",
            "Confirm sandbox credential handle",
            "Node v259 upstream echo",
            "handle-aligned"),
        decisionField(
            "resolver-policy-handle",
            "Name the credential resolver policy handle",
            "operator decision",
            "policy-handle-only"),
        decisionField(
            "approval-marker",
            "Record credential resolver approval marker",
            "operator decision",
            "approval-marker-only"),
        decisionField(
            "operator-identity",
            "Require verified operator identity",
            "access guard",
            "operator-header"),
        decisionField(
            "approval-correlation",
            "Require approval correlation id",
            "access guard",
            "approval-correlation-header"),
        decisionField(
            "redaction-policy",
            "Confirm credential and endpoint redaction policy",
            "Node v259 policy review",
            "redaction-reviewed"),
        decisionField(
            "fallback-rotation-plan",
            "Record fallback and rotation plan handle",
            "operator decision",
            "plan-handle-only"));
  }

  static ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
          .RehearsalSandboxEndpointCredentialResolverDecisionField
      decisionField(String id, String label, String expectedSource, String acceptedEvidence) {
    return new ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
        .RehearsalSandboxEndpointCredentialResolverDecisionField(
        id, label, expectedSource, acceptedEvidence, true, false);
  }

  static List<
          ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
              .RehearsalSandboxEndpointCredentialResolverNoGoCondition>
      explicitNoGoConditions() {
    return List.of(
        noGo("CREDENTIAL_VALUE_REQUIRED", "credential value would need to be read"),
        noGo("RAW_ENDPOINT_URL_REQUIRED", "raw endpoint URL would need to be parsed"),
        noGo("REAL_CONNECTION_REQUIRED", "managed audit connection would need to open"),
        noGo("EXTERNAL_REQUEST_REQUIRED", "external managed audit request would be sent"),
        noGo("SCHEMA_MIGRATION_REQUIRED", "schema migration would need to execute"),
        noGo("UPSTREAM_WRITE_REQUIRED", "upstream approval or audit record would be written"),
        noGo("AUTO_START_REQUIRED", "Java or mini-kv would need to be started"),
        noGo("MINI_KV_BACKEND_REQUIRED", "mini-kv would become managed audit storage"),
        noGo("PRODUCTION_WINDOW_REQUIRED", "production window would need to open"));
  }

  static ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
          .RehearsalSandboxEndpointCredentialResolverNoGoCondition
      noGo(String code, String description) {
    return new ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
        .RehearsalSandboxEndpointCredentialResolverNoGoCondition(code, description, false);
  }

  static ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
          .RehearsalSandboxEndpointCredentialResolverSideEffectBoundary
      sideEffectBoundary() {
    return new ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
        .RehearsalSandboxEndpointCredentialResolverSideEffectBoundary(
        true, true, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false);
  }

  static boolean sourceNodeV259Ready(
      ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
              .RehearsalSandboxEndpointCredentialResolverSourceEcho
          source) {
    return ReleaseApprovalUpstreamContractConstants
            .NODE_V259_SANDBOX_ENDPOINT_HANDLE_UPSTREAM_ECHO_VERIFICATION_VERSION
            .equals(source.sourceVersion())
        && ReleaseApprovalUpstreamContractConstants
            .NODE_V259_SANDBOX_ENDPOINT_HANDLE_UPSTREAM_ECHO_VERIFICATION_PROFILE
            .equals(source.profileVersion())
        && ReleaseApprovalUpstreamContractConstants
            .NODE_V259_SANDBOX_ENDPOINT_HANDLE_UPSTREAM_ECHO_VERIFICATION_STATE
            .equals(source.verificationState())
        && source.readyForUpstreamEchoVerification()
        && source.endpointHandleAligned()
        && source.credentialHandleAligned()
        && source.reviewCountsAligned()
        && source.policyReviewsAligned()
        && source.operatorWindowAligned()
        && source.credentialBoundaryAligned()
        && source.rawEndpointBoundaryAligned()
        && source.connectionBoundaryAligned()
        && source.writeBoundaryAligned()
        && source.autoStartBoundaryAligned()
        && source.miniKvNonParticipationAligned()
        && source.nodeV259BlocksRealConnection()
        && source.evidenceFileCount() == SOURCE_EVIDENCE_FILE_COUNT
        && source.matchedSnippetCount() == SOURCE_MATCHED_SNIPPET_COUNT
        && source.checkCount() == SOURCE_CHECK_COUNT
        && source.passedCheckCount() == SOURCE_PASSED_CHECK_COUNT
        && source.productionBlockerCount() == SOURCE_PRODUCTION_BLOCKER_COUNT
        && source.warningCount() == SOURCE_WARNING_COUNT
        && source.recommendationCount() == SOURCE_RECOMMENDATION_COUNT
        && source.sourceNodeV258Ready()
        && source.javaV104Ready()
        && source.miniKvV113Ready()
        && source.readyForNodeV260CredentialResolverDecisionRecord();
  }

  static boolean decisionFieldsEchoed(
      ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
              .RehearsalSandboxEndpointCredentialResolverDecisionRecord
          record) {
    return record.requiredDecisionFieldCount() == REQUIRED_DECISION_FIELD_COUNT
        && record.requiredDecisionFields().size() == REQUIRED_DECISION_FIELD_COUNT
        && record.requiredDecisionFields().stream()
            .map(
                ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
                        .RehearsalSandboxEndpointCredentialResolverDecisionField
                    ::id)
            .toList()
            .equals(REQUIRED_DECISION_FIELD_IDS)
        && record.requiredDecisionFields().stream()
            .allMatch(field -> field.required() && !field.nodeMayReadValue());
  }

  static boolean endpointHandleEchoed(
      ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
              .RehearsalSandboxEndpointCredentialResolverDecisionRecord
          record) {
    return ENDPOINT_HANDLE.equals(record.endpointHandle())
        && hasDecisionField(record, "endpoint-handle", "Node v259 upstream echo", "handle-aligned");
  }

  static boolean credentialHandleEchoed(
      ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
              .RehearsalSandboxEndpointCredentialResolverDecisionRecord
          record) {
    return CREDENTIAL_HANDLE.equals(record.credentialHandle())
        && hasDecisionField(
            record, "credential-handle", "Node v259 upstream echo", "handle-aligned");
  }

  static boolean resolverPolicyEchoed(
      ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
              .RehearsalSandboxEndpointCredentialResolverDecisionRecord
          record) {
    return RESOLVER_POLICY_HANDLE.equals(record.resolverPolicyHandle())
        && RESOLVER_MODE.equals(record.resolverMode())
        && RESOLVER_CANDIDATE_IMPLEMENTATION.equals(record.resolverCandidateImplementation())
        && hasDecisionField(
            record, "resolver-policy-handle", "operator decision", "policy-handle-only");
  }

  static boolean approvalMarkerEchoed(
      ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
              .RehearsalSandboxEndpointCredentialResolverDecisionRecord
          record) {
    return APPROVAL_MARKER.equals(record.approvalMarker())
        && hasDecisionField(record, "approval-marker", "operator decision", "approval-marker-only");
  }

  static boolean operatorIdentityRequirementEchoed(
      ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
              .RehearsalSandboxEndpointCredentialResolverDecisionRecord
          record) {
    return record.operatorIdentityRequired()
        && hasDecisionField(record, "operator-identity", "access guard", "operator-header");
  }

  static boolean approvalCorrelationRequirementEchoed(
      ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
              .RehearsalSandboxEndpointCredentialResolverDecisionRecord
          record) {
    return record.approvalCorrelationRequired()
        && hasDecisionField(
            record, "approval-correlation", "access guard", "approval-correlation-header");
  }

  static boolean redactionPolicyEchoed(
      ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
              .RehearsalSandboxEndpointCredentialResolverDecisionRecord
          record) {
    return hasDecisionField(
            record, "redaction-policy", "Node v259 policy review", "redaction-reviewed")
        && !record.credentialValueMayBeRead()
        && !record.credentialValueMayBeLoaded()
        && !record.credentialValueMayBeStored()
        && !record.rawEndpointUrlMayBeParsed();
  }

  static boolean fallbackRotationPlanEchoed(
      ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
              .RehearsalSandboxEndpointCredentialResolverDecisionRecord
          record) {
    return hasDecisionField(
        record, "fallback-rotation-plan", "operator decision", "plan-handle-only");
  }

  static boolean explicitNoGoConditionsEchoed(
      ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
              .RehearsalSandboxEndpointCredentialResolverDecisionRecord
          record) {
    return record.explicitNoGoConditionCount() == EXPLICIT_NO_GO_CONDITION_COUNT
        && record.explicitNoGoConditions().size() == EXPLICIT_NO_GO_CONDITION_COUNT
        && record.explicitNoGoConditions().stream()
            .map(
                ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
                        .RehearsalSandboxEndpointCredentialResolverNoGoCondition
                    ::code)
            .toList()
            .equals(EXPLICIT_NO_GO_CONDITION_CODES)
        && record.explicitNoGoConditions().stream()
            .noneMatch(
                ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
                        .RehearsalSandboxEndpointCredentialResolverNoGoCondition
                    ::allowed)
        && !record.managedAuditConnectionMayOpen()
        && !record.schemaMigrationMayExecute()
        && !record.externalRequestMayBeSent()
        && !record.nodeMayStartJavaOrMiniKv()
        && !record.miniKvMayActAsManagedAuditStorage()
        && !record.approvalLedgerMayBeWritten();
  }

  static boolean hasDecisionField(
      ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
              .RehearsalSandboxEndpointCredentialResolverDecisionRecord
          record,
      String id,
      String expectedSource,
      String acceptedEvidence) {
    return record.requiredDecisionFields().stream()
        .anyMatch(
            field ->
                id.equals(field.id())
                    && expectedSource.equals(field.expectedSource())
                    && acceptedEvidence.equals(field.acceptedEvidence())
                    && field.required()
                    && !field.nodeMayReadValue());
  }

  static boolean noCredentialConnectionWriteOrAutoStart(
      ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
              .RehearsalSandboxEndpointCredentialResolverSideEffectBoundary
          boundary) {
    return boundary.readOnlyDecisionRecord()
        && boundary.credentialResolverDecisionOnly()
        && !boundary.readyForManagedAuditSandboxAdapterConnection()
        && !boundary.readyForProductionAudit()
        && !boundary.readyForProductionWindow()
        && !boundary.readyForProductionOperations()
        && !boundary.executionAllowed()
        && !boundary.connectsManagedAudit()
        && !boundary.readsManagedAuditCredential()
        && !boundary.storesManagedAuditCredential()
        && !boundary.credentialValueRead()
        && !boundary.credentialValueLoaded()
        && !boundary.credentialValueIncluded()
        && !boundary.rawEndpointUrlParsed()
        && !boundary.rawEndpointUrlIncluded()
        && !boundary.externalRequestSent()
        && !boundary.schemaMigrationExecuted()
        && !boundary.automaticUpstreamStart()
        && !boundary.approvalLedgerWritten()
        && !boundary.javaStarted()
        && !boundary.miniKvStarted();
  }
}
