package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceTestSupport;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerEvidenceDigestHistoricalCompatibilityTests {

  @Test
  void keepsV220DigestOutOfOlderEndpointSnapshots() {
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184LiveEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT);

    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v179FixtureEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractConsumerEvidenceDigestService.FIXTURE_ENDPOINT);
    assertThat(OpsShardReadinessReadOnlyEvidenceTestSupport.v184FixtureEndpoints())
        .doesNotContain(OpsShardReadinessV1ContractConsumerEvidenceDigestService.FIXTURE_ENDPOINT);
  }

  @Test
  void keepsV220DigestEvidenceIndependentFromFutureFreezeReceipts() {
    OpsShardReadinessV1ContractConsumerEvidenceDigestResponse digest =
        OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220Digest();

    assertThat(digest.digestEvidence())
        .contains(
            OpsShardReadinessV1ContractConsumerVerificationChecklistService.EVIDENCE_PATH,
            OpsShardReadinessV1ContractConsumerVerificationChecklistService
                .CONSUMER_VERIFICATION_CHECKLIST_ROUTE_INVENTORY_EVIDENCE_PATH)
        .doesNotContain(
            OpsShardReadinessV1ContractConsumerEvidenceDigestService
                .CONSUMER_EVIDENCE_DIGEST_SNAPSHOT_FREEZE_EVIDENCE_PATH,
            OpsShardReadinessV1ContractConsumerEvidenceDigestService
                .CONSUMER_EVIDENCE_DIGEST_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH);
    assertThat(digest.evidencePath())
        .isEqualTo(OpsShardReadinessV1ContractConsumerEvidenceDigestService.EVIDENCE_PATH);
  }

  @Test
  void currentRollingRegistryIncludesV220DigestAfterChecklistBeforeReadOnlyCatalog() {
    assertThat(OpsShardReadinessEvidenceEndpoints.liveEndpoints())
        .containsSubsequence(
            OpsShardReadinessV1ContractConsumerVerificationChecklistService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT,
            OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT);
    assertThat(OpsShardReadinessEvidenceEndpoints.fixtureEndpoints())
        .containsSubsequence(
            OpsShardReadinessV1ContractConsumerVerificationChecklistService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractConsumerEvidenceDigestService.FIXTURE_ENDPOINT,
            OpsShardReadinessReadOnlyEvidenceCatalogService.FIXTURE_ENDPOINT);
  }
}
