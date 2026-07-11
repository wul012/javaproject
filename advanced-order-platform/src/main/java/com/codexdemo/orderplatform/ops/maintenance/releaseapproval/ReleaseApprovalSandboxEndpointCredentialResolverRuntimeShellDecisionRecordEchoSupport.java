package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import static com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalDigestSupport.workflowReadiness;
import static com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalDigestSupport.workflowStep;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalDigestSupport.EchoWorkflowReadiness;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoRecords.RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoRecords.RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoRecords.RehearsalRuntimeShellDecisionNoGoCondition;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoRecords.RehearsalRuntimeShellDecisionRecord;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoRecords.RehearsalRuntimeShellDecisionRecordChecks;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoRecords.RehearsalRuntimeShellDecisionRecordSideEffectBoundary;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoRecords.RehearsalRuntimeShellDecisionRecordSourceGateEcho;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoRecords.RehearsalRuntimeShellDecisionRequirement;
import java.util.List;

final class ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoSupport {

  private static final String WARNING_DIGEST_WARNING_INPUT_NAME =
      "managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptWarnings";

  private ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoSupport() {}

  static RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt
      build(
          RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt
              sourceReceipt) {
    RehearsalRuntimeShellDecisionRecordSourceGateEcho sourceEcho = sourceEcho(sourceReceipt);
    RehearsalRuntimeShellDecisionRecord decisionRecord = decisionRecord(sourceEcho);
    RehearsalRuntimeShellDecisionRecordSideEffectBoundary sideEffectBoundary = sideEffectBoundary();
    RehearsalRuntimeShellDecisionRecordChecks checks =
        checks(sourceEcho, decisionRecord, sideEffectBoundary);
    EchoWorkflowReadiness readiness =
        readiness(sourceEcho, decisionRecord, checks, sideEffectBoundary);
    List<String> receiptWarnings = receiptWarnings(readiness);
    String receiptDigest =
        receiptDigest(
            sourceReceipt, sourceEcho, decisionRecord, checks, sideEffectBoundary, readiness);

    return new RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt(
        ReleaseApprovalContractConstants
            .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_RUNTIME_SHELL_DECISION_RECORD_ECHO_RECEIPT_VERSION,
        sourceReceipt.receiptVersion(),
        ReleaseApprovalContractConstants
            .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DISABLED_RUNTIME_SHELL_CANDIDATE_GATE_ECHO_RECEIPT_SCHEMA_VERSION,
        sourceReceipt.receiptDigest(),
        ReleaseApprovalUpstreamContractConstants
            .NODE_V299_CREDENTIAL_RESOLVER_RUNTIME_SHELL_CANDIDATE_GATE_DECISION_RECORD_VERSION,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V299_CREDENTIAL_RESOLVER_RUNTIME_SHELL_CANDIDATE_GATE_DECISION_RECORD_PROFILE,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V299_CREDENTIAL_RESOLVER_RUNTIME_SHELL_CANDIDATE_GATE_DECISION_RECORD_ENDPOINT,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V299_CREDENTIAL_RESOLVER_RUNTIME_SHELL_CANDIDATE_GATE_DECISION_RECORD_MARKDOWN_ENDPOINT,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V299_CREDENTIAL_RESOLVER_RUNTIME_SHELL_CANDIDATE_GATE_DECISION_RECORD_STATE,
        "Node v300",
        "managed-audit-manual-sandbox-connection-credential-resolver-runtime-shell-decision-record-upstream-echo-verification.v1",
        "java-v135-credential-resolver-runtime-shell-decision-record-echo-only",
        "Node v299",
        sourceEcho,
        decisionRecord,
        checks,
        sideEffectBoundary,
        readiness.readyStepNames(),
        readiness.missingStepNames(),
        readiness.ready("sourceCandidateGateEchoed"),
        readiness.ready("nodeV299DecisionRecordEchoed"),
        readiness.ready("blockedDecisionEchoed"),
        readiness.ready("requiredEvidenceEchoed"),
        readiness.ready("noGoConditionsEchoed"),
        readiness.ready("noRuntimeImplementationEchoed"),
        readiness.ready("noRuntimeInvocationEchoed"),
        readiness.ready("noCredentialReadEchoed"),
        readiness.ready("noRawEndpointParseEchoed"),
        readiness.ready("noProviderClientInstantiationEchoed"),
        readiness.ready("noExternalRequestEchoed"),
        readiness.ready("noWriteOrMigrationEchoed"),
        readiness.ready("noAutoStartBoundaryEchoed"),
        checks.readyForNodeV300RuntimeShellDecisionRecordUpstreamEchoVerification(),
        false,
        false,
        false,
        false,
        false,
        false,
        receiptDigest,
        ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
            .decisionRecordRequiredEvidenceIds(),
        ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
            .decisionRecordNoGoConditionCodes(),
        ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
            .decisionRecordNodeWarningCodes(),
        ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
            .decisionRecordNodeRecommendationCodes(),
        ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
            .decisionRecordNextRequiredEchoVersions(),
        receiptWarnings,
        ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
            .decisionRecordNodeVerificationActions());
  }

