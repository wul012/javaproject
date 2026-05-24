package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoReceipt;
import java.util.List;

final class ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoReceiptBuilder {

    RehearsalManagedAuditSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoReceipt build(
            RehearsalManagedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceipt
                    sourceReceipt
    ) {
        return ReleaseApprovalSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoSupport
                .build(sourceReceipt);
    }

    List<String> warningDigestWarningInputNames() {
        return ReleaseApprovalSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoSupport
                .warningDigestWarningInputNames();
    }

    List<String> warningDigestBoundaryInputNames() {
        return ReleaseApprovalSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoSupport
                .warningDigestBoundaryInputNames();
    }

    List<String> proofClaims() {
        return ReleaseApprovalSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoSupport
                .proofClaims();
    }

    List<String> nodeVerificationActions() {
        return ReleaseApprovalSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoSupport
                .nodeVerificationActions();
    }

    List<String> warningDigestWarningLines(
            RehearsalManagedAuditSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoReceipt receipt
    ) {
        return ReleaseApprovalSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoSupport
                .warningDigestWarningLines(receipt);
    }

    List<String> warningDigestBoundaryLines(
            RehearsalManagedAuditSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoReceipt receipt
    ) {
        return ReleaseApprovalSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoSupport
                .warningDigestBoundaryLines(receipt);
    }

    boolean noCredentialConnectionWriteOrAutoStartProved(
            RehearsalManagedAuditSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoReceipt receipt
    ) {
        return ReleaseApprovalSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoSupport
                .noCredentialConnectionWriteOrAutoStartProved(receipt);
    }
}
