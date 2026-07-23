package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalResponse;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.BoundaryGuard;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.CodeHealthGate;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.EvidenceReference;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.HandoffNote;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.PrecheckField;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.SourceReceipt;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.SplitModule;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.VerificationGate;
import java.util.List;

final class ManifestCatalog {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v2002";
  static final String NODE_OWNER_PLAN = "Node v1983-v2002";
  static final String FROZEN_JAVA_VERSION = "Java v99";
  static final String FROZEN_MINI_KV_VERSION = "mini-kv v108";
  static final String PROFILE =
      "java-shard-readiness-sandbox-connection-precheck-upstream-receipt-verification-manifest.v1";

  static final int SOURCE_COUNT = 1;
  static final int MODULE_COUNT = 12;
  static final int REFERENCE_COUNT = 5;
  static final int FIELD_COUNT = 7;
  static final int BOUNDARY_COUNT = 17;
  static final int HEALTH_COUNT = 6;
  static final int VERIFICATION_COUNT = 10;
  static final int HANDOFF_COUNT = 4;
  static final int MARKDOWN_COUNT = 8;

  private ManifestCatalog() {}

  static Evidence evidence(ReleaseApprovalRehearsalResponse rehearsal) {
    var sources = sourceReceipts(rehearsal);
    var modules = splitModules();
    var references = evidenceReferences(rehearsal);
    var fields = precheckFields(rehearsal);
    var boundaries = boundaryGuards(rehearsal);
    var health = codeHealthGates();
    var verification = verificationGates(sources, modules, references, fields, boundaries, health);
    return new Evidence(
        sources, modules, references, fields, boundaries, health, verification, handoffNotes());
  }

  private static List<SourceReceipt> sourceReceipts(ReleaseApprovalRehearsalResponse rehearsal) {
    var receipt = rehearsal.managedAuditSandboxConnectionPrecheckPacketEchoReceipt();
    return List.of(
        new SourceReceipt(
            "managedAuditSandboxConnectionPrecheckPacketEchoReceipt",
            receipt.receiptVersion(),
            receipt.receiptDigest(),
            receipt.consumedByNodePrecheckPacketVersion(),
            receipt.consumedByNodePrecheckPacketProfile(),
            receipt.consumedByNodePrecheckPacketEndpoint(),
            receipt.consumedByNodePrecheckPacketState(),
            receipt.nextNodePrecheckUpstreamReceiptVerificationVersion(),
            receipt.nextNodePrecheckUpstreamReceiptVerificationProfile(),
            receipt.nodeV246MayConsume(),
            receipt.readyForNodeV246ManualSandboxConnectionPrecheckUpstreamReceiptVerification(),
            receipt.readyForManagedAuditSandboxAdapterConnection(),
            receipt.readyForProductionAudit(),
            receipt.nodeMayTreatAsProductionAuditRecord(),
            List.copyOf(receipt.receiptWarnings()),
            List.copyOf(receipt.nodeVerificationActions())));
  }

  private static List<SplitModule> splitModules() {
    return List.of(
        module(
            "v1983",
            "entrypoint-boundary",
            "Record the split boundary and keep public loader/render exports stable."),
        module(
            "v1984",
            "types",
            "Move profile, receipt, evidence, snippet, message, and check types."),
        module(
            "v1985",
            "constants",
            "Move fixed evidence paths, endpoint paths, and Node v245 precheck constants."),
        module(
            "v1986", "references-node-v245", "Move the Node v245 source adapter into references."),
        module(
            "v1987",
            "references-java-v99",
            "Move the frozen Java v99 receipt reference builder into references."),
        module(
            "v1988",
            "references-mini-kv-v108",
            "Move the mini-kv v108 non-participation reference builder into references."),
        module(
            "v1989",
            "references-helpers",
            "Keep evidence-file, snippet, JSON, and field helpers private to references."),
        module(
            "v1990", "policy-checks", "Move receipt verification check construction into policy."),
        module(
            "v1991",
            "policy-messages",
            "Move blockers, warnings, and recommendations into policy."),
        module(
            "v1992",
            "core",
            "Move digest, receiptVerification, summary, endpoints, and nextActions assembly."),
        module("v1993", "renderer", "Move Markdown rendering into a dedicated renderer module."),
        module(
            "v1994",
            "entrypoint-orchestration",
            "Leave the service entrypoint as orchestration only with import compatibility."));
  }

