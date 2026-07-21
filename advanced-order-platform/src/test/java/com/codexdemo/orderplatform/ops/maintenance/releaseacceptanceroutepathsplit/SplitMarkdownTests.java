package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.MarkdownSection;
import java.util.List;
import org.junit.jupiter.api.Test;

class SplitMarkdownTests {

  private static final List<MarkdownSection> EXPECTED_REPORT =
      List.of(
          section(
              "Source Handoff",
              "- release-acceptance-archive-verification-handoff Java v1547 status=passed"),
          section(
              "Route Path Split",
              "- MINIMAL_READ_ONLY_GATE_EXECUTION_REGISTRY /minimal-read-only-gate-execution-registry compatible=true",
              "- MINIMAL_READ_ONLY_GATE_EXECUTION_ARCHIVE_VERIFICATION_REGISTRY /minimal-read-only-gate-execution-archive-verification-registry compatible=true",
              "- MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_REGISTRY /minimal-read-only-gate-operator-ci-handoff-registry compatible=true",
              "- MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_VERIFICATION_REGISTRY /minimal-read-only-gate-operator-ci-handoff-archive-verification-registry compatible=true",
              "- MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_REGISTRY /minimal-read-only-gate-operator-ci-handoff-archive-digest-registry compatible=true",
              "- MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_REGISTRY /minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-registry compatible=true",
              "- MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_REGISTRY /minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-registry compatible=true",
              "- MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_REGISTRY /minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-registry compatible=true",
              "- MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_ARCHIVE_REGISTRY /minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-archive-registry compatible=true",
              "- RELEASE_ACCEPTANCE_ARCHIVE_VERIFICATION_HANDOFF_REGISTRY /release-acceptance-archive-verification-handoff-registry compatible=true",
              "- RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_REGISTRY /release-acceptance-route-path-split-registry compatible=true"),
          section(
              "Compatibility Checks",
              "- MINIMAL_READ_ONLY_GATE_EXECUTION_REGISTRY matched=true",
              "- MINIMAL_READ_ONLY_GATE_EXECUTION_ARCHIVE_VERIFICATION_REGISTRY matched=true",
              "- MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_REGISTRY matched=true",
              "- MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_VERIFICATION_REGISTRY matched=true",
              "- MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_REGISTRY matched=true",
              "- MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_REGISTRY matched=true",
              "- MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_REGISTRY matched=true",
              "- MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_REGISTRY matched=true",
              "- MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_ARCHIVE_REGISTRY matched=true",
              "- RELEASE_ACCEPTANCE_ARCHIVE_VERIFICATION_HANDOFF_REGISTRY matched=true",
              "- RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_REGISTRY matched=true"),
          section(
              "Boundary Guards",
              "- write-routing locked=true",
              "- active-shard-router locked=true",
              "- credential-value-read locked=true",
              "- raw-endpoint-resolution locked=true",
              "- managed-audit-connection locked=true",
              "- deployment-rollback locked=true",
              "- sibling-autostart locked=true"),
          section(
              "Consumer Handoffs",
              "- release-acceptance-archive-verification-handoff-service status=passed",
              "- release-acceptance-archive-verification-handoff-controller status=passed",
              "- route-path-compatibility-tests status=passed",
              "- future-release-acceptance-services status=passed",
              "- node-v1846-parallel-review status=passed"),
          section(
              "Scorecard",
              "- source-handoff passed=true detail=Java v1547 handoff remains the source evidence",
              "- stable-barrel passed=true detail=OpsShardReadinessRoutePaths keeps every migrated constant",
              "- narrow-module passed=true detail=release-acceptance route ownership has a dedicated module",
              "- compatibility passed=true detail=stable and split route values match one-for-one",
              "- consumer-migration passed=true detail=new consumers know whether to use the narrow module or legacy barrel",
              "- boundary-lock passed=true detail=runtime, credential, raw endpoint, audit, and deployment paths remain locked",
              "- node-v1846-parallelism passed=true detail=Java proceeds independently while Node owns its type-barrel split",
              "- maintainability passed=true detail=future release-acceptance route constants now have a local owner"));

  @Test
  void reportMatchesLegacyOutput() {
    var response = SplitTestData.registry();

    assertThat(response.markdownSections()).containsExactlyElementsOf(EXPECTED_REPORT);
    assertThat(response.markdownSections()).hasSize(6);
    assertThat(
            response.markdownSections().stream().mapToInt(section -> section.lines().size()).sum())
        .isEqualTo(43);
  }

  @Test
  void checksDescribeTheSplitContract() {
    var checks = SplitTestData.registry().checks();

    assertThat(checks).hasSize(24);
    assertThat(checks)
        .contains(
            "release-acceptance-route-path-split-source-plan-Node v1846",
            "release-acceptance-route-path-split-source-handoff-version-Java v1547",
            "release-acceptance-route-path-split-route-count-11",
            "release-acceptance-route-path-split-compatible-route-count-11",
            "release-acceptance-route-path-split-stable-barrel-preserved",
            "release-acceptance-route-path-split-no-runtime-execution");
  }

  private static MarkdownSection section(String heading, String... lines) {
    return new MarkdownSection(heading, List.of(lines));
  }
}
