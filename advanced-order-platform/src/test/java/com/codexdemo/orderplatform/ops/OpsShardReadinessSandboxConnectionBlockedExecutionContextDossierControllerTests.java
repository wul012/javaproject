package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.notification.FailedEventSummaryService;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierService;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionRoutePaths;
import com.codexdemo.orderplatform.order.IdempotencyStore;
import com.codexdemo.orderplatform.outbox.OutboxRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierControllerTests {

  private static OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierService service() {
    return new OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierService(
        OpsEvidenceServiceTestFixtures.readOnlyFixtureService(
            Mockito.mock(FailedEventSummaryService.class),
            Mockito.mock(OutboxRepository.class),
            Mockito.mock(IdempotencyStore.class)));
  }

  private static OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
      dossier() {
    return service().dossier();
  }

  @Test
  void routeAndControllerExposeDossier() {
    assertThat(
            OpsShardReadinessSandboxConnectionRoutePaths
                .SANDBOX_CONNECTION_BLOCKED_EXECUTION_CONTEXT_NORMALIZATION_DOSSIER)
        .isEqualTo(
            OpsShardReadinessRoutePaths
                .SANDBOX_CONNECTION_BLOCKED_EXECUTION_CONTEXT_NORMALIZATION_DOSSIER);

    var response =
        new OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierController(service())
            .dossier();

    assertThat(response.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/sandbox-connection-blocked-execution-context-normalization-dossier");
    assertThat(response.checks())
        .contains(
            "sandbox-connection-blocked-execution-context-dossier-source-plan-Node v1982",
            "sandbox-connection-blocked-execution-context-dossier-java-context-Java v90",
            "sandbox-connection-blocked-execution-context-dossier-ready-for-retention");
  }

  @Test
  void rendererListsExpectedSections() {
    var response = dossier();

    assertThat(response.markdownSections())
        .extracting(
            OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.MarkdownSection
                ::heading)
        .containsExactly(
            "Source Receipt",
            "Context Fields",
            "Precondition Evidence",
            "Boundaries",
            "Execution Guards",
            "Warnings",
            "Downstream Intake",
            "Verification Gates",
            "Handoff Notes");
  }
}
