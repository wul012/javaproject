package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import static com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownSections.mapped;

import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.BoundarySnapshot;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.ContextField;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.DownstreamIntakeGate;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.ExecutionGuard;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.HandoffNote;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.PreconditionEvidence;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.SourceReceipt;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.VerificationGate;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.WarningEcho;
import java.util.List;

final class DossierRenderer {

  private DossierRenderer() {}

  static List<MarkdownSection> render(
      List<SourceReceipt> sourceReceipts,
      List<ContextField> contextFields,
      List<PreconditionEvidence> preconditionEvidence,
      List<BoundarySnapshot> boundarySnapshots,
      List<ExecutionGuard> executionGuards,
      List<WarningEcho> warningEchoes,
      List<DownstreamIntakeGate> downstreamIntakeGates,
      List<VerificationGate> verificationGates,
      List<HandoffNote> handoffNotes) {
    return List.of(
        mapped("Source Receipt", sourceReceipts, DossierRenderer::sourceLine, MarkdownSection::new),
        mapped("Context Fields", contextFields, DossierRenderer::fieldLine, MarkdownSection::new),
        mapped(
            "Precondition Evidence",
            preconditionEvidence,
            DossierRenderer::evidenceLine,
            MarkdownSection::new),
        mapped(
            "Boundaries", boundarySnapshots, DossierRenderer::boundaryLine, MarkdownSection::new),
        mapped(
            "Execution Guards", executionGuards, DossierRenderer::guardLine, MarkdownSection::new),
        mapped("Warnings", warningEchoes, DossierRenderer::warningLine, MarkdownSection::new),
        mapped(
            "Downstream Intake",
            downstreamIntakeGates,
            DossierRenderer::intakeLine,
            MarkdownSection::new),
        mapped(
            "Verification Gates",
            verificationGates,
            DossierRenderer::verificationLine,
            MarkdownSection::new),
        mapped("Handoff Notes", handoffNotes, DossierRenderer::handoffLine, MarkdownSection::new));
  }

  private static String sourceLine(SourceReceipt source) {
    return "- " + source.receiptName() + " " + source.consumedNodeVersion();
  }

  private static String fieldLine(ContextField field) {
    return "- " + field.name() + " source=" + field.source();
  }

  private static String evidenceLine(PreconditionEvidence evidence) {
    return "- " + evidence.id() + " present=" + evidence.present();
  }

  private static String boundaryLine(BoundarySnapshot boundary) {
    return "- " + boundary.name() + " closed=" + boundary.closed();
  }

  private static String guardLine(ExecutionGuard guard) {
    return "- " + guard.name() + " passed=" + guard.passed();
  }

  private static String warningLine(WarningEcho warning) {
    return "- " + warning.source() + " " + warning.warning();
  }

  private static String intakeLine(DownstreamIntakeGate gate) {
    return "- " + gate.name() + " ready=" + gate.ready();
  }

  private static String verificationLine(VerificationGate gate) {
    return "- " + gate.name() + " passed=" + gate.passed();
  }

  private static String handoffLine(HandoffNote note) {
    return "- " + note.audience() + " ready=" + note.ready();
  }
}
