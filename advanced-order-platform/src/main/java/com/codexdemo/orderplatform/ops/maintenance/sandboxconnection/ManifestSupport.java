package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalContractConstants;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.BoundaryGuard;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.CodeHealthGate;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.MarkdownSection;
import java.util.ArrayList;
import java.util.List;

final class ManifestSupport {

  private ManifestSupport() {}

  static OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
      response(
          String version,
          String endpoint,
          ManifestCatalog.Evidence evidence,
          List<MarkdownSection> sections) {
    var markdown = List.copyOf(sections);
    var checks = checks(evidence, markdown);
    return new OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse(
        ManifestCatalog.PROJECT,
        version,
        true,
        false,
        ManifestCatalog.SOURCE_PLAN,
        ManifestCatalog.NODE_OWNER_PLAN,
        ManifestCatalog.FROZEN_JAVA_VERSION,
        ManifestCatalog.FROZEN_MINI_KV_VERSION,
        evidence.sourceReceipts().stream()
            .findFirst()
            .map(
                OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                        .SourceReceipt
                    ::receiptVersion)
            .orElse("missing"),
        ReleaseApprovalContractConstants
            .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_CONNECTION_PRECHECK_PACKET_ECHO_RECEIPT_SCHEMA_VERSION,
        endpoint,
        ManifestCatalog.PROFILE,
        evidence.sourceReceipts().size(),
        evidence.splitModules().size(),
        evidence.evidenceReferences().size(),
        evidence.precheckFields().size(),
        evidence.boundaryGuards().size(),
        evidence.codeHealthGates().size(),
        evidence.verificationGates().size(),
        evidence.handoffNotes().size(),
        markdown.size(),
        evidence.sourceReceipts(),
        evidence.splitModules(),
        evidence.evidenceReferences(),
        evidence.precheckFields(),
        evidence.boundaryGuards(),
        evidence.codeHealthGates(),
        evidence.verificationGates(),
        evidence.handoffNotes(),
        markdown,
        checks,
        status(evidence, markdown));
  }

  private static List<String> checks(
      ManifestCatalog.Evidence evidence, List<MarkdownSection> sections) {
    var result = new ArrayList<String>();
    result.add(
        "sandbox-connection-precheck-upstream-receipt-verification-manifest-profile-"
            + ManifestCatalog.PROFILE);
    result.add(
        "sandbox-connection-precheck-upstream-receipt-verification-manifest-source-plan-"
            + ManifestCatalog.SOURCE_PLAN);
    result.add(
        "sandbox-connection-precheck-upstream-receipt-verification-manifest-owner-plan-"
            + ManifestCatalog.NODE_OWNER_PLAN);
    result.add(
        "sandbox-connection-precheck-upstream-receipt-verification-manifest-java-evidence-"
            + ManifestCatalog.FROZEN_JAVA_VERSION);
    result.add(
        "sandbox-connection-precheck-upstream-receipt-verification-manifest-mini-kv-evidence-"
            + ManifestCatalog.FROZEN_MINI_KV_VERSION);
    evidence.sourceReceipts().stream()
        .findFirst()
        .ifPresent(
            source -> {
              result.add(
                  "sandbox-connection-precheck-upstream-receipt-verification-manifest-source-receipt-"
                      + source.receiptVersion());
              result.add(
                  "sandbox-connection-precheck-upstream-receipt-verification-manifest-consumes-"
                      + source.consumedNodeVersion());
              result.add(
                  "sandbox-connection-precheck-upstream-receipt-verification-manifest-next-node-"
                      + source.nextNodeVersion());
              result.add(
                  "sandbox-connection-precheck-upstream-receipt-verification-manifest-production-audit-"
                      + source.nodeMayTreatAsProductionAuditRecord());
              result.add(
                  "sandbox-connection-precheck-upstream-receipt-verification-manifest-current-ready-"
                      + source.readyForReceiptVerification());
              result.add(
                  "sandbox-connection-precheck-upstream-receipt-verification-manifest-historical-retention-true");
            });
    result.add(
        "sandbox-connection-precheck-upstream-receipt-verification-manifest-split-modules-"
            + evidence.splitModules().size());
    result.add(
        "sandbox-connection-precheck-upstream-receipt-verification-manifest-evidence-references-"
            + evidence.evidenceReferences().size());
    result.add(
        "sandbox-connection-precheck-upstream-receipt-verification-manifest-precheck-fields-"
            + evidence.precheckFields().size());
    result.add(
        "sandbox-connection-precheck-upstream-receipt-verification-manifest-boundary-guards-"
            + evidence.boundaryGuards().size());
    result.add(
        "sandbox-connection-precheck-upstream-receipt-verification-manifest-code-health-gates-"
            + evidence.codeHealthGates().size());
    result.add(
        "sandbox-connection-precheck-upstream-receipt-verification-manifest-verification-gates-"
            + evidence.verificationGates().size());
    result.add(
        "sandbox-connection-precheck-upstream-receipt-verification-manifest-handoff-notes-"
            + evidence.handoffNotes().size());
    result.add(
        "sandbox-connection-precheck-upstream-receipt-verification-manifest-markdown-sections-"
            + sections.size());
    result.add(
        "sandbox-connection-precheck-upstream-receipt-verification-manifest-boundaries-passed-"
            + allBoundariesPassed(evidence.boundaryGuards()));
    result.add(
        "sandbox-connection-precheck-upstream-receipt-verification-manifest-code-health-passed-"
            + allHealthPassed(evidence.codeHealthGates()));
    result.add(
        "sandbox-connection-precheck-upstream-receipt-verification-manifest-ready-for-retention");
    return List.copyOf(result);
  }

