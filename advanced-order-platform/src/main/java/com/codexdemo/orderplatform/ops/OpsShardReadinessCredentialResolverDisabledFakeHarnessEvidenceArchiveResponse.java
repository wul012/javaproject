package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String sourcePlan,
        String nodeOwnerPlan,
        String sourceRehearsalVersion,
        String sourceRehearsalSchemaVersion,
        String endpoint,
        String profile,
        int sourceReceiptCount,
        int javaRequirementCount,
        int miniKvRequirementCount,
        int fakeHarnessBoundaryCount,
        int runtimeGuardCount,
        int verificationGateCount,
        int handoffNoteCount,
        int markdownSectionCount,
        List<SourceReceipt> sourceReceipts,
        List<EvidenceRequirement> javaRequirements,
        List<EvidenceRequirement> miniKvRequirements,
        List<FakeHarnessBoundary> fakeHarnessBoundaries,
        List<RuntimeGuard> runtimeGuards,
        List<VerificationGate> verificationGates,
        List<HandoffNote> handoffNotes,
        List<MarkdownSection> markdownSections,
        List<String> checks,
        String status
) {

    public record SourceReceipt(
            String receiptName,
            String receiptVersion,
            String receiptDigest,
            String consumedNodeVersion,
            String consumedNodeProfile,
            String consumedNodeState,
            String sourceSpan,
            String nextJavaEchoVersion,
            String nextMiniKvReceiptVersion,
            String nextNodeVerificationVersion,
            String fakeHarnessDeferredUntil,
            boolean nodeVerificationReady,
            boolean siblingEchoReady,
            boolean fakeHarnessPrecheckReady,
            boolean managedAuditResolverImplementationReady
    ) {
    }

    public record EvidenceRequirement(
            String id,
            String project,
            String expectedVersion,
            String requirement,
            boolean mustRemainReadOnly,
            boolean mustNotConnectManagedAudit,
            boolean mustNotReadCredentialValue,
            boolean mustNotParseRawEndpointUrl,
            boolean mustNotWriteLedgerOrState
    ) {
    }

    public record FakeHarnessBoundary(
            String code,
            String sourceBoundary,
            String title,
            String owner,
            String status,
            List<String> allowedInputs,
            List<String> allowedOutputs,
            List<String> prohibitedActions,
            List<String> requiredArtifacts,
            String verificationRule,
            boolean archiveReady
    ) {
    }

    public record RuntimeGuard(
            String name,
            String evidence,
            boolean passed
    ) {
    }

    public record VerificationGate(
            String name,
            String evidence,
            boolean passed
    ) {
    }

    public record HandoffNote(
            String audience,
            String note,
            boolean ready
    ) {
    }

    public record MarkdownSection(
            String heading,
            List<String> lines
    ) {
    }
}
