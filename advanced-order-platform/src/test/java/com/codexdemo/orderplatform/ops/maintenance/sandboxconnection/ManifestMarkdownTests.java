package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownOracle;
import org.junit.jupiter.api.Test;

class ManifestMarkdownTests {

  @Test
  void freezesCompleteManifestReport() {
    var sections = ManifestTestData.manifest().markdownSections();

    assertThat(sections)
        .extracting(
            OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                    .MarkdownSection
                ::heading)
        .containsExactly(
            "Source Receipt",
            "Split Modules",
            "Evidence References",
            "Precheck Fields",
            "Boundary Guards",
            "Code Health Gates",
            "Verification Gates",
            "Handoff Notes");
    assertThat(sections)
        .extracting(section -> section.lines().size())
        .containsExactly(1, 12, 5, 7, 17, 6, 10, 4);
    assertThat(
            MarkdownOracle.sha256(
                sections,
                OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                        .MarkdownSection
                    ::heading,
                OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                        .MarkdownSection
                    ::lines))
        .isEqualTo("bfe109f24df2475a13c61621fd81a4732b68241cf27edfe6563299c7902976fd");
  }
}
