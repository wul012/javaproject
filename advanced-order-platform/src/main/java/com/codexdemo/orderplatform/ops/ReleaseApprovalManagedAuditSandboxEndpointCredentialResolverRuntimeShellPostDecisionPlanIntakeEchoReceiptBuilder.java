package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt;
import java.util.List;

final class ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptBuilder {

    RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt build(
            RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt sourceReceipt
    ) {
        return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoSupport
                .build(sourceReceipt);
    }

    List<String> warningDigestWarningInputNames() {
        return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoSupport
                .warningDigestWarningInputNames();
    }

    List<String> warningDigestBoundaryInputNames() {
        return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoSupport
                .warningDigestBoundaryInputNames();
    }

    List<String> proofClaims() {
        return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoSupport
                .proofClaims();
    }

    List<String> nodeVerificationActions() {
        return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoSupport
                .nodeVerificationActions();
    }

    List<String> warningDigestWarningLines(
            RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt receipt
    ) {
        return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoSupport
                .warningDigestWarningLines(receipt);
    }

    List<String> warningDigestBoundaryLines(
            RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt receipt
    ) {
        return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoSupport
                .warningDigestBoundaryLines(receipt);
    }

    boolean noCredentialConnectionWriteOrAutoStartProved(
            RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt receipt
    ) {
        return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoSupport
                .noCredentialConnectionWriteOrAutoStartProved(receipt);
    }
}
