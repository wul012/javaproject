package com.codexdemo.orderplatform.ops.maintenance.operatorcidossier;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DossierCatalogTests {

  @Test
  void mirrorsMarkdownAsDigests() {
    var response = DossierTestData.registry();

    assertThat(response.sectionDigestCount()).isEqualTo(9);
    assertThat(response.passedSectionDigestCount()).isEqualTo(9);
    assertThat(response.sectionDigests())
        .extracting(entry -> entry.heading())
        .containsExactly(
            "Source Digest",
            "Manifest",
            "Consumer Audiences",
            "Package Sections",
            "Acceptance Criteria",
            "CI Matrix",
            "Boundary Locks",
            "Handoff Checklist",
            "Scorecard")
        .allSatisfy(heading -> assertThat(heading).isNotBlank());
    assertThat(response.sectionDigests())
        .allSatisfy(
            section -> {
              assertThat(section.lineCount()).isPositive();
              assertThat(section.status()).isEqualTo("passed");
            });
  }

  @Test
  void routesAudiencesAndCiLanes() {
    var response = DossierTestData.registry();

    assertThat(response.audienceRouteCount()).isEqualTo(4);
    assertThat(response.readyAudienceRouteCount()).isEqualTo(4);
    assertThat(response.audienceRoutes())
        .allSatisfy(
            route -> {
              assertThat(route.ready()).isTrue();
              assertThat(route.status()).isEqualTo("passed");
              assertThat(route.reviewerLane()).isNotBlank();
            });
    assertThat(response.ciLaneCount()).isEqualTo(5);
    assertThat(response.readOnlyCiLaneCount()).isEqualTo(5);
    assertThat(response.ciLanes())
        .extracting(entry -> entry.commandFamily())
        .containsExactly("focused", "focused", "grouped", "build", "smoke");
    assertThat(response.ciLanes())
        .extracting(entry -> entry.replayGroup())
        .contains(
            "focused-preflight",
            "grouped-non-docker-regression",
            "package-build",
            "read-only-smoke");
  }

  @Test
  void preservesGatesAndBoundaries() {
    var response = DossierTestData.registry();

    assertThat(response.acceptanceGateCount()).isEqualTo(5);
    assertThat(response.passedAcceptanceGateCount()).isEqualTo(5);
    assertThat(response.acceptanceGates())
        .allSatisfy(
            gate -> {
              assertThat(gate.passed()).isTrue();
              assertThat(gate.verifyingArtifact()).endsWith("-verification-dossier");
              assertThat(gate.status()).isEqualTo("passed");
            });
    assertThat(response.boundaryAuditCount()).isEqualTo(8);
    assertThat(response.lockedBoundaryAuditCount()).isEqualTo(8);
    assertThat(response.boundaryAudits())
        .extracting(entry -> entry.code())
        .contains("no-java-autostart", "no-mini-kv-autostart", "no-write-routing");
    assertThat(response.boundaryAudits())
        .allSatisfy(
            audit -> {
              assertThat(audit.locked()).isTrue();
              assertThat(audit.auditEvidence()).startsWith("consumer-package-boundary-lock:");
              assertThat(audit.status()).isEqualTo("passed");
            });
  }

  @Test
  void carriesChecklistAndReceipts() {
    var response = DossierTestData.registry();

    assertThat(response.releaseChecklistCount()).isEqualTo(5);
    assertThat(response.readyReleaseChecklistCount()).isEqualTo(5);
    assertThat(response.releaseChecklist())
        .extracting(entry -> entry.item())
        .containsExactly(
            "read-source-digest",
            "confirm-boundary-locks",
            "run-focused-first",
            "preserve-read-only-env",
            "archive-ci-conclusion");
    assertThat(response.handoffReceiptCount()).isEqualTo(4);
    assertThat(response.readyHandoffReceiptCount()).isEqualTo(4);
    assertThat(response.handoffReceipts())
        .extracting(entry -> entry.receiver())
        .containsExactly(
            "operator-ci-handoff-owner",
            "node-v368-archive-verifier",
            "node-v369-operator-ci",
            "java-read-only-boundary-owner");
  }
}