  private static List<EvidenceReference> evidenceReferences(
      ReleaseApprovalRehearsalResponse rehearsal) {
    var receipt = rehearsal.managedAuditSandboxConnectionPrecheckPacketEchoReceipt();
    return List.of(
        reference(
            "node-v1983-v2002-roadmap",
            "Node plan",
            NODE_OWNER_PLAN,
            "precheck-upstream-receipt-verification split roadmap",
            "Defines the Node-only module split and verification stop condition."),
        reference(
            "node-v245-precheck-packet",
            "Node receipt",
            receipt.consumedByNodePrecheckPacketVersion(),
            receipt.consumedByNodePrecheckPacketProfile(),
            "Frozen source packet shape consumed by the Java v99 echo receipt."),
        reference(
            "java-v99-precheck-packet-echo",
            "Java receipt",
            FROZEN_JAVA_VERSION,
            receipt.receiptVersion(),
            "Frozen Java evidence used by Node v247 and retained for Node v1983-v2002."),
        reference(
            "mini-kv-v108-non-participation",
            "mini-kv receipt",
            FROZEN_MINI_KV_VERSION,
            "mini-kv-non-participation-reference.v1",
            "Sibling evidence only; Java must not request mini-kv writes or startup."),
        reference(
            "node-v247-verification-report",
            "Node report",
            "Node v247",
            receipt.nextNodePrecheckUpstreamReceiptVerificationProfile(),
            "Existing report reused by the split module family."));
  }

  private static List<PrecheckField> precheckFields(ReleaseApprovalRehearsalResponse rehearsal) {
    var echo = rehearsal.managedAuditSandboxConnectionPrecheckPacketEchoReceipt().fieldEcho();
    return List.of(
        field(
            echo.ownerApprovalArtifactItemId(),
            echo.ownerApprovalArtifactField(),
            "owner approval artifact id",
            echo.ownerApprovalArtifactEchoed()),
        field(
            echo.credentialHandleReviewItemId(),
            echo.credentialHandleReviewField(),
            "credential handle review",
            echo.credentialHandleReviewEchoed()),
        field(
            echo.schemaMigrationRehearsalItemId(),
            echo.schemaMigrationRehearsalIdField(),
            "schema rehearsal evidence id",
            echo.schemaMigrationRehearsalEchoed()),
        field(
            echo.operatorWindowItemId(),
            echo.operatorWindowField(),
            "manual operator window marker",
            echo.operatorWindowEchoed()),
        field(
            echo.rollbackPathItemId(),
            echo.rollbackPathField(),
            "rollback evidence path",
            echo.rollbackPathEchoed()),
        field(
            echo.abortMarkerItemId(),
            echo.abortMarkerField(),
            "manual abort marker",
            echo.abortMarkerEchoed()),
        field(
            echo.timeoutPolicyItemId(),
            echo.timeoutPolicyField(),
            String.valueOf(echo.timeoutBudgetMs()),
            echo.timeoutPolicyEchoed()));
  }

