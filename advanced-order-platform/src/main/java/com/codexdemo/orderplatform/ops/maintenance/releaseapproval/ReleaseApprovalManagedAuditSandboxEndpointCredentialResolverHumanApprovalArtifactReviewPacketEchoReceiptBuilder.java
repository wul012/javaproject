package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoRecords.RehearsalManagedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoRecords.RehearsalManagedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt;
import java.util.List;

final
class ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceiptBuilder {

  RehearsalManagedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt
      build(
          RehearsalManagedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceipt
              sourceReceipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoSupport
        .build(sourceReceipt);
  }

  List<String> warningDigestWarningInputNames() {
    return ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoSupport
        .warningDigestWarningInputNames();
  }

  List<String> warningDigestBoundaryInputNames() {
    return ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoSupport
        .warningDigestBoundaryInputNames();
  }

  List<String> proofClaims() {
    return ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoSupport
        .proofClaims();
  }

  List<String> nodeVerificationActions() {
    return ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoSupport
        .nodeVerificationActions();
  }

  List<String> warningDigestWarningLines(
      RehearsalManagedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt
          receipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoSupport
        .warningDigestWarningLines(receipt);
  }

  List<String> warningDigestBoundaryLines(
      RehearsalManagedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt
          receipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoSupport
        .warningDigestBoundaryLines(receipt);
  }

  boolean noCredentialConnectionWriteOrAutoStartProved(
      RehearsalManagedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceipt
          receipt) {
    return ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoSupport
        .noCredentialConnectionWriteOrAutoStartProved(receipt);
  }
}
