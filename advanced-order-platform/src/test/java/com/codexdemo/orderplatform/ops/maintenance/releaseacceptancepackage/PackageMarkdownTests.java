package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class PackageMarkdownTests {

  @Test
  void preservesEveryLegacyReportLine() {
    var response = PackageTestData.registry();

    assertThat(response.markdownSections())
        .containsExactly(
            reportSection(
                "Source Sustainment",
                "- release-acceptance-route-path-split-sustainment Java v1604 status=passed profile=java-shard-readiness-release-acceptance-route-path-split-sustainment.v1"),
            reportSection(
                "Version Lineage",
                "- route-path-split Java v1570 status=passed",
                "- route-path-split-closeout Java v1579 status=passed",
                "- route-path-split-sustainment Java v1604 status=passed"),
            reportSection(
                "Acceptance Decisions",
                "- accept-sustainment-registry owner=release-acceptance-maintainer accepted=true",
                "- freeze-stable-route-delegate owner=route-owner accepted=true",
                "- require-catalog-before-route owner=catalog-owner accepted=true",
                "- require-renderer-split owner=renderer-owner accepted=true",
                "- keep-runtime-disabled owner=ops-boundary-owner accepted=true",
                "- parallel-node-no-fresh-evidence owner=sibling-plan-owner accepted=true"),
            reportSection(
                "Archive Items",
                "- sustainment-response retention=release-evidence-bundle ready=true",
                "- version-tags-v1580-v1604 retention=version-lineage ready=true",
                "- boundary-lock-matrix retention=runtime-boundary-archive ready=true",
                "- ci-gate-ledger retention=ci-run-archive ready=true",
                "- consumer-handoff-rules retention=handoff-archive ready=true"),
            reportSection(
                "Review Checklist",
                "- release-reviewer checklist=status-and-counts passed=true",
                "- route-owner checklist=route-delegate passed=true",
                "- test-owner checklist=coverage passed=true",
                "- ci-owner checklist=ci-gates passed=true",
                "- archive-owner checklist=archive-items passed=true"),
            reportSection(
                "CI Evidence",
                "- focused-sustainment-tests result=required passed=true",
                "- related-route-path-split-tests result=required passed=true",
                "- full-java-regression result=required passed=true",
                "- git-diff-whitespace-check result=required passed=true",
                "- remote-ci-confirmation result=required passed=true"),
            reportSection(
                "Runtime Boundaries",
                "- write-routing policy=locked-from-sustainment locked=true",
                "- active-shard-router policy=locked-from-sustainment locked=true",
                "- credential-value-read policy=locked-from-sustainment locked=true",
                "- raw-endpoint-resolution policy=locked-from-sustainment locked=true",
                "- managed-audit-connection policy=locked-from-sustainment locked=true",
                "- deployment-rollback policy=locked-from-sustainment locked=true",
                "- sibling-autostart policy=locked-from-sustainment locked=true"),
            reportSection(
                "Next Change Rules",
                "- new-route-path landing=route-path catalog then release acceptance route group reviewer=route-owner ready=true",
                "- new-consumer landing=consumer catalog reviewer=handoff-owner ready=true",
                "- new-ci-gate landing=CI catalog and renderer reviewer=ci-owner ready=true",
                "- new-boundary landing=boundary catalog and runtime boundary package reviewer=ops-boundary-owner ready=true",
                "- source-plan-roll landing=support constants and source catalog reviewer=sibling-plan-owner ready=true",
                "- markdown-copy-change landing=section renderer for the affected concern reviewer=renderer-owner ready=true"),
            reportSection(
                "Acceptance Scorecard",
                "- source passed=true detail=sustainment source passed",
                "- lineage passed=true detail=split, closeout, and sustainment versions are linked",
                "- decisions passed=true detail=acceptance decisions are explicit",
                "- archive passed=true detail=archive items are ready",
                "- review passed=true detail=review checklist passed",
                "- ci passed=true detail=CI evidence remains required",
                "- runtime-boundaries passed=true detail=runtime boundaries are locked",
                "- next-change passed=true detail=future changes have landing zones",
                "- maintainability passed=true detail=acceptance package is split into focused catalogs and renderers"));
  }

  @Test
  void preservesEveryLegacyReceiptLine() {
    var response = ReceiptTestData.receipt();

    assertThat(response.markdownLines())
        .containsExactly(
            "- acceptance-package-passed status=accepted evidence=/api/v1/ops/shard-readiness/release-acceptance-route-path-split-sustainment-acceptance-package:passed",
            "- lineage-complete status=accepted evidence=lineage-count=3",
            "- decisions-accepted status=accepted evidence=decision-count=6",
            "- archive-ready status=accepted evidence=archive-items=5",
            "- ci-evidence-passed status=accepted evidence=ci-evidence=5",
            "- runtime-boundaries-locked status=accepted evidence=runtime-boundaries=7",
            "- next-change-rules-ready status=accepted evidence=next-change-rules=6");
  }

  @Test
  void preservesEveryLegacyArchiveIndexLine() {
    var response = ArchiveIndexTestData.index();

    assertThat(response.markdownSections())
        .containsExactly(
            indexSection(
                "Source Receipt",
                "- sustainment-acceptance-package-closeout-receipt Java v1637 status=passed"),
            indexSection(
                "Criteria Echoes",
                "- acceptance-package-passed status=accepted",
                "- lineage-complete status=accepted",
                "- decisions-accepted status=accepted",
                "- archive-ready status=accepted",
                "- ci-evidence-passed status=accepted",
                "- runtime-boundaries-locked status=accepted",
                "- next-change-rules-ready status=accepted"),
            indexSection(
                "Archive Items",
                "- closeout-receipt-response retention=release-acceptance-archive ready=true",
                "- accepted-criteria-ledger retention=criteria-retention ready=true",
                "- receipt-markdown-lines retention=markdown-retention ready=true",
                "- receipt-checks retention=check-retention ready=true",
                "- version-tags-v1635-v1637 retention=version-lineage ready=true"),
            indexSection(
                "Verification Gates",
                "- focused-archive-index-tests passed=true",
                "- related-route-path-split-tests passed=true",
                "- remote-ci-confirmation passed=true",
                "- runtime-execution-closed passed=true",
                "- sibling-startup-closed passed=true"),
            indexSection(
                "Handoff Notes",
                "- archive-curator ready=true",
                "- release-reviewer ready=true",
                "- route-owner ready=true",
                "- ci-maintainer ready=true"));
  }

  private static
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
          .MarkdownSection
      reportSection(String heading, String... lines) {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
        .MarkdownSection(heading, List.of(lines));
  }

  private static
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
          .MarkdownSection
      indexSection(String heading, String... lines) {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
        .MarkdownSection(heading, List.of(lines));
  }
}
