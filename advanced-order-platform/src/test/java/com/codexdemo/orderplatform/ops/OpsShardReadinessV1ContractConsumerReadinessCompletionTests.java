package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessCompletionTests {

    @Test
    void completesConsumerReadinessSurfaceBeforeGeneralReadOnlyCatalog() {
        assertThat(OpsShardReadinessV1ContractEndpointPairs.endpointPairs()).hasSize(10);
        assertThat(OpsShardReadinessEvidenceEndpoints.liveEndpoints())
                .containsSubsequence(
                        OpsShardReadinessV1ContractEndpointCatalogService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerVerificationChecklistService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT,
                        OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT
                );
        assertThat(OpsShardReadinessEvidenceEndpoints.fixtureEndpoints())
                .containsSubsequence(
                        OpsShardReadinessV1ContractEndpointCatalogService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractConsumerHandoffBundleService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractConsumerVerificationChecklistService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractConsumerEvidenceDigestService.FIXTURE_ENDPOINT,
                        OpsShardReadinessReadOnlyEvidenceCatalogService.FIXTURE_ENDPOINT
                );
    }

    @Test
    void keepsDigestEvidenceFocusedOnChecklistInputChain() {
        OpsShardReadinessV1ContractConsumerEvidenceDigestResponse digest =
                OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220Digest();

        assertThat(digest.digestEvidence())
                .containsExactly(
                        OpsShardReadinessV1ContractConsumerVerificationChecklistService.EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerVerificationChecklistService
                                .CONSUMER_VERIFICATION_CHECKLIST_SNAPSHOT_FREEZE_EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerVerificationChecklistService
                                .CONSUMER_VERIFICATION_CHECKLIST_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerVerificationChecklistService
                                .CONSUMER_VERIFICATION_CHECKLIST_INTEGRITY_EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerVerificationChecklistService
                                .CONSUMER_VERIFICATION_CHECKLIST_ROUTE_INVENTORY_EVIDENCE_PATH
                );
        assertThat(laterDigestGuardEvidence())
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
        assertThat(digest.digestEvidence()).doesNotContain(laterDigestGuardEvidence().toArray(String[]::new));
    }

    @Test
    void keepsConsumerSurfaceReadOnlyAndExecutionDenied() {
        OpsShardReadinessV1ContractConsumerVerificationChecklistResponse checklist =
                OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshot.v215Checklist();
        OpsShardReadinessV1ContractConsumerEvidenceDigestResponse digest =
                OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220Digest();

        assertThat(checklist.readOnly()).isTrue();
        assertThat(digest.readOnly()).isTrue();
        assertThat(checklist.executionAllowed()).isFalse();
        assertThat(digest.executionAllowed()).isFalse();
        assertThat(checklist.upstreamActionsAllowed()).isFalse();
        assertThat(digest.upstreamActionsAllowed()).isFalse();
        assertThat(checklist.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
        assertThat(digest.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
        assertThat(digest.blockedOperations())
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

    private static List<String> laterDigestGuardEvidence() {
        return List.of(
                OpsShardReadinessV1ContractConsumerEvidenceDigestService
                        .CONSUMER_EVIDENCE_DIGEST_SNAPSHOT_FREEZE_EVIDENCE_PATH,
                OpsShardReadinessV1ContractConsumerEvidenceDigestService
                        .CONSUMER_EVIDENCE_DIGEST_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
                OpsShardReadinessV1ContractConsumerEvidenceDigestService
                        .CONSUMER_EVIDENCE_DIGEST_INTEGRITY_EVIDENCE_PATH,
                OpsShardReadinessV1ContractConsumerEvidenceDigestService
                        .CONSUMER_EVIDENCE_DIGEST_READINESS_COMPLETION_EVIDENCE_PATH
        );
    }
}
