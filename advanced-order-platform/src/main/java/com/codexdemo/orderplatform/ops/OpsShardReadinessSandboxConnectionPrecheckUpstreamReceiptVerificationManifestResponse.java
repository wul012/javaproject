package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String sourcePlan,
        String nodeOwnerPlan,
        String frozenJavaEvidenceVersion,
        String frozenMiniKvEvidenceVersion,
        String sourcePrecheckPacketReceiptVersion,
        String sourcePrecheckPacketReceiptSchemaVersion,
        String endpoint,
        String profile,
        int sourceReceiptCount,
        int splitModuleCount,
        int evidenceReferenceCount,
        int precheckFieldCount,
        int boundaryGuardCount,
        int codeHealthGateCount,
        int verificationGateCount,
        int handoffNoteCount,
        int markdownSectionCount,
        List<SourceReceipt> sourceReceipts,
        List<SplitModule> splitModules,
        List<EvidenceReference> evidenceReferences,
        List<PrecheckField> precheckFields,
        List<BoundaryGuard> boundaryGuards,
        List<CodeHealthGate> codeHealthGates,
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
            String consumedNodeEndpoint,
            String consumedNodeState,
            String nextNodeVersion,
            String nextNodeProfile,
            boolean nodeMayConsume,
            boolean readyForReceiptVerification,
            boolean readyForManagedAuditSandboxAdapterConnection,
            boolean readyForProductionAudit,
            boolean nodeMayTreatAsProductionAuditRecord,
            List<String> warnings,
            List<String> nodeVerificationActions
    ) {
    }

    public record SplitModule(
            String version,
            String moduleName,
            String responsibility,
            boolean publicContractPreserved,
            boolean consumesFrozenJavaV99Only,
            boolean runtimeExecutionAllowed
    ) {
    }

    public record EvidenceReference(
            String id,
            String source,
            String version,
            String profile,
            String role,
            boolean accepted,
            boolean frozen
    ) {
    }

    public record PrecheckField(
            String id,
            String fieldName,
            String value,
            boolean echoed,
            boolean carriesCredentialValue
    ) {
    }

    public record BoundaryGuard(
            String name,
            String evidence,
            boolean expectedValue,
            boolean actualValue,
            boolean passed
    ) {
    }

    public record CodeHealthGate(
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
