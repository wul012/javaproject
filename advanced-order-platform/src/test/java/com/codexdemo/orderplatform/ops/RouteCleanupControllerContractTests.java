package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;

class RouteCleanupControllerContractTests {

  private static final List<Class<?>> CONTROLLERS =
      List.of(
          OpsShardReadinessRouteCleanupEvidenceController.class,
          OpsShardReadinessRouteCleanupSummaryController.class,
          OpsShardReadinessRouteCleanupGovernanceController.class,
          OpsShardReadinessRouteCleanupHandoffController.class,
          OpsShardReadinessRouteCleanupAssuranceController.class,
          OpsShardReadinessRouteCleanupCompletionController.class,
          OpsShardReadinessRouteCleanupPostCompletionController.class);

  @Test
  void delegatesEveryGetEndpoint() throws ReflectiveOperationException {
    for (Class<?> controllerType : CONTROLLERS) {
      Constructor<?> constructor = controllerType.getDeclaredConstructors()[0];
      Object[] services =
          Arrays.stream(constructor.getParameterTypes()).map(type -> mock(type)).toArray();
      Object controller = constructor.newInstance(services);

      for (Method endpoint : getEndpoints(controllerType)) {
        assertThat(endpoint.invoke(controller)).isNull();
      }

      assertThat(services)
          .allSatisfy(
              service -> {
                assertThat(mockingDetails(service).getInvocations()).hasSize(1);
                assertThat(mockingDetails(service).getInvocations().iterator().next().getMethod())
                    .returns(0, Method::getParameterCount);
              });
    }
  }

  private static List<Method> getEndpoints(Class<?> controllerType) {
    return Arrays.stream(controllerType.getDeclaredMethods())
        .filter(method -> method.isAnnotationPresent(GetMapping.class))
        .toList();
  }
}
