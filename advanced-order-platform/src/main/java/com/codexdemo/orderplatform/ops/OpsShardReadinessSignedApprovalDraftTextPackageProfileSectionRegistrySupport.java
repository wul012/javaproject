package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistrySupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v1531";
    static final String SOURCE_NODE_TEXT_PACKAGE_RENDERER_VERSION = "Node v1531";
    static final String REGISTRY_STATE =
            "signed-approval-draft-text-package-profile-sections-extracted-with-route-output-stable";
    static final int EXPECTED_MODULE_COUNT = 10;
    static final int EXPECTED_SOURCE_ROUTE_COUNT = 9;
    static final int EXPECTED_SECTION_COUNT = 9;
    static final int EXPECTED_SUBMISSION_RENDERER_SECTION_COUNT = 5;
    static final int EXPECTED_COMPARED_EVIDENCE_RENDERER_SECTION_COUNT = 4;
    static final int EXPECTED_FIELD_ENTRY_COUNT = 63;
    static final int EXPECTED_ROUTE_FIELD_LOCK_COUNT = 9;
    static final int EXPECTED_LOCKED_ROUTE_FIELD_COUNT = 45;
    static final int EXPECTED_GATE_COUNT = 64;

    private OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistrySupport() {
    }

    static OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse response(
            String version,
            String endpoint,
            String profile,
            List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.ModuleEntry> modules,
            List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                    .TextPackageSectionSource> sources,
            List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                    .TextPackageProfileSection> sections,
            List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.FieldEntry>
                    fieldEntries,
            List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.RenderedSection>
                    renderedSections,
            List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.RouteFieldLock>
                    routeFieldLocks,
            List<String> gates,
            List<String> additionalChecks
    ) {
        var moduleCopy = List.copyOf(modules);
        var sourceCopy = List.copyOf(sources);
        var sectionCopy = List.copyOf(sections);
        var fieldCopy = List.copyOf(fieldEntries);
        var renderedCopy = List.copyOf(renderedSections);
        var lockCopy = List.copyOf(routeFieldLocks);
        var gateCopy = List.copyOf(gates);
        int submissionCount = rendererGroupCount(sectionCopy, "submission");
        int comparedEvidenceCount = rendererGroupCount(sectionCopy, "compared-evidence");
        int lockedRouteFieldCount = lockCopy.stream()
                .mapToInt(OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                        .RouteFieldLock::lockedFieldCount)
                .sum();
        List<String> checks = new ArrayList<>();
        checks.add("signed-approval-draft-text-package-profile-section-registry-source-plan-"
                + SOURCE_PLAN);
        checks.add("signed-approval-draft-text-package-profile-section-registry-source-node-"
                + SOURCE_NODE_TEXT_PACKAGE_RENDERER_VERSION);
        checks.add("signed-approval-draft-text-package-profile-section-registry-module-count-"
                + moduleCopy.size());
        checks.add("signed-approval-draft-text-package-profile-section-registry-source-route-count-"
                + sourceCopy.size());
        checks.add("signed-approval-draft-text-package-profile-section-registry-section-count-"
                + sectionCopy.size());
        checks.add("signed-approval-draft-text-package-profile-section-registry-submission-renderer-section-count-"
                + submissionCount);
        checks.add("signed-approval-draft-text-package-profile-section-registry-compared-evidence-renderer-section-count-"
                + comparedEvidenceCount);
        checks.add("signed-approval-draft-text-package-profile-section-registry-rendered-section-count-"
                + renderedCopy.size());
        checks.add("signed-approval-draft-text-package-profile-section-registry-field-entry-count-"
                + fieldCopy.size());
        checks.add("signed-approval-draft-text-package-profile-section-registry-route-field-lock-count-"
                + lockCopy.size());
        checks.add("signed-approval-draft-text-package-profile-section-registry-locked-route-field-count-"
                + lockedRouteFieldCount);
        checks.add("signed-approval-draft-text-package-profile-section-registry-gate-count-"
                + gateCopy.size());
        checks.add("signed-approval-draft-text-package-profile-section-registry-zero-package-acceptance");
        checks.add("signed-approval-draft-text-package-profile-section-registry-zero-signed-approvals");
        checks.add("signed-approval-draft-text-package-profile-section-registry-zero-runtime-payloads");
        checks.add("signed-approval-draft-text-package-profile-section-registry-zero-secret-values");
        checks.add("signed-approval-draft-text-package-profile-section-registry-zero-write-operations");
        checks.add("signed-approval-draft-text-package-profile-section-registry-package-acceptance-disabled");
        checks.add("signed-approval-draft-text-package-profile-section-registry-signature-capture-disabled");
        checks.add("signed-approval-draft-text-package-profile-section-registry-approval-grant-disabled");
        checks.add("signed-approval-draft-text-package-profile-section-registry-value-import-disabled");
        checks.add("signed-approval-draft-text-package-profile-section-registry-runtime-payload-disabled");
        checks.add("signed-approval-draft-text-package-profile-section-registry-secret-value-disabled");
        checks.add("signed-approval-draft-text-package-profile-section-registry-write-disabled");
        checks.add("signed-approval-draft-text-package-profile-section-registry-sibling-mutation-disabled");
        checks.addAll(additionalChecks);

        return new OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse(
                PROJECT,
                version,
                true,
                false,
                true,
                SOURCE_PLAN,
                SOURCE_NODE_TEXT_PACKAGE_RENDERER_VERSION,
                REGISTRY_STATE,
                endpoint,
                profile,
                moduleCopy.size(),
                sourceCopy.size(),
                sectionCopy.size(),
                submissionCount,
                comparedEvidenceCount,
                renderedCopy.size(),
                fieldCopy.size(),
                lockCopy.size(),
                lockedRouteFieldCount,
                gateCopy.size(),
                0,
                0,
                0,
                0,
                0,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                moduleCopy,
                sourceCopy,
                sectionCopy,
                fieldCopy,
                renderedCopy,
                lockCopy,
                gateCopy,
                List.copyOf(checks),
                isComplete(moduleCopy, sourceCopy, sectionCopy, fieldCopy, renderedCopy,
                        lockCopy, lockedRouteFieldCount, submissionCount, comparedEvidenceCount, gateCopy)
                        ? "passed"
                        : "blocked"
        );
    }

    private static int rendererGroupCount(
            List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                    .TextPackageProfileSection> sections,
            String rendererGroup
    ) {
        return (int) sections.stream()
                .filter(section -> rendererGroup.equals(section.rendererGroup()))
                .count();
    }

    private static boolean isComplete(
            List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.ModuleEntry> modules,
            List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                    .TextPackageSectionSource> sources,
            List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                    .TextPackageProfileSection> sections,
            List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.FieldEntry>
                    fieldEntries,
            List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.RenderedSection>
                    renderedSections,
            List<OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.RouteFieldLock>
                    routeFieldLocks,
            int lockedRouteFieldCount,
            int submissionCount,
            int comparedEvidenceCount,
            List<String> gates
    ) {
        return modules.size() == EXPECTED_MODULE_COUNT
                && sources.size() == EXPECTED_SOURCE_ROUTE_COUNT
                && sections.size() == EXPECTED_SECTION_COUNT
                && submissionCount == EXPECTED_SUBMISSION_RENDERER_SECTION_COUNT
                && comparedEvidenceCount == EXPECTED_COMPARED_EVIDENCE_RENDERER_SECTION_COUNT
                && fieldEntries.size() == EXPECTED_FIELD_ENTRY_COUNT
                && renderedSections.size() == EXPECTED_SECTION_COUNT
                && routeFieldLocks.size() == EXPECTED_ROUTE_FIELD_LOCK_COUNT
                && lockedRouteFieldCount == EXPECTED_LOCKED_ROUTE_FIELD_COUNT
                && gates.size() == EXPECTED_GATE_COUNT;
    }
}
