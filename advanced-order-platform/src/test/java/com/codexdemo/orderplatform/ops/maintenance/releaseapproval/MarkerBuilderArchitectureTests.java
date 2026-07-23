package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.Test;

class MarkerBuilderArchitectureTests {

  private static final Path ROOT =
      Path.of(
          "src",
          "main",
          "java",
          "com",
          "codexdemo",
          "orderplatform",
          "ops",
          "maintenance",
          "releaseapproval");

  private static final Map<String, Long> CAPS =
      Map.of(
          "DecisionMarkerBuilder.java", 276L,
          "DecisionMarkerRules.java", 460L,
          "DisabledPrecheckBuilder.java", 237L,
          "DisabledPrecheckRules.java", 489L,
          "EndpointPreflightBuilder.java", 291L,
          "EndpointPreflightRules.java", 299L,
          "MarkerEvidence.java", 26L);

  @Test
  void shortOwnersStayWithinTheirBudgets() throws IOException {
    for (var entry : CAPS.entrySet()) {
      Path source = ROOT.resolve(entry.getKey());
      assertThat(source).as(entry.getKey()).isRegularFile();
      try (var lines = Files.lines(source, StandardCharsets.UTF_8)) {
        assertThat(lines.count()).as(entry.getKey()).isLessThanOrEqualTo(entry.getValue());
      }
    }
  }

  @Test
  void buildersShareImmutableEvidenceMetadata() throws IOException {
    for (String builder :
        new String[] {
          "DecisionMarkerBuilder.java",
          "DisabledPrecheckBuilder.java",
          "EndpointPreflightBuilder.java"
        }) {
      assertThat(read(builder))
          .as(builder)
          .contains(
              "private static final MarkerEvidence EVIDENCE",
              "MarkerEvidence evidence()",
              "EVIDENCE.warningLines(")
          .doesNotContain(
              "List<String> warningDigestWarningInputNames()",
              "List<String> warningDigestBoundaryInputNames()",
              "List<String> proofClaims()",
              "List<String> nodeVerificationActions()");
    }
    assertThat(read("MarkerEvidence.java"))
        .contains(
            "boundaryInputNames = List.copyOf(boundaryInputNames)",
            "proofClaims = List.copyOf(proofClaims)",
            "nodeActions = List.copyOf(nodeActions)");
  }

  @Test
  void retiredOwnersStayDeleted() {
    assertThat(
            new String[] {
              "ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDecisionEchoMarkerBuilder.java",
              "ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder.java",
              "ReleaseApprovalManagedAuditSandboxEndpointHandlePreflightEchoMarkerBuilder.java",
              "ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverCredentialHandleApprovalContractEchoReceiptBuilder.java",
              "ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverEndpointHandleAllowlistApprovalContractEchoReceiptBuilder.java",
              "ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverNoNetworkSafetyFixtureContractEchoReceiptBuilder.java",
              "ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceiptBuilder.java"
            })
        .noneMatch(name -> Files.exists(ROOT.resolve(name)));
  }

  private static String read(String name) throws IOException {
    return Files.readString(ROOT.resolve(name), StandardCharsets.UTF_8);
  }
}
