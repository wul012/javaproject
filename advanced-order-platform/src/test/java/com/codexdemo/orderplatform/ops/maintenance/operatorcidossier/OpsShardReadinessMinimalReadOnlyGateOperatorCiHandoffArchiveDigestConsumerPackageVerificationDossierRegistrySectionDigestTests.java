package com.codexdemo.orderplatform.ops.maintenance.operatorcidossier;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistrySectionDigestTests {

  @Test
  void mirrorsConsumerPackageMarkdownIntoSectionDigests() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryTestSupport
            .registry();

    assertThat(response.sectionDigestCount()).isEqualTo(9);
    assertThat(response.passedSectionDigestCount()).isEqualTo(9);
    assertThat(response.sectionDigests())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                    .SectionDigest
                ::heading)
        .containsExactly(
            "Source Digest",
            "Manifest",
            "Consumer Audiences",
            "Package Sections",
            "Acceptance Criteria",
            "CI Matrix",
            "Boundary Locks",
            "Handoff Checklist",
            "Scorecard");
    assertThat(response.sectionDigests())
        .allSatisfy(
            section -> {
              assertThat(section.lineCount()).isPositive();
              assertThat(section.status()).isEqualTo("passed");
            });
  }
}
