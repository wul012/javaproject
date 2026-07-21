package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import static com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownSections.mapped;

import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.BoundaryGuard;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.CodeHealthGate;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.EvidenceReference;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.HandoffNote;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.PrecheckField;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.SourceReceipt;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.SplitModule;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.VerificationGate;
import java.util.List;

final class ManifestRenderer {

  private ManifestRenderer() {}

  static List<MarkdownSection> render(
      List<SourceReceipt> sourceReceipts,
      List<SplitModule> splitModules,
      List<EvidenceReference> evidenceReferences,
      List<PrecheckField> precheckFields,
      List<BoundaryGuard> boundaryGuards,
      List<CodeHealthGate> codeHealthGates,
      List<VerificationGate> verificationGates,
      List<HandoffNote> handoffNotes) {
    return List.of(
        mapped(
            "Source Receipt", sourceReceipts, ManifestRenderer::sourceLine, MarkdownSection::new),
        mapped("Split Modules", splitModules, ManifestRenderer::moduleLine, MarkdownSection::new),
        mapped(
            "Evidence References",
            evidenceReferences,
            ManifestRenderer::referenceLine,
            MarkdownSection::new),
        mapped(
            "Precheck Fields", precheckFields, ManifestRenderer::fieldLine, MarkdownSection::new),
        mapped(
            "Boundary Guards",
            boundaryGuards,
            ManifestRenderer::boundaryLine,
            MarkdownSection::new),
        mapped(
            "Code Health Gates",
            codeHealthGates,
            ManifestRenderer::healthLine,
            MarkdownSection::new),
        mapped(
            "Verification Gates",
            verificationGates,
            ManifestRenderer::verificationLine,
            MarkdownSection::new),
        mapped("Handoff Notes", handoffNotes, ManifestRenderer::handoffLine, MarkdownSection::new));
  }

  private static String sourceLine(SourceReceipt source) {
    return source.receiptName() + " -> " + source.nextNodeVersion();
  }

  private static String moduleLine(SplitModule module) {
    return module.version() + " " + module.moduleName();
  }

  private static String referenceLine(EvidenceReference reference) {
    return reference.id() + " -> " + reference.version();
  }

  private static String fieldLine(PrecheckField field) {
    return field.id() + "=" + field.fieldName();
  }

  private static String boundaryLine(BoundaryGuard guard) {
    return guard.name() + "=" + guard.passed();
  }

  private static String healthLine(CodeHealthGate gate) {
    return gate.name() + "=" + gate.passed();
  }

  private static String verificationLine(VerificationGate gate) {
    return gate.name() + "=" + gate.passed();
  }

  private static String handoffLine(HandoffNote note) {
    return note.audience() + ": " + note.note();
  }
}
