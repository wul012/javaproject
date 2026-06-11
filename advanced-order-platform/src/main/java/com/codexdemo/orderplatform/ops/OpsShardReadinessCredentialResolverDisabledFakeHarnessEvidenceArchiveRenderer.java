package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveRenderer {

    private OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveRenderer() {
    }

    static List<OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.MarkdownSection> render(
            List<OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.SourceReceipt>
                    sourceReceipts,
            List<OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.EvidenceRequirement>
                    javaRequirements,
            List<OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.EvidenceRequirement>
                    miniKvRequirements,
            List<OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.FakeHarnessBoundary>
                    fakeHarnessBoundaries,
            List<OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.RuntimeGuard>
                    runtimeGuards,
            List<OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.VerificationGate>
                    verificationGates
    ) {
        return List.of(
                section("Source Receipt", sourceReceipts.stream()
                        .map(source -> "- " + source.receiptName() + " " + source.receiptVersion()
                                + " consumes " + source.consumedNodeVersion())
                        .toList()),
                section("Java Requirements", javaRequirements.stream()
                        .map(requirement -> "- " + requirement.id() + " " + requirement.expectedVersion())
                        .toList()),
                section("mini-kv Requirements", miniKvRequirements.stream()
                        .map(requirement -> "- " + requirement.id() + " " + requirement.expectedVersion())
                        .toList()),
                section("Fake Harness Boundary", fakeHarnessBoundaries.stream()
                        .map(boundary -> "- " + boundary.code() + " " + boundary.status())
                        .toList()),
                section("Runtime Guards", runtimeGuards.stream()
                        .map(guard -> "- " + guard.name() + " passed=" + guard.passed())
                        .toList()),
                section("Verification Gates", verificationGates.stream()
                        .map(gate -> "- " + gate.name() + " passed=" + gate.passed())
                        .toList())
        );
    }

    private static OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.MarkdownSection
            section(String heading, List<String> lines) {
        return new OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
                .MarkdownSection(heading, lines);
    }
}
