package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoRecords.RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoRecords.RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt;
import java.util.List;

final
class ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceiptBuilder {

  RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt
      build(
          RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt
              sourceReceipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoSupport
        .build(sourceReceipt);
  }

  List<String> warningDigestWarningInputNames() {
    return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoSupport
        .warningDigestWarningInputNames();
  }

  List<String> warningDigestBoundaryInputNames() {
    return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoSupport
        .warningDigestBoundaryInputNames();
  }

  List<String> proofClaims() {
    return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoSupport
        .proofClaims();
  }

  List<String> nodeVerificationActions() {
    return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoSupport
        .nodeVerificationActions();
  }

  List<String> warningDigestWarningLines(
      RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt
          receipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoSupport
        .warningDigestWarningLines(receipt);
  }

  List<String> warningDigestBoundaryLines(
      RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt
          receipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoSupport
        .warningDigestBoundaryLines(receipt);
  }

  boolean noCredentialConnectionWriteOrAutoStartProved(
      RehearsalManagedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceipt
          receipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoSupport
        .noCredentialConnectionWriteOrAutoStartProved(receipt);
  }
}
