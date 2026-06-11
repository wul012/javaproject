package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v2002";
    static final String NODE_OWNER_PLAN = "Node v1983-v2002";
    static final String FROZEN_JAVA_EVIDENCE_VERSION = "Java v99";
    static final String FROZEN_MINI_KV_EVIDENCE_VERSION = "mini-kv v108";
    static final String PROFILE =
            "java-shard-readiness-sandbox-connection-precheck-upstream-receipt-verification-manifest.v1";
    static final int EXPECTED_SOURCE_RECEIPT_COUNT = 1;
    static final int EXPECTED_SPLIT_MODULE_COUNT = 12;
    static final int EXPECTED_EVIDENCE_REFERENCE_COUNT = 5;
    static final int EXPECTED_PRECHECK_FIELD_COUNT = 7;
    static final int EXPECTED_BOUNDARY_GUARD_COUNT = 17;
    static final int EXPECTED_CODE_HEALTH_GATE_COUNT = 6;
    static final int EXPECTED_VERIFICATION_GATE_COUNT = 10;
    static final int EXPECTED_HANDOFF_NOTE_COUNT = 4;
    static final int EXPECTED_MARKDOWN_SECTION_COUNT = 8;

    private OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSupport() {
    }

    static OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse response(
            String version,
            String endpoint,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.SourceReceipt>
                    sourceReceipts,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.SplitModule>
                    splitModules,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.EvidenceReference>
                    evidenceReferences,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.PrecheckField>
                    precheckFields,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.BoundaryGuard>
                    boundaryGuards,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.CodeHealthGate>
                    codeHealthGates,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.VerificationGate>
                    verificationGates,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.HandoffNote>
                    handoffNotes,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.MarkdownSection>
                    markdownSections
    ) {
        var copiedSourceReceipts = List.copyOf(sourceReceipts);
        var copiedSplitModules = List.copyOf(splitModules);
        var copiedEvidenceReferences = List.copyOf(evidenceReferences);
        var copiedPrecheckFields = List.copyOf(precheckFields);
        var copiedBoundaryGuards = List.copyOf(boundaryGuards);
        var copiedCodeHealthGates = List.copyOf(codeHealthGates);
        var copiedVerificationGates = List.copyOf(verificationGates);
        var copiedHandoffNotes = List.copyOf(handoffNotes);
        var copiedMarkdownSections = List.copyOf(markdownSections);
        var checks = checks(
                copiedSourceReceipts,
                copiedSplitModules,
                copiedEvidenceReferences,
                copiedPrecheckFields,
                copiedBoundaryGuards,
                copiedCodeHealthGates,
                copiedVerificationGates,
                copiedHandoffNotes,
                copiedMarkdownSections
        );
        return new OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse(
                PROJECT,
                version,
                true,
                false,
                SOURCE_PLAN,
                NODE_OWNER_PLAN,
                FROZEN_JAVA_EVIDENCE_VERSION,
                FROZEN_MINI_KV_EVIDENCE_VERSION,
                copiedSourceReceipts.stream()
                        .findFirst()
                        .map(OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                                .SourceReceipt::receiptVersion)
                        .orElse("missing"),
                OpsEvidenceService
                        .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_CONNECTION_PRECHECK_PACKET_ECHO_RECEIPT_SCHEMA_VERSION,
                endpoint,
                PROFILE,
                copiedSourceReceipts.size(),
                copiedSplitModules.size(),
                copiedEvidenceReferences.size(),
                copiedPrecheckFields.size(),
                copiedBoundaryGuards.size(),
                copiedCodeHealthGates.size(),
                copiedVerificationGates.size(),
                copiedHandoffNotes.size(),
                copiedMarkdownSections.size(),
                copiedSourceReceipts,
                copiedSplitModules,
                copiedEvidenceReferences,
                copiedPrecheckFields,
                copiedBoundaryGuards,
                copiedCodeHealthGates,
                copiedVerificationGates,
                copiedHandoffNotes,
                copiedMarkdownSections,
                checks,
                status(
                        copiedSourceReceipts,
                        copiedSplitModules,
                        copiedEvidenceReferences,
                        copiedPrecheckFields,
                        copiedBoundaryGuards,
                        copiedCodeHealthGates,
                        copiedVerificationGates,
                        copiedHandoffNotes,
                        copiedMarkdownSections
                )
        );
    }

    private static List<String> checks(
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.SourceReceipt>
                    sourceReceipts,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.SplitModule>
                    splitModules,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.EvidenceReference>
                    evidenceReferences,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.PrecheckField>
                    precheckFields,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.BoundaryGuard>
                    boundaryGuards,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.CodeHealthGate>
                    codeHealthGates,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.VerificationGate>
                    verificationGates,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.HandoffNote>
                    handoffNotes,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.MarkdownSection>
                    markdownSections
    ) {
        var result = new ArrayList<String>();
        result.add("sandbox-connection-precheck-upstream-receipt-verification-manifest-profile-" + PROFILE);
        result.add("sandbox-connection-precheck-upstream-receipt-verification-manifest-source-plan-" + SOURCE_PLAN);
        result.add("sandbox-connection-precheck-upstream-receipt-verification-manifest-owner-plan-" + NODE_OWNER_PLAN);
        result.add("sandbox-connection-precheck-upstream-receipt-verification-manifest-java-evidence-"
                + FROZEN_JAVA_EVIDENCE_VERSION);
        result.add("sandbox-connection-precheck-upstream-receipt-verification-manifest-mini-kv-evidence-"
                + FROZEN_MINI_KV_EVIDENCE_VERSION);
        sourceReceipts.stream().findFirst().ifPresent(source -> {
            result.add("sandbox-connection-precheck-upstream-receipt-verification-manifest-source-receipt-"
                    + source.receiptVersion());
            result.add("sandbox-connection-precheck-upstream-receipt-verification-manifest-consumes-"
                    + source.consumedNodeVersion());
            result.add("sandbox-connection-precheck-upstream-receipt-verification-manifest-next-node-"
                    + source.nextNodeVersion());
            result.add("sandbox-connection-precheck-upstream-receipt-verification-manifest-production-audit-"
                    + source.nodeMayTreatAsProductionAuditRecord());
            result.add("sandbox-connection-precheck-upstream-receipt-verification-manifest-current-ready-"
                    + source.readyForReceiptVerification());
            result.add("sandbox-connection-precheck-upstream-receipt-verification-manifest-historical-retention-true");
        });
        result.add("sandbox-connection-precheck-upstream-receipt-verification-manifest-split-modules-"
                + splitModules.size());
        result.add("sandbox-connection-precheck-upstream-receipt-verification-manifest-evidence-references-"
                + evidenceReferences.size());
        result.add("sandbox-connection-precheck-upstream-receipt-verification-manifest-precheck-fields-"
                + precheckFields.size());
        result.add("sandbox-connection-precheck-upstream-receipt-verification-manifest-boundary-guards-"
                + boundaryGuards.size());
        result.add("sandbox-connection-precheck-upstream-receipt-verification-manifest-code-health-gates-"
                + codeHealthGates.size());
        result.add("sandbox-connection-precheck-upstream-receipt-verification-manifest-verification-gates-"
                + verificationGates.size());
        result.add("sandbox-connection-precheck-upstream-receipt-verification-manifest-handoff-notes-"
                + handoffNotes.size());
        result.add("sandbox-connection-precheck-upstream-receipt-verification-manifest-markdown-sections-"
                + markdownSections.size());
        result.add("sandbox-connection-precheck-upstream-receipt-verification-manifest-boundaries-passed-"
                + allBoundaryGuardsPassed(boundaryGuards));
        result.add("sandbox-connection-precheck-upstream-receipt-verification-manifest-code-health-passed-"
                + allCodeHealthGatesPassed(codeHealthGates));
        result.add("sandbox-connection-precheck-upstream-receipt-verification-manifest-ready-for-retention");
        return List.copyOf(result);
    }

    static String status(
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.SourceReceipt>
                    sourceReceipts,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.SplitModule>
                    splitModules,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.EvidenceReference>
                    evidenceReferences,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.PrecheckField>
                    precheckFields,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.BoundaryGuard>
                    boundaryGuards,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.CodeHealthGate>
                    codeHealthGates,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.VerificationGate>
                    verificationGates,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.HandoffNote>
                    handoffNotes,
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.MarkdownSection>
                    markdownSections
    ) {
        boolean passed = sourceReceipts.size() == EXPECTED_SOURCE_RECEIPT_COUNT
                && splitModules.size() == EXPECTED_SPLIT_MODULE_COUNT
                && evidenceReferences.size() == EXPECTED_EVIDENCE_REFERENCE_COUNT
                && precheckFields.size() == EXPECTED_PRECHECK_FIELD_COUNT
                && boundaryGuards.size() == EXPECTED_BOUNDARY_GUARD_COUNT
                && codeHealthGates.size() == EXPECTED_CODE_HEALTH_GATE_COUNT
                && verificationGates.size() == EXPECTED_VERIFICATION_GATE_COUNT
                && handoffNotes.size() == EXPECTED_HANDOFF_NOTE_COUNT
                && markdownSections.size() == EXPECTED_MARKDOWN_SECTION_COUNT
                && sourceReceipts.stream().allMatch(source -> "Node v245".equals(source.consumedNodeVersion())
                && "Node v246".equals(source.nextNodeVersion())
                && !source.readyForManagedAuditSandboxAdapterConnection()
                && !source.readyForProductionAudit()
                && !source.nodeMayTreatAsProductionAuditRecord())
                && splitModules.stream().allMatch(module -> module.publicContractPreserved()
                && module.consumesFrozenJavaV99Only()
                && !module.runtimeExecutionAllowed())
                && evidenceReferences.stream().allMatch(reference -> reference.accepted() && reference.frozen())
                && precheckFields.stream().allMatch(field -> field.echoed() && !field.carriesCredentialValue())
                && allBoundaryGuardsPassed(boundaryGuards)
                && allCodeHealthGatesPassed(codeHealthGates)
                && verificationGates.stream().allMatch(
                OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                        .VerificationGate::passed)
                && handoffNotes.stream().allMatch(
                OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                        .HandoffNote::ready);
        return passed ? "passed" : "blocked";
    }

    private static boolean allBoundaryGuardsPassed(
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.BoundaryGuard>
                    boundaryGuards
    ) {
        return boundaryGuards.stream().allMatch(
                OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                        .BoundaryGuard::passed);
    }

    private static boolean allCodeHealthGatesPassed(
            List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.CodeHealthGate>
                    codeHealthGates
    ) {
        return codeHealthGates.stream().allMatch(
                OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                        .CodeHealthGate::passed);
    }
}
