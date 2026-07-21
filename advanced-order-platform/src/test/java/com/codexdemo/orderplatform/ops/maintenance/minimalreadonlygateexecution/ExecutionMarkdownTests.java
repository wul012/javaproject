package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class ExecutionMarkdownTests {

  @Test
  void preservesExecutionReport() {
    var response = ExecutionTestData.registry();

    assertThat(
            response.markdownSections().stream()
                .map(section -> section(section.heading(), section.lines()))
                .toList())
        .containsExactly(
            section(
                "Read Targets",
                "read-target-count=5",
                "java-health | java-operator | HTTP GET | ORDER_PLATFORM_URL handle | GET /actuator/health | status=passed",
                "java-ops-overview | java-operator | HTTP GET | ORDER_PLATFORM_URL handle | GET /api/v1/ops/overview | status=passed",
                "mini-kv-health | mini-kv-operator | TCP command | MINIKV_HOST/MINIKV_PORT handle | HEALTH | status=passed",
                "mini-kv-infojson | mini-kv-operator | TCP command | MINIKV_HOST/MINIKV_PORT handle | INFOJSON | status=passed",
                "mini-kv-statsjson | mini-kv-operator | TCP command | MINIKV_HOST/MINIKV_PORT handle | STATSJSON | status=passed"),
            section(
                "Gate Checks",
                "gate-check-count=20",
                "read-target: java-health-read-target-passed=Java health read target passed; java-ops-overview-read-target-passed=Java ops overview read target passed; mini-kv-health-read-target-passed=mini-kv HEALTH passed; mini-kv-infojson-read-target-passed=mini-kv INFOJSON passed; mini-kv-statsjson-read-target-passed=mini-kv STATSJSON passed",
                "runtime-boundary: upstream-actions-disabled=UPSTREAM_ACTIONS_ENABLED=false; credential-value-not-read=credential handles only; raw-endpoint-url-not-resolved=endpoint handles only; managed-audit-http-not-called=managed audit remains disabled; runtime-shell-not-called=runtime shell remains disabled",
                "archive: json-evidence-archived=Node v367 JSON evidence present; markdown-evidence-archived=Node v367 Markdown evidence present; summary-evidence-archived=Node v367 summary present; screenshot-evidence-archived=Node v367 screenshot present; walkthrough-evidence-archived=Node v367 walkthrough present",
                "lineage: v349-smoke-lane-reused=v367 reused v349 smoke lane; v365-regular-gate-consumed=v367 consumed v365 regular gate; v366-read-window-decision-honored=v367 honored v366 read-window decision; read-targets-five-of-five=5/5 read targets passed; gate-checks-twenty-of-twenty=20/20 checks passed"),
            section(
                "Boundary Rules",
                "boundary-rule-count=10",
                "no-write-routing | route-owner | write routing | allowed=false",
                "no-active-shard-router | shard-owner | active shard router | allowed=false",
                "no-credential-value | security-owner | credential value | allowed=false",
                "no-raw-endpoint-url | security-owner | raw endpoint URL resolution | allowed=false",
                "no-managed-audit-connection | audit-owner | managed audit HTTP/TCP | allowed=false",
                "no-deployment-rollback | release-owner | deployment or rollback | allowed=false",
                "no-java-autostart | java-operator | Java autostart | allowed=false",
                "no-mini-kv-autostart | mini-kv-operator | mini-kv autostart | allowed=false",
                "no-mini-kv-write-admin | mini-kv-operator | mini-kv write/admin command | allowed=false",
                "no-java-ledger-or-sql-write | java-operator | Java ledger/schema/SQL write | allowed=false"),
            section(
                "CI Batches",
                "ci-batch-count=4",
                "1. focused-registry-tests | focused | new registry service and catalog tests",
                "2. grouped-route-tests | grouped | controller and route evidence tests",
                "3. build-validation | build | Maven compile and non-Docker regression",
                "4. read-only-smoke | smoke | read-only gate output smoke"),
            section(
                "Archive Requirements",
                "archive-requirement-count=6",
                "v367-json | Node v367 | 5/5 read target JSON evidence | required=true",
                "v367-markdown | Node v367 | operator-readable gate markdown | required=true",
                "v367-summary | Node v367 | 20/20 check summary | required=true",
                "v367-screenshot | Node v367 | read window execution screenshot | required=true",
                "v367-walkthrough | Node v367 | operator walkthrough transcript | required=true",
                "v367-gate-manifest | Java v1312 | Java read-only registry manifest | required=true"),
            section(
                "Operator Handoff",
                "operator-handoff-count=5",
                "confirm-external-read-window | release-operator | Confirm Java and mini-kv were started outside Node before read probes.",
                "keep-actions-disabled | release-operator | Keep UPSTREAM_ACTIONS_ENABLED=false while read probes are enabled.",
                "run-focused-grouped-build-smoke | ci-operator | Run focused tests, grouped route tests, build validation, then read-only smoke.",
                "archive-read-target-and-check-results | evidence-operator | Archive 5/5 read target results and 20/20 gate check results.",
                "stop-on-invalid-read-contract | release-operator | If invalid-read-contract appears, stop and request Java/mini-kv read-only fixes."));
  }

  @Test
  void preservesArchiveReport() {
    var response = ArchiveTestData.registry();

    assertThat(
            response.markdownSections().stream()
                .map(section -> section(section.heading(), section.lines()))
                .toList())
        .containsExactly(
            section(
                "Source Registry",
                "source-registry-count=1",
                "Java v1312 | /api/v1/ops/shard-readiness/minimal-read-only-gate-execution-registry | Node v367 | status=passed"),
            section(
                "Archive Artifacts",
                "artifact-verification-count=6",
                "v367-json | Node v367 | 5/5 read target JSON evidence | status=passed",
                "v367-markdown | Node v367 | operator-readable gate markdown | status=passed",
                "v367-summary | Node v367 | 20/20 check summary | status=passed",
                "v367-screenshot | Node v367 | read window execution screenshot | status=passed",
                "v367-walkthrough | Node v367 | operator walkthrough transcript | status=passed",
                "v367-gate-manifest | Java v1312 | Java read-only registry manifest | status=passed"),
            section(
                "Read Target Verification",
                "read-target-verification-count=5",
                "java-health | GET /actuator/health | status=passed",
                "java-ops-overview | GET /api/v1/ops/overview | status=passed",
                "mini-kv-health | HEALTH | status=passed",
                "mini-kv-infojson | INFOJSON | status=passed",
                "mini-kv-statsjson | STATSJSON | status=passed"),
            section(
                "Gate Check Verification",
                "gate-check-verification-count=20",
                "read-target: java-health-read-target-passed=passed; java-ops-overview-read-target-passed=passed; mini-kv-health-read-target-passed=passed; mini-kv-infojson-read-target-passed=passed; mini-kv-statsjson-read-target-passed=passed",
                "runtime-boundary: upstream-actions-disabled=passed; credential-value-not-read=passed; raw-endpoint-url-not-resolved=passed; managed-audit-http-not-called=passed; runtime-shell-not-called=passed",
                "archive: json-evidence-archived=passed; markdown-evidence-archived=passed; summary-evidence-archived=passed; screenshot-evidence-archived=passed; walkthrough-evidence-archived=passed",
                "lineage: v349-smoke-lane-reused=passed; v365-regular-gate-consumed=passed; v366-read-window-decision-honored=passed; read-targets-five-of-five=passed; gate-checks-twenty-of-twenty=passed"),
            section(
                "Boundary Verification",
                "boundary-verification-count=10",
                "no-write-routing | write routing | denied=true | status=passed",
                "no-active-shard-router | active shard router | denied=true | status=passed",
                "no-credential-value | credential value | denied=true | status=passed",
                "no-raw-endpoint-url | raw endpoint URL resolution | denied=true | status=passed",
                "no-managed-audit-connection | managed audit HTTP/TCP | denied=true | status=passed",
                "no-deployment-rollback | deployment or rollback | denied=true | status=passed",
                "no-java-autostart | Java autostart | denied=true | status=passed",
                "no-mini-kv-autostart | mini-kv autostart | denied=true | status=passed",
                "no-mini-kv-write-admin | mini-kv write/admin command | denied=true | status=passed",
                "no-java-ledger-or-sql-write | Java ledger/schema/SQL write | denied=true | status=passed"),
            section(
                "CI Handoff Scorecard",
                "ci-batch-verification-count=4",
                "operator-handoff-verification-count=5",
                "scorecard-entry-count=7",
                "source-registry=1/1",
                "archive-artifacts=6/6",
                "read-targets=5/5",
                "gate-checks=20/20",
                "boundary-denials=10/10",
                "ci-batches=4/4",
                "operator-handoffs=5/5"));
  }

  private static Section section(String heading, String... lines) {
    return new Section(heading, List.of(lines));
  }

  private static Section section(String heading, List<String> lines) {
    return new Section(heading, lines);
  }

  private record Section(String heading, List<String> lines) {}
}
