package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.CiGate;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.MarkdownSection;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReportMarkdownTests {

  private static final List<MarkdownSection> EXPECTED_REPORT =
      List.of(
          section(
              "Source Closeout",
              "- release-acceptance-route-path-split-closeout Java v1579 status=passed owner=route-path-split-sustainment"),
          section(
              "Ownership Rules",
              "- stable-route-delegate owner=OpsShardReadinessRoutePaths landing=OpsShardReadinessReleaseAcceptanceRoutePaths enforced=true",
              "- response-contract owner=OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse landing=response record enforced=true",
              "- catalog-ownership owner=sustainment catalog classes landing=catalog package peers enforced=true",
              "- renderer-ownership owner=sustainment renderer classes landing=renderer package peers enforced=true",
              "- controller-surface owner=OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentController landing=controller enforced=true",
              "- test-ownership owner=sustainment tests landing=test suite enforced=true"),
          section(
              "Drift Guards",
              "- source-closeout-version signal=Java v1579 expected=Java v1579 locked=true",
              "- source-split-version signal=Java v1570 expected=Java v1570 locked=true",
              "- route-path-count signal=11 expected=11 locked=true",
              "- compatibility-check-count signal=11 expected=11 locked=true",
              "- closeout-item-count signal=6 expected=6 locked=true",
              "- parallel-node-plan signal=Node v1867-v1878 expected=Node v1867-v1878 locked=true"),
          section(
              "Boundary Guards",
              "- write-routing locked=true evidence=route constants only; no command path is created",
              "- active-shard-router locked=true evidence=split records readiness route names but does not route traffic",
              "- credential-value-read locked=true evidence=no credential handle or credential value is dereferenced",
              "- raw-endpoint-resolution locked=true evidence=only stable relative ops paths are compared",
              "- managed-audit-connection locked=true evidence=no HTTP/TCP client is built by this registry",
              "- deployment-rollback locked=true evidence=route-path split does not emit deployment or rollback actions",
              "- sibling-autostart locked=true evidence=Node v1846 parallel work does not authorize Java or mini-kv startup"),
          section(
              "CI Gates",
              "- focused-sustainment-tests scope=new sustainment registry required=true",
              "- related-route-path-split-tests scope=split, closeout, and sustainment route path evidence required=true",
              "- full-java-regression scope=advanced-order-platform required=true",
              "- git-diff-whitespace-check scope=source tree required=true",
              "- remote-ci-confirmation scope=GitHub Actions after push required=true"),
          section(
              "Consumer Handoffs",
              "- future-release-acceptance-services use=extend catalogs before adding a new endpoint ready=true",
              "- ops-reviewer use=read-only checklist ready=true",
              "- ci-maintainer use=test budget and CI traceability ready=true",
              "- archive-curator use=release evidence package ready=true",
              "- route-owner use=stable public route surface ready=true"),
          section(
              "Sustainment Scorecard",
              "- source-closeout passed=true detail=source closeout remains passed",
              "- ownership passed=true detail=component owners and landing zones are explicit",
              "- drift passed=true detail=source versions and counts are pinned",
              "- boundaries passed=true detail=runtime, credential, endpoint, audit, and deployment boundaries remain closed",
              "- ci passed=true detail=focused, related, full, diff, and remote CI gates are required",
              "- consumers passed=true detail=downstream consumers have explicit handoff rules",
              "- runtime-off passed=true detail=sustainment is read-only and does not execute runtime work",
              "- split-maintainability passed=true detail=catalogs and renderers keep future work out of a giant file"));

  @Test
  void reportMatchesLegacyOutput() {
    var response = SustainmentTestData.registry();

    assertThat(response.markdownSections()).containsExactlyElementsOf(EXPECTED_REPORT);
    assertThat(response.markdownSections()).hasSize(7);
    assertThat(
            response.markdownSections().stream().mapToInt(section -> section.lines().size()).sum())
        .isEqualTo(38);
  }

  @Test
  void checksDescribeTheSustainmentContract() {
    var checks = SustainmentTestData.registry().checks();

    assertThat(checks).hasSize(30);
    assertThat(checks)
        .contains(
            "release-acceptance-route-path-split-sustainment-source-plan-Node v1878",
            "release-acceptance-route-path-split-sustainment-node-parallel-plan-Node v1867-v1878",
            "release-acceptance-route-path-split-sustainment-source-closeout-version-Java v1579",
            "release-acceptance-route-path-split-sustainment-source-split-version-Java v1570",
            "release-acceptance-route-path-split-sustainment-ownership-rule-count-6",
            "release-acceptance-route-path-split-sustainment-no-runtime-execution",
            "release-acceptance-route-path-split-sustainment-no-node-or-minikv-auto-start");
  }

  @Test
  void ciCommandsStayOutOfRenderedStatusLines() {
    var response = SustainmentTestData.registry();

    assertThat(response.ciGates())
        .extracting(CiGate::gate)
        .containsExactly(
            "focused-sustainment-tests",
            "related-route-path-split-tests",
            "full-java-regression",
            "git-diff-whitespace-check",
            "remote-ci-confirmation");
    assertThat(response.markdownSections().get(4).lines())
        .allSatisfy(line -> assertThat(line).contains("required=true"))
        .noneSatisfy(line -> assertThat(line).contains("mvnw"));
  }

  private static MarkdownSection section(String heading, String... lines) {
    return new MarkdownSection(heading, List.of(lines));
  }
}