  private static List<BoundaryGuard> boundaryGuards(ReleaseApprovalRehearsalResponse rehearsal) {
    var boundary =
        rehearsal.managedAuditSandboxConnectionPrecheckPacketEchoReceipt().javaExecutionBoundary();
    return List.of(
        guard(
            "carries-credential-value",
            "carriesCredentialValue",
            boundary.carriesCredentialValue()),
        guard(
            "credential-value-read",
            "credentialValueReadByJava",
            boundary.credentialValueReadByJava()),
        guard(
            "credential-value-stored",
            "credentialValueStoredByJava",
            boundary.credentialValueStoredByJava()),
        guard(
            "actual-connection-attempted",
            "actualConnectionAttemptedByJava",
            boundary.actualConnectionAttemptedByJava()),
        guard(
            "external-managed-audit-connection-opened",
            "externalManagedAuditConnectionOpenedByJava",
            boundary.externalManagedAuditConnectionOpenedByJava()),
        guard(
            "schema-migration-requested",
            "schemaMigrationRequestedByJava",
            boundary.schemaMigrationRequestedByJava()),
        guard(
            "schema-migration-sql-executed",
            "schemaMigrationSqlExecutedByJava",
            boundary.schemaMigrationSqlExecutedByJava()),
        guard(
            "approval-ledger-written",
            "approvalLedgerWrittenByJava",
            boundary.approvalLedgerWrittenByJava()),
        guard(
            "managed-audit-state-write-requested",
            "managedAuditStateWriteRequestedByJava",
            boundary.managedAuditStateWriteRequestedByJava()),
        guard(
            "managed-audit-store-written",
            "managedAuditStoreWrittenByJava",
            boundary.managedAuditStoreWrittenByJava()),
        guard("sql-executed", "sqlExecutedByJava", boundary.sqlExecutedByJava()),
        guard(
            "deployment-triggered",
            "deploymentTriggeredByJava",
            boundary.deploymentTriggeredByJava()),
        guard("rollback-triggered", "rollbackTriggeredByJava", boundary.rollbackTriggeredByJava()),
        guard("restore-executed", "restoreExecutedByJava", boundary.restoreExecutedByJava()),
        guard(
            "upstream-service-auto-start-requested",
            "upstreamServiceAutoStartRequestedByJava",
            boundary.upstreamServiceAutoStartRequestedByJava()),
        guard(
            "mini-kv-write-permission-requested",
            "miniKvWritePermissionRequestedByJava",
            boundary.miniKvWritePermissionRequestedByJava()),
        guard(
            "production-window-opened",
            "productionWindowOpenedByJava",
            boundary.productionWindowOpenedByJava()));
  }

  private static List<CodeHealthGate> codeHealthGates() {
    return List.of(
        healthGate("module-family-scan", "Node v1995 scans the whole v247 module family."),
        healthGate(
            "large-file-inventory",
            "Node v1996 removes the split v247 service from live inventory."),
        healthGate(
            "direct-service-tests",
            "Node v1997 verifies direct v247 behavior and historical fallback."),
        healthGate(
            "downstream-code-health", "Node v1998 keeps managedAuditSandboxCodeHealthPass ready."),
        healthGate(
            "rehearsal-guard",
            "Node v1999 keeps managedAuditManualSandboxConnectionRehearsalGuard ready."),
        healthGate(
            "typecheck-build",
            "Node v2000 covers strict TypeScript typecheck and production build."));
  }