  static List<String> warningDigestWarningInputNames() {
    return ReleaseApprovalDigestSupport.warningInputNames(WARNING_DIGEST_WARNING_INPUT_NAME);
  }

  static List<String> warningDigestBoundaryInputNames() {
    return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoBoundaryCatalog
        .decisionRecordWarningDigestBoundaryInputNames();
  }

  static List<String> proofClaims() {
    return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
        .decisionRecordProofClaims();
  }

  static List<String> nodeVerificationActions() {
    return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
        .decisionRecordNodeVerificationActions();
  }

  static List<String> warningDigestWarningLines(
      RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt
          receipt) {
    return ReleaseApprovalDigestSupport.warningLines(
        WARNING_DIGEST_WARNING_INPUT_NAME, receipt.receiptWarnings());
  }

  static List<String> warningDigestBoundaryLines(
      RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt
          receipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoBoundaryCatalog
        .decisionRecordWarningDigestBoundaryLines(receipt);
  }

  static boolean noCredentialConnectionWriteOrAutoStartProved(
      RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt
          receipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoBoundaryCatalog
        .decisionRecordNoCredentialConnectionWriteOrAutoStartProved(receipt);
  }

  private static RehearsalRuntimeShellDecisionRecordSourceGateEcho sourceEcho(
      RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt
          source) {
    return new RehearsalRuntimeShellDecisionRecordSourceGateEcho(
        source.receiptVersion(),
        ReleaseApprovalContractConstants
            .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DISABLED_RUNTIME_SHELL_CANDIDATE_GATE_ECHO_RECEIPT_SCHEMA_VERSION,
        source.receiptDigest(),
        source.readyForNodeV298RuntimeShellCandidateGateUpstreamEchoVerification(),
        source.sourceHandoffEchoed(),
        source.nodeV297CandidateGateEchoed(),
        source.fiveGateSetEchoed(),
        source.blockedDecisionEchoed(),
        source.readyForDisabledRuntimeShellImplementation(),
        source.readyForDisabledRuntimeShellInvocation(),
        source.readyForManagedAuditResolverImplementation(),
        source.sideEffectBoundary().disabledRuntimeShellImplemented(),
        source.sideEffectBoundary().disabledRuntimeShellInvocationAllowed(),
        source.sideEffectBoundary().credentialValueRead(),
        source.sideEffectBoundary().rawEndpointUrlParsed(),
        source.sideEffectBoundary().externalRequestSent(),
        source.sideEffectBoundary().secretProviderInstantiated(),
        source.sideEffectBoundary().resolverClientInstantiated(),
        source.sideEffectBoundary().approvalLedgerWritten(),
        source.sideEffectBoundary().sqlExecuted(),
        source.sideEffectBoundary().schemaMigrationExecuted(),
        source.sideEffectBoundary().automaticUpstreamStart());
  }

  private static RehearsalRuntimeShellDecisionRecord decisionRecord(
      RehearsalRuntimeShellDecisionRecordSourceGateEcho sourceEcho) {
    List<RehearsalRuntimeShellDecisionRequirement> requiredEvidence = requiredEvidence(sourceEcho);
    List<RehearsalRuntimeShellDecisionNoGoCondition> noGoConditions =
        ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
            .decisionRecordNoGoConditions();
    return new RehearsalRuntimeShellDecisionRecord(
        ReleaseApprovalUpstreamContractConstants
            .NODE_V299_CREDENTIAL_RESOLVER_RUNTIME_SHELL_CANDIDATE_GATE_DECISION_RECORD_VERSION,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V299_CREDENTIAL_RESOLVER_RUNTIME_SHELL_CANDIDATE_GATE_DECISION_RECORD_PROFILE,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V299_CREDENTIAL_RESOLVER_RUNTIME_SHELL_CANDIDATE_GATE_DECISION_RECORD_ENDPOINT,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V299_CREDENTIAL_RESOLVER_RUNTIME_SHELL_CANDIDATE_GATE_DECISION_RECORD_MARKDOWN_ENDPOINT,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V299_CREDENTIAL_RESOLVER_RUNTIME_SHELL_CANDIDATE_GATE_DECISION_RECORD_STATE,
        "runtime-shell-candidate-gate-decision-record-only",
        "managed-audit-manual-sandbox-connection-credential-resolver-disabled-runtime-shell",
        "Node v297-v298 + Java v134 + mini-kv v131",
        "blocked",
        "Node v298 verified the runtime shell candidate gate echoes, but runtime shell implementation remains blocked until a separate successor plan with explicit approval is produced.",
        sourceEcho.readyForNodeV298RuntimeShellCandidateGateUpstreamEchoVerification(),
        true,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        requiredEvidence.size(),
        noGoConditions.size(),
        requiredEvidence,
        noGoConditions);
  }

