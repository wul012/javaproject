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

  static List<MarkdownSection> render(DossierCatalog.Evidence evidence) {
    var sources = evidence.sourceReceipts();
    var contexts = evidence.contextFields();
    var preconditions = evidence.preconditionEvidence();
    var boundaries = evidence.boundarySnapshots();
    var guards = evidence.executionGuards();
    var warnings = evidence.warningEchoes();
    var intake = evidence.downstreamIntakeGates();
    var verification = evidence.verificationGates();
    var handoffs = evidence.handoffNotes();
    return List.of(
        mapped("Source Receipt", sources, DossierRenderer::sourceLine, MarkdownSection::new),
        mapped("Context Fields", contexts, DossierRenderer::fieldLine, MarkdownSection::new),
        mapped(
            "Precondition Evidence",
            preconditions,
            DossierRenderer::evidenceLine,
            MarkdownSection::new),
        mapped("Boundaries", boundaries, DossierRenderer::boundaryLine, MarkdownSection::new),
        mapped("Execution Guards", guards, DossierRenderer::guardLine, MarkdownSection::new),
        mapped("Warnings", warnings, DossierRenderer::warningLine, MarkdownSection::new),
        mapped("Downstream Intake", intake, DossierRenderer::intakeLine, MarkdownSection::new),
        mapped(
            "Verification Gates",
            verification,
            DossierRenderer::verificationLine,
            MarkdownSection::new),
        mapped("Handoff Notes", handoffs, DossierRenderer::handoffLine, MarkdownSection::new));
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
