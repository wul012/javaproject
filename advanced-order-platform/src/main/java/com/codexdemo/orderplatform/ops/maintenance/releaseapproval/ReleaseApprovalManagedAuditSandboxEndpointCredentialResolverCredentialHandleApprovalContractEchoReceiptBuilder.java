package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoRecords.RehearsalManagedAuditSandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoReceipt;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoRecords.RehearsalManagedAuditSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceipt;
import java.util.List;

final
class ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoReceiptBuilder {

  RehearsalManagedAuditSandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoReceipt
      build(
          RehearsalManagedAuditSandboxEndpointCredentialResolverSignedHumanApprovalArtifactContractEchoReceipt
              sourceReceipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoSupport
        .build(sourceReceipt);
  }

  List<String> warningDigestWarningInputNames() {
    return ReleaseApprovalSandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoSupport
        .warningDigestWarningInputNames();
  }

  List<String> warningDigestBoundaryInputNames() {
    return ReleaseApprovalSandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoSupport
        .warningDigestBoundaryInputNames();
  }

  List<String> proofClaims() {
    return ReleaseApprovalSandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoSupport
        .proofClaims();
  }

  List<String> nodeVerificationActions() {
    return ReleaseApprovalSandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoSupport
        .nodeVerificationActions();
  }

  List<String> warningDigestWarningLines(
      RehearsalManagedAuditSandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoReceipt
          receipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoSupport
        .warningDigestWarningLines(receipt);
  }

  List<String> warningDigestBoundaryLines(
      RehearsalManagedAuditSandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoReceipt
          receipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoSupport
        .warningDigestBoundaryLines(receipt);
  }

  boolean noCredentialConnectionWriteOrAutoStartProved(
      RehearsalManagedAuditSandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoReceipt
          receipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoSupport
        .noCredentialConnectionWriteOrAutoStartProved(receipt);
  }
}
