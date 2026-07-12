package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupDigestService {

  static final String ENDPOINT = RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.DIGEST;

  static final String PROFILE = "java-shard-readiness-route-cleanup-digest.v1";

  static final String ALGORITHM = "SHA-256";

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupDigestResponse digest() {
    List<String> sources =
        List.of(
            OpsShardReadinessRouteCleanupEvidenceService.ENDPOINT,
            OpsShardReadinessRouteCleanupPhaseSummaryService.ENDPOINT,
            OpsShardReadinessRouteCleanupBoundaryMatrixService.ENDPOINT,
            OpsShardReadinessRouteCleanupHandoffChecklistService.ENDPOINT,
            OpsShardReadinessRouteCleanupArchivePlanService.ENDPOINT);
    String input =
        String.join(
            "|",
            OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
            String.valueOf(OpsShardReadinessRouteCleanupEvidenceAnalyzer.entries().size()),
            OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus(),
            String.join(",", sources));
    return new OpsShardReadinessRouteCleanupDigestResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        ALGORITHM,
        input,
        sha256(input),
        sources.size(),
        sources,
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus());
  }

  private String sha256(String value) {
    try {
      MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
      return HexFormat.of().formatHex(digest.digest(value.getBytes(StandardCharsets.UTF_8)));
    } catch (NoSuchAlgorithmException exception) {
      throw new IllegalStateException("SHA-256 digest is not available", exception);
    }
  }
}
