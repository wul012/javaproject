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
import java.util.function.Function;

final class ManifestRenderer {

  private ManifestRenderer() {}

  static List<MarkdownSection> render(ManifestCatalog.Evidence evidence) {
    return List.of(
        section("Source Receipt", evidence.sourceReceipts(), ManifestRenderer::sourceLine),
        section("Split Modules", evidence.splitModules(), ManifestRenderer::moduleLine),
        section(
            "Evidence References", evidence.evidenceReferences(), ManifestRenderer::referenceLine),
        section("Precheck Fields", evidence.precheckFields(), ManifestRenderer::fieldLine),
        section("Boundary Guards", evidence.boundaryGuards(), ManifestRenderer::boundaryLine),
        section("Code Health Gates", evidence.codeHealthGates(), ManifestRenderer::healthLine),
        section(
            "Verification Gates", evidence.verificationGates(), ManifestRenderer::verificationLine),
        section("Handoff Notes", evidence.handoffNotes(), ManifestRenderer::handoffLine));
  }

  private static <T> MarkdownSection section(
      String heading, List<T> items, Function<T, String> line) {
    return mapped(heading, items, line, MarkdownSection::new);
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
