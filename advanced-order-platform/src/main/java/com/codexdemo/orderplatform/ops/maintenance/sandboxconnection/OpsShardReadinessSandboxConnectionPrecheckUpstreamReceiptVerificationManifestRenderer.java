package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestRenderer {

  private OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestRenderer() {}

  static List<
          OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
              .MarkdownSection>
      render(
          List<
                  OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                      .SourceReceipt>
              sourceReceipts,
          List<
                  OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                      .SplitModule>
              splitModules,
          List<
                  OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                      .EvidenceReference>
              evidenceReferences,
          List<
                  OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                      .PrecheckField>
              precheckFields,
          List<
                  OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                      .BoundaryGuard>
              boundaryGuards,
          List<
                  OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                      .CodeHealthGate>
              codeHealthGates,
          List<
                  OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                      .VerificationGate>
              verificationGates,
          List<
                  OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                      .HandoffNote>
              handoffNotes) {
    return List.of(
        section(
            "Source Receipt",
            sourceReceipts.stream()
                .map(source -> source.receiptName() + " -> " + source.nextNodeVersion())
                .toList()),
        section(
            "Split Modules",
            splitModules.stream()
                .map(module -> module.version() + " " + module.moduleName())
                .toList()),
        section(
            "Evidence References",
            evidenceReferences.stream()
                .map(reference -> reference.id() + " -> " + reference.version())
                .toList()),
        section(
            "Precheck Fields",
            precheckFields.stream().map(field -> field.id() + "=" + field.fieldName()).toList()),
        section(
            "Boundary Guards",
            boundaryGuards.stream().map(guard -> guard.name() + "=" + guard.passed()).toList()),
        section(
            "Code Health Gates",
            codeHealthGates.stream().map(gate -> gate.name() + "=" + gate.passed()).toList()),
        section(
            "Verification Gates",
            verificationGates.stream().map(gate -> gate.name() + "=" + gate.passed()).toList()),
        section(
            "Handoff Notes",
            handoffNotes.stream().map(note -> note.audience() + ": " + note.note()).toList()));
  }

  private static
  OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
          .MarkdownSection
      section(String heading, List<String> lines) {
    return new OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
        .MarkdownSection(heading, List.copyOf(new ArrayList<>(lines)));
  }
}
