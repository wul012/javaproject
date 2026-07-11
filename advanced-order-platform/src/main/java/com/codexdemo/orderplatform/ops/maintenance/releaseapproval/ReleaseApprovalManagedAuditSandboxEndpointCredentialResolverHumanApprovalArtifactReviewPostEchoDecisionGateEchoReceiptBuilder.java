package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoRecords.RehearsalManagedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoRecords.RehearsalManagedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt;
import java.util.List;

final
class ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceiptBuilder {

  RehearsalManagedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt
      build(
          RehearsalManagedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt
              sourceReceipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoSupport
        .build(sourceReceipt);
  }

  List<String> warningDigestWarningInputNames() {
    return ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoSupport
        .warningDigestWarningInputNames();
  }

  List<String> warningDigestBoundaryInputNames() {
    return ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoSupport
        .warningDigestBoundaryInputNames();
  }

  List<String> proofClaims() {
    return ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoSupport
        .proofClaims();
  }

  List<String> nodeVerificationActions() {
    return ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoSupport
        .nodeVerificationActions();
  }

  List<String> warningDigestWarningLines(
      RehearsalManagedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt
          receipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoSupport
        .warningDigestWarningLines(receipt);
  }

  List<String> warningDigestBoundaryLines(
      RehearsalManagedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt
          receipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoSupport
        .warningDigestBoundaryLines(receipt);
  }

  boolean noCredentialConnectionWriteOrAutoStartProved(
      RehearsalManagedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt
          receipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoSupport
        .noCredentialConnectionWriteOrAutoStartProved(receipt);
  }
}
