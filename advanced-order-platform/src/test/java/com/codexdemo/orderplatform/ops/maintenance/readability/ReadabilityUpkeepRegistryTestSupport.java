package com.codexdemo.orderplatform.ops.maintenance.readability;

final class ReadabilityUpkeepRegistryTestSupport {

    private ReadabilityUpkeepRegistryTestSupport() {
    }

    static ReadabilityUpkeepRegistryService service() {
        return new ReadabilityUpkeepRegistryService();
    }

    static ReadabilityUpkeepRegistryResponse registry() {
        return service().registry();
    }
}
