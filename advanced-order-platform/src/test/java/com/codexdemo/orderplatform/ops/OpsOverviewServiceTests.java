package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.codexdemo.orderplatform.inventory.InventoryRepository;
import com.codexdemo.orderplatform.notification.FailedEventMessageRepository;
import com.codexdemo.orderplatform.notification.FailedEventReplayApprovalStatus;
import com.codexdemo.orderplatform.order.OrderRepository;
import com.codexdemo.orderplatform.outbox.OutboxRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

class OpsOverviewServiceTests {

    private final OrderRepository orderRepository = org.mockito.Mockito.mock(OrderRepository.class);

    private final InventoryRepository inventoryRepository = org.mockito.Mockito.mock(InventoryRepository.class);

    private final OutboxRepository outboxRepository = org.mockito.Mockito.mock(OutboxRepository.class);

    private final FailedEventMessageRepository failedEventMessageRepository =
            org.mockito.Mockito.mock(FailedEventMessageRepository.class);

    @Test
    void aggregatesReadOnlySignalsForOpsOverview() {
        MockEnvironment environment = new MockEnvironment()
                .withProperty("spring.application.name", "advanced-order-platform");
        environment.setActiveProfiles("local", "ops");
        OpsOverviewService service = new OpsOverviewService(
                orderRepository,
                inventoryRepository,
                outboxRepository,
                failedEventMessageRepository,
                environment
        );

        when(orderRepository.count()).thenReturn(12L);
        when(inventoryRepository.count()).thenReturn(4L);
        when(outboxRepository.countByPublishedAtIsNull()).thenReturn(3L);
        when(failedEventMessageRepository.count()).thenReturn(5L);
        when(failedEventMessageRepository.countByReplayApprovalStatus(FailedEventReplayApprovalStatus.PENDING))
                .thenReturn(2L);
        when(failedEventMessageRepository.findTopByOrderByFailedAtDescIdDesc()).thenReturn(Optional.empty());

        OpsOverviewResponse overview = service.overview();

        assertThat(overview.application().name()).isEqualTo("advanced-order-platform");
        assertThat(overview.application().profiles()).containsExactly("local", "ops");
        assertThat(overview.application().startedAt()).isNotNull();
        assertThat(overview.application().uptimeSeconds()).isNotNegative();
        assertThat(overview.orders().total()).isEqualTo(12L);
        assertThat(overview.inventory().items()).isEqualTo(4L);
        assertThat(overview.outbox().pending()).isEqualTo(3L);
        assertThat(overview.failedEvents().total()).isEqualTo(5L);
        assertThat(overview.failedEvents().pendingReplayApprovals()).isEqualTo(2L);
        assertThat(overview.failedEvents().latestFailedAt()).isNull();
    }
}
