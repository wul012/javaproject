package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEndpointRegistryIntegrityService;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceTestSupport;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractAlignmentHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractAlignmentService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerEvidenceDigestResponse;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerEvidenceDigestService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerHandoffBundleResponse;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerHandoffBundleService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerProbePlanResponse;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerProbePlanService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffResponse;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerVerificationChecklistResponse;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerVerificationChecklistService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractEndpointCatalogResponse;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractEndpointCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractEvidencePacketResponse;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractEvidencePacketService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractHandoffManifestResponse;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractHandoffManifestService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractOperatorChecklistResponse;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractOperatorChecklistService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractTestSupport;
import org.junit.jupiter.api.Test;

class OpsShardReadinessHistoricalEndpointSnapshotCompatibilityTests {

  @Test
  void rollingRegistryKeepsHistoricalLiveSnapshotsReachable() {
    assertThat(OpsShardReadinessEvidenceEndpoints.liveEndpoints())
        .hasSizeGreaterThanOrEqualTo(29)
        .containsAll(OpsShardReadinessReadOnlyEvidenceTestSupport.v179LiveEndpoints())
        .containsAll(OpsShardReadinessReadOnlyEvidenceTestSupport.v184LiveEndpoints())
        .contains(
            OpsShardReadinessV1ContractAlignmentService.ENDPOINT,
            OpsShardReadinessV1ContractAlignmentHandoffService.ENDPOINT,
            OpsShardReadinessV1ContractEvidencePacketService.ENDPOINT,
            OpsShardReadinessV1ContractOperatorChecklistService.ENDPOINT,
            OpsShardReadinessV1ContractHandoffManifestService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerProbePlanService.ENDPOINT,
            OpsShardReadinessV1ContractEndpointCatalogService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerVerificationChecklistService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT);
  }

  @Test
  void rollingRegistryKeepsHistoricalFixtureSnapshotsReachable() {
    assertThat(OpsShardReadinessEvidenceEndpoints.fixtureEndpoints())
        .hasSizeGreaterThanOrEqualTo(29)
        .containsAll(OpsShardReadinessReadOnlyEvidenceTestSupport.v179FixtureEndpoints())
        .containsAll(OpsShardReadinessReadOnlyEvidenceTestSupport.v184FixtureEndpoints())
        .contains(
            OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractAlignmentHandoffService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractEvidencePacketService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractOperatorChecklistService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractHandoffManifestService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractConsumerProbePlanService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractEndpointCatalogService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractConsumerHandoffBundleService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractConsumerVerificationChecklistService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractConsumerEvidenceDigestService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT);
  }