  private static List<RehearsalRuntimeShellDecisionRequirement> requiredEvidence(
      RehearsalRuntimeShellDecisionRecordSourceGateEcho sourceEcho) {
    return List.of(
        requirement(
            "node-v298-upstream-echo-ready",
            "Node v298 upstream echo verification",
            sourceEcho.readyForNodeV298RuntimeShellCandidateGateUpstreamEchoVerification()),
        requirement(
            "java-v134-echo-ready",
            "Java v134 runtime shell candidate gate echo",
            sourceEcho.readyForNodeV298RuntimeShellCandidateGateUpstreamEchoVerification()),
        requirement(
            "mini-kv-v131-receipt-ready",
            "mini-kv v131 runtime shell candidate gate non-participation receipt",
            sourceEcho.readyForNodeV298RuntimeShellCandidateGateUpstreamEchoVerification()),
        requirement(
            "runtime-shell-still-blocked",
            "Runtime shell remains blocked",
            !sourceEcho.disabledRuntimeShellImplemented()
                && !sourceEcho.disabledRuntimeShellInvocationAllowed()));
  }

  private static RehearsalRuntimeShellDecisionRequirement requirement(
      String id, String label, boolean present) {
    return new RehearsalRuntimeShellDecisionRequirement(
        id, label, present ? "present" : "missing", present ? "present" : "missing", true);
  }

  private static RehearsalRuntimeShellDecisionRecordSideEffectBoundary sideEffectBoundary() {
    return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoBoundaryCatalog
        .decisionRecordSideEffectBoundary();
  }

