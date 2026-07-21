package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierBoundaryTests {

  @Test
  void preconditionEvidenceAndBoundariesStayClosed() {
    var response = DossierTestData.dossier();

    assertThat(response.preconditionEvidence())
        .extracting(
            OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                    .PreconditionEvidence
                ::id)
        .containsExactly(
            "owner-approval-artifact-id-field",
            "credential-handle-review-field",
            "schema-rehearsal-evidence-field",
            "rollback-path-field",
            "timeout-budget",
            "manual-abort-marker-field");
    assertThat(response.preconditionEvidence())
        .allSatisfy(
            evidence -> {
              assertThat(evidence.required()).isTrue();
              assertThat(evidence.present()).isTrue();
            });
    assertThat(response.boundarySnapshots())
        .allSatisfy(
            boundary -> {
              assertThat(boundary.required()).isTrue();
              assertThat(boundary.closed()).isTrue();
            });
  }

  @Test
  void executionGuardsProveNoRuntimeExecution() {
    var response = DossierTestData.dossier();

    assertThat(response.executionGuards())
        .extracting(
            OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.ExecutionGuard
                ::name)
        .contains(
            "credential-value-read",
            "schema-migration-sql",
            "external-managed-audit-connection",
            "actual-connection-attempt");
    assertThat(response.executionGuards()).allSatisfy(guard -> assertThat(guard.passed()).isTrue());
    assertThat(response.warningEchoes())
        .extracting(
            OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.WarningEcho
                ::warning)
        .contains(
            "REHEARSAL_REQUEST_ID_MISSING",
            "OPERATOR_IDENTITY_MISSING",
            "AUDIT_CORRELATION_ID_MISSING",
            "NODE_V235_SOURCE_SANDBOX_CONNECTION_PREFLIGHT_ECHO_MARKER_NOT_READY");
  }
}
