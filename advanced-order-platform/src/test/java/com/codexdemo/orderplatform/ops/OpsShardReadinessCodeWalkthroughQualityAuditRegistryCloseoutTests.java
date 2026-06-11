package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughQualityAuditRegistryCloseoutTests {

    @Test
    void closesOutAuditWithBothBatchesAndRequiredVerificationSteps() {
        var response = OpsShardReadinessCodeWalkthroughQualityAuditRegistryTestSupport.registry();

        assertThat(response.batchAssessments())
                .extracting(OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse
                        .BatchAssessment::versionRange)
                .containsExactly("v1748-v1753", "v1754-v1758");
        assertThat(response.verificationSteps())
                .extracting(OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse
                        .VerificationStep::name)
                .containsExactly(
                        "quality-audit-targeted-tests",
                        "walkthrough-archive-compliance",
                        "quality-gate-regression",
                        "full-maven-regression",
                        "remote-ci"
                );
        assertThat(response.checks())
                .contains(
                        "code-walkthrough-quality-audit-audited-batch-Java v1748-v1753",
                        "code-walkthrough-quality-audit-verification-step-count-5"
                );
    }
}