  static String status(ManifestCatalog.Evidence evidence, List<MarkdownSection> sections) {
    boolean passed =
        evidence.sourceReceipts().size() == ManifestCatalog.SOURCE_COUNT
            && evidence.splitModules().size() == ManifestCatalog.MODULE_COUNT
            && evidence.evidenceReferences().size() == ManifestCatalog.REFERENCE_COUNT
            && evidence.precheckFields().size() == ManifestCatalog.FIELD_COUNT
            && evidence.boundaryGuards().size() == ManifestCatalog.BOUNDARY_COUNT
            && evidence.codeHealthGates().size() == ManifestCatalog.HEALTH_COUNT
            && evidence.verificationGates().size() == ManifestCatalog.VERIFICATION_COUNT
            && evidence.handoffNotes().size() == ManifestCatalog.HANDOFF_COUNT
            && sections.size() == ManifestCatalog.MARKDOWN_COUNT
            && evidence.sourceReceipts().stream()
                .allMatch(
                    source ->
                        "Node v245".equals(source.consumedNodeVersion())
                            && "Node v246".equals(source.nextNodeVersion())
                            && !source.readyForManagedAuditSandboxAdapterConnection()
                            && !source.readyForProductionAudit()
                            && !source.nodeMayTreatAsProductionAuditRecord())
            && evidence.splitModules().stream()
                .allMatch(
                    module ->
                        module.publicContractPreserved()
                            && module.consumesFrozenJavaV99Only()
                            && !module.runtimeExecutionAllowed())
            && evidence.evidenceReferences().stream()
                .allMatch(reference -> reference.accepted() && reference.frozen())
            && evidence.precheckFields().stream()
                .allMatch(field -> field.echoed() && !field.carriesCredentialValue())
            && allBoundariesPassed(evidence.boundaryGuards())
            && allHealthPassed(evidence.codeHealthGates())
            && evidence.verificationGates().stream().allMatch(gate -> gate.passed())
            && evidence.handoffNotes().stream().allMatch(note -> note.ready());
    return passed ? "passed" : "blocked";
  }

  private static boolean allBoundariesPassed(List<BoundaryGuard> boundaries) {
    return boundaries.stream().allMatch(BoundaryGuard::passed);
  }

  private static boolean allHealthPassed(List<CodeHealthGate> health) {
    return health.stream().allMatch(CodeHealthGate::passed);
  }
}
