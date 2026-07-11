package com.codexdemo.orderplatform.ops.maintenance.runtimeexecution;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutServiceTests {

  @Test
  void buildsJavaPassEvidenceCloseoutWithoutRerunningSmoke() {
    OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutResponse receipt =
        OpsShardReadinessRuntimeExecutionTestSupport.passEvidenceCloseoutService().closeout();

    assertThat(receipt.project()).isEqualTo("advanced-order-platform");
    assertThat(receipt.version()).isEqualTo("Java v170");
    assertThat(receipt.readOnly()).isTrue();
    assertThat(receipt.executionAllowed()).isFalse();
    assertThat(receipt.passEvidenceCloseoutReceiptPresent()).isTrue();
    assertThat(receipt.passEvidenceCloseoutReceiptComplete()).isTrue();
    assertThat(receipt.sourceLiveReadGatePresent()).isTrue();
    assertThat(receipt.sourceLiveReadGateComplete()).isTrue();
    assertThat(receipt.nodeApprovedSmokePresent()).isTrue();
    assertThat(receipt.nodeApprovedSmokePassed()).isTrue();
    assertThat(receipt.nodeArchiveVerificationPresent()).isTrue();
    assertThat(receipt.nodeArchiveVerificationPassed()).isTrue();
    assertThat(receipt.nodePassEvidenceCloseoutPresent()).isTrue();
    assertThat(receipt.nodePassEvidenceCloseoutReady()).isTrue();
    assertThat(receipt.readyForRuntimeExecutionChainHandoff()).isTrue();
    assertThat(receipt.approvedLocalLoopbackReadOnlySmokePassed()).isTrue();
    assertThat(receipt.cleanupProofPresent()).isTrue();
    assertThat(receipt.cleanupProofPassed()).isTrue();
    assertThat(receipt.archiveVerificationPassed()).isTrue();
    assertThat(receipt.runtimeSmokeRerunByJava()).isFalse();
    assertThat(receipt.startsJavaService()).isFalse();
    assertThat(receipt.startsMiniKvService()).isFalse();
    assertThat(receipt.stopsJavaService()).isFalse();
    assertThat(receipt.stopsMiniKvService()).isFalse();
    assertThat(receipt.mutatesJavaState()).isFalse();
    assertThat(receipt.mutatesMiniKvState()).isFalse();
    assertThat(receipt.connectsManagedAudit()).isFalse();
    assertThat(receipt.credentialValueRead()).isFalse();
    assertThat(receipt.rawEndpointUrlParsed()).isFalse();
    assertThat(receipt.writeOperationsAllowed()).isFalse();
    assertThat(receipt.activeShardPrototypeEnabled()).isFalse();
    assertThat(receipt.sourceLiveReadGateVersion()).isEqualTo("Java v169");
    assertThat(receipt.sourceNodeLiveReadGateVersion()).isEqualTo("Node v406");
    assertThat(receipt.nodeApprovedSmokeVersion()).isEqualTo("Node v407");
    assertThat(receipt.nodeArchiveVerificationVersion()).isEqualTo("Node v408");
    assertThat(receipt.nodePassEvidenceCloseoutVersion()).isEqualTo("Node v409");
    assertThat(receipt.nextNodeConsumerHint()).isEqualTo("Node v410");
    assertThat(receipt.closeoutDecision()).isEqualTo("close-runtime-execution-pass-evidence-chain");
    assertThat(receipt.receiptId())
        .isEqualTo("java-runtime-execution-pass-evidence-closeout-receipt-v170");
    assertThat(receipt.sourceSummaryCount()).isEqualTo(4);
    assertThat(receipt.readyStageCount()).isEqualTo(4);
    assertThat(receipt.totalSourceCheckCount()).isEqualTo(114);
    assertThat(receipt.totalSourcePassedCheckCount()).isEqualTo(114);
    assertThat(receipt.totalSourceProductionBlockerCount()).isZero();
    assertThat(receipt.archiveReferenceCount()).isEqualTo(7);
    assertThat(receipt.presentArchiveReferenceCount()).isEqualTo(7);
    assertThat(receipt.sourceStageSummaries())
        .contains(
            "node-v407:approved-local-loopback-read-only-smoke-passed",
            "node-v409:runtime-execution-pass-evidence-closeout-ready");
    assertThat(receipt.smokePassEvidenceFields())
        .contains("attemptedTargetCount:2", "passedTargetCount:2", "failedTargetCount:0");
    assertThat(receipt.cleanupProofFields())
        .contains("cleanupPassed:true", "checkedPort:8080", "afterListeningSocketCount:0");
    assertThat(receipt.closeoutHandoffChecks())
        .contains(
            "node-v407-smoke-pass-evidence-consumed",
            "node-v409-closeout-ledger-consumed",
            "java-v170-does-not-rerun-runtime-smoke");
    assertThat(receipt.failClosedRules())
        .contains(
            "node-v409-closeout-is-not-new-runtime-permission",
            "java-v170-does-not-rerun-smoke",
            "future-route-group-refactors-must-not-change-api-paths");
    assertThat(receipt.stopConditions())
        .contains(
            "request-would-rerun-smoke-from-closeout-receipt",
            "request-would-ignore-cleanup-proof");
    assertThat(receipt.evidencePath())
        .isEqualTo(
            "e/170/evidence/java-shard-readiness-runtime-execution-pass-evidence-closeout-v170.json");
    assertThat(receipt.status()).isEqualTo("passed");
  }
}
