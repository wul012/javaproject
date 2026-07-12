package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import com.codexdemo.orderplatform.ops.OpsShardReadinessRoutePaths;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupEndpointManifestService {

  public static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.ENDPOINT_MANIFEST;

  static final String PROFILE = "java-shard-readiness-route-cleanup-endpoint-manifest.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupEndpointManifestResponse manifest() {
    List<OpsShardReadinessRouteCleanupEndpointManifestResponse.EndpointEntry> endpoints =
        routeFields()
            .sorted(Comparator.comparing(this::manifestName))
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
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus());
  }

  private Stream<Field> routeFields() {
    Stream<Field> rootRoutes =
        Arrays.stream(OpsShardReadinessRoutePaths.class.getDeclaredFields())
            .filter(this::isRootRoute);
    Stream<Field> familyRoutes =
        Arrays.stream(RouteCleanupRoutes.class.getDeclaredFields()).filter(this::isFamilyRoute);
    return Stream.concat(rootRoutes, familyRoutes);
  }

  private boolean isRootRoute(Field field) {
    return Modifier.isStatic(field.getModifiers())
        && field.getType().equals(String.class)
        && field.getName().startsWith("ROUTE_CLEANUP_");
  }

  private boolean isFamilyRoute(Field field) {
    return Modifier.isStatic(field.getModifiers())
        && field.getType().equals(String.class)
        && !field.getName().equals("BASE_PATH");
  }

  private String manifestName(Field field) {
    return field.getDeclaringClass().equals(RouteCleanupRoutes.class)
        ? "ROUTE_CLEANUP_" + field.getName()
        : field.getName();
  }

  private OpsShardReadinessRouteCleanupEndpointManifestResponse.EndpointEntry endpointEntry(
      Field field) {
    String route = stringValue(field);
    return new OpsShardReadinessRouteCleanupEndpointManifestResponse.EndpointEntry(
        manifestName(field), route, RouteCleanupRoutes.BASE_PATH + route, true, false, "passed");
  }

  private String stringValue(Field field) {
    try {
      if (!field.trySetAccessible()) {
        throw new IllegalStateException(
            "Cannot access route cleanup endpoint constant " + field.getName());
      }
      return (String) field.get(null);
    } catch (IllegalAccessException exception) {
      throw new IllegalStateException(
          "Cannot read route cleanup endpoint constant " + field.getName(), exception);
    }
  }
}
