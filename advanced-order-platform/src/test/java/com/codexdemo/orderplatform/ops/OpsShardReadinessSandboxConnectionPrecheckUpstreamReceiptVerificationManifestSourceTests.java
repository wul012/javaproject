package com.codexdemo.orderplatform.ops;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSourceTests {

    @Test
    void manifestPinsNodePlanAndFrozenJavaPrecheckEvidence() {
        var response =
                OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestTestSupport.manifest();

        assertThat(response.version()).isEqualTo("Java v1707");
        assertThat(response.sourcePlan()).isEqualTo("Node v2002");
        assertThat(response.nodeOwnerPlan()).isEqualTo("Node v1983-v2002");
        assertThat(response.frozenJavaEvidenceVersion()).isEqualTo("Java v99");
        assertThat(response.frozenMiniKvEvidenceVersion()).isEqualTo("mini-kv v108");
        assertThat(response.profile()).isEqualTo(
                "java-shard-readiness-sandbox-connection-precheck-upstream-receipt-verification-manifest.v1");
        assertThat(response.sourceReceiptCount()).isEqualTo(1);
        assertThat(response.splitModuleCount()).isEqualTo(12);
        assertThat(response.evidenceReferenceCount()).isEqualTo(5);
        assertThat(response.precheckFieldCount()).isEqualTo(7);
        assertThat(response.boundaryGuardCount()).isEqualTo(17);
        assertThat(response.codeHealthGateCount()).isEqualTo(6);
        assertThat(response.verificationGateCount()).isEqualTo(10);
        assertThat(response.handoffNoteCount()).isEqualTo(4);
        assertThat(response.markdownSectionCount()).isEqualTo(8);
        assertThat(response.checks()).hasSize(22);
        assertThat(response.status()).isEqualTo("passed");
    }

    @Test
    void sourceReceiptRetainsNodeV245ToV246ContractWithoutProductionUse() {
        var source =
                OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestTestSupport.manifest()
                        .sourceReceipts()
                        .getFirst();

        assertThat(source.receiptName()).isEqualTo("managedAuditSandboxConnectionPrecheckPacketEchoReceipt");
        assertThat(source.receiptVersion()).isEqualTo(
                "java-release-approval-rehearsal-managed-audit-sandbox-connection-precheck-packet-echo-receipt.v1");
        assertThat(source.receiptDigest()).startsWith("sha256:");
        assertThat(source.consumedNodeVersion()).isEqualTo("Node v245");
        assertThat(source.consumedNodeProfile()).isEqualTo(
                "managed-audit-manual-sandbox-connection-precheck-packet.v1");
        assertThat(source.nextNodeVersion()).isEqualTo("Node v246");
        assertThat(source.nextNodeProfile()).isEqualTo(
                "managed-audit-manual-sandbox-connection-precheck-upstream-receipt-verification.v1");
        assertThat(source.nodeMayConsume()).isTrue();
        assertThat(source.readyForReceiptVerification()).isFalse();
        assertThat(source.warnings()).contains("NODE_V246_SOURCE_DRY_RUN_COMMAND_PACKAGE_ECHO_RECEIPT_NOT_READY");
        assertThat(source.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(source.readyForProductionAudit()).isFalse();
        assertThat(source.nodeMayTreatAsProductionAuditRecord()).isFalse();
    }
}
