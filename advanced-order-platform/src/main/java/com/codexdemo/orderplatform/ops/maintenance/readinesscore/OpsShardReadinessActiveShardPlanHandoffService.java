package com.codexdemo.orderplatform.ops.maintenance.readinesscore;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessActiveShardPlanHandoffService {

  public static final String ENDPOINT = "/api/v1/ops/shard-readiness/active-shard-plan-handoff";
  public static final String FIXTURE_ENDPOINT =
      "/contracts/java-shard-readiness-active-shard-plan-handoff-v158.fixture.json";
  public static final String EVIDENCE_PATH =
      "e/158/evidence/java-shard-readiness-active-shard-plan-handoff-v158.json";

  private final OpsShardReadinessEvidenceHandoffService evidenceHandoffService;

  public OpsShardReadinessActiveShardPlanHandoffService(
      OpsShardReadinessEvidenceHandoffService evidenceHandoffService) {
    this.evidenceHandoffService = evidenceHandoffService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessActiveShardPlanHandoffResponse handoff() {
    OpsShardReadinessEvidenceHandoffResponse sourceHandoff = evidenceHandoffService.handoff();

    return new OpsShardReadinessActiveShardPlanHandoffResponse(
        "advanced-order-platform",
        "Java v158",
        true,
        false,
        false,
        false,
        sourceHandoff.version(),
        "Node v380",
        "Node v381",
        "read-only-active-shard-plan-boundary-handoff",
        "mini-kv-active-prototype-plan",
        frozenJavaEvidence(sourceHandoff),
        nodeConsumptionReferences(),
        javaBoundaryRules(),
        stopConditions(),
        EVIDENCE_PATH,
        handoffStatus(sourceHandoff));
  }

  private List<String> frozenJavaEvidence(OpsShardReadinessEvidenceHandoffResponse sourceHandoff) {
    return List.of(
        sourceHandoff.version(),
        OpsShardReadinessEvidenceHandoffService.ENDPOINT,
        OpsShardReadinessEvidenceHandoffService.FIXTURE_ENDPOINT,
        sourceHandoff.evidencePath());
  }

  private List<String> nodeConsumptionReferences() {
    return List.of(
        "Node v380 consumed Java v157 handoff as frozen evidence",
        "Node v381 verified the v380 archive with frozen evidence replay",
        "docs/plans3/v380-post-java-mini-kv-active-shard-plan-evidence-intake-roadmap.md",
        "docs/plans3/v381-post-java-mini-kv-active-shard-plan-evidence-intake-archive-verification-roadmap.md");
  }

  private List<String> javaBoundaryRules() {
    return List.of(
        "java-remains-read-only-contract-echo-and-handoff-producer",
        "active-shard-prototype-authority-stays-with-mini-kv-plan",
        "do-not-enable-java-shard-router-or-write-routing",
        "do-not-start-or-stop-node-or-mini-kv-from-java-handoff",
        "live-read-gate-requires-explicit-service-start-port-and-cleanup-plan");
  }

  private List<String> stopConditions() {
    return List.of(
        "source-handoff-status-not-passed",
        "request-would-enable-active-shard-prototype",
        "request-would-change-order-payment-inventory-ledger-or-sql-routing",
        "node-requests-live-read-without-service-responsibility-plan",
        "mini-kv-active-prototype-plan-not-frozen-or-not-read-only");
  }

  private String handoffStatus(OpsShardReadinessEvidenceHandoffResponse sourceHandoff) {
    boolean passed =
        "passed".equals(sourceHandoff.status())
            && sourceHandoff.readOnly()
            && !sourceHandoff.executionAllowed();
    return passed ? "passed" : "blocked";
  }
}
