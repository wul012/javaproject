package com.codexdemo.orderplatform.ops.maintenance.operatorcidossier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

class DossierChecksTests {

  @Test
  void countsAndChecksStayConsistent() {
    var response = DossierTestData.registry();

    assertThat(
            List.of(
                response.sourcePackageSnapshotCount(),
                response.provenanceEntryCount(),
                response.sectionDigestCount(),
                response.audienceRouteCount(),
                response.ciLaneCount(),
                response.acceptanceGateCount(),
                response.boundaryAuditCount(),
                response.releaseChecklistCount(),
                response.handoffReceiptCount(),
                response.scorecardEntryCount(),
                response.markdownSectionCount(),
                response.checks().size()))
        .containsExactly(1, 6, 9, 4, 5, 5, 8, 5, 4, 10, 10, 34);
    assertThat(response.checks())
        .contains(
            "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-no-upstream-autostart",
            "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-no-write-routing",
            "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-no-secret-value",
            "minimal-read-only-gate-operator-ci-handoff-consumer-package-dossier-no-runtime-execution");
    assertThat(response.status()).isEqualTo("passed");
  }

  @Test
  void responseCollectionsAreImmutable() {
    var response = DossierTestData.registry();

    assertThatThrownBy(() -> response.checks().add("late-mutation"))
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.sourcePackageSnapshots().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(
            () ->
                response
                    .markdownSections()
                    .add(
                        new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                            .MarkdownSection("late", List.of("mutation"))))
        .isInstanceOf(UnsupportedOperationException.class);
  }
}
