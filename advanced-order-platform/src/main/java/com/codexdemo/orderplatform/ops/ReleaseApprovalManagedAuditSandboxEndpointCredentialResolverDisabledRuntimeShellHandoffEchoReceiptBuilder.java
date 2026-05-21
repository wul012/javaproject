package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverExecutionDeniedEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt;

import java.util.List;

final class ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceiptBuilder {

    RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceipt build(
            RehearsalManagedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceipt sourceReceipt
    ) {
        return ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoSupport
                .build(sourceReceipt);
    }

    List<String> warningDigestWarningInputNames() {
        return ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoSupport
                .warningDigestWarningInputNames();
    }

    List<String> warningDigestBoundaryInputNames() {
        return ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoSupport
                .warningDigestBoundaryInputNames();
    }

    List<String> proofClaims() {
        return ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoSupport
                .proofClaims();
    }

    List<String> nodeVerificationActions() {
        return ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoSupport
                .nodeVerificationActions();
    }

    List<String> warningDigestWarningLines(
            RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceipt receipt
    ) {
        return ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoSupport
                .warningDigestWarningLines(receipt);
    }

    List<String> warningDigestBoundaryLines(
            RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceipt receipt
    ) {
        return ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoSupport
                .warningDigestBoundaryLines(receipt);
    }

    boolean noCredentialConnectionWriteOrAutoStartProved(
            RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceipt receipt
    ) {
        return ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoSupport
                .noCredentialConnectionWriteOrAutoStartProved(receipt);
    }
}
