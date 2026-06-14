package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import java.util.List;

public record OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String sourcePlan,
    String nodeOwnerPlan,
    String javaContextEvidenceVersion,
    String sourcePreconditionReceiptVersion,
    String sourcePreconditionReceiptSchemaVersion,
    String endpoint,
    String profile,
    int sourceReceiptCount,
    int contextFieldCount,
    int normalizationRuleCount,
    int preconditionEvidenceCount,
    int boundarySnapshotCount,
    int executionGuardCount,
    int warningEchoCount,
    int downstreamIntakeGateCount,
    int verificationGateCount,
    int handoffNoteCount,
    int markdownSectionCount,
    List<SourceReceipt> sourceReceipts,
    List<ContextField> contextFields,
    List<NormalizationRule> normalizationRules,
    List<PreconditionEvidence> preconditionEvidence,
    List<BoundarySnapshot> boundarySnapshots,
    List<ExecutionGuard> executionGuards,
    List<WarningEcho> warningEchoes,
    List<DownstreamIntakeGate> downstreamIntakeGates,
    List<VerificationGate> verificationGates,
    List<HandoffNote> handoffNotes,
    List<MarkdownSection> markdownSections,
    List<String> checks,
    String status) {

  public record SourceReceipt(
      String receiptName,
      String receiptVersion,
      String receiptDigest,
      String consumedNodeVersion,
      String consumedNodeProfile,
      String consumedNodeState,
      String nextNodeVersion,
      String nextNodeProfile,
      boolean nodeMayConsume,
      boolean readyForPreconditionIntake,
      boolean readyForManagedAuditSandboxAdapterConnection,
      boolean nodeMayTreatAsProductionAuditRecord) {}

  public record ContextField(
      String name, String value, String source, boolean echoed, boolean normalized) {}

  public record NormalizationRule(String name, String evidence, boolean enforced) {}

  public record PreconditionEvidence(
      String id, String evidence, boolean required, boolean present) {}

  public record BoundarySnapshot(String name, String evidence, boolean required, boolean closed) {}

  public record ExecutionGuard(String name, String evidence, boolean passed) {}

  public record WarningEcho(String source, String warning, boolean archived) {}

  public record DownstreamIntakeGate(String name, String evidence, boolean ready) {}

  public record VerificationGate(String name, String evidence, boolean passed) {}

  public record HandoffNote(String audience, String note, boolean ready) {}

  public record MarkdownSection(String heading, List<String> lines) {}
}
