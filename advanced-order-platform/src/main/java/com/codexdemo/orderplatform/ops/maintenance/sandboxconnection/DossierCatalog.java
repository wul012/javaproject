package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalResponse;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalManagedAuditSandboxConnectionPreconditionReceipt;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.BoundarySnapshot;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.ContextField;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.DownstreamIntakeGate;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.ExecutionGuard;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.HandoffNote;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.NormalizationRule;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.PreconditionEvidence;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.SourceReceipt;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.VerificationGate;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.WarningEcho;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

final class DossierCatalog {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v1982";
  static final String NODE_OWNER_PLAN = "Node v1968-v1982";
  static final String JAVA_CONTEXT_VERSION = "Java v90";
  static final String PROFILE =
      "java-shard-readiness-sandbox-connection-blocked-execution-context-normalization-dossier.v1";

  static final int SOURCE_COUNT = 1;
  static final int CONTEXT_COUNT = 3;
  static final int NORMALIZATION_COUNT = 5;
  static final int PRECONDITION_COUNT = 6;
  static final int BOUNDARY_COUNT = 5;
  static final int GUARD_COUNT = 12;
  static final int WARNING_COUNT = 4;
  static final int INTAKE_COUNT = 5;
  static final int VERIFICATION_COUNT = 10;
  static final int HANDOFF_COUNT = 4;
  static final int MARKDOWN_COUNT = 9;

  private DossierCatalog() {}

  static Evidence evidence(ReleaseApprovalRehearsalResponse rehearsal) {
    var receipt = rehearsal.managedAuditSandboxConnectionPreconditionReceipt();
    var sources = sourceReceipts(rehearsal);
    var contexts = contextFields(rehearsal);
    var rules = normalizationRules();
    var preconditions = preconditionEvidence(rehearsal);
    var boundaries = boundarySnapshots(receipt);
    var guards = executionGuards(receipt);
    var warnings = warningEchoes(rehearsal);
    var intake = downstreamIntakeGates(rehearsal);
    var verification =
        verificationGates(sources, contexts, preconditions, boundaries, guards, warnings, intake);
    return new Evidence(
        sources,
        contexts,
        rules,
        preconditions,
        boundaries,
        guards,
        warnings,
        intake,
        verification,
        handoffNotes());
  }

  private static List<SourceReceipt> sourceReceipts(ReleaseApprovalRehearsalResponse rehearsal) {
    var receipt = rehearsal.managedAuditSandboxConnectionPreconditionReceipt();
    return List.of(
        new SourceReceipt(
            "managedAuditSandboxConnectionPreconditionReceipt",
            receipt.receiptVersion(),
            receipt.receiptDigest(),
            receipt.consumedByNodeBlockedExecutionRehearsalVersion(),
            receipt.consumedByNodeBlockedExecutionRehearsalProfile(),
            receipt.consumedByNodeBlockedExecutionRehearsalState(),
            receipt.nextNodePreconditionIntakeVersion(),
            receipt.nextNodePreconditionIntakeProfile(),
            receipt.nodeV235MayConsume(),
            receipt.readyForNodeV235ManualSandboxConnectionPreconditionIntake(),
            receipt.readyForManagedAuditSandboxAdapterConnection(),
            receipt.nodeMayTreatAsProductionAuditRecord()));
  }

  private static List<ContextField> contextFields(ReleaseApprovalRehearsalResponse rehearsal) {
    var context = rehearsal.requestContext();
    return List.of(
        contextField("requestId", context.requestId(), context.requestIdSource()),
        contextField(
            "operatorIdentity", context.operatorIdentity(), context.operatorIdentitySource()),
        contextField(
            "auditCorrelationId", context.auditCorrelationId(), context.auditCorrelationSource()));
  }

