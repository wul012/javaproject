package com.codexdemo.orderplatform.ops.maintenance.credentialresolver;

import static com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownSections.mapped;

import com.codexdemo.orderplatform.ops.maintenance.credentialresolver.OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.EvidenceRequirement;
import com.codexdemo.orderplatform.ops.maintenance.credentialresolver.OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.FakeHarnessBoundary;
import com.codexdemo.orderplatform.ops.maintenance.credentialresolver.OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.credentialresolver.OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.RuntimeGuard;
import com.codexdemo.orderplatform.ops.maintenance.credentialresolver.OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.SourceReceipt;
import com.codexdemo.orderplatform.ops.maintenance.credentialresolver.OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.VerificationGate;
import java.util.List;

final class ArchiveRenderer {

  private ArchiveRenderer() {}

  static List<MarkdownSection> render(
      List<SourceReceipt> sourceReceipts,
      List<EvidenceRequirement> javaRequirements,
      List<EvidenceRequirement> miniKvRequirements,
      List<FakeHarnessBoundary> fakeHarnessBoundaries,
      List<RuntimeGuard> runtimeGuards,
      List<VerificationGate> verificationGates) {
    return List.of(
        mapped("Source Receipt", sourceReceipts, ArchiveRenderer::sourceLine, MarkdownSection::new),
        mapped(
            "Java Requirements",
            javaRequirements,
            ArchiveRenderer::requirementLine,
            MarkdownSection::new),
        mapped(
            "mini-kv Requirements",
            miniKvRequirements,
            ArchiveRenderer::requirementLine,
            MarkdownSection::new),
        mapped(
            "Fake Harness Boundary",
            fakeHarnessBoundaries,
            ArchiveRenderer::boundaryLine,
            MarkdownSection::new),
        mapped("Runtime Guards", runtimeGuards, ArchiveRenderer::guardLine, MarkdownSection::new),
        mapped(
            "Verification Gates",
            verificationGates,
            ArchiveRenderer::gateLine,
            MarkdownSection::new));
  }

  private static String sourceLine(SourceReceipt source) {
    return "- "
        + source.receiptName()
        + " "
        + source.receiptVersion()
        + " consumes "
        + source.consumedNodeVersion();
  }

  private static String requirementLine(EvidenceRequirement requirement) {
    return "- " + requirement.id() + " " + requirement.expectedVersion();
  }

  private static String boundaryLine(FakeHarnessBoundary boundary) {
    return "- " + boundary.code() + " " + boundary.status();
  }

  private static String guardLine(RuntimeGuard guard) {
    return "- " + guard.name() + " passed=" + guard.passed();
  }

  private static String gateLine(VerificationGate gate) {
    return "- " + gate.name() + " passed=" + gate.passed();
  }
}
