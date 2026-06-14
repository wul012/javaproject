package com.codexdemo.orderplatform.ops.maintenance.credentialresolver;

import com.codexdemo.orderplatform.ops.ReleaseApprovalRehearsalResponse;
import java.util.List;

final
class OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveVerificationCatalog {

  private
  OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveVerificationCatalog() {}

  static List<
          OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
              .VerificationGate>
      gates(
          ReleaseApprovalRehearsalResponse rehearsal,
          List<
                  OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
                      .SourceReceipt>
              sourceReceipts,
          List<
                  OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
                      .EvidenceRequirement>
              javaRequirements,
          List<
                  OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
                      .EvidenceRequirement>
              miniKvRequirements,
          List<
                  OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
                      .FakeHarnessBoundary>
              fakeHarnessBoundaries,
          List<
                  OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
                      .RuntimeGuard>
              runtimeGuards) {
    return List.of(
        gate(
            "source-implementation-plan-echo-ready",
            "readyForNodeV284CredentialResolverImplementationPlanEchoVerification=true",
            sourceReceipts.stream()
                .allMatch(
                    source ->
                        source.nodeVerificationReady()
                            && "Node v284".equals(source.nextNodeVerificationVersion()))),
        gate(
            "java-v121-requirements-frozen",
            "javaV121EchoRequirements.size=4",
            javaRequirements.size()
                == OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveSupport
                    .EXPECTED_JAVA_REQUIREMENT_COUNT),
        gate(
            "mini-kv-v126-requirements-frozen",
            "miniKvV126ReceiptRequirements.size=4",
            miniKvRequirements.size()
                == OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveSupport
                    .EXPECTED_MINI_KV_REQUIREMENT_COUNT),
        gate(
            "fake-harness-boundary-present",
            "TEST_ONLY_FAKE_HARNESS_CONTRACT archived",
            fakeHarnessBoundaries.size()
                == OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveSupport
                    .EXPECTED_FAKE_HARNESS_BOUNDARY_COUNT),
        gate(
            "fake-harness-remains-deferred",
            "fakeHarnessDeferredUntil=Node v285 and precheck=false",
            sourceReceipts.stream()
                .allMatch(
                    source ->
                        "Node v285".equals(source.fakeHarnessDeferredUntil())
                            && !source.fakeHarnessPrecheckReady())),
        gate(
            "runtime-side-effects-blocked",
            "all runtime guard records passed",
            runtimeGuards.stream()
                .allMatch(
                    OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
                            .RuntimeGuard
                        ::passed)),
        gate(
            "receipt-warnings-empty",
            "implementation plan receipt warnings empty",
            rehearsal
                .managedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt()
                .receiptWarnings()
                .isEmpty()),
        gate(
            "not-production-audit-record",
            "nodeMayTreatAsProductionAuditRecord=false",
            !rehearsal
                .managedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt()
                .nodeMayTreatAsProductionAuditRecord()));
  }

  private static OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
          .VerificationGate
      gate(String name, String evidence, boolean passed) {
    return new OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
        .VerificationGate(name, evidence, passed);
  }
}
