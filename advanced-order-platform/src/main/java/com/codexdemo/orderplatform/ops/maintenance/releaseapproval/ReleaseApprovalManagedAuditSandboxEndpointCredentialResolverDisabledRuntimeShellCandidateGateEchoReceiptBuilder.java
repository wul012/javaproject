package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoRecords.RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoRecords.RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceipt;
import java.util.List;

final
class ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceiptBuilder {

  RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt
      build(
          RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceipt
              sourceReceipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoSupport
        .build(sourceReceipt);
  }

  List<String> warningDigestWarningInputNames() {
    return ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoSupport
        .warningDigestWarningInputNames();
  }

  List<String> warningDigestBoundaryInputNames() {
    return ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoSupport
        .warningDigestBoundaryInputNames();
  }

  List<String> proofClaims() {
    return ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoSupport
        .proofClaims();
  }

  List<String> nodeVerificationActions() {
    return ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoSupport
        .nodeVerificationActions();
  }

  List<String> warningDigestWarningLines(
      RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt
          receipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoSupport
        .warningDigestWarningLines(receipt);
  }

  List<String> warningDigestBoundaryLines(
      RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt
          receipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoSupport
        .warningDigestBoundaryLines(receipt);
  }

  boolean noCredentialConnectionWriteOrAutoStartProved(
      RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt
          receipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoSupport
        .noCredentialConnectionWriteOrAutoStartProved(receipt);
  }
}
