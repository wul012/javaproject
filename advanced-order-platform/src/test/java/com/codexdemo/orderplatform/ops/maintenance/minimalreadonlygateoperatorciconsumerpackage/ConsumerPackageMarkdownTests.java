package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.MarkdownSection;
import java.util.List;
import org.junit.jupiter.api.Test;

class ConsumerPackageMarkdownTests {

  @Test
  void rendersExactSections() {
    var response = ConsumerPackageTestData.registry();

    assertThat(response.markdownSections())
        .containsExactly(
            section(
                "Source Digest",
                "source-digest-count=1",
                "Java v1402 | /api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-digest-registry | minimal-read-only-gate-operator-ci-handoff-archive-digest-ready | status=passed"),
            section(
                "Manifest",
                "manifest-entry-count=5",
                "source-digest-version=Java v1402 | required=true | status=passed",
                "source-archive-version=Java v1377 | required=true | status=passed",
                "source-digest-state=minimal-read-only-gate-operator-ci-handoff-archive-digest-ready | required=true | status=passed",
                "source-endpoint=/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-digest-registry | required=true | status=passed",
                "source-profile=java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-archive-digest-registry.v1 | required=true | status=passed"),
            section(
                "Consumer Audiences",
                "consumer-audience-count=4",
                "operator-runbook-extract | operator | packet=operator-runbook-extract | status=passed",
                "ci-batch-matrix | ci | packet=ci-batch-matrix | status=passed",
                "boundary-lock-manifest | operator-ci | packet=boundary-lock-manifest | status=passed",
                "archive-scorecard-summary | release-review | packet=archive-scorecard-summary | status=passed"),
            section(
                "Package Sections",
                "package-section-count=5",
                "source-digest-summary | release-review | Java v1402 | status=passed",
                "manifest | operator-ci | java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-archive-digest-registry.v1 | status=passed",
                "consumer-packets | operator-ci | packets=4 | status=passed",
                "ci-matrix | ci | replay-instructions=5 | status=passed",
                "boundary-locks | operator | locked-boundaries=8 | status=passed"),
            section(
                "Acceptance Criteria",
                "acceptance-criterion-count=5",
                "source-digest-passed | status=passed | passed=true",
                "digest-sections-passed | digest-sections=6/6 | passed=true",
                "consumer-packets-ready | consumer-packets=4/4 | passed=true",
                "replay-instructions-read-only | replay=5/5 | passed=true",
                "boundaries-locked | boundaries=8/8 | passed=true"),
            section(
                "CI Matrix",
                "ci-matrix-count=5",
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
                "Handoff Checklist",
                "handoff-checklist-count=5",
                "1. read-source-digest | operator | ready=true | status=passed",
                "2. confirm-boundary-locks | operator | ready=true | status=passed",
                "3. run-focused-first | ci | ready=true | status=passed",
                "4. preserve-read-only-env | ci | ready=true | status=passed",
                "5. archive-ci-conclusion | release-review | ready=true | status=passed"),
            section(
                "Scorecard",
                "scorecard-entry-count=8",
                "source-digest-status=1/1 | status=passed",
                "manifest=5/5 | status=passed",
                "consumer-audiences=4/4 | status=passed",
                "package-sections=5/5 | status=passed",
                "acceptance-criteria=5/5 | status=passed",
                "ci-matrix=5/5 | status=passed",
                "boundary-locks=8/8 | status=passed",
                "handoff-checklist=5/5 | status=passed"));
  }

  private static MarkdownSection section(String heading, String... lines) {
    return new MarkdownSection(heading, List.of(lines));
  }
}