  private static RehearsalRuntimeShellDecisionRecordChecks checks(
      RehearsalRuntimeShellDecisionRecordSourceGateEcho sourceEcho,
      RehearsalRuntimeShellDecisionRecord decisionRecord,
      RehearsalRuntimeShellDecisionRecordSideEffectBoundary boundary) {
    boolean sourceLoaded =
        sourceEcho
            .sourceReceiptSchemaVersion()
            .equals(
                ReleaseApprovalContractConstants
                    .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DISABLED_RUNTIME_SHELL_CANDIDATE_GATE_ECHO_RECEIPT_SCHEMA_VERSION);
    boolean sourceReady =
        sourceEcho.readyForNodeV298RuntimeShellCandidateGateUpstreamEchoVerification()
            && sourceEcho.sourceHandoffEchoed()
            && sourceEcho.nodeV297CandidateGateEchoed()
            && sourceEcho.fiveGateSetEchoed()
            && sourceEcho.blockedDecisionEchoed();
    boolean sourceRuntimeBlocked =
        !sourceEcho.readyForDisabledRuntimeShellImplementation()
            && !sourceEcho.readyForDisabledRuntimeShellInvocation()
            && !sourceEcho.readyForManagedAuditResolverImplementation()
            && !sourceEcho.disabledRuntimeShellImplemented()
            && !sourceEcho.disabledRuntimeShellInvocationAllowed();
    boolean sourceSideEffectsClosed =
        !sourceEcho.credentialValueRead()
            && !sourceEcho.rawEndpointUrlParsed()
            && !sourceEcho.externalRequestSent()
            && !sourceEcho.secretProviderInstantiated()
            && !sourceEcho.resolverClientInstantiated()
            && !sourceEcho.approvalLedgerWritten()
            && !sourceEcho.sqlExecuted()
            && !sourceEcho.schemaMigrationExecuted()
            && !sourceEcho.automaticUpstreamStart();
    boolean decisionBlocked =
        "blocked".equals(decisionRecord.decision())
            && !decisionRecord.allowsDisabledRuntimeShellImplementation();
    boolean decisionBlocksRuntime =
        !decisionRecord.allowsDisabledRuntimeShellImplementation()
            && !decisionRecord.allowsDisabledRuntimeShellInvocation()
            && !decisionRecord.allowsRealResolverImplementation()
            && !decisionRecord.allowsFakeHarnessRuntimeImplementation();
    boolean decisionReadOnly =
        !decisionRecord.allowsSecretProviderInstantiation()
            && !decisionRecord.allowsResolverClientInstantiation()
            && !decisionRecord.allowsCredentialValueRead()
            && !decisionRecord.allowsRawEndpointUrlParse()
            && !decisionRecord.allowsExternalRequest()
            && !decisionRecord.allowsManagedAuditConnection()
            && !decisionRecord.allowsSchemaMigration()
            && !decisionRecord.allowsApprovalLedgerWrite()
            && !decisionRecord.allowsAutomaticUpstreamStart();
    List<String> requiredEvidenceIds =
        ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
            .decisionRecordRequiredEvidenceIds();
    List<String> noGoConditionCodes =
        ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
            .decisionRecordNoGoConditionCodes();
    boolean requiredEvidenceStable =
        decisionRecord.requiredEvidenceCount() == requiredEvidenceIds.size()
            && decisionRecord.requiredEvidence().stream()
                .map(RehearsalRuntimeShellDecisionRequirement::id)
                .toList()
                .equals(requiredEvidenceIds)
            && decisionRecord.requiredEvidence().stream()
                .allMatch(
                    item -> "present".equals(item.status()) && item.requiredBeforeRuntimeShell());
    boolean noGoConditionsStable =
        decisionRecord.noGoConditionCount() == noGoConditionCodes.size()
            && decisionRecord.explicitNoGoConditions().stream()
                .map(RehearsalRuntimeShellDecisionNoGoCondition::code)
                .toList()
                .equals(noGoConditionCodes)
            && decisionRecord.explicitNoGoConditions().stream()
                .allMatch(item -> "pause-and-do-not-implement-runtime-shell".equals(item.action()));
    boolean noRuntime =
        !boundary.disabledRuntimeShellImplemented()
            && !boundary.disabledRuntimeShellInvocationAllowed()
            && !boundary.executionAllowed();
    boolean noProviderClient =
        !boundary.secretProviderInstantiated()
            && !boundary.resolverClientInstantiated()
            && !boundary.fakeSecretProviderInstantiated()
            && !boundary.fakeResolverClientInstantiated();
    boolean noWritesOrMigrations =
        !boundary.approvalLedgerWritten()
            && !boundary.managedAuditStoreWritten()
            && !boundary.sqlExecuted()
            && !boundary.schemaMigrationExecuted();
    boolean ready =
        sourceLoaded
            && sourceReady
            && sourceRuntimeBlocked
            && sourceSideEffectsClosed
            && decisionBlocked
            && decisionBlocksRuntime
            && decisionReadOnly
            && requiredEvidenceStable
            && noGoConditionsStable
            && decisionRecord.allowsParallelJavaV135MiniKvV132EchoRequest()
            && noRuntime
            && noProviderClient
            && noWritesOrMigrations
            && !boundary.credentialValueRead()
            && !boundary.rawEndpointUrlParsed()
            && !boundary.externalRequestSent()
            && !boundary.automaticUpstreamStart();
    return new RehearsalRuntimeShellDecisionRecordChecks(
        sourceLoaded,
        sourceReady,
        sourceRuntimeBlocked,
        sourceSideEffectsClosed,
        decisionBlocked,
        decisionBlocksRuntime,
        decisionReadOnly,
        requiredEvidenceStable,
        noGoConditionsStable,
        decisionRecord.allowsParallelJavaV135MiniKvV132EchoRequest(),
        noRuntime,
        !boundary.disabledRuntimeShellInvocationAllowed(),
        !boundary.credentialValueRead() && !boundary.credentialValueProvided(),
        !boundary.rawEndpointUrlParsed() && !boundary.rawEndpointUrlRendered(),
        noProviderClient,
        !boundary.connectsManagedAudit() && !boundary.externalRequestSent(),
        noWritesOrMigrations,
        !boundary.automaticUpstreamStart(),
        !boundary.productionAuditAllowed(),
        !boundary.productionWindowAllowed(),
        ready);
  }

