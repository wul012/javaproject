package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsEvidenceServiceTests extends OpsEvidenceContractTestSupport {

  @Test
  void preservesCoreEvidenceContract() {
    Scenario scenario = scenario();
    OpsEvidenceResponse evidence = scenario.evidence();
    var latestFailedAt = scenario.latestFailedAt();
    var latestApprovalAt = scenario.latestApprovalAt();
    assertThat(evidence.evidenceVersion()).isEqualTo("java-ops-evidence.v1");
    assertThat(evidence.readOnly()).isTrue();
    assertThat(evidence.executionAllowed()).isFalse();
    assertThat(evidence.service().name()).isEqualTo("advanced-order-platform");
    assertThat(evidence.service().version()).isEqualTo("0.1.0-test");
    assertThat(evidence.service().profiles()).containsExactly("local", "ops");
    assertThat(evidence.healthProbe().endpoint()).isEqualTo("/actuator/health");
    assertThat(evidence.healthProbe().method()).isEqualTo("GET");
    assertThat(evidence.healthProbe().expectedStatus()).isEqualTo("UP");
    assertThat(evidence.healthProbe().evidenceEndpoint()).isEqualTo("/api/v1/ops/evidence");
    OpsEvidenceServiceEndpointAssertions.assertAdditionalProbeEndpoints(evidence);
    assertThat(evidence.healthProbe().liveProbeRequiredForPass()).isTrue();
    assertThat(evidence.healthProbe().staticSampleOnly()).isFalse();
    assertThat(evidence.readOnlyWindow().windowVersion()).isEqualTo("java-read-only-window.v1");
    assertThat(evidence.readOnlyWindow().operatorStartRequired()).isTrue();
    assertThat(evidence.readOnlyWindow().nodeAutoStartAllowed()).isFalse();
    assertThat(evidence.readOnlyWindow().upstreamProbesRequired()).isTrue();
    assertThat(evidence.readOnlyWindow().upstreamActionsAllowed()).isFalse();
    assertThat(evidence.readOnlyWindow().readyForReadOnlyLiveProbe()).isTrue();
    assertThat(evidence.readOnlyWindow().readyForProductionOperations()).isFalse();
    assertThat(evidence.readOnlyWindow().allowedProbeEndpoints())
        .containsExactly(
            "GET /actuator/health",
            "GET /api/v1/ops/overview",
            "GET /api/v1/ops/evidence",
            "GET /api/v1/ops/shard-readiness",
            "GET /api/v1/ops/shard-readiness/hardening",
            "GET /api/v1/ops/shard-readiness/echo",
            "GET /api/v1/ops/shard-readiness/v1-contract-alignment",
            "GET /api/v1/ops/shard-readiness/v1-contract-alignment-handoff",
            "GET /api/v1/ops/shard-readiness/v1-contract-evidence-packet",
            "GET /api/v1/ops/shard-readiness/v1-contract-operator-checklist",
            "GET /api/v1/ops/shard-readiness/v1-contract-handoff-manifest",
            "GET /api/v1/ops/shard-readiness/v1-contract-consumer-probe-plan",
            "GET /api/v1/ops/shard-readiness/v1-contract-endpoint-catalog",
            "GET /api/v1/ops/shard-readiness/v1-contract-consumer-handoff-bundle",
            "GET /api/v1/ops/shard-readiness/v1-contract-consumer-verification-checklist",
            "GET /api/v1/ops/shard-readiness/v1-contract-consumer-evidence-digest",
            "GET /api/v1/ops/shard-readiness/v1-contract-consumer-readiness-handoff",
            "GET /api/v1/ops/shard-readiness/read-only-evidence-catalog",
            "GET /api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff",
            "GET /api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff-verification",
            "GET /api/v1/ops/shard-readiness/read-only-endpoint-registry-integrity",
            "GET /api/v1/ops/shard-readiness/evidence-index",
            "GET /api/v1/ops/shard-readiness/evidence-verification",
            "GET /api/v1/ops/shard-readiness/evidence-handoff",
            "GET /api/v1/ops/shard-readiness/active-shard-plan-handoff",
            "GET /api/v1/ops/shard-readiness/live-read-gate-plan",
            "GET /api/v1/ops/shard-readiness/operator-service-lifecycle",
            "GET /api/v1/ops/shard-readiness/declared-operator-lifecycle",
            "GET /api/v1/ops/shard-readiness/runtime-execution-artifact-candidate",
            "GET /api/v1/ops/shard-readiness/runtime-execution-packet-contribution",
            "GET /api/v1/ops/shard-readiness/runtime-execution-approval-gate-input",
            "GET /api/v1/ops/shard-readiness/runtime-execution-approval-input-contract-handoff",
            "GET /api/v1/ops/shard-readiness/runtime-execution-approval-input-template-compatibility",
            "GET /api/v1/ops/shard-readiness/runtime-execution-approval-input-template-compatibility-intake",
            "GET /api/v1/ops/shard-readiness/runtime-execution-approval-input-value-validation",
            "GET /api/v1/ops/shard-readiness/runtime-execution-live-read-gate",
            "GET /api/v1/ops/shard-readiness/runtime-execution-pass-evidence-closeout",
            "GET /api/v1/ops/release-approval-rehearsal",
            "GET /contracts/java-shard-readiness-v153.fixture.json",
            "GET /contracts/java-shard-readiness-hardening-v154.fixture.json",
            "GET /contracts/java-shard-readiness-echo-v174.fixture.json",
            "GET /contracts/java-shard-readiness-v1-contract-alignment-v187.fixture.json",
            "GET /contracts/java-shard-readiness-v1-contract-alignment-handoff-v190.fixture.json",
            "GET /contracts/java-shard-readiness-v1-contract-evidence-packet-v193.fixture.json",
            "GET /contracts/java-shard-readiness-v1-contract-operator-checklist-v196.fixture.json",
            "GET /contracts/java-shard-readiness-v1-contract-handoff-manifest-v199.fixture.json",
            "GET /contracts/java-shard-readiness-v1-contract-consumer-probe-plan-v202.fixture.json",
            "GET /contracts/java-shard-readiness-v1-contract-endpoint-catalog-v208.fixture.json",
            "GET /contracts/java-shard-readiness-v1-contract-consumer-handoff-bundle-v211.fixture.json",
            "GET /contracts/java-shard-readiness-v1-contract-consumer-verification-checklist-v215.fixture.json",
            "GET /contracts/java-shard-readiness-v1-contract-consumer-evidence-digest-v220.fixture.json",
            "GET /contracts/java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.fixture.json",
            "GET /contracts/java-shard-readiness-read-only-evidence-catalog-v175.fixture.json",
            "GET /contracts/java-shard-readiness-read-only-evidence-catalog-handoff-v177.fixture.json",
            "GET /contracts/java-shard-readiness-read-only-evidence-catalog-handoff-verification-v179.fixture.json",
            "GET /contracts/java-shard-readiness-read-only-endpoint-registry-integrity-v184.fixture.json",
            "GET /contracts/java-shard-readiness-evidence-index-v155.fixture.json",
            "GET /contracts/java-shard-readiness-evidence-verification-v156.fixture.json",
            "GET /contracts/java-shard-readiness-evidence-handoff-v157.fixture.json",
            "GET /contracts/java-shard-readiness-active-shard-plan-handoff-v158.fixture.json",
            "GET /contracts/java-shard-readiness-live-read-gate-plan-v159.fixture.json",
            "GET /contracts/java-shard-readiness-operator-service-lifecycle-v160.fixture.json",
            "GET /contracts/java-shard-readiness-declared-operator-lifecycle-v161.fixture.json",
            "GET /contracts/java-shard-readiness-runtime-execution-artifact-candidate-v162.fixture.json",
            "GET /contracts/java-shard-readiness-runtime-execution-packet-contribution-v163.fixture.json",
            "GET /contracts/java-shard-readiness-runtime-execution-approval-gate-input-v164.fixture.json",
            "GET /contracts/java-shard-readiness-runtime-execution-approval-input-contract-handoff-v165.fixture.json",
            "GET /contracts/java-shard-readiness-runtime-execution-approval-input-template-compatibility-v166.fixture.json",
            "GET /contracts/java-shard-readiness-runtime-execution-approval-input-template-compatibility-intake-v167.fixture.json",
            "GET /contracts/java-shard-readiness-runtime-execution-approval-input-value-validation-v168.fixture.json",
            "GET /contracts/java-shard-readiness-runtime-execution-live-read-gate-v169.fixture.json",
            "GET /contracts/java-shard-readiness-runtime-execution-pass-evidence-closeout-v170.fixture.json",
            "GET /contracts/ops-read-only-evidence.sample.json",
            "GET /contracts/order-idempotency-boundary.sample.json",
            "GET /contracts/order-idempotency-store-abstraction.sample.json",
            "GET /contracts/release-verification-manifest.sample.json",
            "GET /contracts/deployment-rollback-evidence.sample.json",
            "GET /contracts/release-bundle-manifest.sample.json",
            "GET /contracts/release-handoff-checklist.fixture.json",
            "GET /contracts/release-audit-retention.fixture.json",
            "GET /contracts/release-operator-signoff.fixture.json",
            "GET /contracts/rollback-approver-evidence.fixture.json",
            "GET /contracts/rollback-approval-handoff.sample.json",
            "GET /contracts/rollback-approval-record.fixture.json",
            "GET /contracts/rollback-sql-review-gate.sample.json",
            "GET /contracts/production-secret-source-contract.sample.json",
            "GET /contracts/production-deployment-runbook-contract.sample.json");
    assertThat(evidence.readOnlyWindow().forbiddenOperations())
        .contains("POST /api/v1/failed-events/{id}/replay", "Any non-GET Node upstream action");
    assertThat(evidence.readOnlyWindow().requiredNodeEnvironment())
        .containsExactly("UPSTREAM_PROBES_ENABLED=true", "UPSTREAM_ACTIONS_ENABLED=false");
    assertThat(evidence.readOnlyWindow().replayPostBoundary())
        .contains("must not call POST /api/v1/failed-events/{id}/replay");
    assertThat(evidence.orderIdempotency().boundaryVersion())
        .isEqualTo("java-order-idempotency-boundary.v1");
    assertThat(evidence.orderIdempotency().storeAbstractionVersion())
        .isEqualTo("java-idempotency-store.v1");
    assertThat(evidence.orderIdempotency().createOrderEndpoint()).isEqualTo("/api/v1/orders");
    assertThat(evidence.orderIdempotency().requiredHeader()).isEqualTo("Idempotency-Key");
    assertThat(evidence.orderIdempotency().requestFingerprintVersion())
        .isEqualTo("order-create-request-sha256.v1");
    assertThat(evidence.orderIdempotency().sameKeyDifferentRequestErrorCode())
        .isEqualTo("IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST");
    assertThat(evidence.orderIdempotency().activeStore()).isEqualTo("jpa-order-idempotency-store");
    assertThat(evidence.orderIdempotency().activeStoreImplementation())
        .isEqualTo("JpaIdempotencyStore");
    assertThat(evidence.orderIdempotency().activeStoreMode()).isEqualTo("JPA_DATABASE");
    assertThat(evidence.orderIdempotency().authoritativeStore())
        .isEqualTo(
            "orders table via orders.idempotency_key and orders.idempotency_request_fingerprint");
    assertThat(evidence.orderIdempotency().storeCandidates())
        .extracting(OpsEvidenceResponse.IdempotencyStoreCandidate::name)
        .containsExactly("jpa-order-idempotency-store", "mini-kv-ttl-token-adapter");
    assertThat(evidence.orderIdempotency().storeCandidates().get(1).enabled()).isFalse();
    assertThat(evidence.orderIdempotency().storeCandidates().get(1).connected()).isFalse();
    assertThat(evidence.orderIdempotency().storeCandidates().get(1).mode())
        .isEqualTo("DISABLED_CANDIDATE_ONLY");
    assertThat(evidence.orderIdempotency().miniKvConnected()).isFalse();
    assertThat(evidence.orderIdempotency().externalTokenStoreConnected()).isFalse();
    assertThat(evidence.orderIdempotency().changesPaymentOrInventoryTransaction()).isFalse();
    assertThat(evidence.failedEventReplay().totalFailedEvents()).isEqualTo(4);
    assertThat(evidence.failedEventReplay().pendingReplayApprovals()).isEqualTo(2);
    assertThat(evidence.failedEventReplay().approvedReplayApprovals()).isEqualTo(1);
    assertThat(evidence.failedEventReplay().rejectedReplayApprovals()).isEqualTo(1);
    assertThat(evidence.failedEventReplay().replayBacklog()).isEqualTo(3);
    assertThat(evidence.failedEventReplay().latestFailedAt()).isEqualTo(latestFailedAt);
    assertThat(evidence.failedEventReplay().latestApprovalAt()).isEqualTo(latestApprovalAt);
    assertThat(evidence.failedEventReplay().realReplayAllowedByEvidence()).isFalse();
    assertThat(evidence.outbox().pendingEvents()).isEqualTo(6);
    assertThat(evidence.outbox().publisherEnabled()).isFalse();
    assertThat(evidence.outbox().rabbitMqEnabled()).isFalse();
    assertThat(evidence.outbox().blockers())
        .containsExactly("OUTBOX_PUBLISHER_DISABLED", "RABBITMQ_OUTBOX_DISABLED");
    assertThat(evidence.approvalExecution().requiredApprovalStatus()).isEqualTo("APPROVED");
    assertThat(evidence.approvalExecution().approvalRequired()).isTrue();
    assertThat(evidence.approvalExecution().dryRun()).isTrue();
    assertThat(evidence.approvalExecution().executionBlockers())
        .containsExactly(
            "READ_ONLY_EVIDENCE_ENDPOINT",
            "REPLAY_APPROVAL_PENDING",
            "REPLAY_APPROVAL_REJECTED",
            "REPLAY_BACKLOG_PRESENT");
    assertThat(evidence.blockers())
        .contains(
            "READ_ONLY_EVIDENCE_ENDPOINT", "OUTBOX_PUBLISHER_DISABLED", "RABBITMQ_OUTBOX_DISABLED");
    assertThat(evidence.warnings())
        .containsExactly("OUTBOX_PENDING_EVENTS", "APPROVED_REPLAY_REQUIRES_DIGEST_CHECK");
    OpsEvidenceServiceEndpointAssertions.assertEvidenceEndpoints(evidence);
  }
}
