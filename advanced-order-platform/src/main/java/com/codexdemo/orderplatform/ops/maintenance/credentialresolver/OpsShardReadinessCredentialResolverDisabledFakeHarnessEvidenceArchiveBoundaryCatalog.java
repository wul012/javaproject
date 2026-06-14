package com.codexdemo.orderplatform.ops.maintenance.credentialresolver;

import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverImplementationPlanEchoRecords.RehearsalManagedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt;
import java.util.List;

final class OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveBoundaryCatalog {

  private static final String FAKE_HARNESS_BOUNDARY_CODE = "TEST_ONLY_FAKE_HARNESS_CONTRACT";

  private OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveBoundaryCatalog() {}

  static List<
          OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
              .FakeHarnessBoundary>
      fakeHarnessBoundaries(
          RehearsalManagedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt
              receipt) {
    return receipt.interfaceBoundaries().stream()
        .filter(boundary -> FAKE_HARNESS_BOUNDARY_CODE.equals(boundary.code()))
        .map(
            boundary ->
                new OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
                    .FakeHarnessBoundary(
                    boundary.code(),
                    boundary.sourceBoundary(),
                    boundary.title(),
                    boundary.owner(),
                    boundary.status(),
                    boundary.allowedInputs(),
                    boundary.allowedOutputs(),
                    boundary.prohibitedActions(),
                    boundary.requiredArtifacts(),
                    boundary.verificationRule(),
                    "drafted-for-upstream-echo".equals(boundary.status())
                        && boundary.requiredArtifacts().contains("test-only-fake-harness-plan-id")
                        && boundary.requiredArtifacts().contains("fake-harness-disabled-toggle")
                        && boundary.prohibitedActions().contains("send-real-http-request")))
        .toList();
  }
}
