package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import com.codexdemo.orderplatform.notification.FailedEventSummaryService;
import com.codexdemo.orderplatform.ops.OpsEvidenceService;
import com.codexdemo.orderplatform.ops.OpsEvidenceServiceTestFixtures;
import com.codexdemo.orderplatform.order.IdempotencyStore;
import com.codexdemo.orderplatform.outbox.OutboxRepository;

public abstract class ReleaseApprovalRehearsalTestSupport {

  private final FailedEventSummaryService failedEventSummaryService =
      org.mockito.Mockito.mock(FailedEventSummaryService.class);
  private final OutboxRepository outboxRepository =
      org.mockito.Mockito.mock(OutboxRepository.class);
  private final IdempotencyStore idempotencyStore =
      org.mockito.Mockito.mock(IdempotencyStore.class);

  protected OpsEvidenceService readOnlyFixtureService() {
    return OpsEvidenceServiceTestFixtures.readOnlyFixtureService(
        failedEventSummaryService, outboxRepository, idempotencyStore);
  }

  protected ReleaseApprovalRehearsalRequest paddedHeaderBackedRehearsalRequest() {
    return OpsEvidenceServiceTestFixtures.paddedHeaderBackedRehearsalRequest();
  }

  protected ReleaseApprovalRehearsalRequest headerBackedRehearsalRequest() {
    return OpsEvidenceServiceTestFixtures.headerBackedRehearsalRequest();
  }
}
