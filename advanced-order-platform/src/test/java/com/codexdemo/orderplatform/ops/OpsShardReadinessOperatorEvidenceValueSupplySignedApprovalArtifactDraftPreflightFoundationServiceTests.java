package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFoundationServiceTests {

    @Test
    void exposesCatalogWithoutManualDraftCreation() {
        var response = new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCatalogService()
                .catalog();

        assertThat(response.version()).isEqualTo("Java v796");
        assertThat(response.sourcePlan()).isEqualTo("Node v1111");
        assertThat(response.sourceArtifactPreflightVersion()).isEqualTo("Node v1086");
        assertThat(response.sourceJavaDraftReadinessVersion()).isEqualTo("Java v784");
        assertThat(response.readyForDraftPreflight()).isTrue();
        assertThat(response.readyForManualDraft()).isFalse();
        assertThat(response.readyForSignatureCapture()).isFalse();
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.fieldCount()).isEqualTo(25);
        assertThat(response.guardCount()).isEqualTo(25);
        assertThat(response.gateCount()).isEqualTo(20);
        assertThat(response.status()).isEqualTo("passed");
    }

    @Test
    void exposesDigestChainWithoutMaterialization() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightDigestChainService()
                        .digestChain();

        assertThat(response.version()).isEqualTo("Java v797");
        assertThat(response.readyForManualDraft()).isFalse();
        assertThat(response.draftMaterializationState()).isEqualTo("not-materialized");
        assertThat(response.fieldCount()).isEqualTo(4);
        assertThat(response.guardCount()).isEqualTo(4);
        assertThat(response.gateCount()).isEqualTo(2);
    }

    @Test
    void exposesOperatorWindowWithoutGrantOrWriteRoute() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightOperatorWindowService()
                        .operatorWindow();

        assertThat(response.version()).isEqualTo("Java v798");
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.readyForRuntimePayload()).isFalse();
        assertThat(response.fieldCount()).isEqualTo(4);
        assertThat(response.guardCount()).isEqualTo(4);
        assertThat(response.gateCount()).isEqualTo(2);
    }

    @Test
    void exposesSignatureStatementWithoutSignatureMaterial() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSignatureStatementService()
                        .signatureStatement();

        assertThat(response.version()).isEqualTo("Java v799");
        assertThat(response.readyForSignatureCapture()).isFalse();
        assertThat(response.readyForOperatorValueSubmission()).isFalse();
        assertThat(response.fieldCount()).isEqualTo(5);
        assertThat(response.guardCount()).isEqualTo(5);
        assertThat(response.gateCount()).isEqualTo(3);
    }
}
