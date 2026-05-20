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
class OpsOverviewStaticRollbackContractsIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void staticRollbackApprovalHandoffSampleExplainsHumanConfirmationBoundary() throws Exception {
        mockMvc.perform(get("/contracts/rollback-approval-handoff.sample.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.handoffVersion").value("java-rollback-approval-handoff.v1"))
                .andExpect(jsonPath("$.scenario").value("ROLLBACK_APPROVAL_HANDOFF_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.handoffEndpoint")
                        .value("/contracts/rollback-approval-handoff.sample.json"))
                .andExpect(jsonPath("$.approvalMode").value("OPERATOR_CONFIRMATION_REQUIRED"))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("artifact-version-target")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("runtime-config-profile")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("configuration-secret-source")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("production-secret-source-contract")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("production-deployment-runbook-contract")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("database-migration-direction")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("release-handoff-checklist-fixture")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("release-audit-retention-fixture")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("rollback-approval-record-fixture")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("rollback-sql-review-gate")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("release-bundle-manifest")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("deployment-rollback-evidence")))
                .andExpect(jsonPath("$.handoffArtifacts",
                        hasItem("/contracts/release-handoff-checklist.fixture.json")))
                .andExpect(jsonPath("$.handoffArtifacts",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.handoffArtifacts",
                        hasItem("/contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.handoffArtifacts",
                        hasItem("/contracts/deployment-rollback-evidence.sample.json")))
                .andExpect(jsonPath("$.handoffArtifacts",
                        hasItem("/contracts/rollback-approval-record.fixture.json")))
                .andExpect(jsonPath("$.handoffArtifacts",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.handoffArtifacts",
                        hasItem("/contracts/production-secret-source-contract.sample.json")))
                .andExpect(jsonPath("$.handoffArtifacts",
                        hasItem("/contracts/production-deployment-runbook-contract.sample.json")))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayRenderChecklist").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayExecuteRollbackSql").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayModifyRuntimeConfig").value(false))
                .andExpect(jsonPath("$.nodeConsumption.requiresUpstreamActionsEnabled").value(false))
                .andExpect(jsonPath("$.boundaries.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Executing database rollback SQL from this handoff")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Triggering Java rollback from Node")));
    }

    @Test
    void staticRollbackApprovalRecordFixtureExplainsNoExecutionBoundary() throws Exception {
        mockMvc.perform(get("/contracts/rollback-approval-record.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fixtureVersion")
                        .value("java-rollback-approval-record-fixture.v1"))
                .andExpect(jsonPath("$.scenario").value("ROLLBACK_APPROVAL_RECORD_FIXTURE_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.fixtureEndpoint")
                        .value("/contracts/rollback-approval-record.fixture.json"))
                .andExpect(jsonPath("$.fixtureMode").value("READ_ONLY_APPROVAL_RECORD_FIXTURE"))
                .andExpect(jsonPath("$.approvalRecord.reviewer").value("rollback-reviewer-placeholder"))
                .andExpect(jsonPath("$.approvalRecord.approvalTimestampPlaceholder")
                        .value("approval-timestamp-placeholder"))
                .andExpect(jsonPath("$.approvalRecord.rollbackTarget")
                        .value("release-tag-or-artifact-version-placeholder"))
                .andExpect(jsonPath("$.approvalRecord.operatorMustReplacePlaceholders").value(true))
                .andExpect(jsonPath("$.databaseMigration.directionOptions",
                        hasItem("rollback-script-reviewed")))
                .andExpect(jsonPath("$.databaseMigration.selectedDirection").value("no-database-change"))
                .andExpect(jsonPath("$.databaseMigration.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.databaseMigration.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.requiredRecordFields[*].name", hasItem("reviewer")))
                .andExpect(jsonPath("$.requiredRecordFields[*].name",
                        hasItem("approval-timestamp-placeholder")))
                .andExpect(jsonPath("$.requiredRecordFields[*].name", hasItem("rollback-target")))
                .andExpect(jsonPath("$.requiredRecordFields[*].name",
                        hasItem("no-secret-value-boundary")))
                .andExpect(jsonPath("$.recordArtifacts",
                        hasItem("/contracts/rollback-approval-handoff.sample.json")))
                .andExpect(jsonPath("$.recordArtifacts",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.noSecretValueBoundaries",
                        hasItem("Secret values must not be read by Java or Node when rendering this record")))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayRenderReleaseWindowPacket").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayExecuteRollbackSql").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayReadSecretValues").value(false))
                .andExpect(jsonPath("$.boundaries.rollbackExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Executing Java rollback from this fixture")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Reading production secret values from this fixture")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Triggering Java rollback from Node")));
    }

    @Test
    void staticProductionSecretSourceContractSampleExplainsSecretValueBoundary() throws Exception {
        mockMvc.perform(get("/contracts/production-secret-source-contract.sample.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contractVersion").value("java-production-secret-source-contract.v1"))
                .andExpect(jsonPath("$.scenario").value("PRODUCTION_SECRET_SOURCE_CONTRACT_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.contractEndpoint")
                        .value("/contracts/production-secret-source-contract.sample.json"))
                .andExpect(jsonPath("$.contractMode").value("READ_ONLY_SECRET_SOURCE_CONTRACT"))
                .andExpect(jsonPath("$.secretSource.selectedSourceType").value("external-secret-manager"))
                .andExpect(jsonPath("$.secretSource.allowedSourceTypes",
                        hasItem("platform-managed-secret")))
                .andExpect(jsonPath("$.secretSource.secretManagerOwner").value("platform-security-owner"))
                .andExpect(jsonPath("$.rotationPolicy.rotationOwner").value("security-operations-owner"))
                .andExpect(jsonPath("$.rotationPolicy.reviewCadence")
                        .value("quarterly-or-before-production-cutover"))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("secret-value-access-boundary")))
                .andExpect(jsonPath("$.secretValueBoundaries",
                        hasItem("Secret values are never read by this contract")))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayRenderChecklist").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayReadSecretValues").value(false))
                .andExpect(jsonPath("$.nodeConsumption.requiresUpstreamActionsEnabled").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Reading production secret values from this contract")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Embedding secret values in static JSON samples")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Triggering Java runtime configuration changes from Node")));
    }

    @Test
    void staticProductionDeploymentRunbookContractSampleExplainsNoExecutionBoundary() throws Exception {
        mockMvc.perform(get("/contracts/production-deployment-runbook-contract.sample.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contractVersion")
                        .value("java-production-deployment-runbook-contract.v1"))
                .andExpect(jsonPath("$.scenario").value("PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.contractEndpoint")
                        .value("/contracts/production-deployment-runbook-contract.sample.json"))
                .andExpect(jsonPath("$.contractMode").value("READ_ONLY_DEPLOYMENT_RUNBOOK_CONTRACT"))
                .andExpect(jsonPath("$.deploymentWindow.owner").value("release-window-owner"))
                .andExpect(jsonPath("$.deploymentWindow.rollbackApprover").value("rollback-approval-owner"))
                .andExpect(jsonPath("$.databaseMigration.directionOptions",
                        hasItem("rollback-script-reviewed")))
                .andExpect(jsonPath("$.databaseMigration.selectedDirection").value("no-database-change"))
                .andExpect(jsonPath("$.secretSourceConfirmation.endpoint")
                        .value("/contracts/production-secret-source-contract.sample.json"))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("deployment-window-owner")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("rollback-approver")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("release-audit-retention-fixture")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("release-operator-signoff-fixture")))
                .andExpect(jsonPath("$.runbookArtifacts",
                        hasItem("/contracts/release-handoff-checklist.fixture.json")))
                .andExpect(jsonPath("$.runbookArtifacts",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.runbookArtifacts",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.runbookArtifacts",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerDeployment").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayExecuteRollbackSql").value(false))
                .andExpect(jsonPath("$.boundaries.sqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Executing Java deployment from this runbook contract")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Executing rollback SQL from this runbook contract")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Reading production secret values from this runbook contract")));
    }

    @Test
    void staticRollbackSqlReviewGateSampleExplainsSqlReviewBoundary() throws Exception {
        mockMvc.perform(get("/contracts/rollback-sql-review-gate.sample.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gateVersion").value("java-rollback-sql-review-gate.v1"))
                .andExpect(jsonPath("$.scenario").value("ROLLBACK_SQL_REVIEW_GATE_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.gateEndpoint")
                        .value("/contracts/rollback-sql-review-gate.sample.json"))
                .andExpect(jsonPath("$.gateMode").value("READ_ONLY_SQL_REVIEW_GATE"))
                .andExpect(jsonPath("$.reviewOwner").value("database-release-owner"))
                .andExpect(jsonPath("$.requiredReviewFields[*].name",
                        hasItem("rollback-sql-review-owner")))
                .andExpect(jsonPath("$.requiredReviewFields[*].name",
                        hasItem("migration-direction")))
                .andExpect(jsonPath("$.requiredReviewFields[*].name",
                        hasItem("operator-approval-placeholder")))
                .andExpect(jsonPath("$.requiredReviewFields[*].name",
                        hasItem("rollback-sql-artifact-reference")))
                .andExpect(jsonPath("$.migrationDirectionOptions",
                        hasItem("rollback-script-reviewed")))
                .andExpect(jsonPath("$.operatorApprovalPlaceholder")
                        .value("operator-approval-required-before-any-sql-execution"))
                .andExpect(jsonPath("$.handoffArtifacts",
                        hasItem("/contracts/rollback-approval-handoff.sample.json")))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayRenderPreflight").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayExecuteRollbackSql").value(false))
                .andExpect(jsonPath("$.nodeConsumption.requiresUpstreamActionsEnabled").value(false))
                .andExpect(jsonPath("$.boundaries.sqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Executing rollback SQL from this sample")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Connecting to a production database from this sample")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Triggering Java rollback from Node")));
    }
}
