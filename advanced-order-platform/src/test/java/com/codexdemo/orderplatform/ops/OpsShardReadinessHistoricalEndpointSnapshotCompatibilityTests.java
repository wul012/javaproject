package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessHistoricalEndpointSnapshotCompatibilityTests {

    @Test
    void rollingRegistryKeepsHistoricalLiveSnapshotsReachable() {
        assertThat(OpsShardReadinessEvidenceEndpoints.liveEndpoints())
                .hasSizeGreaterThanOrEqualTo(29)
                .containsAll(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179LiveEndpoints())
                .containsAll(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184LiveEndpoints())
                .contains(
                        OpsShardReadinessV1ContractAlignmentService.ENDPOINT,
                        OpsShardReadinessV1ContractAlignmentHandoffService.ENDPOINT,
                        OpsShardReadinessV1ContractEvidencePacketService.ENDPOINT,
                        OpsShardReadinessV1ContractOperatorChecklistService.ENDPOINT,
                        OpsShardReadinessV1ContractHandoffManifestService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerProbePlanService.ENDPOINT,
                        OpsShardReadinessV1ContractEndpointCatalogService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT
                );
    }

    @Test
    void rollingRegistryKeepsHistoricalFixtureSnapshotsReachable() {
        assertThat(OpsShardReadinessEvidenceEndpoints.fixtureEndpoints())
                .hasSizeGreaterThanOrEqualTo(29)
                .containsAll(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179FixtureEndpoints())
                .containsAll(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184FixtureEndpoints())
                .contains(
                        OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractAlignmentHandoffService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractEvidencePacketService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractOperatorChecklistService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractHandoffManifestService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractConsumerProbePlanService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractEndpointCatalogService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractConsumerHandoffBundleService.FIXTURE_ENDPOINT
                );
    }

    @Test
    void historicalSnapshotsLayerForwardWithoutMutatingOlderReceipts() {
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184LiveEndpoints())
                .containsAll(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179LiveEndpoints())
                .contains(OpsShardReadinessReadOnlyEndpointRegistryIntegrityService.ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179LiveEndpoints())
                .doesNotContain(OpsShardReadinessReadOnlyEndpointRegistryIntegrityService.ENDPOINT);

        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184FixtureEndpoints())
                .containsAll(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179FixtureEndpoints())
                .contains(OpsShardReadinessReadOnlyEndpointRegistryIntegrityService.FIXTURE_ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179FixtureEndpoints())
                .doesNotContain(OpsShardReadinessReadOnlyEndpointRegistryIntegrityService.FIXTURE_ENDPOINT);
    }

    @Test
    void v187ContractAlignmentDoesNotBackfillOlderEndpointSnapshots() {
        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179LiveEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractAlignmentService.ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184LiveEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractAlignmentService.ENDPOINT);

        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179FixtureEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184FixtureEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT);

        assertThat(OpsShardReadinessV1ContractAlignmentSnapshot.v187SourceEndpoint())
                .isEqualTo(OpsShardReadinessService.ENDPOINT);
        assertThat(OpsShardReadinessV1ContractAlignmentSnapshot.v187MinimalFields())
                .hasSize(10);
    }

    @Test
    void v190ContractAlignmentHandoffDoesNotBackfillOlderEndpointSnapshots() {
        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179LiveEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractAlignmentHandoffService.ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184LiveEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractAlignmentHandoffService.ENDPOINT);

        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179FixtureEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractAlignmentHandoffService.FIXTURE_ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184FixtureEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractAlignmentHandoffService.FIXTURE_ENDPOINT);

        assertThat(OpsShardReadinessV1ContractAlignmentHandoffSnapshot.v190SourceAlignment().version())
                .isEqualTo("Java v187");
        assertThat(OpsShardReadinessV1ContractAlignmentHandoffSnapshot.v190HistoricalSnapshotsProtected())
                .isTrue();
    }

    @Test
    void v193ContractEvidencePacketDoesNotBackfillOlderEndpointSnapshots() {
        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179LiveEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractEvidencePacketService.ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184LiveEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractEvidencePacketService.ENDPOINT);

        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179FixtureEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractEvidencePacketService.FIXTURE_ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184FixtureEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractEvidencePacketService.FIXTURE_ENDPOINT);

        OpsShardReadinessV1ContractEvidencePacketResponse packet =
                OpsShardReadinessV1ContractEvidencePacketSnapshot.v193Packet();
        assertThat(packet.version()).isEqualTo("Java v193");
        assertThat(packet.evidenceChain())
                .containsExactlyElementsOf(OpsShardReadinessV1ContractEvidencePacketSnapshot.v193EvidenceChain());
        assertThat(packet.nodeConsumableEndpoints())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractEvidencePacketSnapshot.v193NodeConsumableEndpoints()
                );
        assertThat(packet.nodeConsumableFixtureEndpoints())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractEvidencePacketSnapshot.v193NodeConsumableFixtureEndpoints()
                );
    }

    @Test
    void v196ContractOperatorChecklistDoesNotBackfillOlderEndpointSnapshots() {
        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179LiveEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractOperatorChecklistService.ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184LiveEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractOperatorChecklistService.ENDPOINT);

        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179FixtureEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractOperatorChecklistService.FIXTURE_ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184FixtureEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractOperatorChecklistService.FIXTURE_ENDPOINT);

        OpsShardReadinessV1ContractOperatorChecklistResponse checklist =
                OpsShardReadinessV1ContractOperatorChecklistSnapshot.v196Checklist();
        OpsShardReadinessV1ContractEvidencePacketResponse packet =
                OpsShardReadinessV1ContractEvidencePacketSnapshot.v193Packet();
        assertThat(checklist.version()).isEqualTo("Java v196");
        assertThat(checklist.packetEndpoint()).isEqualTo(packet.packetEndpoint());
        assertThat(checklist.requiredReadOnlyEvidence())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractOperatorChecklistSnapshot.v196RequiredReadOnlyEvidence(packet)
                );
        assertThat(checklist.operatorChecklistItems())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractOperatorChecklistSnapshot.v196OperatorChecklistItems()
                );
    }

    @Test
    void v199ContractHandoffManifestDoesNotBackfillOlderEndpointSnapshots() {
        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179LiveEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractHandoffManifestService.ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184LiveEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractHandoffManifestService.ENDPOINT);

        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179FixtureEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractHandoffManifestService.FIXTURE_ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184FixtureEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractHandoffManifestService.FIXTURE_ENDPOINT);

        OpsShardReadinessV1ContractHandoffManifestResponse manifest =
                OpsShardReadinessV1ContractHandoffManifestSnapshot.v199Manifest();
        OpsShardReadinessV1ContractOperatorChecklistResponse checklist =
                OpsShardReadinessV1ContractOperatorChecklistSnapshot.v196Checklist();
        assertThat(manifest.version()).isEqualTo("Java v199");
        assertThat(manifest.checklistEndpoint()).isEqualTo(checklist.checklistEndpoint());
        assertThat(manifest.prerequisiteEvidence())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractHandoffManifestSnapshot.v199PrerequisiteEvidence(checklist)
                );
        assertThat(manifest.consumerReadTargets())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractHandoffManifestSnapshot.v199ConsumerReadTargets(checklist)
                );
    }

    @Test
    void v202ContractConsumerProbePlanDoesNotBackfillOlderEndpointSnapshots() {
        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179LiveEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractConsumerProbePlanService.ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184LiveEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractConsumerProbePlanService.ENDPOINT);

        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179FixtureEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractConsumerProbePlanService.FIXTURE_ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184FixtureEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractConsumerProbePlanService.FIXTURE_ENDPOINT);

        OpsShardReadinessV1ContractConsumerProbePlanResponse probePlan =
                OpsShardReadinessV1ContractConsumerProbePlanSnapshot.v202ProbePlan();
        OpsShardReadinessV1ContractHandoffManifestResponse manifest =
                OpsShardReadinessV1ContractHandoffManifestSnapshot.v199Manifest();
        assertThat(probePlan.version()).isEqualTo("Java v202");
        assertThat(probePlan.manifestEndpoint()).isEqualTo(manifest.manifestEndpoint());
        assertThat(probePlan.readTargets())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractConsumerProbePlanSnapshot.v202ReadTargets(manifest)
                );
        assertThat(probePlan.stopConditions())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractConsumerProbePlanSnapshot.v202StopConditions()
                );
    }

    @Test
    void v208ContractEndpointCatalogDoesNotBackfillOlderEndpointSnapshots() {
        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179LiveEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractEndpointCatalogService.ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184LiveEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractEndpointCatalogService.ENDPOINT);

        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179FixtureEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractEndpointCatalogService.FIXTURE_ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184FixtureEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractEndpointCatalogService.FIXTURE_ENDPOINT);

        OpsShardReadinessV1ContractEndpointCatalogResponse catalog =
                new OpsShardReadinessV1ContractEndpointCatalogService().catalog();
        assertThat(catalog.version()).isEqualTo("Java v208");
        assertThat(catalog.contractEndpointCount()).isEqualTo(6);
        assertThat(catalog.endpoints())
                .extracting(OpsShardReadinessV1ContractEndpointCatalogResponse.EndpointEntry::liveEndpoint)
                .containsExactly(
                        OpsShardReadinessV1ContractAlignmentService.ENDPOINT,
                        OpsShardReadinessV1ContractAlignmentHandoffService.ENDPOINT,
                        OpsShardReadinessV1ContractEvidencePacketService.ENDPOINT,
                        OpsShardReadinessV1ContractOperatorChecklistService.ENDPOINT,
                        OpsShardReadinessV1ContractHandoffManifestService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerProbePlanService.ENDPOINT
                );
    }

    @Test
    void v211ContractConsumerHandoffBundleDoesNotBackfillOlderEndpointSnapshots() {
        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179LiveEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184LiveEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT);

        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179FixtureEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractConsumerHandoffBundleService.FIXTURE_ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184FixtureEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractConsumerHandoffBundleService.FIXTURE_ENDPOINT);

        OpsShardReadinessV1ContractConsumerHandoffBundleResponse bundle =
                new OpsShardReadinessV1ContractConsumerHandoffBundleService().bundle();
        assertThat(bundle.version()).isEqualTo("Java v211");
        assertThat(bundle.endpointCatalogEndpoint())
                .isEqualTo(OpsShardReadinessV1ContractEndpointCatalogService.ENDPOINT);
        assertThat(bundle.requiredEvidence())
                .contains(
                        OpsShardReadinessV1ContractEndpointCatalogService.EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerHandoffBundleService
                                .ENDPOINT_CATALOG_SNAPSHOT_FREEZE_EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerHandoffBundleService
                                .ENDPOINT_CATALOG_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH
                );
    }
}
