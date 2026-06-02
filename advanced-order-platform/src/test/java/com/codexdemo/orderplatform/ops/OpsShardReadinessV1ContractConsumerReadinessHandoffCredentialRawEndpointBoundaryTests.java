package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffCredentialRawEndpointBoundaryTests {

    @Test
    void keepsCredentialValuesAndRawEndpointsUnreadAcrossConsumerReadinessChain() {
        assertThat(boundaryRows())
                .allSatisfy(row -> {
                    assertThat(row.credentialValueRead()).as(row.version()).isFalse();
                    assertThat(row.rawEndpointParsed()).as(row.version()).isFalse();
                    assertThat(row.blockedOperations())
                            .as(row.version())
                            .contains("credential-value-read", "raw-endpoint-parse");
                });
    }

    @Test
    void keepsCredentialRawEndpointBoundaryEvidencePathVersionedToV249() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_CREDENTIAL_RAW_ENDPOINT_BOUNDARY_EVIDENCE_PATH)
                .isEqualTo(
                        "e/249/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "credential-raw-endpoint-boundary-v249.json"
                );
    }

    private static List<BoundaryRow> boundaryRows() {
        OpsShardReadinessV1ContractConsumerVerificationChecklistResponse checklist =
                OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshot.v215Checklist();
        OpsShardReadinessV1ContractConsumerEvidenceDigestResponse digest =
                OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220Digest();
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

        return List.of(
                new BoundaryRow(
                        checklist.version(),
                        checklist.credentialValueRead(),
                        checklist.rawEndpointParsed(),
                        checklist.blockedOperations()
                ),
                new BoundaryRow(
                        digest.version(),
                        digest.credentialValueRead(),
                        digest.rawEndpointParsed(),
                        digest.blockedOperations()
                ),
                new BoundaryRow(
                        handoff.version(),
                        handoff.credentialValueRead(),
                        handoff.rawEndpointParsed(),
                        handoff.blockedOperations()
                )
        );
    }

    private record BoundaryRow(
            String version,
            boolean credentialValueRead,
            boolean rawEndpointParsed,
            List<String> blockedOperations
    ) {
    }
}