  @Test
  void historicalSnapshotsLayerForwardWithoutMutatingOlderReceipts() {
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184LiveEndpoints())
        .containsAll(OpsShardReadinessReadOnlyEvidenceTestSupport.v179LiveEndpoints())
        .contains(OpsShardReadinessReadOnlyEndpointRegistryIntegrityService.ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179LiveEndpoints())
        .doesNotContain(OpsShardReadinessReadOnlyEndpointRegistryIntegrityService.ENDPOINT);

    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184FixtureEndpoints())
        .containsAll(OpsShardReadinessReadOnlyEvidenceTestSupport.v179FixtureEndpoints())
        .contains(OpsShardReadinessReadOnlyEndpointRegistryIntegrityService.FIXTURE_ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179FixtureEndpoints())
        .doesNotContain(OpsShardReadinessReadOnlyEndpointRegistryIntegrityService.FIXTURE_ENDPOINT);
  }

  @Test
  void v187ContractAlignmentDoesNotBackfillOlderEndpointSnapshots() {
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractAlignmentService.ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractAlignmentService.ENDPOINT);

    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179FixtureEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184FixtureEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT);

    assertThat(OpsShardReadinessV1ContractTestSupport.v187SourceEndpoint())
        .isEqualTo(OpsShardReadinessService.ENDPOINT);
    assertThat(OpsShardReadinessV1ContractTestSupport.v187MinimalFields()).hasSize(10);
  }

  @Test
  void v190ContractAlignmentHandoffDoesNotBackfillOlderEndpointSnapshots() {
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractAlignmentHandoffService.ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractAlignmentHandoffService.ENDPOINT);

    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179FixtureEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractAlignmentHandoffService.FIXTURE_ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184FixtureEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractAlignmentHandoffService.FIXTURE_ENDPOINT);

    assertThat(OpsShardReadinessV1ContractTestSupport.v190SourceAlignment().version())
        .isEqualTo("Java v187");
    assertThat(OpsShardReadinessV1ContractTestSupport.v190HistoricalSnapshotsProtected()).isTrue();
  }

  @Test
  void v193ContractEvidencePacketDoesNotBackfillOlderEndpointSnapshots() {
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractEvidencePacketService.ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractEvidencePacketService.ENDPOINT);

    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179FixtureEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractEvidencePacketService.FIXTURE_ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184FixtureEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractEvidencePacketService.FIXTURE_ENDPOINT);

    OpsShardReadinessV1ContractEvidencePacketResponse packet =
        OpsShardReadinessV1ContractTestSupport.v193Packet();
    assertThat(packet.version()).isEqualTo("Java v193");
    assertThat(packet.evidenceChain())
        .containsExactlyElementsOf(OpsShardReadinessV1ContractTestSupport.v193EvidenceChain());
    assertThat(packet.nodeConsumableEndpoints())
        .containsExactlyElementsOf(
            OpsShardReadinessV1ContractTestSupport.v193NodeConsumableEndpoints());
    assertThat(packet.nodeConsumableFixtureEndpoints())
        .containsExactlyElementsOf(
            OpsShardReadinessV1ContractTestSupport.v193NodeConsumableFixtureEndpoints());
  }

  @Test
  void v196ContractOperatorChecklistDoesNotBackfillOlderEndpointSnapshots() {
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractOperatorChecklistService.ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractOperatorChecklistService.ENDPOINT);

    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179FixtureEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractOperatorChecklistService.FIXTURE_ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184FixtureEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractOperatorChecklistService.FIXTURE_ENDPOINT);

    OpsShardReadinessV1ContractOperatorChecklistResponse checklist =
        OpsShardReadinessV1ContractTestSupport.v196Checklist();
    OpsShardReadinessV1ContractEvidencePacketResponse packet =
        OpsShardReadinessV1ContractTestSupport.v193Packet();
    assertThat(checklist.version()).isEqualTo("Java v196");
    assertThat(checklist.packetEndpoint()).isEqualTo(packet.packetEndpoint());
    assertThat(checklist.requiredReadOnlyEvidence())
        .containsExactlyElementsOf(
            OpsShardReadinessV1ContractTestSupport.v196RequiredReadOnlyEvidence(packet));
    assertThat(checklist.operatorChecklistItems())
        .containsExactlyElementsOf(
            OpsShardReadinessV1ContractTestSupport.v196OperatorChecklistItems());
  }

  @Test
  void v199ContractHandoffManifestDoesNotBackfillOlderEndpointSnapshots() {
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractHandoffManifestService.ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractHandoffManifestService.ENDPOINT);

    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179FixtureEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractHandoffManifestService.FIXTURE_ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184FixtureEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractHandoffManifestService.FIXTURE_ENDPOINT);

    OpsShardReadinessV1ContractHandoffManifestResponse manifest =
        OpsShardReadinessV1ContractTestSupport.v199Manifest();
    OpsShardReadinessV1ContractOperatorChecklistResponse checklist =
        OpsShardReadinessV1ContractTestSupport.v196Checklist();
    assertThat(manifest.version()).isEqualTo("Java v199");
    assertThat(manifest.checklistEndpoint()).isEqualTo(checklist.checklistEndpoint());
    assertThat(manifest.prerequisiteEvidence())
        .containsExactlyElementsOf(
            OpsShardReadinessV1ContractTestSupport.v199PrerequisiteEvidence(checklist));
    assertThat(manifest.consumerReadTargets())
        .containsExactlyElementsOf(
            OpsShardReadinessV1ContractTestSupport.v199ConsumerReadTargets(checklist));
  }

  @Test
  void v202ContractConsumerProbePlanDoesNotBackfillOlderEndpointSnapshots() {
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractConsumerProbePlanService.ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractConsumerProbePlanService.ENDPOINT);

    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179FixtureEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractConsumerProbePlanService.FIXTURE_ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184FixtureEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractConsumerProbePlanService.FIXTURE_ENDPOINT);

    OpsShardReadinessV1ContractConsumerProbePlanResponse probePlan =
        OpsShardReadinessV1ContractTestSupport.v202ProbePlan();
    OpsShardReadinessV1ContractHandoffManifestResponse manifest =
        OpsShardReadinessV1ContractTestSupport.v199Manifest();
    assertThat(probePlan.version()).isEqualTo("Java v202");
    assertThat(probePlan.manifestEndpoint()).isEqualTo(manifest.manifestEndpoint());
    assertThat(probePlan.readTargets())
        .containsExactlyElementsOf(
            OpsShardReadinessV1ContractTestSupport.v202ReadTargets(manifest));
    assertThat(probePlan.stopConditions())
        .containsExactlyElementsOf(OpsShardReadinessV1ContractTestSupport.v202StopConditions());
  }

  @Test
  void v208ContractEndpointCatalogDoesNotBackfillOlderEndpointSnapshots() {
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractEndpointCatalogService.ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractEndpointCatalogService.ENDPOINT);

    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179FixtureEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractEndpointCatalogService.FIXTURE_ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184FixtureEndpoints())
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
            OpsShardReadinessV1ContractConsumerProbePlanService.ENDPOINT);
  }

  @Test
  void v211ContractConsumerHandoffBundleDoesNotBackfillOlderEndpointSnapshots() {
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT);

    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179FixtureEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractConsumerHandoffBundleService.FIXTURE_ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184FixtureEndpoints())
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
                .ENDPOINT_CATALOG_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH);
  }

  @Test
  void v215ContractConsumerVerificationChecklistDoesNotBackfillOlderEndpointSnapshots() {
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractConsumerVerificationChecklistService.ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractConsumerVerificationChecklistService.ENDPOINT);

    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179FixtureEndpoints())
        .doesNotContain(
            OpsShardReadinessV1ContractConsumerVerificationChecklistService.FIXTURE_ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184FixtureEndpoints())
        .doesNotContain(
            OpsShardReadinessV1ContractConsumerVerificationChecklistService.FIXTURE_ENDPOINT);

    OpsShardReadinessV1ContractConsumerVerificationChecklistResponse checklist =
        new OpsShardReadinessV1ContractConsumerVerificationChecklistService().checklist();
    assertThat(checklist.version()).isEqualTo("Java v215");
    assertThat(checklist.handoffBundleEndpoint())
        .isEqualTo(OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT);
    assertThat(checklist.requiredEvidence())
        .contains(
            OpsShardReadinessV1ContractEndpointCatalogService.EVIDENCE_PATH,
            OpsShardReadinessV1ContractConsumerHandoffBundleService.EVIDENCE_PATH,
            OpsShardReadinessV1ContractConsumerVerificationChecklistService
                .HANDOFF_BUNDLE_INTEGRITY_EVIDENCE_PATH);
  }

  @Test
  void v220ContractConsumerEvidenceDigestDoesNotBackfillOlderEndpointSnapshots() {
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT);

    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179FixtureEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractConsumerEvidenceDigestService.FIXTURE_ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184FixtureEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractConsumerEvidenceDigestService.FIXTURE_ENDPOINT);

    OpsShardReadinessV1ContractConsumerEvidenceDigestResponse digest =
        new OpsShardReadinessV1ContractConsumerEvidenceDigestService().digest();
    assertThat(digest.version()).isEqualTo("Java v220");
    assertThat(digest.verificationChecklistEndpoint())
        .isEqualTo(OpsShardReadinessV1ContractConsumerVerificationChecklistService.ENDPOINT);
    assertThat(digest.digestEvidence())
        .contains(
            OpsShardReadinessV1ContractConsumerVerificationChecklistService.EVIDENCE_PATH,
            OpsShardReadinessV1ContractConsumerVerificationChecklistService
                .CONSUMER_VERIFICATION_CHECKLIST_ROUTE_INVENTORY_EVIDENCE_PATH);
  }

  @Test
  void v225ContractConsumerReadinessHandoffDoesNotBackfillOlderEndpointSnapshots() {
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT);

    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179FixtureEndpoints())
        .doesNotContain(
            OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184FixtureEndpoints())
        .doesNotContain(
            OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT);

    OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
        new OpsShardReadinessV1ContractConsumerReadinessHandoffService().handoff();
    assertThat(handoff.version()).isEqualTo("Java v225");
    assertThat(handoff.evidenceDigestEndpoint())
        .isEqualTo(OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT);
    assertThat(handoff.handoffGuardEvidence())
        .contains(
            OpsShardReadinessV1ContractConsumerEvidenceDigestService
                .CONSUMER_EVIDENCE_DIGEST_READINESS_COMPLETION_EVIDENCE_PATH);
  }
}