  private static List<NormalizationRule> normalizationRules() {
    return List.of(
        rule("trim-blank-inputs", "ContextHeaderField.normalizeValue trims supplied values."),
        rule(
            "blank-becomes-placeholder",
            "Blank or null context values become explicit placeholders."),
        rule("source-labels-preserved", "Header source labels stay visible for Node comparison."),
        rule("missing-warnings-archived", "Missing context warnings are surfaced, not suppressed."),
        rule(
            "read-only-flags",
            "Context normalization does not authenticate, persist, or write approvals."));
  }

  private static List<PreconditionEvidence> preconditionEvidence(
      ReleaseApprovalRehearsalResponse rehearsal) {
    return rehearsal
        .managedAuditSandboxConnectionPreconditionReceipt()
        .requiredPreconditionEvidence()
        .stream()
        .map(source -> new PreconditionEvidence(evidenceId(source), source, true, true))
        .toList();
  }

  private static List<BoundarySnapshot> boundarySnapshots(
      RehearsalManagedAuditSandboxConnectionPreconditionReceipt receipt) {
    return List.of(
        boundary(
            "owner-approval",
            receipt.ownerApprovalBoundary().ownerApprovalArtifactIdField(),
            receipt.ownerApprovalBoundary().ownerApprovalArtifactRequired(),
            !receipt.ownerApprovalBoundary().ownerApprovalArtifactProvidedByJava()
                && !receipt.ownerApprovalBoundary().javaApprovalLedgerWritten()),
        boundary(
            "credential-handle",
            receipt.credentialBoundary().credentialHandleNameField(),
            receipt.credentialBoundary().credentialHandleReviewRequired(),
            !receipt.credentialBoundary().credentialValueReadByJava()
                && !receipt.credentialBoundary().credentialValueStoredByJava()),
        boundary(
            "schema-rehearsal",
            receipt.schemaRehearsalBoundary().schemaRehearsalIdField(),
            receipt.schemaRehearsalBoundary().schemaRehearsalEvidenceRequired(),
            !receipt.schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()
                && !receipt.schemaRehearsalBoundary().schemaMigrationAppliedByJava()),
        boundary(
            "rollback-path",
            receipt.rollbackPathBoundary().rollbackPathIdField(),
            receipt.rollbackPathBoundary().rollbackPathRequired()
                && receipt.rollbackPathBoundary().manualAbortMarkerRequired(),
            !receipt.rollbackPathBoundary().rollbackExecutionAllowedByJava()
                && !receipt.rollbackPathBoundary().restoreExecutionAllowedByJava()),
        boundary(
            "java-execution",
            "actualConnectionAttemptedByJava=false",
            true,
            !receipt.javaExecutionBoundary().actualConnectionAttemptedByJava()
                && !receipt.javaExecutionBoundary().javaStartsManagedAuditService()));
  }

  private static List<ExecutionGuard> executionGuards(
      RehearsalManagedAuditSandboxConnectionPreconditionReceipt receipt) {
    var execution = receipt.javaExecutionBoundary();
    return List.of(
        guard(
            "owner-approval-artifact-provided",
            "ownerApprovalArtifactProvidedByJava=false",
            !receipt.ownerApprovalBoundary().ownerApprovalArtifactProvidedByJava()),
        guard(
            "credential-value-read",
            "credentialValueReadByJava=false",
            !receipt.credentialBoundary().credentialValueReadByJava()),
        guard(
            "schema-migration-sql",
            "schemaMigrationSqlExecutedByJava=false",
            !receipt.schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()),
        guard(
            "rollback-execution",
            "rollbackExecutionAllowedByJava=false",
            !receipt.rollbackPathBoundary().rollbackExecutionAllowedByJava()),
        guard(
            "restore-execution",
            "restoreExecutionAllowedByJava=false",
            !receipt.rollbackPathBoundary().restoreExecutionAllowedByJava()),
        guard(
            "approval-ledger-write",
            "approvalLedgerWrittenByJava=false",
            !execution.approvalLedgerWrittenByJava()),
        guard(
            "managed-audit-store-write",
            "managedAuditStoreWrittenByJava=false",
            !execution.managedAuditStoreWrittenByJava()),
        guard(
            "external-managed-audit-connection",
            "externalManagedAuditConnectionOpenedByJava=false",
            !execution.externalManagedAuditConnectionOpenedByJava()),
        guard("sql-execution", "sqlExecutedByJava=false", !execution.sqlExecutedByJava()),
        guard(
            "deployment-trigger",
            "deploymentTriggeredByJava=false",
            !execution.deploymentTriggeredByJava()),
        guard(
            "managed-audit-service-start",
            "javaStartsManagedAuditService=false",
            !execution.javaStartsManagedAuditService()),
        guard(
            "actual-connection-attempt",
            "actualConnectionAttemptedByJava=false",
            !execution.actualConnectionAttemptedByJava()));
  }

