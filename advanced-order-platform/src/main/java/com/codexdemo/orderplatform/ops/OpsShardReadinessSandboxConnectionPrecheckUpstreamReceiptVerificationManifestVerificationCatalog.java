package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestVerificationCatalog {

    private OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestVerificationCatalog() {
    }

    static List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.VerificationGate>
    gates(
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.SourceReceipt>
                    sourceReceipts,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.SplitModule>
                    splitModules,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.EvidenceReference>
                    evidenceReferences,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.PrecheckField>
                    precheckFields,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.BoundaryGuard>
                    boundaryGuards,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.CodeHealthGate>
                    codeHealthGates
    ) {
        return List.of(
                gate("node-plan-pinned",
                        OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSupport
                                .NODE_OWNER_PLAN,
                        true),
                gate("source-receipt-ready",
                        "Java v99 precheck packet echo receipt is retained for historical Node v246 verification.",
                        sourceReceipts.stream().allMatch(source -> "Node v245".equals(source.consumedNodeVersion())
                                && "Node v246".equals(source.nextNodeVersion())
                                && source.receiptDigest().startsWith("sha256:"))),
                gate("source-receipt-digest-present", "Receipt digest is retained for stale inventory comparisons.",
                        sourceReceipts.stream().allMatch(source -> source.receiptDigest().startsWith("sha256:"))),
                gate("split-modules-contract-preserved",
                        "Each v1983-v1994 module keeps the public loader/import contract stable.",
                        splitModules.stream().allMatch(
                                OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                                        .SplitModule::publicContractPreserved)),
                gate("frozen-java-reference-only", "Node consumes frozen Java v99 evidence without new Java work.",
                        splitModules.stream().allMatch(
                                OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                                        .SplitModule::consumesFrozenJavaV99Only)),
                gate("evidence-references-frozen", "Node v245, Java v99, mini-kv v108, and Node v247 are retained.",
                        evidenceReferences.stream().allMatch(
                                OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                                        .EvidenceReference::frozen)),
                gate("precheck-fields-echoed", "Seven precheck fields remain explicit and value-free.",
                        precheckFields.stream().allMatch(field -> field.echoed() && !field.carriesCredentialValue())),
                gate("runtime-boundary-closed", "Java did not open credentials, SQL, deployment, rollback, or startup.",
                        boundaryGuards.stream().allMatch(
                                OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                                        .BoundaryGuard::passed)),
                gate("code-health-gates-passed", "Node v1995-v2000 verification gates are represented.",
                        codeHealthGates.stream().allMatch(
                                OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                                        .CodeHealthGate::passed)),
                gate("adapter-production-still-blocked",
                        "Receipt remains unavailable for adapter connection and production audit use.",
                        sourceReceipts.stream().allMatch(source -> !source.readyForManagedAuditSandboxAdapterConnection()
                                && !source.readyForProductionAudit()
                                && !source.nodeMayTreatAsProductionAuditRecord()))
        );
    }

    private static OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.VerificationGate
    gate(String name, String evidence, boolean passed) {
        return new OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                .VerificationGate(name, evidence, passed);
    }
}
