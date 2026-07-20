package com.codexdemo.orderplatform.ops.maintenance.operatorcidossier;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse.MarkdownSection;
import java.util.List;
import org.junit.jupiter.api.Test;

class DossierMarkdownTests {

  @Test
  void rendersExactSections() {
    var response = DossierTestData.registry();

    assertThat(response.markdownSections())
        .containsExactly(
            section(
                "Source Consumer Package",
                "Java v1432 | state=minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-ready | manifest=5 | sections=5 | ci=5 | status=passed"),
            section(
                "Provenance",
                "source-consumer-package-version=Java v1432 | required=true | status=passed",
                "source-consumer-package-endpoint=/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-registry | required=true | status=passed",
                "source-consumer-package-profile=java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-registry.v1 | required=true | status=passed",
                "source-digest-version=Java v1402 | required=true | status=passed",
                "source-digest-state=minimal-read-only-gate-operator-ci-handoff-archive-digest-ready | required=true | status=passed",
                "source-consumer-package-state=minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-ready | required=true | status=passed"),
            section(
                "Section Digests",
                "Source Digest | lines=2 | required=true | status=passed",
                "Manifest | lines=6 | required=true | status=passed",
                "Consumer Audiences | lines=5 | required=true | status=passed",
                "Package Sections | lines=6 | required=true | status=passed",
                "Acceptance Criteria | lines=6 | required=true | status=passed",
                "CI Matrix | lines=6 | required=true | status=passed",
                "Boundary Locks | lines=9 | required=true | status=passed",
                "Handoff Checklist | lines=6 | required=true | status=passed",
                "Scorecard | lines=9 | required=true | status=passed"),
            section(
                "Audience Routes",
                "operator-runbook-extract -> operator-review | owner=operator | packet=operator-runbook-extract | status=passed",
                "ci-batch-matrix -> ci-non-docker-regression | owner=ci | packet=ci-batch-matrix | status=passed",
                "boundary-lock-manifest -> read-only-consumer-review | owner=operator-ci | packet=boundary-lock-manifest | status=passed",
                "archive-scorecard-summary -> archive-verification | owner=release-review | packet=archive-scorecard-summary | status=passed"),
            section(
                "CI Lanes",
                "1. archive-verification-registry | command=focused | replayGroup=focused-preflight | readOnly=true | status=passed",
                "2. operator-ci-handoff-registry | command=focused | replayGroup=focused-preflight | readOnly=true | status=passed",
                "3. route-evidence | command=grouped | replayGroup=grouped-non-docker-regression | readOnly=true | status=passed",
                "4. non-docker-regression | command=build | replayGroup=package-build | readOnly=true | status=passed",
                "5. read-only-smoke | command=smoke | replayGroup=read-only-smoke | readOnly=true | status=passed"),
            section(
                "Acceptance Gates",
                "source-digest-passed | artifact=source-digest-passed-verification-dossier | evidence=status=passed | status=passed",
                "digest-sections-passed | artifact=digest-sections-passed-verification-dossier | evidence=digest-sections=6/6 | status=passed",
                "consumer-packets-ready | artifact=consumer-packets-ready-verification-dossier | evidence=consumer-packets=4/4 | status=passed",
                "replay-instructions-read-only | artifact=replay-instructions-read-only-verification-dossier | evidence=replay=5/5 | status=passed",
                "boundaries-locked | artifact=boundaries-locked-verification-dossier | evidence=boundaries=8/8 | status=passed"),
            section(
                "Boundary Audits",
                "no-java-autostart | lockedBehavior=Node must not start Java | evidence=consumer-package-boundary-lock:archived boundary remains locked | status=passed",
                "no-mini-kv-autostart | lockedBehavior=Node must not start mini-kv | evidence=consumer-package-boundary-lock:archived boundary remains locked | status=passed",
                "no-write-routing | lockedBehavior=No write routing may be enabled | evidence=consumer-package-boundary-lock:archived boundary remains locked | status=passed",
                "no-credential-value | lockedBehavior=Credential values stay unread | evidence=consumer-package-boundary-lock:archived boundary remains locked | status=passed",
                "no-raw-endpoint-url | lockedBehavior=Raw endpoint URLs stay unresolved | evidence=consumer-package-boundary-lock:archived boundary remains locked | status=passed",
                "no-managed-audit-http | lockedBehavior=Managed audit HTTP/TCP stays disabled | evidence=consumer-package-boundary-lock:archived boundary remains locked | status=passed",
                "no-runtime-shell | lockedBehavior=Runtime shell remains disabled | evidence=consumer-package-boundary-lock:archived boundary remains locked | status=passed",
                "no-mini-kv-write-admin | lockedBehavior=mini-kv write/admin commands remain forbidden | evidence=consumer-package-boundary-lock:archived boundary remains locked | status=passed"),
            section(
                "Release Checklist",
                "1. read-source-digest | owner=operator | evidence=consumer-package-checklist:passed | status=passed",
                "2. confirm-boundary-locks | owner=operator | evidence=consumer-package-checklist:passed | status=passed",
                "3. run-focused-first | owner=ci | evidence=consumer-package-checklist:passed | status=passed",
                "4. preserve-read-only-env | owner=ci | evidence=consumer-package-checklist:passed | status=passed",
                "5. archive-ci-conclusion | owner=release-review | evidence=consumer-package-checklist:passed | status=passed"),
            section(
                "Handoff Receipts",
                "operator-ci-handoff-owner | receiptType=consumer-package-source | evidence=Java v1432 | status=passed",
                "node-v368-archive-verifier | receiptType=archive-verification-input | evidence=Java v1402 | status=passed",
                "node-v369-operator-ci | receiptType=operator-ci-handoff-input | evidence=java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-registry.v1 | status=passed",
                "java-read-only-boundary-owner | receiptType=read-only-boundary-continuity | evidence=/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-registry | status=passed"),
            section(
                "Scorecard",
                "source-consumer-package-status | expected=1 | actual=1 | status=passed",
                "source-package-snapshot | expected=1 | actual=1 | status=passed",
                "provenance | expected=6 | actual=6 | status=passed",
                "section-digests | expected=9 | actual=9 | status=passed",
                "audience-routes | expected=4 | actual=4 | status=passed",
                "ci-lanes | expected=5 | actual=5 | status=passed",
                "acceptance-gates | expected=5 | actual=5 | status=passed",
                "boundary-audits | expected=8 | actual=8 | status=passed",
                "release-checklist | expected=5 | actual=5 | status=passed",
                "handoff-receipts | expected=4 | actual=4 | status=passed"));
  }

  private static MarkdownSection section(String heading, String... lines) {
    return new MarkdownSection(heading, List.of(lines));
  }
}
