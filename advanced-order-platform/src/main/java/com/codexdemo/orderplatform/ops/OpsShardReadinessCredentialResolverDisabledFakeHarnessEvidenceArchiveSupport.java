package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveSupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v1967";
    static final String NODE_OWNER_PLAN = "Node v1953-v1967";
    static final String PROFILE =
            "java-shard-readiness-credential-resolver-disabled-fake-harness-evidence-archive.v1";
    static final int EXPECTED_SOURCE_RECEIPT_COUNT = 1;
    static final int EXPECTED_JAVA_REQUIREMENT_COUNT = 4;
    static final int EXPECTED_MINI_KV_REQUIREMENT_COUNT = 4;
    static final int EXPECTED_FAKE_HARNESS_BOUNDARY_COUNT = 1;
    static final int EXPECTED_RUNTIME_GUARD_COUNT = 10;
    static final int EXPECTED_VERIFICATION_GATE_COUNT = 8;
    static final int EXPECTED_HANDOFF_NOTE_COUNT = 4;
    static final int EXPECTED_MARKDOWN_SECTION_COUNT = 6;

    private OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveSupport() {
    }

    static OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse response(
            String version,
            String endpoint,
            ReleaseApprovalRehearsalResponse rehearsal,
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
                    verificationGates,
            List<OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.HandoffNote>
                    handoffNotes,
            List<OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.MarkdownSection>
                    markdownSections
    ) {
        var checks = checks(
                rehearsal,
                sourceReceipts,
                javaRequirements,
                miniKvRequirements,
                fakeHarnessBoundaries,
                runtimeGuards,
                verificationGates,
                handoffNotes,
                markdownSections
        );
        return new OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse(
                PROJECT,
                version,
                true,
                false,
                SOURCE_PLAN,
                NODE_OWNER_PLAN,
                rehearsal.rehearsalVersion(),
                rehearsal.verificationHint().responseSchemaVersion(),
                endpoint,
                PROFILE,
                sourceReceipts.size(),
                javaRequirements.size(),
                miniKvRequirements.size(),
                fakeHarnessBoundaries.size(),
                runtimeGuards.size(),
                verificationGates.size(),
                handoffNotes.size(),
                markdownSections.size(),
                sourceReceipts,
                javaRequirements,
                miniKvRequirements,
                fakeHarnessBoundaries,
                runtimeGuards,
                verificationGates,
                handoffNotes,
                markdownSections,
                checks,
                status(
                        sourceReceipts,
                        javaRequirements,
                        miniKvRequirements,
                        fakeHarnessBoundaries,
                        runtimeGuards,
                        verificationGates,
                        handoffNotes,
                        markdownSections
                )
        );
    }

    private static List<String> checks(
            ReleaseApprovalRehearsalResponse rehearsal,
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
                    verificationGates,
            List<OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.HandoffNote>
                    handoffNotes,
            List<OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.MarkdownSection>
                    markdownSections
    ) {
        var result = new ArrayList<String>();
        result.add("credential-resolver-disabled-fake-harness-evidence-archive-profile-" + PROFILE);
        result.add("credential-resolver-disabled-fake-harness-evidence-archive-source-plan-" + SOURCE_PLAN);
        result.add("credential-resolver-disabled-fake-harness-evidence-archive-owner-plan-" + NODE_OWNER_PLAN);
        result.add("credential-resolver-disabled-fake-harness-evidence-archive-source-schema-"
                + rehearsal.verificationHint().responseSchemaVersion());
        sourceReceipts.stream().findFirst().ifPresent(source -> {
            result.add("credential-resolver-disabled-fake-harness-evidence-archive-source-receipt-"
                    + source.receiptVersion());
            result.add("credential-resolver-disabled-fake-harness-evidence-archive-source-digest-present-"
                    + source.receiptDigest().startsWith("sha256:"));
            result.add("credential-resolver-disabled-fake-harness-evidence-archive-consumes-"
                    + source.consumedNodeVersion());
            result.add("credential-resolver-disabled-fake-harness-evidence-archive-next-verification-"
                    + source.nextNodeVerificationVersion());
            result.add("credential-resolver-disabled-fake-harness-evidence-archive-fake-harness-deferred-until-"
                    + source.fakeHarnessDeferredUntil());
        });
        result.add("credential-resolver-disabled-fake-harness-evidence-archive-java-requirements-"
                + javaRequirements.size());
        result.add("credential-resolver-disabled-fake-harness-evidence-archive-mini-kv-requirements-"
                + miniKvRequirements.size());
        result.add("credential-resolver-disabled-fake-harness-evidence-archive-fake-harness-boundaries-"
                + fakeHarnessBoundaries.size());
        result.add("credential-resolver-disabled-fake-harness-evidence-archive-runtime-guards-"
                + runtimeGuards.size());
        result.add("credential-resolver-disabled-fake-harness-evidence-archive-verification-gates-"
                + verificationGates.size());
        result.add("credential-resolver-disabled-fake-harness-evidence-archive-handoff-notes-"
                + handoffNotes.size());
        result.add("credential-resolver-disabled-fake-harness-evidence-archive-markdown-sections-"
                + markdownSections.size());
        result.add("credential-resolver-disabled-fake-harness-evidence-archive-receipt-warnings-"
                + rehearsal.managedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt()
                .receiptWarnings().size());
        result.add("credential-resolver-disabled-fake-harness-evidence-archive-all-runtime-guards-passed-"
                + allRuntimeGuardsPassed(runtimeGuards));
        result.add("credential-resolver-disabled-fake-harness-evidence-archive-ready-for-retention");
        return List.copyOf(result);
    }

    static String status(
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
                    verificationGates,
            List<OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.HandoffNote>
                    handoffNotes,
            List<OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.MarkdownSection>
                    markdownSections
    ) {
        boolean passed = sourceReceipts.size() == EXPECTED_SOURCE_RECEIPT_COUNT
                && javaRequirements.size() == EXPECTED_JAVA_REQUIREMENT_COUNT
                && miniKvRequirements.size() == EXPECTED_MINI_KV_REQUIREMENT_COUNT
                && fakeHarnessBoundaries.size() == EXPECTED_FAKE_HARNESS_BOUNDARY_COUNT
                && runtimeGuards.size() == EXPECTED_RUNTIME_GUARD_COUNT
                && verificationGates.size() == EXPECTED_VERIFICATION_GATE_COUNT
                && handoffNotes.size() == EXPECTED_HANDOFF_NOTE_COUNT
                && markdownSections.size() == EXPECTED_MARKDOWN_SECTION_COUNT
                && sourceReceipts.stream().allMatch(source -> source.nodeVerificationReady()
                && source.siblingEchoReady()
                && !source.fakeHarnessPrecheckReady()
                && !source.managedAuditResolverImplementationReady()
                && "Node v285".equals(source.fakeHarnessDeferredUntil()))
                && javaRequirements.stream().allMatch(EvidenceRequirementRules::readOnlyAndClosed)
                && miniKvRequirements.stream().allMatch(EvidenceRequirementRules::readOnlyAndClosed)
                && fakeHarnessBoundaries.stream().allMatch(
                OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
                        .FakeHarnessBoundary::archiveReady)
                && allRuntimeGuardsPassed(runtimeGuards)
                && verificationGates.stream().allMatch(
                OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
                        .VerificationGate::passed)
                && handoffNotes.stream().allMatch(
                OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
                        .HandoffNote::ready);
        return passed ? "passed" : "blocked";
    }

    private static boolean allRuntimeGuardsPassed(
            List<OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.RuntimeGuard>
                    runtimeGuards
    ) {
        return runtimeGuards.stream().allMatch(
                OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
                        .RuntimeGuard::passed);
    }

    private static final class EvidenceRequirementRules {

        private EvidenceRequirementRules() {
        }

        private static boolean readOnlyAndClosed(
                OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.EvidenceRequirement
                        requirement
        ) {
            return requirement.mustRemainReadOnly()
                    && requirement.mustNotConnectManagedAudit()
                    && requirement.mustNotReadCredentialValue()
                    && requirement.mustNotParseRawEndpointUrl()
                    && requirement.mustNotWriteLedgerOrState();
        }
    }
}
