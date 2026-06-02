package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffGetOnlyProbeBoundaryTests {

    @Test
    void keepsConsumerReadinessChainGetOnly() {
        OpsShardReadinessV1ContractConsumerVerificationChecklistResponse checklist =
                OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshot.v215Checklist();
        OpsShardReadinessV1ContractConsumerEvidenceDigestResponse digest =
                OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220Digest();
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

        assertThat(checklist.probesAreGetOnly()).isTrue();
        assertThat(digest.probesAreGetOnly()).isTrue();
        assertThat(handoff.probesAreGetOnly()).isTrue();
        assertThat(checklist.upstreamActionsAllowed()).isFalse();
        assertThat(digest.upstreamActionsAllowed()).isFalse();
        assertThat(handoff.upstreamActionsAllowed()).isFalse();
    }

    @Test
    void keepsEvidenceProbeEndpointsGetOnly() {
        assertThat(OpsShardReadinessEvidenceEndpoints.liveProbeEndpoints())
                .allSatisfy(endpoint -> assertThat(endpoint).startsWith("GET "))
                .noneMatch(OpsShardReadinessV1ContractConsumerReadinessHandoffGetOnlyProbeBoundaryTests
                        ::isWriteProbe);
        assertThat(OpsShardReadinessEvidenceEndpoints.fixtureProbeEndpoints())
                .allSatisfy(endpoint -> assertThat(endpoint).startsWith("GET "));
    }

    @Test
    void keepsGetOnlyProbeBoundaryEvidencePathVersionedToV248() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_GET_ONLY_PROBE_BOUNDARY_EVIDENCE_PATH)
                .isEqualTo(
                        "e/248/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "get-only-probe-boundary-v248.json"
                );
    }

    private static boolean isWriteProbe(String endpoint) {
        return endpoint.startsWith("POST ")
                || endpoint.startsWith("PUT ")
                || endpoint.startsWith("PATCH ")
                || endpoint.startsWith("DELETE ");
    }
}
