package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import java.util.List;

final class OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierRenderer {

  private OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierRenderer() {}

  static List<
          OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.MarkdownSection>
      render(
          List<
                  OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                      .SourceReceipt>
              sourceReceipts,
          List<
                  OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                      .ContextField>
              contextFields,
          List<
                  OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                      .PreconditionEvidence>
              preconditionEvidence,
          List<
                  OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                      .BoundarySnapshot>
              boundarySnapshots,
          List<
                  OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                      .ExecutionGuard>
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
              handoffNotes) {
    return List.of(
        section(
            "Source Receipt",
            sourceReceipts.stream()
                .map(source -> "- " + source.receiptName() + " " + source.consumedNodeVersion())
                .toList()),
        section(
            "Context Fields",
            contextFields.stream()
                .map(field -> "- " + field.name() + " source=" + field.source())
                .toList()),
        section(
            "Precondition Evidence",
            preconditionEvidence.stream()
                .map(evidence -> "- " + evidence.id() + " present=" + evidence.present())
                .toList()),
        section(
            "Boundaries",
            boundarySnapshots.stream()
                .map(boundary -> "- " + boundary.name() + " closed=" + boundary.closed())
                .toList()),
        section(
            "Execution Guards",
            executionGuards.stream()
                .map(guard -> "- " + guard.name() + " passed=" + guard.passed())
                .toList()),
        section(
            "Warnings",
            warningEchoes.stream()
                .map(warning -> "- " + warning.source() + " " + warning.warning())
                .toList()),
        section(
            "Downstream Intake",
            downstreamIntakeGates.stream()
                .map(gate -> "- " + gate.name() + " ready=" + gate.ready())
                .toList()),
        section(
            "Verification Gates",
            verificationGates.stream()
                .map(gate -> "- " + gate.name() + " passed=" + gate.passed())
                .toList()),
        section(
            "Handoff Notes",
            handoffNotes.stream()
                .map(note -> "- " + note.audience() + " ready=" + note.ready())
                .toList()));
  }

  private static OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
          .MarkdownSection
      section(String heading, List<String> lines) {
    return new OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
        .MarkdownSection(heading, lines);
  }
}
