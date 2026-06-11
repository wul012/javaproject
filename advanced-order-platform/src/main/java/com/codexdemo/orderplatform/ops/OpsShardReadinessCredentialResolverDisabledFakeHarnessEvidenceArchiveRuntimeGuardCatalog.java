package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverImplementationPlanEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt;
import java.util.List;

final class OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveRuntimeGuardCatalog {

    private OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveRuntimeGuardCatalog() {
    }

    static List<OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.RuntimeGuard> guards(
            RehearsalManagedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceipt receipt
    ) {
        var boundary = receipt.sideEffectBoundary();
        return List.of(
                guard("real-resolver-implementation", "realResolverImplementationAllowed=false",
                        !boundary.realResolverImplementationAllowed()),
                guard("test-only-fake-harness-precheck", "readyForTestOnlyFakeHarnessPrecheck=false",
                        !boundary.readyForTestOnlyFakeHarnessPrecheck()),
                guard("test-only-fake-harness-runtime", "testOnlyFakeHarnessAllowed=false",
                        !boundary.testOnlyFakeHarnessAllowed()),
                guard("credential-value", "credentialValueRead=false", !boundary.credentialValueRead()),
                guard("raw-endpoint", "rawEndpointUrlParsed=false", !boundary.rawEndpointUrlParsed()),
                guard("external-request", "externalRequestSent=false", !boundary.externalRequestSent()),
                guard("provider-client", "secretProviderInstantiated=false,resolverClientInstantiated=false",
                        !boundary.secretProviderInstantiated() && !boundary.resolverClientInstantiated()),
                guard("ledger-write", "approvalLedgerWritten=false,managedAuditStoreWritten=false",
                        !boundary.approvalLedgerWritten() && !boundary.managedAuditStoreWritten()),
                guard("schema-sql", "sqlExecuted=false,schemaMigrationExecuted=false",
                        !boundary.sqlExecuted() && !boundary.schemaMigrationExecuted()),
                guard("auto-start", "automaticUpstreamStart=false,javaStartedNodeOrMiniKv=false",
                        !boundary.automaticUpstreamStart() && !boundary.javaStartedNodeOrMiniKv())
        );
    }

    private static OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.RuntimeGuard guard(
            String name,
            String evidence,
            boolean passed
    ) {
        return new OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse
                .RuntimeGuard(name, evidence, passed);
    }
}
