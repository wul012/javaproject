package com.codexdemo.orderplatform.ops;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveService {

    static final String RESPONSE_VERSION = "Java v1667";
    static final String ENDPOINT =
            OpsShardReadinessCredentialResolverRoutePaths.BASE_PATH
                    + OpsShardReadinessCredentialResolverRoutePaths
                    .CREDENTIAL_RESOLVER_DISABLED_FAKE_HARNESS_EVIDENCE_ARCHIVE;

    private final OpsEvidenceService opsEvidenceService;

    public OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveService(
            OpsEvidenceService opsEvidenceService
    ) {
        this.opsEvidenceService = opsEvidenceService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse archive() {
        var rehearsal = opsEvidenceService.releaseApprovalRehearsal();
        var receipt = rehearsal.managedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt();
        var sourceReceipts =
                OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveSourceCatalog
                        .receipts(rehearsal);
        var javaRequirements =
                OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveRequirementCatalog
                        .javaRequirements(receipt);
        var miniKvRequirements =
                OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveRequirementCatalog
                        .miniKvRequirements(receipt);
        var fakeHarnessBoundaries =
                OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveBoundaryCatalog
                        .fakeHarnessBoundaries(receipt);
        var runtimeGuards =
                OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveRuntimeGuardCatalog
                        .guards(receipt);
        var verificationGates =
                OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveVerificationCatalog
                        .gates(
                                rehearsal,
                                sourceReceipts,
                                javaRequirements,
                                miniKvRequirements,
                                fakeHarnessBoundaries,
                                runtimeGuards
                        );
        var handoffNotes =
                OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveHandoffCatalog
                        .notes();
        var markdownSections =
                OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveRenderer
                        .render(
                                sourceReceipts,
                                javaRequirements,
                                miniKvRequirements,
                                fakeHarnessBoundaries,
                                runtimeGuards,
                                verificationGates
                        );
        return OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveSupport.response(
                RESPONSE_VERSION,
                ENDPOINT,
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
    }
}
