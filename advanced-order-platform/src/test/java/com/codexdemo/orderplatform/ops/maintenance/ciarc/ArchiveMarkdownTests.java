package com.codexdemo.orderplatform.ops.maintenance.ciarc;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.ciarc.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse.MarkdownSection;
import java.util.List;
import org.junit.jupiter.api.Test;

class ArchiveMarkdownTests {

  @Test
  void preservesEveryLegacyMarkdownLine() {
    var response = ArchiveTestData.registry();

    assertThat(response.markdownSections())
        .containsExactly(
            section(
                "Source Archive",
                "source-archive-snapshot-count=1",
                "Java v1502 | /api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-registry | java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-registry.v1 | minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-ready | status=passed"),
            section(
                "Artifact Manifest",
                "artifact-manifest-count=7",
                "source-release-acceptance-version=Java v1502 | required=true | status=passed",
                "source-release-acceptance-state=minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-ready | required=true | status=passed",
                "readiness-gates-passed=6 | required=true | status=passed",
                "evidence-chain-passed=6 | required=true | status=passed",
                "signoff-lanes-ready=4 | required=true | status=passed",
                "ci-replay-lanes-read-only=5 | required=true | status=passed",
                "closeout-checkpoints-ready=6 | required=true | status=passed"),
            section(
                "Route Packages",
                "route-package-count=4",
                "operator-ci-handoff-owner | operator-ci-handoff-owner-owner | Java v1432 | ready=true | status=passed",
                "node-v368-archive-verifier | node-v368-archive-verifier-owner | Java v1402 | ready=true | status=passed",
                "node-v369-operator-ci | node-v369-operator-ci-owner | java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-registry.v1 | ready=true | status=passed",
                "java-read-only-boundary-owner | java-read-only-boundary-owner-owner | /api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-registry | ready=true | status=passed"),
            section(
                "Operator Packs",
                "operator-pack-count=4",
                "1. operator-ci-handoff-owner-owner | Java v1432 | ready=true | status=passed",
                "2. node-v368-archive-verifier-owner | Java v1402 | ready=true | status=passed",
                "3. node-v369-operator-ci-owner | java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-registry.v1 | ready=true | status=passed",
                "4. java-read-only-boundary-owner-owner | /api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-registry | ready=true | status=passed"),
            section(
                "CI Attestations",
                "ci-attestation-count=5",
                "1. archive-verification-registry | focused | readOnly=true | sourcePassed=true | status=passed",
                "2. operator-ci-handoff-registry | focused | readOnly=true | sourcePassed=true | status=passed",
                "3. route-evidence | grouped | readOnly=true | sourcePassed=true | status=passed",
                "4. non-docker-regression | build | readOnly=true | sourcePassed=true | status=passed",
                "5. read-only-smoke | smoke | readOnly=true | sourcePassed=true | status=passed"),
            section(
                "Boundary Seals",
                "boundary-seal-count=8",
                "no-java-autostart | Node must not start Java | consumer-package-boundary-lock:archived boundary remains locked | locked=true | status=passed",
                "no-mini-kv-autostart | Node must not start mini-kv | consumer-package-boundary-lock:archived boundary remains locked | locked=true | status=passed",
                "no-write-routing | No write routing may be enabled | consumer-package-boundary-lock:archived boundary remains locked | locked=true | status=passed",
                "no-credential-value | Credential values stay unread | consumer-package-boundary-lock:archived boundary remains locked | locked=true | status=passed",
                "no-raw-endpoint-url | Raw endpoint URLs stay unresolved | consumer-package-boundary-lock:archived boundary remains locked | locked=true | status=passed",
                "no-managed-audit-http | Managed audit HTTP/TCP stays disabled | consumer-package-boundary-lock:archived boundary remains locked | locked=true | status=passed",
                "no-runtime-shell | Runtime shell remains disabled | consumer-package-boundary-lock:archived boundary remains locked | locked=true | status=passed",
                "no-mini-kv-write-admin | mini-kv write/admin commands remain forbidden | consumer-package-boundary-lock:archived boundary remains locked | locked=true | status=passed"),
            section(
                "Retention Windows",
                "retention-window-count=5",
                "source-dossier-snapshot | Java v1467 | release+2-cycles | ready=true | status=passed",
                "provenance-chain | provenance=6 | release+2-cycles | ready=true | status=passed",
                "section-digests | section-digests=9 | release+2-cycles | ready=true | status=passed",
                "ci-replay-lanes | ci-lanes=5 | release+1-cycle | ready=true | status=passed",
                "boundary-controls | boundaries=8 | release+2-cycles | ready=true | status=passed"),
            section(
                "Closeout Ledger",
                "closeout-ledger-count=6",
                "1. read-verification-dossier | release-review | Java v1467 | ready=true | status=passed",
                "2. verify-readiness-gates | release-review | scorecard=10 | ready=true | status=passed",
                "3. confirm-boundary-controls | operator | boundaries=8 | ready=true | status=passed",
                "4. record-ci-replay-lanes | ci | ci-lanes=5 | ready=true | status=passed",
                "5. archive-release-evidence | release-review | markdown=10 | ready=true | status=passed",
                "6. handoff-release-acceptance | operator-ci | receipts=4 | ready=true | status=passed"),
            section(
                "Scorecard",
                "scorecard-entry-count=8",
                "source-release-acceptance-status=1/1 | status=passed",
                "artifact-manifest=7/7 | status=passed",
                "route-packages=4/4 | status=passed",
                "operator-packs=4/4 | status=passed",
                "ci-attestations=5/5 | status=passed",
                "boundary-seals=8/8 | status=passed",
                "retention-windows=5/5 | status=passed",
                "closeout-ledger=6/6 | status=passed"));
  }

  private static MarkdownSection section(String heading, String... lines) {
    return new MarkdownSection(heading, List.of(lines));
  }
}
