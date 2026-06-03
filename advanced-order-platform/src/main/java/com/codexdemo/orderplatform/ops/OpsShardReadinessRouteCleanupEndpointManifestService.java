package com.codexdemo.orderplatform.ops;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupEndpointManifestService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_ENDPOINT_MANIFEST;

    static final String PROFILE = "java-shard-readiness-route-cleanup-endpoint-manifest.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupEndpointManifestResponse manifest() {
        List<OpsShardReadinessRouteCleanupEndpointManifestResponse.EndpointEntry> endpoints =
                Arrays.stream(OpsShardReadinessRoutePaths.class.getDeclaredFields())
                        .filter(this::isRouteCleanupConstant)
                        .sorted(Comparator.comparing(Field::getName))
                        .map(this::endpointEntry)
                        .toList();
        return new OpsShardReadinessRouteCleanupEndpointManifestResponse(
                "advanced-order-platform",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
                true,
                false,
                ENDPOINT,
                PROFILE,
                endpoints.size(),
                endpoints,
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus()
        );
    }

    private boolean isRouteCleanupConstant(Field field) {
        return Modifier.isStatic(field.getModifiers())
                && field.getType().equals(String.class)
                && field.getName().startsWith("ROUTE_CLEANUP_");
    }

    private OpsShardReadinessRouteCleanupEndpointManifestResponse.EndpointEntry endpointEntry(Field field) {
        String route = stringValue(field);
        return new OpsShardReadinessRouteCleanupEndpointManifestResponse.EndpointEntry(
                field.getName(),
                route,
                OpsShardReadinessRoutePaths.BASE_PATH + route,
                true,
                false,
                "passed"
        );
    }

    private String stringValue(Field field) {
        try {
            return (String) field.get(null);
        } catch (IllegalAccessException exception) {
            throw new IllegalStateException("Cannot read route cleanup endpoint constant " + field.getName(), exception);
        }
    }
}
