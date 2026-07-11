package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalResponse;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalTestSupport;
import org.junit.jupiter.api.Test;

class OpsEvidenceServiceReleaseApprovalRehearsalSummaryOverviewTests
    extends ReleaseApprovalRehearsalTestSupport {

  @Test
  void buildsReleaseApprovalRehearsalSummaryOverviewForDefaultRequest() {
    OpsEvidenceService service = readOnlyFixtureService();

    ReleaseApprovalRehearsalResponse rehearsal = service.releaseApprovalRehearsal();
    assertThat(rehearsal.failureTaxonomy().taxonomyVersion())
        .isEqualTo("java-release-approval-rehearsal-failure-taxonomy.v1");
    assertThat(rehearsal.failureTaxonomy().upstreamReadiness()).isEqualTo("READY");
    assertThat(rehearsal.failureTaxonomy().authContextReadiness()).isEqualTo("WARNING");
    assertThat(rehearsal.failureTaxonomy().auditCorrelationReadiness()).isEqualTo("WARNING");
    assertThat(rehearsal.failureTaxonomy().javaReadOnlyUpstreamReady()).isTrue();
    assertThat(rehearsal.failureTaxonomy().authContextComplete()).isFalse();
    assertThat(rehearsal.failureTaxonomy().auditCorrelationPresent()).isFalse();
    assertThat(rehearsal.failureTaxonomy().retryableByReadOnlyAdapter()).isTrue();
    assertThat(rehearsal.failureTaxonomy().writeActionRequired()).isFalse();
    assertThat(rehearsal.failureTaxonomy().failureCategories())
        .containsExactly(
            "AUTH_CONTEXT_WARNING", "AUDIT_CORRELATION_WARNING", "READ_ONLY_EXECUTION_BLOCKED");
    assertThat(rehearsal.failureTaxonomy().taxonomyWarnings())
        .containsExactly(
            "REQUEST_ID_OR_OPERATOR_IDENTITY_MISSING",
            "AUDIT_CORRELATION_ID_MISSING",
            "REHEARSAL_REMAINS_READ_ONLY");
    assertThat(rehearsal.evidenceExportHint().exportHintVersion())
        .isEqualTo("java-release-approval-rehearsal-evidence-export-hint.v1");
    assertThat(rehearsal.evidenceExportHint().responseSchemaVersion())
        .isEqualTo("java-release-approval-rehearsal-response-schema.v52");
    assertThat(rehearsal.evidenceExportHint().currentJsonEndpoint())
        .isEqualTo("/api/v1/ops/release-approval-rehearsal");
    assertThat(rehearsal.evidenceExportHint().sourceEvidenceEndpoint())
        .isEqualTo("/api/v1/ops/evidence");
    assertThat(rehearsal.evidenceExportHint().preferredArtifactName())
        .isEqualTo("release-approval-rehearsal-current.json");
    assertThat(rehearsal.evidenceExportHint().readOnly()).isTrue();
    assertThat(rehearsal.evidenceExportHint().stableCurrentResponse()).isTrue();
    assertThat(rehearsal.evidenceExportHint().historicalFallbackAllowed()).isTrue();
    assertThat(rehearsal.evidenceExportHint().requiresCredentialValue()).isFalse();
    assertThat(rehearsal.evidenceExportHint().parsesRawEndpointUrl()).isFalse();
    assertThat(rehearsal.evidenceExportHint().executesNetworkRequest()).isFalse();
    assertThat(rehearsal.evidenceExportHint().writesLedgerOrSchema()).isFalse();
    assertThat(rehearsal.evidenceExportHint().startsUpstreamProcess()).isFalse();
    assertThat(rehearsal.evidenceExportHint().requiredConsumerChecks())
        .containsExactly(
            "responseSchemaVersion",
            "verificationHint.warningDigest",
            "verificationHint.noLedgerWriteProved",
            "executionBoundaries.nodeMayWriteApprovalLedger",
            "executionBoundaries.nodeMayTriggerRollback");
    assertThat(rehearsal.evidenceExportHint().prohibitedConsumerActions())
        .contains(
            "credential_value_read",
            "raw_endpoint_url_parse",
            "deployment_or_rollback_execution",
            "automatic_upstream_start");
    assertThat(rehearsal.inputHardeningDecisionEcho().echoVersion())
        .isEqualTo("java-release-approval-rehearsal-input-hardening-decision-echo.v1");
    assertThat(rehearsal.inputHardeningDecisionEcho().sourceNodeVersion()).isEqualTo("Node v329");
    assertThat(rehearsal.inputHardeningDecisionEcho().sourceProfileVersion())
        .isEqualTo(
            "managed-audit-manual-sandbox-connection-credential-resolver-implementation-candidate-gate-input-hardening-decision.v1");
    assertThat(rehearsal.inputHardeningDecisionEcho().sourceCandidateGateDecision())
        .isEqualTo("require-input-export-hardening-before-disabled-runtime-design");
    assertThat(rehearsal.inputHardeningDecisionEcho().sourceDecisionDigest())
        .isEqualTo("e92109116e9d106ffdaa5691aa2eaa626fb595f8a9f4020dbf903fbce7874e87");
    assertThat(rehearsal.inputHardeningDecisionEcho().consumedEvidenceExportMode())
        .isEqualTo("stable-read-only-current-response");
    assertThat(rehearsal.inputHardeningDecisionEcho().readOnlyEcho()).isTrue();
    assertThat(rehearsal.inputHardeningDecisionEcho().consumesNodeV329()).isTrue();
    assertThat(rehearsal.inputHardeningDecisionEcho().stableJavaEvidenceExportAvailable()).isTrue();
    assertThat(
            rehearsal.inputHardeningDecisionEcho().readyForNodeV330CandidateGateUpstreamAlignment())
        .isFalse();
    assertThat(rehearsal.inputHardeningDecisionEcho().readyForDisabledRuntimeShellDesignDraft())
        .isFalse();
    assertThat(rehearsal.inputHardeningDecisionEcho().requiresCredentialValue()).isFalse();
    assertThat(rehearsal.inputHardeningDecisionEcho().parsesRawEndpointUrl()).isFalse();
    assertThat(rehearsal.inputHardeningDecisionEcho().opensManagedAuditConnection()).isFalse();
    assertThat(rehearsal.inputHardeningDecisionEcho().executesNetworkRequest()).isFalse();
    assertThat(rehearsal.inputHardeningDecisionEcho().writesApprovalLedger()).isFalse();
    assertThat(rehearsal.inputHardeningDecisionEcho().triggersDeploymentOrRollback()).isFalse();
    assertThat(rehearsal.inputHardeningDecisionEcho().satisfiedJavaInputHardeningRequirements())
        .containsExactly("java-stable-evidence-export");
    assertThat(rehearsal.inputHardeningDecisionEcho().acceptedNoGoConditions())
        .contains(
            "CREDENTIAL_VALUE_REQUIRED",
            "NETWORK_REQUEST_REQUIRED",
            "JAVA_WRITE_REQUIRED",
            "AUTO_START_REQUIRED");
    assertThat(rehearsal.releaseApprovalInputs().releaseOperatorSignoffFixtureEndpoint())
        .isEqualTo("/contracts/release-operator-signoff.fixture.json");
    assertThat(rehearsal.releaseApprovalInputs().rollbackApproverEvidenceFixtureEndpoint())
        .isEqualTo("/contracts/rollback-approver-evidence.fixture.json");
    assertThat(rehearsal.releaseApprovalInputs().rollbackApprovalRecordFixtureEndpoint())
        .isEqualTo("/contracts/rollback-approval-record.fixture.json");
    assertThat(rehearsal.releaseApprovalInputs().releaseBundleManifestEndpoint())
        .isEqualTo("/contracts/release-bundle-manifest.sample.json");
    assertThat(rehearsal.releaseApprovalInputs().requiredEvidenceEndpoints())
        .containsExactly(
            "/contracts/release-operator-signoff.fixture.json",
            "/contracts/rollback-approver-evidence.fixture.json",
            "/contracts/rollback-approval-record.fixture.json",
            "/contracts/release-bundle-manifest.sample.json",
            "/contracts/release-verification-manifest.sample.json",
            "/contracts/deployment-rollback-evidence.sample.json",
            "/contracts/production-deployment-runbook-contract.sample.json",
            "/contracts/production-secret-source-contract.sample.json",
            "/contracts/rollback-sql-review-gate.sample.json");
    assertThat(rehearsal.liveSignals().pendingReplayApprovals()).isEqualTo(2);
    assertThat(rehearsal.liveSignals().approvedReplayApprovals()).isEqualTo(1);
    assertThat(rehearsal.liveSignals().rejectedReplayApprovals()).isEqualTo(1);
    assertThat(rehearsal.liveSignals().replayBacklog()).isEqualTo(3);
    assertThat(rehearsal.liveSignals().pendingOutboxEvents()).isEqualTo(6);
    assertThat(rehearsal.liveSignals().realReplayAllowedByEvidence()).isFalse();
    assertThat(rehearsal.liveSignals().approvalExecutionDryRun()).isTrue();
    assertThat(rehearsal.liveSignals().evidenceExecutionAllowed()).isFalse();
    assertThat(rehearsal.executionBoundaries().nodeMayConsume()).isTrue();
    assertThat(rehearsal.executionBoundaries().nodeMayCreateApprovalDecision()).isFalse();
    assertThat(rehearsal.executionBoundaries().nodeMayWriteApprovalLedger()).isFalse();
    assertThat(rehearsal.executionBoundaries().nodeMayTriggerDeployment()).isFalse();
    assertThat(rehearsal.executionBoundaries().nodeMayTriggerRollback()).isFalse();
    assertThat(rehearsal.executionBoundaries().nodeMayExecuteRollbackSql()).isFalse();
    assertThat(rehearsal.executionBoundaries().requiresProductionDatabase()).isFalse();
    assertThat(rehearsal.executionBoundaries().requiresProductionSecrets()).isFalse();
    assertThat(rehearsal.executionBoundaries().changesOrderTransactionSemantics()).isFalse();
    assertThat(rehearsal.rehearsalBlockers())
        .contains(
            "READ_ONLY_RELEASE_APPROVAL_REHEARSAL",
            "APPROVAL_DECISION_CREATION_DISABLED",
            "ROLLBACK_SQL_EXECUTION_DISABLED",
            "REPLAY_APPROVAL_PENDING");
    assertThat(rehearsal.requiredNodeEnvironment())
        .containsExactly("UPSTREAM_PROBES_ENABLED=true", "UPSTREAM_ACTIONS_ENABLED=false");
    assertThat(rehearsal.nextEvidenceActions())
        .containsExactly(
            "GET /api/v1/ops/evidence",
            "GET /api/v1/ops/release-approval-rehearsal",
            "GET /contracts/release-operator-signoff.fixture.json",
            "GET /contracts/rollback-approver-evidence.fixture.json",
            "GET /contracts/rollback-approval-record.fixture.json",
            "Keep UPSTREAM_ACTIONS_ENABLED=false");
  }
}
