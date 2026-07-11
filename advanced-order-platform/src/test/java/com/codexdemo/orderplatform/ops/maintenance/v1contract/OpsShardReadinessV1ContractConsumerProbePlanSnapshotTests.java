package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerProbePlanSnapshotTests {

  @Test
  void freezesV202ProbePlanInputsWithoutReadingCurrentManifestServiceOrRegistryState() {
    OpsShardReadinessV1ContractConsumerProbePlanResponse probePlan =
        OpsShardReadinessV1ContractConsumerProbePlanSnapshot.v202ProbePlan();
    OpsShardReadinessV1ContractHandoffManifestResponse manifest =
        OpsShardReadinessV1ContractHandoffManifestSnapshot.v199Manifest();

    assertThat(probePlan.version()).isEqualTo("Java v202");
    assertThat(probePlan.probePlanEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-consumer-probe-plan");
    assertThat(probePlan.manifestEndpoint()).isEqualTo(manifest.manifestEndpoint());
    assertThat(probePlan.manifestReceiptId()).isEqualTo(manifest.receiptId());
    assertThat(probePlan.readTargets())
        .containsExactlyElementsOf(
            OpsShardReadinessV1ContractConsumerProbePlanSnapshot.v202ReadTargets(manifest));
    assertThat(probePlan.fixtureTargets())
        .containsExactlyElementsOf(
            OpsShardReadinessV1ContractConsumerProbePlanSnapshot.v202FixtureTargets(manifest));
    assertThat(probePlan.probeSequence())
        .containsExactlyElementsOf(
            OpsShardReadinessV1ContractConsumerProbePlanSnapshot.v202ProbeSequence(manifest));
    assertThat(probePlan.requiredEvidence())
        .containsExactlyElementsOf(
            OpsShardReadinessV1ContractConsumerProbePlanSnapshot.v202RequiredEvidence(manifest));
    assertThat(probePlan.stopConditions())
        .containsExactlyElementsOf(
            OpsShardReadinessV1ContractConsumerProbePlanSnapshot.v202StopConditions());
    assertThat(probePlan.verificationChecks())
        .containsExactlyElementsOf(
            OpsShardReadinessV1ContractConsumerProbePlanSnapshot.v202VerificationChecks(manifest));
    assertThat(probePlan.probesAreGetOnly()).isTrue();
    assertThat(probePlan.upstreamActionsAllowed()).isFalse();
    assertThat(probePlan.startsJavaService()).isFalse();
    assertThat(probePlan.startsMiniKvService()).isFalse();
    assertThat(probePlan.receiptId())
        .isEqualTo("java-shard-readiness-v1-contract-consumer-probe-plan-receipt-v202");
    assertThat(probePlan.status()).isEqualTo("passed");
  }
}
