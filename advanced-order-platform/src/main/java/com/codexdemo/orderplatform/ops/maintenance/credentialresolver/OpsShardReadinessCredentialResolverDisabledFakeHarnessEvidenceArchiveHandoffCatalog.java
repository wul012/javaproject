package com.codexdemo.orderplatform.ops.maintenance.credentialresolver;

import java.util.List;

final class OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveHandoffCatalog {

  private OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveHandoffCatalog() {}

  static List<
          OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.HandoffNote>
      notes() {
    return List.of(
        note("node", "Consume this archive as frozen Java v121 / mini-kv v126 evidence only."),
        note(
            "java",
            "Keep the archive read-only; do not extend OpsEvidenceService for this handoff."),
        note(
            "operators",
            "Treat fake harness material as contract evidence, not executable runtime."),
        note("release", "Use Node v1953-v1967 as owner scope and keep sibling startup disabled."));
  }

  private static OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
          .HandoffNote
      note(String audience, String note) {
    return new OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
        .HandoffNote(audience, note, true);
  }
}
