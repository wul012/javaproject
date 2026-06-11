package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.notification.FailedEventSummaryService;
import com.codexdemo.orderplatform.order.IdempotencyStore;
import com.codexdemo.orderplatform.outbox.OutboxRepository;

final class OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveTestSupport {

    private OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveTestSupport() {
    }

    static OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveService service() {
        return new OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveService(
                OpsEvidenceServiceTestFixtures.readOnlyFixtureService(
                        org.mockito.Mockito.mock(FailedEventSummaryService.class),
                        org.mockito.Mockito.mock(OutboxRepository.class),
                        org.mockito.Mockito.mock(IdempotencyStore.class)
                )
        );
    }

    static OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse archive() {
        return service().archive();
    }
}
