package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffIntegrityTests {

    @Test
    void keepsV225HandoffDigestAndGuardEvidenceExact() {
        OpsShardReadinessV1ContractConsumerEvidenceDigestResponse digest =
                OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220Digest();
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

        assertThat(handoff.digestEvidenceCount()).isEqualTo(digest.digestEvidence().size());
        assertThat(handoff.digestCheckCount()).isEqualTo(digest.digestChecks().size());
        assertThat(handoff.digestEvidence())
                .containsExactlyElementsOf(digest.digestEvidence());
        assertThat(handoff.handoffGuardEvidence())
                .containsExactly(
                        OpsShardReadinessV1ContractConsumerEvidenceDigestService
                                .CONSUMER_EVIDENCE_DIGEST_SNAPSHOT_FREEZE_EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerEvidenceDigestService
                                .CONSUMER_EVIDENCE_DIGEST_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerEvidenceDigestService
                                .CONSUMER_EVIDENCE_DIGEST_INTEGRITY_EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerEvidenceDigestService
                                .CONSUMER_EVIDENCE_DIGEST_READINESS_COMPLETION_EVIDENCE_PATH
                );
        assertThat(handoff.handoffChecks())
                .containsExactly(
                        "digest-version:Java v220",
                        "digest-evidence-count:5",
                        "digest-check-count:7",
                        "handoff-guard-evidence-count:4",
                        "probes-are-get-only:true",
                        "upstream-actions-allowed:false",
                        "node-may-start-or-stop-java-or-mini-kv:false"
                );
    }

    @Test
    void keepsFutureHandoffGuardReceiptsOutOfFrozenV225Handoff() {
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

        assertThat(handoff.digestEvidence())
                .doesNotContain(
                        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                                .CONSUMER_READINESS_HANDOFF_SNAPSHOT_FREEZE_EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                                .CONSUMER_READINESS_HANDOFF_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                                .CONSUMER_READINESS_HANDOFF_INTEGRITY_EVIDENCE_PATH
                );
        assertThat(handoff.handoffGuardEvidence())
                .doesNotContain(
                        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                                .CONSUMER_READINESS_HANDOFF_SNAPSHOT_FREEZE_EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                                .CONSUMER_READINESS_HANDOFF_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                                .CONSUMER_READINESS_HANDOFF_INTEGRITY_EVIDENCE_PATH
                );
    }

    @Test
    void keepsV225HandoffExecutionBoundaryFullyClosed() {
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

        assertThat(handoff.readOnly()).isTrue();
        assertThat(handoff.executionAllowed()).isFalse();
        assertThat(handoff.shardEnabled()).isFalse();
        assertThat(handoff.probesAreGetOnly()).isTrue();
        assertThat(handoff.upstreamActionsAllowed()).isFalse();
        assertThat(handoff.startsJavaService()).isFalse();
        assertThat(handoff.startsMiniKvService()).isFalse();
        assertThat(handoff.writeRoutingAllowed()).isFalse();
        assertThat(handoff.activeShardRouterAllowed()).isFalse();
        assertThat(handoff.credentialValueRead()).isFalse();
        assertThat(handoff.rawEndpointParsed()).isFalse();
        assertThat(handoff.managedAuditConnectionAllowed()).isFalse();
        assertThat(handoff.deploymentOrRollbackAllowed()).isFalse();
        assertThat(handoff.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
        assertThat(handoff.blockedOperations())
                .containsExactly(
                        "write-routing",
                        "active-shard-router",
                        "credential-value-read",
                        "raw-endpoint-parse",
                        "managed-audit-connection",
                        "deployment-or-rollback",
                        "node-start-or-stop-java-or-mini-kv"
                );
    }
}
