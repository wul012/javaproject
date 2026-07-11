package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEchoResponse;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEchoService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessHardeningResponse;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessHardeningService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessResponse;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ops")
public class OpsShardReadinessController {

  private final OpsShardReadinessService opsShardReadinessService;

  private final OpsShardReadinessHardeningService opsShardReadinessHardeningService;

  private final OpsShardReadinessEchoService opsShardReadinessEchoService;

  public OpsShardReadinessController(
      OpsShardReadinessService opsShardReadinessService,
      OpsShardReadinessHardeningService opsShardReadinessHardeningService,
      OpsShardReadinessEchoService opsShardReadinessEchoService) {
    this.opsShardReadinessService = opsShardReadinessService;
    this.opsShardReadinessHardeningService = opsShardReadinessHardeningService;
    this.opsShardReadinessEchoService = opsShardReadinessEchoService;
  }

  @GetMapping("/shard-readiness")
  public OpsShardReadinessResponse shardReadiness() {
    return opsShardReadinessService.readiness();
  }

  @GetMapping("/shard-readiness/hardening")
  public OpsShardReadinessHardeningResponse shardReadinessHardening() {
    return opsShardReadinessHardeningService.hardening();
  }

  @GetMapping("/shard-readiness/echo")
  public OpsShardReadinessEchoResponse shardReadinessEcho() {
    return opsShardReadinessEchoService.echo();
  }
}
