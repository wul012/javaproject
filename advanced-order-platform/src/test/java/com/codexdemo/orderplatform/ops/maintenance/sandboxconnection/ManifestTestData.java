package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import com.codexdemo.orderplatform.notification.FailedEventSummaryService;
import com.codexdemo.orderplatform.ops.OpsEvidenceServiceTestFixtures;
import com.codexdemo.orderplatform.order.IdempotencyStore;
import com.codexdemo.orderplatform.outbox.OutboxRepository;

final class ManifestTestData {

  private ManifestTestData() {}

  static OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestService
      service() {
    return new OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestService(
        OpsEvidenceServiceTestFixtures.readOnlyFixtureService(
            org.mockito.Mockito.mock(FailedEventSummaryService.class),
            org.mockito.Mockito.mock(OutboxRepository.class),
            org.mockito.Mockito.mock(IdempotencyStore.class)));
  }

  static OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
      manifest() {
    return service().manifest();
  }
}