  private static List<WarningEcho> warningEchoes(ReleaseApprovalRehearsalResponse rehearsal) {
    var warnings = new ArrayList<WarningEcho>();
    rehearsal
        .requestContext()
        .contextWarnings()
        .forEach(warning -> warnings.add(warning("requestContext", warning)));
    rehearsal
        .managedAuditSandboxConnectionPreconditionReceipt()
        .receiptWarnings()
        .forEach(warning -> warnings.add(warning("preconditionReceipt", warning)));
    return warnings;
  }

  private static List<DownstreamIntakeGate> downstreamIntakeGates(
      ReleaseApprovalRehearsalResponse rehearsal) {
    var receipt = rehearsal.managedAuditSandboxConnectionPreconditionReceipt();
    return List.of(
        intakeGate(
            "node-v234-blocked-execution-rehearsal",
            receipt.consumedByNodeBlockedExecutionRehearsalState(),
            "Node v234".equals(receipt.consumedByNodeBlockedExecutionRehearsalVersion())),
        intakeGate(
            "java-v90-context-normalization",
            JAVA_CONTEXT_VERSION,
            rehearsal.requestContext().contextVersion().endsWith(".v1")),
        intakeGate(
            "java-v91-precondition-receipt",
            receipt.receiptVersion(),
            receipt.nodeV235MayConsume()),
        intakeGate(
            "mini-kv-v99-wal-regression-evidence",
            "frozen sibling evidence only; Java does not start mini-kv",
            true),
        intakeGate(
            "upstream-actions-disabled",
            "UPSTREAM_ACTIONS_ENABLED must remain false",
            receipt
                .nodeV235Prerequisites()
                .contains("UPSTREAM_ACTIONS_ENABLED must remain false")));
  }

  private static List<VerificationGate> verificationGates(
      List<SourceReceipt> sources,
      List<ContextField> contexts,
      List<PreconditionEvidence> preconditions,
      List<BoundarySnapshot> boundaries,
      List<ExecutionGuard> guards,
      List<WarningEcho> warnings,
      List<DownstreamIntakeGate> intake) {
    return List.of(
        verificationGate("node-v1982-source-plan-pinned", SOURCE_PLAN, true),
        verificationGate(
            "node-v234-source-receipt-pinned",
            "consumedByNodeBlockedExecutionRehearsalVersion=Node v234",
            sources.stream().allMatch(source -> "Node v234".equals(source.consumedNodeVersion()))),
        verificationGate(
            "java-v90-context-fields-present",
            "requestId/operatorIdentity/auditCorrelationId archived",
            contexts.size() == CONTEXT_COUNT),
        verificationGate(
            "java-v91-precondition-evidence-present",
            "requiredPreconditionEvidence.size=6",
            preconditions.size() == PRECONDITION_COUNT),
        verificationGate(
            "all-precondition-boundaries-closed",
            "owner/credential/schema/rollback/execution closed",
            boundaries.stream().allMatch(boundary -> boundary.required() && boundary.closed())),
        verificationGate(
            "execution-guards-passed",
            "no write, connection, sql, deployment, rollback, or startup",
            guards.stream().allMatch(ExecutionGuard::passed)),
        verificationGate(
            "warnings-archived",
            "request context and precondition warnings remain visible",
            warnings.size() == WARNING_COUNT && warnings.stream().allMatch(WarningEcho::archived)),
        verificationGate(
            "downstream-intake-gates-ready",
            "Node v235 inputs are present without enabling execution",
            intake.stream().allMatch(DownstreamIntakeGate::ready)),
        verificationGate(
            "production-audit-still-blocked",
            "nodeMayTreatAsProductionAuditRecord=false",
            sources.stream().allMatch(source -> !source.nodeMayTreatAsProductionAuditRecord())),
        verificationGate(
            "managed-audit-adapter-still-blocked",
            "readyForManagedAuditSandboxAdapterConnection=false",
            sources.stream()
                .allMatch(source -> !source.readyForManagedAuditSandboxAdapterConnection())));
  }

