package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryTestSupport {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryTestSupport() {
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService
            sourceDigestService() {
        return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService(
                new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService(
                        new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService(
                                new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService(
                                        new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService()
                                )
                        )
                )
        );
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryService
            service() {
        return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryService(
                sourceDigestService()
        );
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
            registry() {
        return service().registry();
    }
}
