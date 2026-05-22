package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt;
import java.util.List;

final class ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceiptBuilder {

    RehearsalManagedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt build(
            RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt sourceReceipt
    ) {
        return ReleaseApprovalSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoSupport
                .build(sourceReceipt);
    }

    List<String> warningDigestWarningInputNames() {
        return ReleaseApprovalSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoSupport
                .warningDigestWarningInputNames();
    }

    List<String> warningDigestBoundaryInputNames() {
        return ReleaseApprovalSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoSupport
                .warningDigestBoundaryInputNames();
    }

    List<String> proofClaims() {
        return ReleaseApprovalSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoSupport
                .proofClaims();
    }

    List<String> nodeVerificationActions() {
        return ReleaseApprovalSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoSupport
                .nodeVerificationActions();
    }

    List<String> warningDigestWarningLines(
            RehearsalManagedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt receipt
    ) {
        return ReleaseApprovalSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoSupport
                .warningDigestWarningLines(receipt);
    }

    List<String> warningDigestBoundaryLines(
            RehearsalManagedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt receipt
    ) {
        return ReleaseApprovalSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoSupport
                .warningDigestBoundaryLines(receipt);
    }

    boolean noCredentialConnectionWriteOrAutoStartProved(
            RehearsalManagedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt receipt
    ) {
        return ReleaseApprovalSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoSupport
                .noCredentialConnectionWriteOrAutoStartProved(receipt);
    }
}
