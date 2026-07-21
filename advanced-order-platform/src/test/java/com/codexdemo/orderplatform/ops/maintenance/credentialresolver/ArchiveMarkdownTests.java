package com.codexdemo.orderplatform.ops.maintenance.credentialresolver;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownOracle;
import org.junit.jupiter.api.Test;

class ArchiveMarkdownTests {

  @Test
  void freezesCompleteArchiveReport() {
    var sections = ArchiveTestData.archive().markdownSections();

    assertThat(sections)
        .extracting(
            OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
                    .MarkdownSection
                ::heading)
        .containsExactly(
            "Source Receipt",
            "Java Requirements",
            "mini-kv Requirements",
            "Fake Harness Boundary",
            "Runtime Guards",
            "Verification Gates");
    assertThat(sections)
        .extracting(section -> section.lines().size())
        .containsExactly(1, 4, 4, 1, 10, 8);
    assertThat(
            MarkdownOracle.sha256(
                sections,
                OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
                        .MarkdownSection
                    ::heading,
                OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
                        .MarkdownSection
                    ::lines))
        .isEqualTo("f9f498cb1e6cb70f21eabe5b6d5b9c2459df84193c57d48df328fece62ac6165");
  }
}
