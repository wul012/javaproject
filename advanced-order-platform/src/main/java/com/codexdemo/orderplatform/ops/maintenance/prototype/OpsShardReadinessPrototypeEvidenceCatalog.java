package com.codexdemo.orderplatform.ops.maintenance.prototype;

import java.util.List;

final class OpsShardReadinessPrototypeEvidenceCatalog {

  private OpsShardReadinessPrototypeEvidenceCatalog() {}

  static List<Entry> entries() {
    return List.of(
        entry(
            409,
            "prototype-catalog",
            "Java shard-readiness.v1 prototype catalog",
            "Node v368",
            OpsShardReadinessPrototypeEvidenceService.CATALOG_ENDPOINT,
            "java-shard-readiness-prototype-catalog.v1",
            "e/409/evidence/java-shard-readiness-prototype-catalog-v409.json",
            List.of(
                "freeze-minimal-shard-readiness-v1-fields",
                "reuse-java-v153-root-readiness",
                "reuse-java-v174-echo-boundary",
                "reuse-route-cleanup-v408-closeout",
                "keep-read-only-and-execution-disabled")),
        entry(
            411,
            "prototype-fixture-echo",
            "Java shard-readiness.v1 fixture echo",
            "Node v368",
            OpsShardReadinessPrototypeEvidenceService.FIXTURE_ECHO_ENDPOINT,
            "java-shard-readiness-prototype-fixture-echo.v1",
            "e/411/evidence/java-shard-readiness-prototype-fixture-echo-v411.json",
            List.of(
                "echo-project-advanced-order-platform",
                "echo-contract-shard-readiness-v1",
                "echo-shard-enabled-false",
                "echo-routing-mode-fixture",
                "echo-status-passed")),
        entry(
            413,
            "prototype-field-alignment",
            "Java shard-readiness.v1 field alignment",
            "Node v368",
            OpsShardReadinessPrototypeEvidenceService.FIELD_ALIGNMENT_ENDPOINT,
            "java-shard-readiness-prototype-field-alignment.v1",
            "e/413/evidence/java-shard-readiness-prototype-field-alignment-v413.json",
            List.of(
                "field-project-present",
                "field-version-present",
                "field-readOnly-true",
                "field-executionAllowed-false",
                "field-shardEnabled-false",
                "field-shardCount-zero",
                "field-slotCount-zero",
                "field-routingMode-fixture",
                "field-evidencePath-present",
                "field-status-passed")),
        entry(
            415,
            "prototype-read-only-integration-bridge",
            "Java read-only integration bridge for shard-readiness.v1",
            "Node v368",
            OpsShardReadinessPrototypeEvidenceService.READ_ONLY_INTEGRATION_BRIDGE_ENDPOINT,
            "java-shard-readiness-prototype-read-only-integration-bridge.v1",
            "e/415/evidence/java-shard-readiness-prototype-read-only-integration-bridge-v415.json",
            List.of(
                "bridge-node-v367-read-targets-passed",
                "bridge-node-v368-archive-verified",
                "bridge-java-does-not-start-services",
                "bridge-executionAllowed-false",
                "bridge-upstream-actions-disabled")),
        entry(
            417,
            "prototype-route-cleanup-bridge",
            "Java route-cleanup closeout bridge for shard-readiness.v1",
            "Node v368",
            OpsShardReadinessPrototypeEvidenceService.ROUTE_CLEANUP_BRIDGE_ENDPOINT,
            "java-shard-readiness-prototype-route-cleanup-bridge.v1",
            "e/417/evidence/java-shard-readiness-prototype-route-cleanup-bridge-v417.json",
            List.of(
                "route-cleanup-v408-closeout-present",
                "route-cleanup-boundary-status-passed",
                "route-cleanup-forbidden-operations-carried-forward",
                "route-cleanup-does-not-enable-active-shard-router",
                "route-cleanup-consumable-as-read-only-evidence")),
        entry(
            419,
            "prototype-read-window-handoff",
            "Java shard-readiness.v1 read window handoff",
            "Node v368",
            OpsShardReadinessPrototypeEvidenceService.READ_WINDOW_HANDOFF_ENDPOINT,
            "java-shard-readiness-prototype-read-window-handoff.v1",
            "e/419/evidence/java-shard-readiness-prototype-read-window-handoff-v419.json",
            List.of(
                "java-health-read-window-required",
                "java-ops-overview-read-window-required",
                "java-shard-readiness-read-window-required",
                "node-may-probe-only-with-upstream-probes-enabled",
                "node-upstream-actions-must-remain-disabled")),
        entry(
            421,
            "prototype-consumer-gate-packet",
            "Java shard-readiness.v1 consumer gate packet",
            "Node v368",
            OpsShardReadinessPrototypeEvidenceService.CONSUMER_GATE_PACKET_ENDPOINT,
            "java-shard-readiness-prototype-consumer-gate-packet.v1",
            "e/421/evidence/java-shard-readiness-prototype-consumer-gate-packet-v421.json",
            List.of(
                "consumer-must-check-contract-name",
                "consumer-must-check-readOnly-true",
                "consumer-must-check-executionAllowed-false",
                "consumer-must-check-status-passed",
                "consumer-must-fail-closed-on-missing-evidence")),
        entry(
            423,
            "prototype-operator-ci-handoff",
            "Java shard-readiness.v1 operator and CI handoff",
            "Node v368",
            OpsShardReadinessPrototypeEvidenceService.OPERATOR_CI_HANDOFF_ENDPOINT,
            "java-shard-readiness-prototype-operator-ci-handoff.v1",
            "e/423/evidence/java-shard-readiness-prototype-operator-ci-handoff-v423.json",
            List.of(
                "ci-step-focused-service-tests",
                "ci-step-controller-route-tests",
                "ci-step-full-maven-test",
                "ci-step-read-only-smoke-after-user-window",
                "ci-step-no-owned-process-left-running")),
        entry(
            425,
            "prototype-audit-digest",
            "Java shard-readiness.v1 prototype audit digest",
            "Node v368",
            OpsShardReadinessPrototypeEvidenceService.AUDIT_DIGEST_ENDPOINT,
            "java-shard-readiness-prototype-audit-digest.v1",
            "e/425/evidence/java-shard-readiness-prototype-audit-digest-v425.json",
            List.of(
                "digest-covers-entry-key",
                "digest-covers-profile",
                "digest-covers-root-readiness-version",
                "digest-covers-echo-version",
                "digest-covers-route-cleanup-closeout-version")),
        entry(
            427,
            "prototype-closeout",
            "Java shard-readiness.v1 prototype closeout",
            "Node v368",
            OpsShardReadinessPrototypeEvidenceService.CLOSEOUT_ENDPOINT,
            "java-shard-readiness-prototype-closeout.v1",
            "e/427/evidence/java-shard-readiness-prototype-closeout-v427.json",
            List.of(
                "closeout-entry-count-10",
                "closeout-first-entry-v409",
                "closeout-latest-entry-v427",
                "closeout-all-evidence-read-only",
                "closeout-ready-for-node-consumer-gate")));
  }

  static Entry entryFor(String key) {
    return entries().stream()
        .filter(entry -> entry.key().equals(key))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Unknown prototype evidence key: " + key));
  }

  private static Entry entry(
      int javaVersion,
      String key,
      String phase,
      String nodePlanVersion,
      String endpoint,
      String profile,
      String evidencePath,
      List<String> checks) {
    return new Entry(
        javaVersion,
        "Java v" + javaVersion,
        key,
        phase,
        nodePlanVersion,
        endpoint,
        profile,
        evidencePath,
        checks);
  }

  record Entry(
      int javaVersion,
      String version,
      String key,
      String phase,
      String nodePlanVersion,
      String endpoint,
      String profile,
      String evidencePath,
      List<String> checks) {}
}
