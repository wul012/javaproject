package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePathsTests {

    @Test
    void approvalPreflightEndpointsUseSharedRouteConstants() {
        assertThat(Map.ofEntries(
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_CATALOG,
                        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCatalogService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_IDENTITY_SIGNATURE,
                        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightIdentitySignatureService.ENDPOINT
                )
        )).allSatisfy((route, endpoint) ->
                assertThat(endpoint).isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route));
    }
}
