package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalResponse;
import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v1982";
  static final String NODE_OWNER_PLAN = "Node v1968-v1982";
  static final String JAVA_CONTEXT_EVIDENCE_VERSION = "Java v90";
  static final String PROFILE =
      "java-shard-readiness-sandbox-connection-blocked-execution-context-normalization-dossier.v1";
  static final int EXPECTED_SOURCE_RECEIPT_COUNT = 1;
  static final int EXPECTED_CONTEXT_FIELD_COUNT = 3;
  static final int EXPECTED_NORMALIZATION_RULE_COUNT = 5;
  static final int EXPECTED_PRECONDITION_EVIDENCE_COUNT = 6;
  static final int EXPECTED_BOUNDARY_SNAPSHOT_COUNT = 5;
  static final int EXPECTED_EXECUTION_GUARD_COUNT = 12;
  static final int EXPECTED_WARNING_ECHO_COUNT = 4;
  static final int EXPECTED_DOWNSTREAM_INTAKE_GATE_COUNT = 5;
  static final int EXPECTED_VERIFICATION_GATE_COUNT = 10;
  static final int EXPECTED_HANDOFF_NOTE_COUNT = 4;
  static final int EXPECTED_MARKDOWN_SECTION_COUNT = 9;

  private OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierSupport() {}

  static OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse response(
      String version,
      String endpoint,
      ReleaseApprovalRehearsalResponse rehearsal,
      List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.SourceReceipt>
          sourceReceipts,
      List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.ContextField>
          contextFields,
      List<
              OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                  .NormalizationRule>
          normalizationRules,
      List<
              OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                  .PreconditionEvidence>
          preconditionEvidence,
      List<
              OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                  .BoundarySnapshot>
          boundarySnapshots,
      List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.ExecutionGuard>
          executionGuards,
      List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.WarningEcho>
          warningEchoes,
      List<
              OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                  .DownstreamIntakeGate>
          downstreamIntakeGates,
      List<
              OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                  .VerificationGate>
          verificationGates,
      List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.HandoffNote>
          handoffNotes,
      List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.MarkdownSection>
          markdownSections) {
    var sourceReceipt = rehearsal.managedAuditSandboxConnectionPreconditionReceipt();
    var checks =
        checks(
            sourceReceipts,
            contextFields,
            normalizationRules,
            preconditionEvidence,
            boundarySnapshots,
            executionGuards,
            warningEchoes,
            downstreamIntakeGates,
            verificationGates,
            handoffNotes,
            markdownSections);
    return new OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse(
        PROJECT,
        version,
        true,
        false,
        SOURCE_PLAN,
        NODE_OWNER_PLAN,
        JAVA_CONTEXT_EVIDENCE_VERSION,
        sourceReceipt.receiptVersion(),
        sourceReceipt.sourceSandboxConnectionPreflightEchoMarkerSchemaVersion(),
        endpoint,
        PROFILE,
        sourceReceipts.size(),
        contextFields.size(),
        normalizationRules.size(),
        preconditionEvidence.size(),
        boundarySnapshots.size(),
        executionGuards.size(),
        warningEchoes.size(),
        downstreamIntakeGates.size(),
        verificationGates.size(),
        handoffNotes.size(),
        markdownSections.size(),
        sourceReceipts,
        contextFields,
        normalizationRules,
        preconditionEvidence,
        boundarySnapshots,
        executionGuards,
        warningEchoes,
        downstreamIntakeGates,
        verificationGates,
        handoffNotes,
        markdownSections,
        checks,
        status(
            sourceReceipts,
            contextFields,
            normalizationRules,
            preconditionEvidence,
            boundarySnapshots,
            executionGuards,
            warningEchoes,
            downstreamIntakeGates,
            verificationGates,
            handoffNotes,
            markdownSections));
  }

  private static List<String> checks(
      List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.SourceReceipt>
          sourceReceipts,
      List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.ContextField>
          contextFields,
      List<
              OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                  .NormalizationRule>
          normalizationRules,
      List<
              OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                  .PreconditionEvidence>
          preconditionEvidence,
      List<
              OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                  .BoundarySnapshot>
          boundarySnapshots,
      List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.ExecutionGuard>
          executionGuards,
      List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.WarningEcho>
          warningEchoes,
      List<
              OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                  .DownstreamIntakeGate>
          downstreamIntakeGates,
      List<
              OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                  .VerificationGate>
          verificationGates,
      List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.HandoffNote>
          handoffNotes,
      List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.MarkdownSection>
          markdownSections) {
    var result = new ArrayList<String>();
    result.add("sandbox-connection-blocked-execution-context-dossier-profile-" + PROFILE);
    result.add("sandbox-connection-blocked-execution-context-dossier-source-plan-" + SOURCE_PLAN);
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-owner-plan-" + NODE_OWNER_PLAN);
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-java-context-"
            + JAVA_CONTEXT_EVIDENCE_VERSION);
    sourceReceipts.stream()
        .findFirst()
        .ifPresent(
            source -> {
              result.add(
                  "sandbox-connection-blocked-execution-context-dossier-source-receipt-"
                      + source.receiptVersion());
              result.add(
                  "sandbox-connection-blocked-execution-context-dossier-consumes-"
                      + source.consumedNodeVersion());
              result.add(
                  "sandbox-connection-blocked-execution-context-dossier-next-node-"
                      + source.nextNodeVersion());
              result.add(
                  "sandbox-connection-blocked-execution-context-dossier-production-audit-"
                      + source.nodeMayTreatAsProductionAuditRecord());
            });
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-context-fields-"
            + contextFields.size());
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-normalization-rules-"
            + normalizationRules.size());
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-precondition-evidence-"
            + preconditionEvidence.size());
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-boundaries-"
            + boundarySnapshots.size());
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-execution-guards-"
            + executionGuards.size());
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-warning-echoes-"
            + warningEchoes.size());
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-downstream-gates-"
            + downstreamIntakeGates.size());
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-verification-gates-"
            + verificationGates.size());
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-handoff-notes-"
            + handoffNotes.size());
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-markdown-sections-"
            + markdownSections.size());
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-all-execution-guards-passed-"
            + allExecutionGuardsPassed(executionGuards));
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-warnings-archived-"
            + warningEchoes.stream()
                .allMatch(
                    OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                            .WarningEcho
                        ::archived));
    result.add("sandbox-connection-blocked-execution-context-dossier-ready-for-retention");
    return List.copyOf(result);
  }

  static String status(
      List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.SourceReceipt>
          sourceReceipts,
      List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.ContextField>
          contextFields,
      List<
              OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                  .NormalizationRule>
          normalizationRules,
      List<
              OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                  .PreconditionEvidence>
          preconditionEvidence,
      List<
              OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                  .BoundarySnapshot>
          boundarySnapshots,
      List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.ExecutionGuard>
          executionGuards,
      List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.WarningEcho>
          warningEchoes,
      List<
              OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                  .DownstreamIntakeGate>
          downstreamIntakeGates,
      List<
              OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                  .VerificationGate>
          verificationGates,
      List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.HandoffNote>
          handoffNotes,
      List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.MarkdownSection>
          markdownSections) {
    boolean passed =
        sourceReceipts.size() == EXPECTED_SOURCE_RECEIPT_COUNT
            && contextFields.size() == EXPECTED_CONTEXT_FIELD_COUNT
            && normalizationRules.size() == EXPECTED_NORMALIZATION_RULE_COUNT
            && preconditionEvidence.size() == EXPECTED_PRECONDITION_EVIDENCE_COUNT
            && boundarySnapshots.size() == EXPECTED_BOUNDARY_SNAPSHOT_COUNT
            && executionGuards.size() == EXPECTED_EXECUTION_GUARD_COUNT
            && warningEchoes.size() == EXPECTED_WARNING_ECHO_COUNT
            && downstreamIntakeGates.size() == EXPECTED_DOWNSTREAM_INTAKE_GATE_COUNT
            && verificationGates.size() == EXPECTED_VERIFICATION_GATE_COUNT
            && handoffNotes.size() == EXPECTED_HANDOFF_NOTE_COUNT
            && markdownSections.size() == EXPECTED_MARKDOWN_SECTION_COUNT
            && sourceReceipts.stream()
                .allMatch(
                    source ->
                        "Node v234".equals(source.consumedNodeVersion())
                            && "Node v235".equals(source.nextNodeVersion())
                            && !source.readyForManagedAuditSandboxAdapterConnection()
                            && !source.nodeMayTreatAsProductionAuditRecord())
            && contextFields.stream()
                .allMatch(
                    OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                            .ContextField
                        ::normalized)
            && normalizationRules.stream()
                .allMatch(
                    OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                            .NormalizationRule
                        ::enforced)
            && preconditionEvidence.stream()
                .allMatch(evidence -> evidence.required() && evidence.present())
            && boundarySnapshots.stream()
                .allMatch(snapshot -> snapshot.required() && snapshot.closed())
            && allExecutionGuardsPassed(executionGuards)
            && warningEchoes.stream()
                .allMatch(
                    OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                            .WarningEcho
                        ::archived)
            && downstreamIntakeGates.stream()
                .allMatch(
                    OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                            .DownstreamIntakeGate
                        ::ready)
            && verificationGates.stream()
                .allMatch(
                    OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                            .VerificationGate
                        ::passed)
            && handoffNotes.stream()
                .allMatch(
                    OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                            .HandoffNote
                        ::ready);
    return passed ? "passed" : "blocked";
  }

  private static boolean allExecutionGuardsPassed(
      List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.ExecutionGuard>
          executionGuards) {
    return executionGuards.stream()
        .allMatch(
            OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.ExecutionGuard
                ::passed);
  }
}
