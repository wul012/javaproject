package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownOracle;
import org.junit.jupiter.api.Test;

class DossierMarkdownTests {

  @Test
  void freezesCompleteDossierReport() {
    var sections = DossierTestData.dossier().markdownSections();

    assertThat(sections)
        .extracting(
            OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.MarkdownSection
                ::heading)
        .containsExactly(
            "Source Receipt",
            "Context Fields",
            "Precondition Evidence",
            "Boundaries",
            "Execution Guards",
            "Warnings",
            "Downstream Intake",
            "Verification Gates",
            "Handoff Notes");
    assertThat(sections)
        .extracting(section -> section.lines().size())
        .containsExactly(1, 3, 6, 5, 12, 4, 5, 10, 4);
    assertThat(
            MarkdownOracle.sha256(
                sections,
                OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                        .MarkdownSection
                    ::heading,
                OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                        .MarkdownSection
                    ::lines))
        .isEqualTo("6343820c1f3bda7b2574e17515fa949713cd8ebfe819797e727ca320fce57aff");
  }
}
