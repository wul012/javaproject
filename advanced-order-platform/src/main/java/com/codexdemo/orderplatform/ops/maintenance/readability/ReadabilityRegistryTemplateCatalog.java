package com.codexdemo.orderplatform.ops.maintenance.readability;

import java.util.List;

final class ReadabilityRegistryTemplateCatalog {

    private ReadabilityRegistryTemplateCatalog() {
    }

    static List<ReadabilityUpkeepRegistryResponse.RegistryTemplateRule> templateRules() {
        return List.of(
                rule("response-version", "Service", "RESPONSE_VERSION", true),
                rule("endpoint", "Route paths and service", "ENDPOINT and route constant", true),
                rule("profile", "Service", "stable response profile string", true),
                rule("read-only-transaction", "Service", "@Transactional(readOnly = true)", true),
                rule("response-record", "Response", "record with flags, counts, checks, status", true),
                rule("catalog-data", "Catalog", "stable coded catalog entries", true),
                rule("renderer", "Renderer", "markdown sections generated from catalog data", true),
                rule("support", "Support", "count and status calculation", true),
                rule("controller", "Controller", "single GET route with no mutation", true),
                rule("tests", "Tests", "route, service, renderer, boundary, controller checks", true)
        );
    }

    private static ReadabilityUpkeepRegistryResponse.RegistryTemplateRule rule(
            String code,
            String requiredLayer,
            String evidence,
            boolean required
    ) {
        return new ReadabilityUpkeepRegistryResponse.RegistryTemplateRule(
                code,
                requiredLayer,
                evidence,
                required
        );
    }
}
