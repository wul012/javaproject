package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionRegistryServiceTests {

    @Test
    void buildsReadOnlySignedApprovalDraftProfileSectionRegistryFromFiveRoutes() {
        var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v1237");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.readyForDraftProfileSectionRegistry()).isTrue();
        assertThat(response.sourcePlan()).isEqualTo("Node v1506");
        assertThat(response.sourceNodeProfileRendererVersion()).isEqualTo("Node v1506");
        assertThat(response.moduleCount()).isEqualTo(8);
        assertThat(response.sourceRouteCount()).isEqualTo(5);
        assertThat(response.sectionCount()).isEqualTo(5);
        assertThat(response.renderedSectionCount()).isEqualTo(5);
        assertThat(response.fieldEntryCount()).isEqualTo(30);
        assertThat(response.routeFieldLockCount()).isEqualTo(5);
        assertThat(response.lockedRouteFieldCount()).isEqualTo(25);
        assertThat(response.gateCount()).isEqualTo(46);
        assertThat(response.status()).isEqualTo("passed");
    }

    @Test
    void keepsDraftArtifactRuntimeAndMutationPathsClosed() {
        var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

        assertThat(response.draftArtifactCount()).isZero();
        assertThat(response.signedApprovalCount()).isZero();
        assertThat(response.runtimePayloadCount()).isZero();
        assertThat(response.writeOperationCount()).isZero();
        assertThat(response.siblingMutationCount()).isZero();
        assertThat(response.draftArtifactMaterializationAllowed()).isFalse();
        assertThat(response.signedApprovalCaptureAllowed()).isFalse();
        assertThat(response.approvalGrantAllowed()).isFalse();
        assertThat(response.valueImportAllowed()).isFalse();
        assertThat(response.runtimePayloadAllowed()).isFalse();
        assertThat(response.writeAllowed()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
    }
}
