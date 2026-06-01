package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractHandoffManifestServiceTests {

    @Test
    void buildsReadOnlyManifestFromPacketAndChecklistReceipts() {
        OpsShardReadinessV1ContractHandoffManifestResponse manifest =
                new OpsShardReadinessV1ContractHandoffManifestService().manifest();

        assertThat(manifest.project()).isEqualTo("advanced-order-platform");
        assertThat(manifest.version()).isEqualTo("Java v199");
        assertThat(manifest.contractName()).isEqualTo("shard-readiness.v1");
        assertThat(manifest.readOnly()).isTrue();
        assertThat(manifest.executionAllowed()).isFalse();
        assertThat(manifest.shardEnabled()).isFalse();
        assertThat(manifest.manifestEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-handoff-manifest");
        assertThat(manifest.manifestFixtureEndpoint())
                .isEqualTo("/contracts/java-shard-readiness-v1-contract-handoff-manifest-v199.fixture.json");
        assertThat(manifest.packetEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-evidence-packet");
        assertThat(manifest.checklistEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-operator-checklist");
        assertThat(manifest.prerequisiteEvidence()).hasSize(7)
                .contains(
                        "e/197/evidence/java-shard-readiness-v196-operator-checklist-snapshot-freeze-v197.json",
                        "e/198/evidence/java-shard-readiness-v196-operator-checklist-historical-snapshot-compatibility-v198.json"
                );
        assertThat(manifest.manifestSections())
                .containsExactly(
                        "contract-summary",
                        "read-only-endpoints",
                        "fixture-endpoints",
                        "required-evidence",
                        "operator-handoff-checks",
                        "blocked-operations"
                );
        assertThat(manifest.consumerReadTargets())
                .containsExactly(
                        "/api/v1/ops/shard-readiness",
                        "/api/v1/ops/shard-readiness/v1-contract-evidence-packet",
                        "/api/v1/ops/shard-readiness/v1-contract-operator-checklist",
                        "/api/v1/ops/shard-readiness/v1-contract-handoff-manifest"
                );
        assertThat(manifest.consumerFixtureTargets())
                .containsExactly(
                        "/contracts/java-shard-readiness-v153.fixture.json",
                        "/contracts/java-shard-readiness-v1-contract-evidence-packet-v193.fixture.json",
                        "/contracts/java-shard-readiness-v1-contract-operator-checklist-v196.fixture.json",
                        "/contracts/java-shard-readiness-v1-contract-handoff-manifest-v199.fixture.json"
                );
        assertThat(manifest.operatorHandoffChecks())
                .contains(
                        "read-manifest-with-get-only",
                        "keep-upstream-actions-disabled"
                );
        assertThat(manifest.verificationChecks())
                .contains(
                        "manifest-section-count:6",
                        "prerequisite-evidence-count:7",
                        "execution-allowed:false"
                );
        assertThat(manifest.packetFrozen()).isTrue();
        assertThat(manifest.checklistFrozen()).isTrue();
        assertThat(manifest.historicalSnapshotsProtected()).isTrue();
        assertThat(manifest.writeRoutingAllowed()).isFalse();
        assertThat(manifest.activeShardRouterAllowed()).isFalse();
        assertThat(manifest.credentialValueRead()).isFalse();
        assertThat(manifest.rawEndpointParsed()).isFalse();
        assertThat(manifest.managedAuditConnectionAllowed()).isFalse();
        assertThat(manifest.deploymentOrRollbackAllowed()).isFalse();
        assertThat(manifest.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
        assertThat(manifest.receiptId())
                .isEqualTo("java-shard-readiness-v1-contract-handoff-manifest-receipt-v199");
        assertThat(manifest.evidencePath())
                .isEqualTo("e/199/evidence/java-shard-readiness-v1-contract-handoff-manifest-v199.json");
        assertThat(manifest.status()).isEqualTo("passed");
    }
}
