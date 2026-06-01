package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractHandoffManifestSnapshotTests {

    @Test
    void freezesV199ManifestInputsWithoutReadingCurrentChecklistServiceOrRegistryState() {
        OpsShardReadinessV1ContractHandoffManifestResponse manifest =
                OpsShardReadinessV1ContractHandoffManifestSnapshot.v199Manifest();
        OpsShardReadinessV1ContractOperatorChecklistResponse checklist =
                OpsShardReadinessV1ContractOperatorChecklistSnapshot.v196Checklist();

        assertThat(manifest.version()).isEqualTo("Java v199");
        assertThat(manifest.manifestEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-handoff-manifest");
        assertThat(manifest.packetEndpoint()).isEqualTo(checklist.packetEndpoint());
        assertThat(manifest.checklistEndpoint()).isEqualTo(checklist.checklistEndpoint());
        assertThat(manifest.prerequisiteEvidence())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractHandoffManifestSnapshot.v199PrerequisiteEvidence(checklist)
                );
        assertThat(manifest.manifestSections())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractHandoffManifestSnapshot.v199ManifestSections()
                );
        assertThat(manifest.consumerReadTargets())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractHandoffManifestSnapshot.v199ConsumerReadTargets(checklist)
                );
        assertThat(manifest.consumerFixtureTargets())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractHandoffManifestSnapshot.v199ConsumerFixtureTargets(checklist)
                );
        assertThat(manifest.operatorHandoffChecks())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractHandoffManifestSnapshot.v199OperatorHandoffChecks()
                );
        assertThat(manifest.verificationChecks())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractHandoffManifestSnapshot.v199VerificationChecks(checklist)
                );
        assertThat(manifest.packetFrozen()).isTrue();
        assertThat(manifest.checklistFrozen()).isTrue();
        assertThat(manifest.historicalSnapshotsProtected()).isTrue();
        assertThat(manifest.receiptId())
                .isEqualTo("java-shard-readiness-v1-contract-handoff-manifest-receipt-v199");
        assertThat(manifest.status()).isEqualTo("passed");
    }
}
