package com.codexdemo.orderplatform.ops;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessService {

  public static final String ENDPOINT = "/api/v1/ops/shard-readiness";
  public static final String FIXTURE_ENDPOINT = "/contracts/java-shard-readiness-v153.fixture.json";
  public static final String EVIDENCE_PATH = "e/153/evidence/java-shard-readiness-v153.json";

  @Transactional(readOnly = true)
  public OpsShardReadinessResponse readiness() {
    return new OpsShardReadinessResponse(
        "advanced-order-platform",
        "Java v153",
        true,
        false,
        false,
        0,
        0,
        "fixture",
        EVIDENCE_PATH,
        "passed");
  }
}