  private static List<VerificationGate> verificationGates(
      List<SourceReceipt> sources,
      List<SplitModule> modules,
      List<EvidenceReference> references,
      List<PrecheckField> fields,
      List<BoundaryGuard> boundaries,
      List<CodeHealthGate> health) {
    return List.of(
        verificationGate("node-plan-pinned", NODE_OWNER_PLAN, true),
        verificationGate(
            "source-receipt-ready",
            "Java v99 precheck packet echo receipt is retained for historical Node v246 verification.",
            sources.stream()
                .allMatch(
                    source ->
                        "Node v245".equals(source.consumedNodeVersion())
                            && "Node v246".equals(source.nextNodeVersion())
                            && source.receiptDigest().startsWith("sha256:"))),
        verificationGate(
            "source-receipt-digest-present",
            "Receipt digest is retained for stale inventory comparisons.",
            sources.stream().allMatch(source -> source.receiptDigest().startsWith("sha256:"))),
        verificationGate(
            "split-modules-contract-preserved",
            "Each v1983-v1994 module keeps the public loader/import contract stable.",
            modules.stream().allMatch(SplitModule::publicContractPreserved)),
        verificationGate(
            "frozen-java-reference-only",
            "Node consumes frozen Java v99 evidence without new Java work.",
            modules.stream().allMatch(SplitModule::consumesFrozenJavaV99Only)),
        verificationGate(
            "evidence-references-frozen",
            "Node v245, Java v99, mini-kv v108, and Node v247 are retained.",
            references.stream().allMatch(EvidenceReference::frozen)),
        verificationGate(
            "precheck-fields-echoed",
            "Seven precheck fields remain explicit and value-free.",
            fields.stream().allMatch(field -> field.echoed() && !field.carriesCredentialValue())),
        verificationGate(
            "runtime-boundary-closed",
            "Java did not open credentials, SQL, deployment, rollback, or startup.",
            boundaries.stream().allMatch(BoundaryGuard::passed)),
        verificationGate(
            "code-health-gates-passed",
            "Node v1995-v2000 verification gates are represented.",
            health.stream().allMatch(CodeHealthGate::passed)),
        verificationGate(
            "adapter-production-still-blocked",
            "Receipt remains unavailable for adapter connection and production audit use.",
            sources.stream()
                .allMatch(
                    source ->
                        !source.readyForManagedAuditSandboxAdapterConnection()
                            && !source.readyForProductionAudit()
                            && !source.nodeMayTreatAsProductionAuditRecord())));
  }

  private static List<HandoffNote> handoffNotes() {
    return List.of(
        handoff(
            "Node",
            "Reuse Java v99 and mini-kv v108 frozen references; do not request new Java writes."),
        handoff(
            "Java",
            "Keep this endpoint read-only and outside the release approval mutation chain."),
        handoff(
            "mini-kv",
            "Treat mini-kv v108 as non-participation evidence only; no startup or writes."),
        handoff(
            "Ops", "Archive the manifest with the Node v1983-v2002 split closeout and CI result."));
  }

  private static SplitModule module(String version, String name, String responsibility) {
    return new SplitModule(version, name, responsibility, true, true, false);
  }

  private static EvidenceReference reference(
      String id, String source, String version, String profile, String role) {
    return new EvidenceReference(id, source, version, profile, role, true, true);
  }

  private static PrecheckField field(String id, String name, String value, boolean echoed) {
    return new PrecheckField(id, name, value, echoed, false);
  }

  private static BoundaryGuard guard(String name, String field, boolean actual) {
    return new BoundaryGuard(name, "javaExecutionBoundary." + field, false, actual, !actual);
  }

  private static CodeHealthGate healthGate(String name, String evidence) {
    return new CodeHealthGate(name, evidence, true);
  }

  private static VerificationGate verificationGate(String name, String evidence, boolean passed) {
    return new VerificationGate(name, evidence, passed);
  }

  private static HandoffNote handoff(String audience, String note) {
    return new HandoffNote(audience, note, true);
  }

  record Evidence(
      List<SourceReceipt> sourceReceipts,
      List<SplitModule> splitModules,
      List<EvidenceReference> evidenceReferences,
      List<PrecheckField> precheckFields,
      List<BoundaryGuard> boundaryGuards,
      List<CodeHealthGate> codeHealthGates,
      List<VerificationGate> verificationGates,
      List<HandoffNote> handoffNotes) {

    Evidence {
      sourceReceipts = List.copyOf(sourceReceipts);
      splitModules = List.copyOf(splitModules);
      evidenceReferences = List.copyOf(evidenceReferences);
      precheckFields = List.copyOf(precheckFields);
      boundaryGuards = List.copyOf(boundaryGuards);
      codeHealthGates = List.copyOf(codeHealthGates);
      verificationGates = List.copyOf(verificationGates);
      handoffNotes = List.copyOf(handoffNotes);
    }
  }
}
