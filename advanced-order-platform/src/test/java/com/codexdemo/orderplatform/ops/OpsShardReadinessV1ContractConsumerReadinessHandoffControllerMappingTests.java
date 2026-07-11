package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerEvidenceDigestService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffResponse;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffService;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;

class OpsShardReadinessV1ContractConsumerReadinessHandoffControllerMappingTests {

  @Test
  void keepsControllerMethodMappedToReadinessHandoffRoute() throws NoSuchMethodException {
    Method method =
        OpsShardReadinessV1ContractController.class.getMethod("consumerReadinessHandoff");
    GetMapping mapping = method.getAnnotation(GetMapping.class);

    assertThat(method.getReturnType())
        .isEqualTo(OpsShardReadinessV1ContractConsumerReadinessHandoffResponse.class);
    assertThat(mapping).isNotNull();
    assertThat(mapping.value())
        .containsExactly(OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_READINESS_HANDOFF);
  }

  @Test
  void keepsControllerConstructorInjectionOrderedAfterEvidenceDigestService() {
    Class<?>[] parameterTypes =
        OpsShardReadinessV1ContractController.class.getConstructors()[0].getParameterTypes();

    assertThat(Arrays.asList(parameterTypes))
        .containsSubsequence(
            OpsShardReadinessV1ContractConsumerEvidenceDigestService.class,
            OpsShardReadinessV1ContractConsumerReadinessHandoffService.class);
  }
}
