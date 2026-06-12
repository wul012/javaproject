package com.codexdemo.orderplatform.ops.maintenance.readability;

final class ReadabilityUpkeepAuditTestSupport {

    private ReadabilityUpkeepAuditTestSupport() {
    }

    static ReadabilityUpkeepAuditService service() {
        return new ReadabilityUpkeepAuditService();
    }

    static ReadabilityUpkeepAuditResponse audit() {
        return service().audit();
    }
}
