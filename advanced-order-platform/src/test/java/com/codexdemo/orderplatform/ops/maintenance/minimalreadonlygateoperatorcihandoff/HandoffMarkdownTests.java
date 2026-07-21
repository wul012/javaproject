package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class HandoffMarkdownTests {

  @Test
  void preservesEveryLegacyHandoffLine() {
    var response = HandoffTestData.registry();

    assertThat(response.markdownSections())
        .containsExactly(
            handoffSection(
                "Source Archive",
                "source-archive-count=1",
                "Java v1337 | /api/v1/ops/shard-readiness/minimal-read-only-gate-execution-archive-verification-registry | minimal-read-only-gate-execution-archive-verification-ready | status=passed"),
            handoffSection(
                "Operator Lanes",
                "operator-lane-count=4",
                "1. focused | ci-operator | ready=true | Run focused registry and catalog tests first.",
                "2. grouped | ci-operator | ready=true | Run grouped controller and route evidence tests after focused success.",
                "3. build | build-operator | ready=true | Run Maven compile and non-Docker regression before smoke.",
                "4. smoke | release-operator | ready=true | Run read-only smoke only after build validation passes."),
            handoffSection(
                "CI Batches",
                "ci-batch-count=5",
                "1. archive-verification-registry | focused | passed=true",
                "2. operator-ci-handoff-registry | focused | passed=true",
                "3. route-evidence | grouped | passed=true",
                "4. non-docker-regression | build | passed=true",
                "5. read-only-smoke | smoke | passed=true"),
            handoffSection(
                "Boundary Locks",
                "boundary-lock-count=8",
                "no-java-autostart | locked=true",
                "no-mini-kv-autostart | locked=true",
                "no-write-routing | locked=true",
                "no-credential-value | locked=true",
                "no-raw-endpoint-url | locked=true",
                "no-managed-audit-http | locked=true",
                "no-runtime-shell | locked=true",
                "no-mini-kv-write-admin | locked=true"),
            handoffSection(
                "Scorecard",
                "scorecard-entry-count=5",
                "source-archive-status=1/1",
                "operator-lanes=4/4",
                "ci-batches=5/5",
                "boundary-locks=8/8",
                "source-archive-scorecard=7/7"));
  }

  @Test
  void preservesEveryLegacyArchiveLine() {
    var response = ArchiveTestData.registry();

    assertThat(response.markdownSections())
        .containsExactly(
            archiveSection(
                "Source Handoff",
                "source-handoff-count=1",
                "Java v1352 | /api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-registry | minimal-read-only-gate-operator-ci-handoff-ready | status=passed"),
            archiveSection(
                "Artifact Verifications",
                "artifact-verification-count=6",
                "source-response-json | Java v1352 | archived=true | status=passed",
                "markdown-section-rendering | Java v1352 | archived=true | status=passed",
                "operator-lane-plan | Java v1352 | archived=true | status=passed",
                "ci-batch-plan | Java v1352 | archived=true | status=passed",
                "boundary-lock-plan | Java v1352 | archived=true | status=passed",
                "source-scorecard-summary | Java v1352 | archived=true | status=passed"),
            archiveSection(
                "Operator Lane Verifications",
                "operator-lane-verification-count=4",
                "1. focused | ci-operator | archived=true | status=passed",
                "2. grouped | ci-operator | archived=true | status=passed",
                "3. build | build-operator | archived=true | status=passed",
                "4. smoke | release-operator | archived=true | status=passed"),
            archiveSection(
                "CI Batch Verifications",
                "ci-batch-verification-count=5",
                "1. archive-verification-registry | focused | archived=true | status=passed",
                "2. operator-ci-handoff-registry | focused | archived=true | status=passed",
                "3. route-evidence | grouped | archived=true | status=passed",
                "4. non-docker-regression | build | archived=true | status=passed",
                "5. read-only-smoke | smoke | archived=true | status=passed"),
            archiveSection(
                "Boundary Verifications",
                "boundary-verification-count=8",
                "no-java-autostart | locked=true | archived=true | status=passed",
                "no-mini-kv-autostart | locked=true | archived=true | status=passed",
                "no-write-routing | locked=true | archived=true | status=passed",
                "no-credential-value | locked=true | archived=true | status=passed",
                "no-raw-endpoint-url | locked=true | archived=true | status=passed",
                "no-managed-audit-http | locked=true | archived=true | status=passed",
                "no-runtime-shell | locked=true | archived=true | status=passed",
                "no-mini-kv-write-admin | locked=true | archived=true | status=passed"),
            archiveSection(
                "Scorecard",
                "scorecard-entry-count=6",
                "source-handoff-status=1/1 | status=passed",
                "artifact-verifications=6/6 | status=passed",
                "operator-lane-verifications=4/4 | status=passed",
                "ci-batch-verifications=5/5 | status=passed",
                "boundary-lock-verifications=8/8 | status=passed",
                "source-handoff-scorecard=5/5 | status=passed"));
  }

  private static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
          .MarkdownSection
      handoffSection(String heading, String... lines) {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
        .MarkdownSection(heading, List.of(lines));
  }

  private static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
          .MarkdownSection
      archiveSection(String heading, String... lines) {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
        .MarkdownSection(heading, List.of(lines));
  }
}
