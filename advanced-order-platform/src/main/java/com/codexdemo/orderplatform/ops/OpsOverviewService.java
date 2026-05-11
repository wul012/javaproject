package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.inventory.InventoryRepository;
import com.codexdemo.orderplatform.notification.FailedEventMessage;
import com.codexdemo.orderplatform.notification.FailedEventMessageRepository;
import com.codexdemo.orderplatform.notification.FailedEventReplayApprovalStatus;
import com.codexdemo.orderplatform.order.OrderRepository;
import com.codexdemo.orderplatform.outbox.OutboxRepository;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsOverviewService {

    private final Instant startedAt = Instant.now();

    private final OrderRepository orderRepository;

    private final InventoryRepository inventoryRepository;

    private final OutboxRepository outboxRepository;

    private final FailedEventMessageRepository failedEventMessageRepository;

    private final Environment environment;

    public OpsOverviewService(
            OrderRepository orderRepository,
            InventoryRepository inventoryRepository,
            OutboxRepository outboxRepository,
            FailedEventMessageRepository failedEventMessageRepository,
            Environment environment
    ) {
        this.orderRepository = orderRepository;
        this.inventoryRepository = inventoryRepository;
        this.outboxRepository = outboxRepository;
        this.failedEventMessageRepository = failedEventMessageRepository;
        this.environment = environment;
    }

    @Transactional(readOnly = true)
    public OpsOverviewResponse overview() {
        Instant sampledAt = Instant.now();
        return new OpsOverviewResponse(
                sampledAt,
                application(sampledAt),
                new OpsOverviewResponse.Orders(orderRepository.count()),
                new OpsOverviewResponse.Inventory(inventoryRepository.count()),
                new OpsOverviewResponse.Outbox(outboxRepository.countByPublishedAtIsNull()),
                failedEvents()
        );
    }

    private OpsOverviewResponse.Application application(Instant sampledAt) {
        return new OpsOverviewResponse.Application(
                environment.getProperty("spring.application.name", "advanced-order-platform"),
                profiles(),
                startedAt,
                Math.max(Duration.between(startedAt, sampledAt).toSeconds(), 0)
        );
    }

    private List<String> profiles() {
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length > 0) {
            return List.copyOf(Arrays.asList(activeProfiles));
        }
        return List.copyOf(Arrays.asList(environment.getDefaultProfiles()));
    }

    private OpsOverviewResponse.FailedEvents failedEvents() {
        return new OpsOverviewResponse.FailedEvents(
                failedEventMessageRepository.count(),
                failedEventMessageRepository.countByReplayApprovalStatus(FailedEventReplayApprovalStatus.PENDING),
                failedEventMessageRepository.findTopByOrderByFailedAtDescIdDesc()
                        .map(FailedEventMessage::getFailedAt)
                        .orElse(null)
        );
    }
}
