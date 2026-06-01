package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerProbePlanServiceTests {

    @Test
    void buildsReadOnlyConsumerProbePlanFromHandoffManifestWithoutOpeningExecution() {
        OpsShardReadinessV1ContractConsumerProbePlanResponse probePlan =
                new OpsShardReadinessV1ContractConsumerProbePlanService().probePlan();

        assertThat(probePlan.project()).isEqualTo("advanced-order-platform");
        assertThat(probePlan.version()).isEqualTo("Java v202");
        assertThat(probePlan.contractName()).isEqualTo("shard-readiness.v1");
        assertThat(probePlan.readOnly()).isTrue();
        assertThat(probePlan.executionAllowed()).isFalse();
        assertThat(probePlan.shardEnabled()).isFalse();
        assertThat(probePlan.probePlanEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-consumer-probe-plan");
        assertThat(probePlan.probePlanFixtureEndpoint())
                .isEqualTo("/contracts/java-shard-readiness-v1-contract-consumer-probe-plan-v202.fixture.json");
        assertThat(probePlan.manifestEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-handoff-manifest");
        assertThat(probePlan.manifestEvidencePath())
                .isEqualTo("e/199/evidence/java-shard-readiness-v1-contract-handoff-manifest-v199.json");
        assertThat(probePlan.manifestReceiptId())
                .isEqualTo("java-shard-readiness-v1-contract-handoff-manifest-receipt-v199");
        assertThat(probePlan.readTargets())
                .containsExactly(
                        "/api/v1/ops/shard-readiness",
                        "/api/v1/ops/shard-readiness/v1-contract-evidence-packet",
                        "/api/v1/ops/shard-readiness/v1-contract-operator-checklist",
                        "/api/v1/ops/shard-readiness/v1-contract-handoff-manifest",
                        "/api/v1/ops/shard-readiness/v1-contract-consumer-probe-plan"
                );
        assertThat(probePlan.fixtureTargets())
                .containsExactly(
                        "/contracts/java-shard-readiness-v153.fixture.json",
                        "/contracts/java-shard-readiness-v1-contract-evidence-packet-v193.fixture.json",
                        "/contracts/java-shard-readiness-v1-contract-operator-checklist-v196.fixture.json",
                        "/contracts/java-shard-readiness-v1-contract-handoff-manifest-v199.fixture.json",
                        "/contracts/java-shard-readiness-v1-contract-consumer-probe-plan-v202.fixture.json"
                );
        assertThat(probePlan.probeSequence())
                .containsExactly(
                        "GET /api/v1/ops/shard-readiness",
                        "GET /api/v1/ops/shard-readiness/v1-contract-evidence-packet",
                        "GET /api/v1/ops/shard-readiness/v1-contract-operator-checklist",
                        "GET /api/v1/ops/shard-readiness/v1-contract-handoff-manifest",
                        "GET /api/v1/ops/shard-readiness/v1-contract-consumer-probe-plan",
                        "GET /contracts/java-shard-readiness-v1-contract-consumer-probe-plan-v202.fixture.json"
                );
        assertThat(probePlan.requiredEvidence()).hasSize(10)
                .contains(
                        "e/200/evidence/java-shard-readiness-v199-handoff-manifest-snapshot-freeze-v200.json",
                        "e/201/evidence/java-shard-readiness-v199-handoff-manifest-historical-snapshot-compatibility-v201.json",
                        "e/202/evidence/java-shard-readiness-v1-contract-consumer-probe-plan-v202.json"
                );
        assertThat(probePlan.stopConditions())
                .contains(
                        "non-get-request-required",
                        "java-or-mini-kv-process-control-required",
                        "write-routing-or-active-shard-router-required"
                );
        assertThat(probePlan.verificationChecks())
                .contains(
                        "read-target-count:5",
                        "fixture-target-count:5",
                        "probe-sequence-count:6",
                        "required-evidence-count:10",
                        "execution-allowed:false"
                );
        assertThat(probePlan.probesAreGetOnly()).isTrue();
        assertThat(probePlan.upstreamActionsAllowed()).isFalse();
        assertThat(probePlan.startsJavaService()).isFalse();
        assertThat(probePlan.startsMiniKvService()).isFalse();
        assertThat(probePlan.writeRoutingAllowed()).isFalse();
        assertThat(probePlan.activeShardRouterAllowed()).isFalse();
        assertThat(probePlan.credentialValueRead()).isFalse();
        assertThat(probePlan.rawEndpointParsed()).isFalse();
        assertThat(probePlan.managedAuditConnectionAllowed()).isFalse();
        assertThat(probePlan.deploymentOrRollbackAllowed()).isFalse();
        assertThat(probePlan.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
        assertThat(probePlan.receiptId())
                .isEqualTo("java-shard-readiness-v1-contract-consumer-probe-plan-receipt-v202");
        assertThat(probePlan.evidencePath())
                .isEqualTo("e/202/evidence/java-shard-readiness-v1-contract-consumer-probe-plan-v202.json");
        assertThat(probePlan.status()).isEqualTo("passed");
    }
}
