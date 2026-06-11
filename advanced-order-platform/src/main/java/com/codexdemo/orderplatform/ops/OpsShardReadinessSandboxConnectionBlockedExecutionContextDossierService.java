package com.codexdemo.orderplatform.ops;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierService {

    static final String RESPONSE_VERSION = "Java v1687";
    static final String ENDPOINT =
            OpsShardReadinessSandboxConnectionRoutePaths.BASE_PATH
                    + OpsShardReadinessSandboxConnectionRoutePaths
                    .SANDBOX_CONNECTION_BLOCKED_EXECUTION_CONTEXT_NORMALIZATION_DOSSIER;

    private final OpsEvidenceService opsEvidenceService;

    public OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierService(
            OpsEvidenceService opsEvidenceService
    ) {
        this.opsEvidenceService = opsEvidenceService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse dossier() {
        var rehearsal = opsEvidenceService.releaseApprovalRehearsal();
        var receipt = rehearsal.managedAuditSandboxConnectionPreconditionReceipt();
        var sourceReceipts =
                OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierSourceCatalog
                        .receipts(rehearsal);
        var contextFields =
                OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierContextCatalog
                        .fields(rehearsal);
        var normalizationRules =
                OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierContextCatalog
                        .rules();
        var preconditionEvidence =
                OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierPreconditionEvidenceCatalog
                        .evidence(rehearsal);
        var boundarySnapshots =
                OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierBoundaryCatalog
                        .boundaries(receipt);
        var executionGuards =
                OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierExecutionGuardCatalog
                        .guards(receipt);
        var warningEchoes =
                OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierWarningCatalog
                        .warnings(rehearsal);
        var downstreamIntakeGates =
                OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierDownstreamIntakeCatalog
                        .gates(rehearsal);
        var verificationGates =
                OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierVerificationCatalog
                        .gates(
                                sourceReceipts,
                                contextFields,
                                preconditionEvidence,
                                boundarySnapshots,
                                executionGuards,
                                warningEchoes,
                                downstreamIntakeGates
                        );
        var handoffNotes =
                OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierHandoffCatalog
                        .notes();
        var markdownSections =
                OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierRenderer
                        .render(
                                sourceReceipts,
                                contextFields,
                                preconditionEvidence,
                                boundarySnapshots,
                                executionGuards,
                                warningEchoes,
                                downstreamIntakeGates,
                                verificationGates,
                                handoffNotes
                        );
        return OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierSupport.response(
                RESPONSE_VERSION,
                ENDPOINT,
                rehearsal,
                sourceReceipts,
                contextFields,
                normalizationRules,
                preconditionEvidence,
                boundarySnapshots,
                executionGuards,
                warningEchoes,
                downstreamIntakeGates,
                verificationGates,
                handoffNotes,
                markdownSections
        );
    }
}
