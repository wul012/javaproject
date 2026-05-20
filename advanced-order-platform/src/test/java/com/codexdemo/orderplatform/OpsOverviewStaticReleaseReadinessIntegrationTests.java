package com.codexdemo.orderplatform;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
        "order.expiration.enabled=false",
        "outbox.publisher.enabled=false"
})
@AutoConfigureMockMvc
class OpsOverviewStaticReleaseReadinessIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void staticReleaseHandoffChecklistFixtureExplainsNoExecutionBoundary() throws Exception {
        mockMvc.perform(get("/contracts/release-handoff-checklist.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fixtureVersion")
                        .value("java-release-handoff-checklist-fixture.v1"))
                .andExpect(jsonPath("$.scenario").value("RELEASE_HANDOFF_CHECKLIST_FIXTURE_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.fixtureEndpoint")
                        .value("/contracts/release-handoff-checklist.fixture.json"))
                .andExpect(jsonPath("$.fixtureMode")
                        .value("READ_ONLY_RELEASE_HANDOFF_CHECKLIST_FIXTURE"))
                .andExpect(jsonPath("$.releaseChecklist.releaseOperator")
                        .value("release-operator-placeholder"))
                .andExpect(jsonPath("$.releaseChecklist.rollbackApprover")
                        .value("rollback-approver-placeholder"))
                .andExpect(jsonPath("$.releaseChecklist.artifactTarget")
                        .value("release-tag-or-artifact-version-placeholder"))
                .andExpect(jsonPath("$.releaseChecklist.operatorMustReplacePlaceholders").value(true))
                .andExpect(jsonPath("$.databaseMigration.directionOptions",
                        hasItem("rollback-script-reviewed")))
                .andExpect(jsonPath("$.databaseMigration.selectedDirection").value("no-database-change"))
                .andExpect(jsonPath("$.databaseMigration.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.databaseMigration.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.secretSourceConfirmation.endpoint")
                        .value("/contracts/production-secret-source-contract.sample.json"))
                .andExpect(jsonPath("$.secretSourceConfirmation.secretValueRecorded").value(false))
                .andExpect(jsonPath("$.requiredChecklistFields[*].name", hasItem("release-operator")))
                .andExpect(jsonPath("$.requiredChecklistFields[*].name", hasItem("rollback-approver")))
                .andExpect(jsonPath("$.requiredChecklistFields[*].name", hasItem("artifact-target")))
                .andExpect(jsonPath("$.requiredChecklistFields[*].name",
                        hasItem("secret-source-confirmation")))
                .andExpect(jsonPath("$.requiredChecklistFields[*].name",
                        hasItem("deployment-runbook-contract")))
                .andExpect(jsonPath("$.requiredChecklistFields[*].name",
                        hasItem("rollback-approval-record-fixture")))
                .andExpect(jsonPath("$.requiredChecklistFields[*].name",
                        hasItem("release-audit-retention-fixture")))
                .andExpect(jsonPath("$.requiredChecklistFields[*].name",
                        hasItem("release-operator-signoff-fixture")))
                .andExpect(jsonPath("$.checklistArtifacts",
                        hasItem("/contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.checklistArtifacts",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.checklistArtifacts",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.checklistArtifacts",
                        hasItem("/contracts/production-deployment-runbook-contract.sample.json")))
                .andExpect(jsonPath("$.checklistArtifacts",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.noSecretValueBoundaries",
                        hasItem("Secret values must not be read by Java or Node when rendering this checklist")))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayRenderReleaseHandoffReview").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerDeployment").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayExecuteRollbackSql").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayReadSecretValues").value(false))
                .andExpect(jsonPath("$.boundaries.deploymentExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Executing Java deployment from this fixture")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Reading production secret values from this fixture")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Triggering Java deployment from Node")));
    }

    @Test
    void staticReleaseAuditRetentionFixtureExplainsReadOnlyRetentionBoundary() throws Exception {
        mockMvc.perform(get("/contracts/release-audit-retention.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fixtureVersion")
                        .value("java-release-audit-retention-fixture.v1"))
                .andExpect(jsonPath("$.scenario").value("RELEASE_AUDIT_RETENTION_FIXTURE_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.fixtureEndpoint")
                        .value("/contracts/release-audit-retention.fixture.json"))
                .andExpect(jsonPath("$.fixtureMode")
                        .value("READ_ONLY_RELEASE_AUDIT_RETENTION_FIXTURE"))
                .andExpect(jsonPath("$.retentionRecord.retentionId")
                        .value("release-retention-record-placeholder"))
                .andExpect(jsonPath("$.retentionRecord.releaseOperator")
                        .value("release-operator-placeholder"))
                .andExpect(jsonPath("$.retentionRecord.artifactTarget")
                        .value("release-tag-or-artifact-version-placeholder"))
                .andExpect(jsonPath("$.retentionRecord.retentionDays").value(180))
                .andExpect(jsonPath("$.retentionRecord.operatorMustReplacePlaceholders").value(true))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/api/v1/failed-events/replay-evidence-index")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/release-handoff-checklist.fixture.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.auditExportFields[*].name", hasItem("retention-id")))
                .andExpect(jsonPath("$.auditExportFields[*].name",
                        hasItem("audit-export-location-placeholder")))
                .andExpect(jsonPath("$.auditExportFields[*].name",
                        hasItem("release-operator-signoff-fixture")))
                .andExpect(jsonPath("$.auditExportFields[*].name",
                        hasItem("no-secret-value-boundary")))
                .andExpect(jsonPath("$.retainedArtifacts",
                        hasItem("/contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.retainedArtifacts",
                        hasItem("/contracts/production-secret-source-contract.sample.json")))
                .andExpect(jsonPath("$.retainedArtifacts",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.noSecretValueBoundaries",
                        hasItem("Secret values must not be read by Java or Node when rendering this retention record")))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayRenderRetentionGate").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerDeployment").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayWriteAuditExport").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayReadSecretValues").value(false))
                .andExpect(jsonPath("$.boundaries.auditExportReadOnly").value(true))
                .andExpect(jsonPath("$.boundaries.deploymentExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Executing Java deployment from this fixture")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Writing audit export files from Node through this fixture")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Reading production secret values from this fixture")));
    }

    @Test
    void staticReleaseOperatorSignoffFixtureExplainsNoApprovalDecisionBoundary() throws Exception {
        mockMvc.perform(get("/contracts/release-operator-signoff.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fixtureVersion")
                        .value("java-release-operator-signoff-fixture.v1"))
                .andExpect(jsonPath("$.scenario").value("RELEASE_OPERATOR_SIGNOFF_FIXTURE_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.fixtureEndpoint")
                        .value("/contracts/release-operator-signoff.fixture.json"))
                .andExpect(jsonPath("$.fixtureMode")
                        .value("READ_ONLY_RELEASE_OPERATOR_SIGNOFF_FIXTURE"))
                .andExpect(jsonPath("$.signoffRecord.releaseOperator")
                        .value("release-operator-placeholder"))
                .andExpect(jsonPath("$.signoffRecord.rollbackApprover")
                        .value("rollback-approver-placeholder"))
                .andExpect(jsonPath("$.signoffRecord.releaseWindow")
                        .value("release-window-placeholder"))
                .andExpect(jsonPath("$.signoffRecord.artifactTarget")
                        .value("release-tag-or-artifact-version-placeholder"))
                .andExpect(jsonPath("$.signoffRecord.operatorSignoffPlaceholder")
                        .value("operator-signoff-placeholder"))
                .andExpect(jsonPath("$.signoffRecord.operatorMustReplacePlaceholders").value(true))
                .andExpect(jsonPath("$.requiredSignoffFields[*].name", hasItem("release-operator")))
                .andExpect(jsonPath("$.requiredSignoffFields[*].name", hasItem("rollback-approver")))
                .andExpect(jsonPath("$.requiredSignoffFields[*].name", hasItem("release-window")))
                .andExpect(jsonPath("$.requiredSignoffFields[*].name", hasItem("artifact-target")))
                .andExpect(jsonPath("$.requiredSignoffFields[*].name",
                        hasItem("operator-signoff-placeholder")))
                .andExpect(jsonPath("$.requiredSignoffFields[*].name",
                        hasItem("release-audit-retention-fixture")))
                .andExpect(jsonPath("$.signoffArtifacts",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.signoffArtifacts",
                        hasItem("/contracts/production-deployment-runbook-contract.sample.json")))
                .andExpect(jsonPath("$.noSecretValueBoundaries",
                        hasItem("Secret values must not be read by Java or Node when rendering this signoff")))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayRenderApprovalPrerequisiteGate").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayCreateApprovalDecision").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayWriteApprovalLedger").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerDeployment").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayExecuteRollbackSql").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayReadSecretValues").value(false))
                .andExpect(jsonPath("$.boundaries.deploymentExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.rollbackExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.approvalDecisionCreated").value(false))
                .andExpect(jsonPath("$.boundaries.approvalLedgerWriteAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Creating a real approval decision from this fixture")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Writing approval ledger entries from this fixture")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Executing Java deployment from this fixture")));
    }

    @Test
    void staticRollbackApproverEvidenceFixtureExplainsNoRollbackExecutionBoundary() throws Exception {
        mockMvc.perform(get("/contracts/rollback-approver-evidence.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fixtureVersion")
                        .value("java-rollback-approver-evidence-fixture.v1"))
                .andExpect(jsonPath("$.scenario").value("ROLLBACK_APPROVER_EVIDENCE_FIXTURE_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.fixtureEndpoint")
                        .value("/contracts/rollback-approver-evidence.fixture.json"))
                .andExpect(jsonPath("$.fixtureMode")
                        .value("READ_ONLY_ROLLBACK_APPROVER_EVIDENCE_FIXTURE"))
                .andExpect(jsonPath("$.approverEvidence.rollbackApprover")
                        .value("rollback-approver-placeholder"))
                .andExpect(jsonPath("$.approverEvidence.operatorMustReplacePlaceholders").value(true))
                .andExpect(jsonPath("$.approverEvidence.evidenceStatus")
                        .value("PENDING_OPERATOR_CONFIRMATION"))
                .andExpect(jsonPath("$.databaseMigration.directionOptions",
                        hasItem("rollback-script-reviewed")))
                .andExpect(jsonPath("$.databaseMigration.selectedDirection").value("no-database-change"))
                .andExpect(jsonPath("$.databaseMigration.rollbackSqlArtifactReference")
                        .value("rollback-sql-artifact-reference-placeholder"))
                .andExpect(jsonPath("$.databaseMigration.rollbackSqlTextEmbedded").value(false))
                .andExpect(jsonPath("$.databaseMigration.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.databaseMigration.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.databaseMigration.productionDatabaseBoundary")
                        .value("production-database-connection-outside-this-fixture"))
                .andExpect(jsonPath("$.requiredEvidenceFields[*].name", hasItem("rollback-approver")))
                .andExpect(jsonPath("$.requiredEvidenceFields[*].name",
                        hasItem("database-migration-direction")))
                .andExpect(jsonPath("$.requiredEvidenceFields[*].name",
                        hasItem("rollback-sql-artifact-reference")))
                .andExpect(jsonPath("$.requiredEvidenceFields[*].name",
                        hasItem("production-database-access-boundary")))
                .andExpect(jsonPath("$.requiredEvidenceFields[*].name",
                        hasItem("rollback-sql-review-gate")))
                .andExpect(jsonPath("$.evidenceArtifacts",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.evidenceArtifacts",
                        hasItem("/contracts/production-deployment-runbook-contract.sample.json")))
                .andExpect(jsonPath("$.noSecretValueBoundaries",
                        hasItem("Secret values must not be read by Java or Node when rendering this evidence")))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayRenderDecisionRehearsalInput").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayCreateApprovalDecision").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayWriteApprovalLedger").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayExecuteRollbackSql").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayReadSecretValues").value(false))
                .andExpect(jsonPath("$.boundaries.approvalDecisionCreated").value(false))
                .andExpect(jsonPath("$.boundaries.approvalLedgerWriteAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.rollbackExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Creating a real approval decision from this fixture")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Executing database rollback SQL from this fixture")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Connecting production database from this fixture")));
    }

}
