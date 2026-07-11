package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoRecords.RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoRecords.RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt;
import java.util.List;

final
class ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptBuilder {

  RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt build(
      RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceipt
          sourceReceipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoSupport
        .build(sourceReceipt);
  }

  List<String> warningDigestWarningInputNames() {
    return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoSupport
        .warningDigestWarningInputNames();
  }

  List<String> warningDigestBoundaryInputNames() {
    return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoSupport
        .warningDigestBoundaryInputNames();
  }

  List<String> proofClaims() {
    return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoSupport
        .proofClaims();
  }

  List<String> nodeVerificationActions() {
    return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoSupport
        .nodeVerificationActions();
  }

  List<String> warningDigestWarningLines(
      RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt
          receipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoSupport
        .warningDigestWarningLines(receipt);
  }

  List<String> warningDigestBoundaryLines(
      RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt
          receipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoSupport
        .warningDigestBoundaryLines(receipt);
  }

  boolean noCredentialConnectionWriteOrAutoStartProved(
      RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt
          receipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoSupport
        .noCredentialConnectionWriteOrAutoStartProved(receipt);
  }
}