  private static EchoWorkflowReadiness readiness(
      RehearsalRuntimeShellDecisionRecordSourceGateEcho sourceEcho,
      RehearsalRuntimeShellDecisionRecord decisionRecord,
      RehearsalRuntimeShellDecisionRecordChecks checks,
      RehearsalRuntimeShellDecisionRecordSideEffectBoundary boundary) {
    return workflowReadiness(
        workflowStep(
            "sourceCandidateGateEchoed",
            checks.sourceCandidateGateEchoReady()
                && sourceEcho.readyForNodeV298RuntimeShellCandidateGateUpstreamEchoVerification()),
        workflowStep("nodeV299DecisionRecordEchoed", checks.decisionRecordBlocked()),
        workflowStep("blockedDecisionEchoed", checks.decisionRecordBlocksRuntimeShell()),
        workflowStep("requiredEvidenceEchoed", checks.requiredEvidenceStable()),
        workflowStep("noGoConditionsEchoed", checks.noGoConditionsStable()),
        workflowStep(
            "noRuntimeImplementationEchoed",
            checks.noRuntimeImplementationCreated()
                && !decisionRecord.allowsDisabledRuntimeShellImplementation()),
        workflowStep(
            "noRuntimeInvocationEchoed",
            checks.noRuntimeInvocationAllowed()
                && !decisionRecord.allowsDisabledRuntimeShellInvocation()),
        workflowStep("noCredentialReadEchoed", checks.credentialBoundaryClosed()),
        workflowStep("noRawEndpointParseEchoed", checks.rawEndpointBoundaryClosed()),
        workflowStep("noProviderClientInstantiationEchoed", checks.providerClientBoundaryClosed()),
        workflowStep("noExternalRequestEchoed", checks.connectionBoundaryClosed()),
        workflowStep("noWriteOrMigrationEchoed", checks.writeBoundaryClosed()),
        workflowStep(
            "noAutoStartBoundaryEchoed",
            checks.autoStartBoundaryClosed() && !boundary.javaStartedNodeMiniKvOrHarness()));
  }

  private static String receiptDigest(
      RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt
          sourceReceipt,
      RehearsalRuntimeShellDecisionRecordSourceGateEcho sourceEcho,
      RehearsalRuntimeShellDecisionRecord decisionRecord,
      RehearsalRuntimeShellDecisionRecordChecks checks,
      RehearsalRuntimeShellDecisionRecordSideEffectBoundary boundary,
      EchoWorkflowReadiness readiness) {
    return ReleaseApprovalDigestSupport.digest(
        List.of(
            ReleaseApprovalDigestSupport.line(
                "receiptVersion",
                ReleaseApprovalContractConstants
                    .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_RUNTIME_SHELL_DECISION_RECORD_ECHO_RECEIPT_VERSION),
            ReleaseApprovalDigestSupport.line(
                "sourceCandidateGateEchoReceiptVersion", sourceReceipt.receiptVersion()),
            ReleaseApprovalDigestSupport.line(
                "sourceCandidateGateEchoReceiptDigest", sourceReceipt.receiptDigest()),
            ReleaseApprovalDigestSupport.line("sourceEcho", sourceEcho),
            ReleaseApprovalDigestSupport.line("decisionRecord", decisionRecord),
            ReleaseApprovalDigestSupport.line("checks", checks),
            ReleaseApprovalDigestSupport.line("sideEffectBoundary", boundary),
            ReleaseApprovalDigestSupport.line("readySteps", readiness.readyStepNames())));
  }

  private static List<String> receiptWarnings(EchoWorkflowReadiness readiness) {
    return ReleaseApprovalDigestSupport.warnings(
        readiness.warningIfMissing(
            "sourceCandidateGateEchoed", "SOURCE_CANDIDATE_GATE_ECHO_NOT_READY"),
        readiness.warningIfMissing(
            "nodeV299DecisionRecordEchoed", "NODE_V299_DECISION_RECORD_NOT_READY"),
        readiness.warningIfMissing(
            "blockedDecisionEchoed", "RUNTIME_SHELL_DECISION_RECORD_NOT_BLOCKED"),
        readiness.warningIfMissing(
            "requiredEvidenceEchoed", "RUNTIME_SHELL_DECISION_REQUIRED_EVIDENCE_UNSTABLE"),
        readiness.warningIfMissing(
            "noGoConditionsEchoed", "RUNTIME_SHELL_DECISION_NO_GO_CONDITIONS_UNSTABLE"),
        readiness.warningIfMissing(
            "noWriteOrMigrationEchoed", "RUNTIME_SHELL_DECISION_WRITE_BOUNDARY_OPEN"));
  }
}
