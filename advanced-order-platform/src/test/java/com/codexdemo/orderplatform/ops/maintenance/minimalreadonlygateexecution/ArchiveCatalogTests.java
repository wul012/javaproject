package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse.ArtifactVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse.BoundaryVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse.GateCheckVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse.ReadTargetVerification;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ArchiveCatalogTests {

  @Test
  void projectsSourceArtifactsAndReadTargets() {
    var evidence = ArchiveCatalog.evidence(ArchiveTestData.sourceRegistry());

    assertThat(evidence.sourceRegistrySnapshots())
        .singleElement()
        .satisfies(
            snapshot -> {
              assertThat(snapshot.version()).isEqualTo("Java v1312");
              assertThat(snapshot.status()).isEqualTo("passed");
            });
    assertThat(evidence.artifactVerifications())
        .hasSize(
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                .EXPECTED_ARTIFACT_VERIFICATION_COUNT)
        .allSatisfy(artifact -> assertThat(artifact.status()).isEqualTo("passed"))
        .extracting(ArtifactVerification::artifact)
        .containsExactly(
            "v367-json",
            "v367-markdown",
            "v367-summary",
            "v367-screenshot",
            "v367-walkthrough",
            "v367-gate-manifest");
    assertThat(evidence.readTargetVerifications())
        .hasSize(
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                .EXPECTED_READ_TARGET_VERIFICATION_COUNT)
        .allSatisfy(
            target -> {
              assertThat(target.archived()).isTrue();
              assertThat(target.status()).isEqualTo("passed");
              assertThat(target.commandOrRoute()).doesNotContain("://");
            })
        .extracting(ReadTargetVerification::target)
        .containsExactly(
            "java-health",
            "java-ops-overview",
            "mini-kv-health",
            "mini-kv-infojson",
            "mini-kv-statsjson");
  }

  @Test
  void projectsPassedChecksAndDeniedBoundaries() {
    var evidence = ArchiveCatalog.evidence(ArchiveTestData.sourceRegistry());

    assertThat(evidence.gateCheckVerifications())
        .hasSize(
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                .EXPECTED_GATE_CHECK_VERIFICATION_COUNT)
        .allSatisfy(
            check -> {
              assertThat(check.sourcePassed()).isTrue();
              assertThat(check.archived()).isTrue();
              assertThat(check.status()).isEqualTo("passed");
            })
        .extracting(GateCheckVerification::code)
        .contains("read-targets-five-of-five", "gate-checks-twenty-of-twenty");
    assertThat(evidence.boundaryVerifications())
        .hasSize(
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                .EXPECTED_BOUNDARY_VERIFICATION_COUNT)
        .allSatisfy(
            boundary -> {
              assertThat(boundary.allowed()).isFalse();
              assertThat(boundary.denied()).isTrue();
              assertThat(boundary.status()).isEqualTo("passed");
            })
        .extracting(BoundaryVerification::code)
        .contains("no-write-routing", "no-managed-audit-connection", "no-java-autostart");
  }

  @Test
  void projectsCiHandoffsAndScorecard() {
    var evidence = ArchiveCatalog.evidence(ArchiveTestData.sourceRegistry());

    assertThat(evidence.ciBatchVerifications())
        .hasSize(
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                .EXPECTED_CI_BATCH_VERIFICATION_COUNT)
        .allSatisfy(batch -> assertThat(batch.status()).isEqualTo("passed"));
    assertThat(evidence.operatorHandoffVerifications())
        .hasSize(
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                .EXPECTED_OPERATOR_HANDOFF_VERIFICATION_COUNT)
        .allSatisfy(handoff -> assertThat(handoff.status()).isEqualTo("passed"));
    assertThat(evidence.scorecard())
        .hasSize(
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                .EXPECTED_SCORECARD_ENTRY_COUNT)
        .allSatisfy(score -> assertThat(score.status()).isEqualTo("passed"));
  }

  @Test
  void evidenceOwnsAllEightLists() {
    var original = ArchiveCatalog.evidence(ArchiveTestData.sourceRegistry());
    var snapshots = new ArrayList<>(original.sourceRegistrySnapshots());
    var artifacts = new ArrayList<>(original.artifactVerifications());
    var readTargets = new ArrayList<>(original.readTargetVerifications());
    var gateChecks = new ArrayList<>(original.gateCheckVerifications());
    var boundaries = new ArrayList<>(original.boundaryVerifications());
    var ciBatches = new ArrayList<>(original.ciBatchVerifications());
    var handoffs = new ArrayList<>(original.operatorHandoffVerifications());
    var scorecard = new ArrayList<>(original.scorecard());

    var evidence =
        new ArchiveCatalog.Evidence(
            snapshots,
            artifacts,
            readTargets,
            gateChecks,
            boundaries,
            ciBatches,
            handoffs,
            scorecard);

    assertOwned(evidence.sourceRegistrySnapshots(), snapshots);
    assertOwned(evidence.artifactVerifications(), artifacts);
    assertOwned(evidence.readTargetVerifications(), readTargets);
    assertOwned(evidence.gateCheckVerifications(), gateChecks);
    assertOwned(evidence.boundaryVerifications(), boundaries);
    assertOwned(evidence.ciBatchVerifications(), ciBatches);
    assertOwned(evidence.operatorHandoffVerifications(), handoffs);
    assertOwned(evidence.scorecard(), scorecard);
  }

  private static <T> void assertOwned(List<T> owned, List<T> source) {
    source.clear();
    assertThat(owned).isNotEmpty();
    assertThatThrownBy(() -> owned.add(owned.getFirst()))
        .isInstanceOf(UnsupportedOperationException.class);
  }
}
