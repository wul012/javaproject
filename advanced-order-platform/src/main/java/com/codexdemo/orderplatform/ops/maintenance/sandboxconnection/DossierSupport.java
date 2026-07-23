package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalResponse;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.ContextField;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.DownstreamIntakeGate;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.ExecutionGuard;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.HandoffNote;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.NormalizationRule;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.VerificationGate;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.WarningEcho;
import java.util.ArrayList;
import java.util.List;

final class DossierSupport {

  private DossierSupport() {}

  static OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse response(
      String version,
      String endpoint,
      ReleaseApprovalRehearsalResponse rehearsal,
      DossierCatalog.Evidence evidence,
      List<MarkdownSection> markdownSections) {
    var sourceReceipt = rehearsal.managedAuditSandboxConnectionPreconditionReceipt();
    var checks = checks(evidence, markdownSections);
    return new OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse(
        DossierCatalog.PROJECT,
        version,
        true,
        false,
        DossierCatalog.SOURCE_PLAN,
        DossierCatalog.NODE_OWNER_PLAN,
        DossierCatalog.JAVA_CONTEXT_VERSION,
        sourceReceipt.receiptVersion(),
        sourceReceipt.sourceSandboxConnectionPreflightEchoMarkerSchemaVersion(),
        endpoint,
        DossierCatalog.PROFILE,
        evidence.sourceReceipts().size(),
        evidence.contextFields().size(),
        evidence.normalizationRules().size(),
        evidence.preconditionEvidence().size(),
        evidence.boundarySnapshots().size(),
        evidence.executionGuards().size(),
        evidence.warningEchoes().size(),
        evidence.downstreamIntakeGates().size(),
        evidence.verificationGates().size(),
        evidence.handoffNotes().size(),
        markdownSections.size(),
        evidence.sourceReceipts(),
        evidence.contextFields(),
        evidence.normalizationRules(),
        evidence.preconditionEvidence(),
        evidence.boundarySnapshots(),
        evidence.executionGuards(),
        evidence.warningEchoes(),
        evidence.downstreamIntakeGates(),
        evidence.verificationGates(),
        evidence.handoffNotes(),
        markdownSections,
        checks,
        status(evidence, markdownSections));
  }

  private static List<String> checks(
      DossierCatalog.Evidence evidence, List<MarkdownSection> markdownSections) {
    var result = new ArrayList<String>();
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-profile-" + DossierCatalog.PROFILE);
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-source-plan-"
            + DossierCatalog.SOURCE_PLAN);
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-owner-plan-"
            + DossierCatalog.NODE_OWNER_PLAN);
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-java-context-"
            + DossierCatalog.JAVA_CONTEXT_VERSION);
    evidence.sourceReceipts().stream()
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
            + evidence.contextFields().size());
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-normalization-rules-"
            + evidence.normalizationRules().size());
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-precondition-evidence-"
            + evidence.preconditionEvidence().size());
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-boundaries-"
            + evidence.boundarySnapshots().size());
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-execution-guards-"
            + evidence.executionGuards().size());
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-warning-echoes-"
            + evidence.warningEchoes().size());
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-downstream-gates-"
            + evidence.downstreamIntakeGates().size());
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-verification-gates-"
            + evidence.verificationGates().size());
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-handoff-notes-"
            + evidence.handoffNotes().size());
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-markdown-sections-"
            + markdownSections.size());
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-all-execution-guards-passed-"
            + allGuardsPassed(evidence));
    result.add(
        "sandbox-connection-blocked-execution-context-dossier-warnings-archived-"
            + evidence.warningEchoes().stream().allMatch(WarningEcho::archived));
    result.add("sandbox-connection-blocked-execution-context-dossier-ready-for-retention");
    return List.copyOf(result);
  }

  static String status(DossierCatalog.Evidence evidence, List<MarkdownSection> markdownSections) {
    boolean passed =
        evidence.sourceReceipts().size() == DossierCatalog.SOURCE_COUNT
            && evidence.contextFields().size() == DossierCatalog.CONTEXT_COUNT
            && evidence.normalizationRules().size() == DossierCatalog.NORMALIZATION_COUNT
            && evidence.preconditionEvidence().size() == DossierCatalog.PRECONDITION_COUNT
            && evidence.boundarySnapshots().size() == DossierCatalog.BOUNDARY_COUNT
            && evidence.executionGuards().size() == DossierCatalog.GUARD_COUNT
            && evidence.warningEchoes().size() == DossierCatalog.WARNING_COUNT
            && evidence.downstreamIntakeGates().size() == DossierCatalog.INTAKE_COUNT
            && evidence.verificationGates().size() == DossierCatalog.VERIFICATION_COUNT
            && evidence.handoffNotes().size() == DossierCatalog.HANDOFF_COUNT
            && markdownSections.size() == DossierCatalog.MARKDOWN_COUNT
            && evidence.sourceReceipts().stream()
                .allMatch(
                    source ->
                        "Node v234".equals(source.consumedNodeVersion())
                            && "Node v235".equals(source.nextNodeVersion())
                            && !source.readyForManagedAuditSandboxAdapterConnection()
                            && !source.nodeMayTreatAsProductionAuditRecord())
            && evidence.contextFields().stream().allMatch(ContextField::normalized)
            && evidence.normalizationRules().stream().allMatch(NormalizationRule::enforced)
            && evidence.preconditionEvidence().stream()
                .allMatch(item -> item.required() && item.present())
            && evidence.boundarySnapshots().stream()
                .allMatch(item -> item.required() && item.closed())
            && allGuardsPassed(evidence)
            && evidence.warningEchoes().stream().allMatch(WarningEcho::archived)
            && evidence.downstreamIntakeGates().stream().allMatch(DownstreamIntakeGate::ready)
            && evidence.verificationGates().stream().allMatch(VerificationGate::passed)
            && evidence.handoffNotes().stream().allMatch(HandoffNote::ready);
    return passed ? "passed" : "blocked";
  }

  private static boolean allGuardsPassed(DossierCatalog.Evidence evidence) {
    return evidence.executionGuards().stream().allMatch(ExecutionGuard::passed);
  }
}
