package com.codexdemo.orderplatform.ops.maintenance.ciaccept;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.ciaccept.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.MarkdownSection;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReleaseAcceptanceMarkdownTests {

  @Test
  void preservesEveryLegacyMarkdownLine() {
    var response = ReleaseAcceptanceTestData.registry();

    assertThat(response.markdownSections())
        .containsExactly(
            section(
                "Source Dossier",
                "Java v1467 | state=minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-ready | sections=9 | ci=5 | boundaries=8 | status=passed"),
            section(
                "Readiness Gates",
                "source-dossier-status | expected=1 | actual=1 | evidence=status=passed | status=passed",
                "source-package-snapshot | expected=1 | actual=1 | evidence=source-dossier-snapshot=1 | status=passed",
                "section-digests | expected=9 | actual=9 | evidence=section-digests=9 | status=passed",
                "audience-routes | expected=4 | actual=4 | evidence=audience-routes=4 | status=passed",
                "ci-lanes | expected=5 | actual=5 | evidence=ci-lanes=5 | status=passed",
                "boundary-audits | expected=8 | actual=8 | evidence=boundary-audits=8 | status=passed"),
            section(
                "Evidence Chain",
                "1. source-consumer-package-version | target=release-acceptance:source-consumer-package-version | source=Java v1432 | status=passed",
                "2. source-consumer-package-endpoint | target=release-acceptance:source-consumer-package-endpoint | source=/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-registry | status=passed",
                "3. source-consumer-package-profile | target=release-acceptance:source-consumer-package-profile | source=java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-registry.v1 | status=passed",
                "4. source-digest-version | target=release-acceptance:source-digest-version | source=Java v1402 | status=passed",
                "5. source-digest-state | target=release-acceptance:source-digest-state | source=minimal-read-only-gate-operator-ci-handoff-archive-digest-ready | status=passed",
                "6. source-consumer-package-state | target=release-acceptance:source-consumer-package-state | source=minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-ready | status=passed"),
            section(
                "Signoff Lanes",
                "operator-ci-handoff-owner | owner=operator-ci-handoff-owner-owner | evidence=Java v1432 | status=passed",
                "node-v368-archive-verifier | owner=node-v368-archive-verifier-owner | evidence=Java v1402 | status=passed",
                "node-v369-operator-ci | owner=node-v369-operator-ci-owner | evidence=java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-registry.v1 | status=passed",
                "java-read-only-boundary-owner | owner=java-read-only-boundary-owner-owner | evidence=/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-registry | status=passed"),
            section(
                "CI Replay Lanes",
                "1. archive-verification-registry | command=focused | replay=focused-preflight | readOnly=true | status=passed",
                "2. operator-ci-handoff-registry | command=focused | replay=focused-preflight | readOnly=true | status=passed",
                "3. route-evidence | command=grouped | replay=grouped-non-docker-regression | readOnly=true | status=passed",
                "4. non-docker-regression | command=build | replay=package-build | readOnly=true | status=passed",
                "5. read-only-smoke | command=smoke | replay=read-only-smoke | readOnly=true | status=passed"),
            section(
                "Boundary Controls",
                "no-java-autostart | lockedBehavior=Node must not start Java | audit=consumer-package-boundary-lock:archived boundary remains locked | status=passed",
                "no-mini-kv-autostart | lockedBehavior=Node must not start mini-kv | audit=consumer-package-boundary-lock:archived boundary remains locked | status=passed",
                "no-write-routing | lockedBehavior=No write routing may be enabled | audit=consumer-package-boundary-lock:archived boundary remains locked | status=passed",
                "no-credential-value | lockedBehavior=Credential values stay unread | audit=consumer-package-boundary-lock:archived boundary remains locked | status=passed",
                "no-raw-endpoint-url | lockedBehavior=Raw endpoint URLs stay unresolved | audit=consumer-package-boundary-lock:archived boundary remains locked | status=passed",
                "no-managed-audit-http | lockedBehavior=Managed audit HTTP/TCP stays disabled | audit=consumer-package-boundary-lock:archived boundary remains locked | status=passed",
                "no-runtime-shell | lockedBehavior=Runtime shell remains disabled | audit=consumer-package-boundary-lock:archived boundary remains locked | status=passed",
                "no-mini-kv-write-admin | lockedBehavior=mini-kv write/admin commands remain forbidden | audit=consumer-package-boundary-lock:archived boundary remains locked | status=passed"),
            section(
                "Retention Policies",
                "source-dossier-snapshot | window=release+2-cycles | evidence=Java v1467 | status=passed",
                "provenance-chain | window=release+2-cycles | evidence=provenance=6 | status=passed",
                "section-digests | window=release+2-cycles | evidence=section-digests=9 | status=passed",
                "ci-replay-lanes | window=release+1-cycle | evidence=ci-lanes=5 | status=passed",
                "boundary-controls | window=release+2-cycles | evidence=boundaries=8 | status=passed"),
            section(
                "Replay Decisions",
                "focused-first | decision=run-focused-lanes-before-grouped | evidence=ci-lanes=5 | status=passed",
                "grouped-second | decision=run-grouped-after-focused | evidence=read-only-ci=5 | status=passed",
                "build-third | decision=package-after-regression | evidence=markdown=10 | status=passed",
                "smoke-last | decision=smoke-after-build | evidence=source=Java v1467 | status=passed",
                "runtime-closed | decision=keep-runtime-execution-disabled | evidence=executionAllowed=false | status=passed"),
            section(
                "Closeout Checkpoints",
                "1. read-verification-dossier | owner=release-review | evidence=Java v1467 | status=passed",
                "2. verify-readiness-gates | owner=release-review | evidence=scorecard=10 | status=passed",
                "3. confirm-boundary-controls | owner=operator | evidence=boundaries=8 | status=passed",
                "4. record-ci-replay-lanes | owner=ci | evidence=ci-lanes=5 | status=passed",
                "5. archive-release-evidence | owner=release-review | evidence=markdown=10 | status=passed",
                "6. handoff-release-acceptance | owner=operator-ci | evidence=receipts=4 | status=passed"),
            section(
                "Scorecard",
                "source-dossier-status | expected=1 | actual=1 | status=passed",
                "readiness-gates | expected=6 | actual=6 | status=passed",
                "evidence-chain | expected=6 | actual=6 | status=passed",
                "signoff-lanes | expected=4 | actual=4 | status=passed",
                "ci-replay-lanes | expected=5 | actual=5 | status=passed",
                "boundary-controls | expected=8 | actual=8 | status=passed",
                "retention-policies | expected=5 | actual=5 | status=passed",
                "replay-decisions | expected=5 | actual=5 | status=passed",
                "closeout-checkpoints | expected=6 | actual=6 | status=passed",
                "source-dossier-scorecard | expected=10 | actual=10 | status=passed"));
  }

  private static MarkdownSection section(String heading, String... lines) {
    return new MarkdownSection(heading, List.of(lines));
  }
}
