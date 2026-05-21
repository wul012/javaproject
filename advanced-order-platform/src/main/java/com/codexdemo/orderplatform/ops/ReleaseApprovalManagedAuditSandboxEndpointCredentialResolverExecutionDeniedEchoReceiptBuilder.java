package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverExecutionDeniedEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverImplementationPlanEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt;

import java.util.List;

final class ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder {

    RehearsalManagedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt build(
            RehearsalManagedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt sourceReceipt
    ) {
        return ReleaseApprovalSandboxEndpointCredentialResolverExecutionDeniedEchoSupport.build(sourceReceipt);
    }

    List<String> warningDigestWarningInputNames() {
        return ReleaseApprovalSandboxEndpointCredentialResolverExecutionDeniedEchoSupport
                .warningDigestWarningInputNames();
    }

    List<String> warningDigestBoundaryInputNames() {
        return ReleaseApprovalSandboxEndpointCredentialResolverExecutionDeniedEchoSupport
                .warningDigestBoundaryInputNames();
    }

    List<String> proofClaims() {
        return ReleaseApprovalSandboxEndpointCredentialResolverExecutionDeniedEchoSupport.proofClaims();
    }

    List<String> nodeVerificationActions() {
        return ReleaseApprovalSandboxEndpointCredentialResolverExecutionDeniedEchoSupport
                .nodeVerificationActions();
    }

    List<String> warningDigestWarningLines(
            RehearsalManagedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt receipt
    ) {
        return ReleaseApprovalSandboxEndpointCredentialResolverExecutionDeniedEchoSupport
                .warningDigestWarningLines(receipt);
    }

    List<String> warningDigestBoundaryLines(
            RehearsalManagedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt receipt
    ) {
        return ReleaseApprovalSandboxEndpointCredentialResolverExecutionDeniedEchoSupport
                .warningDigestBoundaryLines(receipt);
    }

    boolean noCredentialConnectionWriteOrAutoStartProved(
            RehearsalManagedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt receipt
    ) {
        return ReleaseApprovalSandboxEndpointCredentialResolverExecutionDeniedEchoSupport
                .noCredentialConnectionWriteOrAutoStartProved(receipt);
    }
}
