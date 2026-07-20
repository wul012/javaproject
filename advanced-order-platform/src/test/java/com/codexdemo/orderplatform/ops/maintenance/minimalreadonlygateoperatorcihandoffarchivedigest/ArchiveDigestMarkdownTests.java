package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse.MarkdownSection;
import java.util.List;
import org.junit.jupiter.api.Test;

class ArchiveDigestMarkdownTests {

  @Test
  void rendersExactSections() {
    var response = ArchiveDigestTestData.registry();

    assertThat(response.markdownSections())
        .containsExactly(
            section(
                "Source Archive",
                "source-archive-count=1",
                "Java v1377 | /api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-verification-registry | minimal-read-only-gate-operator-ci-handoff-archive-verification-ready | status=passed"),
            section(
                "Digest Sections",
                "digest-section-count=6",
                "source-handoff-snapshot=1/1 | Java v1352 | status=passed",
                "artifact-verifications=6/6 | artifacts=6/6 | status=passed",
                "operator-lane-verifications=4/4 | lanes=4/4 | status=passed",
                "ci-batch-verifications=5/5 | ci-batches=5/5 | status=passed",
                "boundary-lock-verifications=8/8 | boundaries=8/8 | status=passed",
                "source-archive-scorecard=6/6 | scorecard=6/6 | status=passed"),
            section(
                "Consumer Packets",
                "consumer-packet-count=4",
                "operator-runbook-extract | operator | digest=true | boundary-locks=true | status=passed",
                "ci-batch-matrix | ci | digest=true | boundary-locks=true | status=passed",
                "boundary-lock-manifest | operator-ci | digest=true | boundary-locks=true | status=passed",
                "archive-scorecard-summary | release-review | digest=true | boundary-locks=true | status=passed"),
            section(
                "Replay Instructions",
                "replay-instruction-count=5",
                "1. archive-verification-registry | focused | read-only=true | status=passed",
                "2. operator-ci-handoff-registry | focused | read-only=true | status=passed",
                "3. route-evidence | grouped | read-only=true | status=passed",
                "4. non-docker-regression | build | read-only=true | status=passed",
                "5. read-only-smoke | smoke | read-only=true | status=passed"),
            section(
                "Boundary Locks",
                "boundary-lock-count=8",
                "no-java-autostart | locked=true | archived boundary remains locked",
                "no-mini-kv-autostart | locked=true | archived boundary remains locked",
                "no-write-routing | locked=true | archived boundary remains locked",
                "no-credential-value | locked=true | archived boundary remains locked",
                "no-raw-endpoint-url | locked=true | archived boundary remains locked",
                "no-managed-audit-http | locked=true | archived boundary remains locked",
                "no-runtime-shell | locked=true | archived boundary remains locked",
                "no-mini-kv-write-admin | locked=true | archived boundary remains locked"),
            section(
                "Scorecard",
                "scorecard-entry-count=6",
                "source-archive-status=1/1 | status=passed",
                "digest-sections=6/6 | status=passed",
                "consumer-packets=4/4 | status=passed",
                "read-only-replay-instructions=5/5 | status=passed",
                "boundary-locks=8/8 | status=passed",
                "source-archive-scorecard=6/6 | status=passed"));
  }

  private static MarkdownSection section(String heading, String... lines) {
    return new MarkdownSection(heading, List.of(lines));
  }
}
