package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessEvidenceHandoffService {

  public static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH + OpsShardReadinessRoutePaths.EVIDENCE_HANDOFF;
  public static final String FIXTURE_ENDPOINT =
      "/contracts/java-shard-readiness-evidence-handoff-v157.fixture.json";
  public static final String EVIDENCE_PATH =
      "e/157/evidence/java-shard-readiness-evidence-handoff-v157.json";

  private final OpsShardReadinessEvidenceIndexService evidenceIndexService;
  private final OpsShardReadinessEvidenceVerificationService evidenceVerificationService;

  public OpsShardReadinessEvidenceHandoffService(
      OpsShardReadinessEvidenceIndexService evidenceIndexService,
      OpsShardReadinessEvidenceVerificationService evidenceVerificationService) {
    this.evidenceIndexService = evidenceIndexService;
    this.evidenceVerificationService = evidenceVerificationService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessEvidenceHandoffResponse handoff() {
    OpsShardReadinessEvidenceIndexResponse index = evidenceIndexService.evidenceIndex();
    OpsShardReadinessEvidenceVerificationResponse verification =
        evidenceVerificationService.verification();

    return new OpsShardReadinessEvidenceHandoffResponse(
        "advanced-order-platform",
        "Java v157",
        true,
        false,
        index.version(),
        verification.version(),
        "Node v378",
        completedEvidenceVersions(index, verification),
        handoffArtifacts(),
        consumerRules(),
        stopConditions(),
        EVIDENCE_PATH,
        handoffStatus(verification));
  }

  private List<String> completedEvidenceVersions(
      OpsShardReadinessEvidenceIndexResponse index,
      OpsShardReadinessEvidenceVerificationResponse verification) {
    List<String> versions = new ArrayList<>();
    versions.add(index.version());
    versions.add(verification.version());
    return List.copyOf(versions);
  }

  private List<String> handoffArtifacts() {
    return List.of(
        OpsShardReadinessEvidenceIndexService.ENDPOINT,
        OpsShardReadinessEvidenceIndexService.FIXTURE_ENDPOINT,
        OpsShardReadinessEvidenceIndexService.EVIDENCE_PATH,
        OpsShardReadinessEvidenceVerificationService.ENDPOINT,
        OpsShardReadinessEvidenceVerificationService.FIXTURE_ENDPOINT,
        OpsShardReadinessEvidenceVerificationService.EVIDENCE_PATH);
  }

  private List<String> consumerRules() {
    return List.of(
        "consume-only-completed-and-tagged-java-evidence",
        "prefer-versioned-fixture-and-archive-paths",
        "do-not-read-rolling-current-files-for-historical-baselines",
        "treat-active-sharding-as-disabled",
        "do-not-start-or-stop-java-from-node-consumption");
  }

  private List<String> stopConditions() {
    return List.of(
        "source-index-status-not-passed",
        "source-verification-status-not-passed",
        "missing-versioned-fixture-or-archive",
        "node-requests-live-read-without-explicit-service-plan",
        "request-would-enable-write-routing-or-active-sharding");
  }

  private String handoffStatus(OpsShardReadinessEvidenceVerificationResponse verification) {
    boolean passed =
        "passed".equals(verification.status())
            && verification.checks().stream()
                .allMatch(OpsShardReadinessEvidenceVerificationResponse.VerificationCheck::passed);
    return passed ? "passed" : "blocked";
  }
}
