package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import com.codexdemo.orderplatform.ops.OpsShardReadinessRoutePaths;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessV1ContractEndpointCatalogService {

  public static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.V1_CONTRACT_ENDPOINT_CATALOG;

  public static final String FIXTURE_ENDPOINT =
      "/contracts/java-shard-readiness-v1-contract-endpoint-catalog-v208.fixture.json";

  public static final String EVIDENCE_PATH =
      "e/208/evidence/java-shard-readiness-v1-contract-endpoint-catalog-v208.json";

  @Transactional(readOnly = true)
  public OpsShardReadinessV1ContractEndpointCatalogResponse catalog() {
    return OpsShardReadinessV1ContractEndpointCatalogSnapshot.v208Catalog();
  }
}
