package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffSectionCatalogTests {

    @Test
    void handoffSectionsKeepSignedApprovalDraftOrder() {
        var response = OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport.handoff();

        assertThat(response.sectionHandoffs())
                .extracting(OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse
                        .SectionHandoff::sectionCode)
                .containsExactly(
                        "signed-approval-artifact-draft-preflight-section",
                        "signed-approval-artifact-draft-readiness-section",
                        "signed-approval-artifact-draft-review-package-preflight-section",
                        "signed-approval-artifact-draft-authoring-readiness-section",
                        "signed-approval-artifact-draft-instruction-preflight-section");
    }

    @Test
    void handoffSectionsDeclareMetadataOnlyConsumerBoundary() {
        var response = OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport.handoff();

        assertThat(response.sectionHandoffs())
                .allSatisfy(section -> {
                    assertThat(section.handoffAction()).isEqualTo("handoff-section-metadata-only");
                    assertThat(section.consumerBoundary()).isEqualTo("read-only-consumer-no-execution");
                    assertThat(section.routeFieldCount()).isEqualTo(6);
                });
    }
}
