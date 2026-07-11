package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

public record ReleaseApprovalRehearsalRequest(
    Context context,
    OperatorWindow operatorWindow,
    CiEvidence ciEvidence,
    ArtifactRetention artifactRetention,
    RuntimeReadiness runtimeReadiness,
    ManagedAudit managedAudit,
    ApprovalBinding approvalBinding) {

  public ReleaseApprovalRehearsalRequest {
    context = context == null ? Context.empty() : context;
    operatorWindow = operatorWindow == null ? OperatorWindow.empty() : operatorWindow;
    ciEvidence = ciEvidence == null ? CiEvidence.empty() : ciEvidence;
    artifactRetention = artifactRetention == null ? ArtifactRetention.empty() : artifactRetention;
    runtimeReadiness = runtimeReadiness == null ? RuntimeReadiness.empty() : runtimeReadiness;
    managedAudit = managedAudit == null ? ManagedAudit.empty() : managedAudit;
    approvalBinding = approvalBinding == null ? ApprovalBinding.empty() : approvalBinding;
  }

  public static ReleaseApprovalRehearsalRequest empty() {
    return new ReleaseApprovalRehearsalRequest(
        Context.empty(),
        OperatorWindow.empty(),
        CiEvidence.empty(),
        ArtifactRetention.empty(),
        RuntimeReadiness.empty(),
        ManagedAudit.empty(),
        ApprovalBinding.empty());
  }

  public record Context(String requestId, String operatorIdentity, String auditCorrelationId) {
    static Context empty() {
      return new Context(null, null, null);
    }
  }

  public record OperatorWindow(
      String operatorId, String roles, String verifiedClaim, String approvalCorrelationId) {
    static OperatorWindow empty() {
      return new OperatorWindow(null, null, null, null);
    }
  }

  public record CiEvidence(
      String manifestVersion,
      String manifestDigest,
      String manifestEndpoint,
      String artifactRecordCount,
      String approvalCorrelationId) {
    static CiEvidence empty() {
      return new CiEvidence(null, null, null, null, null);
    }
  }

  public record ArtifactRetention(
      String uploadContractVersion,
      String uploadContractDigest,
      String artifactName,
      String artifactRoot,
      String retentionDays,
      String uploadMode) {
    static ArtifactRetention empty() {
      return new ArtifactRetention(null, null, null, null, null, null);
    }
  }

  public record RuntimeReadiness(
      String preflightVersion,
      String preflightDigest,
      String smokeSessionId,
      String readTargetId,
      String windowMode) {
    static RuntimeReadiness empty() {
      return new RuntimeReadiness(null, null, null, null, null);
    }
  }

  public record ManagedAudit(
      String candidateVersion,
      String candidateDigest,
      String sinkMode,
      String retentionDays,
      String rotationPolicy) {
    static ManagedAudit empty() {
      return new ManagedAudit(null, null, null, null, null);
    }
  }

  public record ApprovalBinding(
      String contractVersion,
      String contractDigest,
      String requestId,
      String decisionState,
      String recordCorrelationId) {
    static ApprovalBinding empty() {
      return new ApprovalBinding(null, null, null, null, null);
    }
  }
}
