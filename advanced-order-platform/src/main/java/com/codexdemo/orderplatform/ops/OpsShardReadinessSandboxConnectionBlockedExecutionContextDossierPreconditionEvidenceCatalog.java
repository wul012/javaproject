package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierPreconditionEvidenceCatalog {

    private OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierPreconditionEvidenceCatalog() {
    }

    static List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.PreconditionEvidence>
            evidence(ReleaseApprovalRehearsalResponse rehearsal) {
        return rehearsal.managedAuditSandboxConnectionPreconditionReceipt().requiredPreconditionEvidence().stream()
                .map(source -> new OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                        .PreconditionEvidence(id(source), source, true, true))
                .toList();
    }

    private static String id(String evidence) {
        return evidence.substring(0, evidence.indexOf(':'))
                .replace(' ', '-')
                .toLowerCase();
    }
}
