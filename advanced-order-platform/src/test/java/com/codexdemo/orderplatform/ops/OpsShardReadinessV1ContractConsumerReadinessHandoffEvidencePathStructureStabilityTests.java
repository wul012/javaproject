package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePathStructureStabilityTests {

    @Test
    void keepsEveryCatalogEvidencePathInTheVersionedReadinessHandoffStructure() {
        for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt
                : OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts()) {
            assertThat(receipt.evidencePath())
                    .as("evidence path for v" + receipt.version())
                    .startsWith("e/" + receipt.version() + "/evidence/java-shard-readiness-")
                    .contains("consumer-readiness-handoff")
                    .endsWith("-v" + receipt.version() + ".json")
                    .doesNotContain(" ")
                    .doesNotContain("\\");
        }
    }

    @Test
    void keepsEvidencePathStructureStabilityPathVersionedToV278() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_EVIDENCE_PATH_STRUCTURE_STABILITY_EVIDENCE_PATH)
                .isEqualTo(
                        "e/278/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "evidence-path-structure-stability-v278.json"
                );
    }
}
