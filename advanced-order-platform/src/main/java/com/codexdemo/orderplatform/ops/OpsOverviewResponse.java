package com.codexdemo.orderplatform.ops;

import java.time.Instant;
import java.util.List;

public record OpsOverviewResponse(
        Instant sampledAt,
        Application application,
        Orders orders,
        Inventory inventory,
        Outbox outbox,
        FailedEvents failedEvents
) {

    public record Application(
            String name,
            List<String> profiles,
            Instant startedAt,
            long uptimeSeconds
    ) {
    }

    public record Orders(long total) {
    }

    public record Inventory(long items) {
    }

    public record Outbox(long pending) {
    }

    public record FailedEvents(
            long total,
            long pendingReplayApprovals,
            Instant latestFailedAt
    ) {
    }
}