  private static List<HandoffNote> handoffNotes() {
    return List.of(
        handoffNote(
            "node",
            "Consume as frozen Java v90 context-normalization evidence for Node v1968-v1982."),
        handoffNote(
            "java",
            "Keep managed audit connection execution blocked; this dossier is archive-only."),
        handoffNote(
            "mini-kv",
            "Treat mini-kv v99 WAL regression evidence as sibling-only, with no Java startup."),
        handoffNote(
            "operators",
            "Warnings are retained as evidence that missing runtime context does not authorize execution."));
  }

  private static ContextField contextField(String name, String value, String source) {
    return new ContextField(
        name, value, source, !"NOT_SUPPLIED".equals(source), value != null && !value.isBlank());
  }

  private static NormalizationRule rule(String name, String evidence) {
    return new NormalizationRule(name, evidence, true);
  }

  static String evidenceId(String evidence) {
    return evidence.substring(0, evidence.indexOf(':')).replace(' ', '-').toLowerCase(Locale.ROOT);
  }

  private static BoundarySnapshot boundary(
      String name, String evidence, boolean required, boolean closed) {
    return new BoundarySnapshot(name, evidence, required, closed);
  }

  private static ExecutionGuard guard(String name, String evidence, boolean passed) {
    return new ExecutionGuard(name, evidence, passed);
  }

  private static WarningEcho warning(String source, String warning) {
    return new WarningEcho(source, warning, true);
  }

  private static DownstreamIntakeGate intakeGate(String name, String evidence, boolean ready) {
    return new DownstreamIntakeGate(name, evidence, ready);
  }

  private static VerificationGate verificationGate(String name, String evidence, boolean passed) {
    return new VerificationGate(name, evidence, passed);
  }

  private static HandoffNote handoffNote(String audience, String note) {
    return new HandoffNote(audience, note, true);
  }

  record Evidence(
      List<SourceReceipt> sourceReceipts,
      List<ContextField> contextFields,
      List<NormalizationRule> normalizationRules,
      List<PreconditionEvidence> preconditionEvidence,
      List<BoundarySnapshot> boundarySnapshots,
      List<ExecutionGuard> executionGuards,
      List<WarningEcho> warningEchoes,
      List<DownstreamIntakeGate> downstreamIntakeGates,
      List<VerificationGate> verificationGates,
      List<HandoffNote> handoffNotes) {

    Evidence {
      sourceReceipts = List.copyOf(sourceReceipts);
      contextFields = List.copyOf(contextFields);
      normalizationRules = List.copyOf(normalizationRules);
      preconditionEvidence = List.copyOf(preconditionEvidence);
      boundarySnapshots = List.copyOf(boundarySnapshots);
      executionGuards = List.copyOf(executionGuards);
      warningEchoes = List.copyOf(warningEchoes);
      downstreamIntakeGates = List.copyOf(downstreamIntakeGates);
      verificationGates = List.copyOf(verificationGates);
      handoffNotes = List.copyOf(handoffNotes);
    }
  }
}
