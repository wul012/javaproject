package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverImplementationPlanEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverImplementationPlanEchoRecords
        .RehearsalSandboxEndpointCredentialResolverImplementationUpstreamEchoRequirement;
import java.util.List;

final class OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveRequirementCatalog {

    private OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveRequirementCatalog() {
    }

    static List<OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.EvidenceRequirement>
            javaRequirements(
            RehearsalManagedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt receipt
    ) {
        return receipt.javaV121EchoRequirements().stream()
                .map(OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveRequirementCatalog
                        ::requirement)
                .toList();
    }

    static List<OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.EvidenceRequirement>
            miniKvRequirements(
            RehearsalManagedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt receipt
    ) {
        return receipt.miniKvV126ReceiptRequirements().stream()
                .map(OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveRequirementCatalog
                        ::requirement)
                .toList();
    }

    private static OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.EvidenceRequirement
            requirement(
            RehearsalSandboxEndpointCredentialResolverImplementationUpstreamEchoRequirement source
    ) {
        return new OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
                .EvidenceRequirement(
                source.id(),
                source.project(),
                source.expectedVersion(),
                source.requirement(),
                source.mustRemainReadOnly(),
                source.mustNotConnectManagedAudit(),
                source.mustNotReadCredentialValue(),
                source.mustNotParseRawEndpointUrl(),
                source.mustNotWriteLedgerOrState()
        );
    }
}
