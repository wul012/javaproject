package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerVerificationChecklistIntegrityTests {

  @Test
  void keepsChecklistAlignedWithBundleSnapshotAndV1Registry() {
    OpsShardReadinessV1ContractConsumerHandoffBundleResponse bundle =
        OpsShardReadinessV1ContractConsumerHandoffBundleSnapshot.v211Bundle();
    OpsShardReadinessV1ContractConsumerVerificationChecklistResponse checklist =
        OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshot.v215Checklist();

    assertThat(OpsShardReadinessV1ContractEndpointPairs.endpointPairs()).hasSize(11);
    assertThat(OpsShardReadinessV1ContractEndpointPairs.liveEndpoints())
        .doesNotHaveDuplicates()
        .containsSubsequence(
            OpsShardReadinessV1ContractEndpointCatalogService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerVerificationChecklistService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT)
        .doesNotContain(
            OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT,
            OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.ENDPOINT);
    assertThat(OpsShardReadinessV1ContractEndpointPairs.fixtureEndpoints())
        .doesNotHaveDuplicates()
        .containsSubsequence(
            OpsShardReadinessV1ContractEndpointCatalogService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractConsumerHandoffBundleService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractConsumerVerificationChecklistService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractConsumerEvidenceDigestService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT)
        .doesNotContain(
            OpsShardReadinessReadOnlyEvidenceCatalogService.FIXTURE_ENDPOINT,
            OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.FIXTURE_ENDPOINT);

    assertThat(checklist.handoffBundleEndpoint()).isEqualTo(bundle.handoffBundleEndpoint());
    assertThat(checklist.handoffBundleFixtureEndpoint())
        .isEqualTo(bundle.handoffBundleFixtureEndpoint());
    assertThat(checklist.handoffBundleEvidencePath()).isEqualTo(bundle.evidencePath());
    assertThat(checklist.handoffBundleReceiptId()).isEqualTo(bundle.receiptId());
    assertThat(checklist.catalogedArtifactCount()).isEqualTo(bundle.catalogedArtifactCount());
    assertThat(checklist.blockedOperations()).containsExactlyElementsOf(bundle.blockedOperations());
    assertThat(checklist.requiredEvidence())
        .containsExactly(
            bundle.endpointCatalogEvidencePath(),
            bundle.evidencePath(),
            OpsShardReadinessV1ContractConsumerVerificationChecklistService
                .HANDOFF_BUNDLE_SNAPSHOT_FREEZE_EVIDENCE_PATH,
            OpsShardReadinessV1ContractConsumerVerificationChecklistService
                .HANDOFF_BUNDLE_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
            OpsShardReadinessV1ContractConsumerVerificationChecklistService
                .HANDOFF_BUNDLE_INTEGRITY_EVIDENCE_PATH)
        .doesNotContain(
            OpsShardReadinessV1ContractConsumerVerificationChecklistService.EVIDENCE_PATH,
            OpsShardReadinessV1ContractConsumerVerificationChecklistService
                .CONSUMER_VERIFICATION_CHECKLIST_SNAPSHOT_FREEZE_EVIDENCE_PATH,
            OpsShardReadinessV1ContractConsumerVerificationChecklistService
                .CONSUMER_VERIFICATION_CHECKLIST_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
            OpsShardReadinessV1ContractConsumerVerificationChecklistService
                .CONSUMER_VERIFICATION_CHECKLIST_INTEGRITY_EVIDENCE_PATH);
    assertThat(checklist.verificationChecks())
        .containsExactly(
            "bundle-version:" + bundle.version(),
            "cataloged-artifact-count:" + bundle.catalogedArtifactCount(),
            "required-evidence-count:" + bundle.requiredEvidence().size(),
            "handoff-evidence-count:" + bundle.handoffEvidence().size(),
            "probes-are-get-only:" + bundle.probesAreGetOnly(),
            "upstream-actions-allowed:" + bundle.upstreamActionsAllowed(),
            "node-may-start-or-stop-java-or-mini-kv:" + bundle.nodeMayStartOrStopJavaOrMiniKv());
  }
}
