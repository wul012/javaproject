package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupCompletionAuditDigestService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_COMPLETION_AUDIT_DIGEST;

  static final String PROFILE = "java-shard-readiness-route-cleanup-completion-audit-digest.v1";

  static final String ALGORITHM = "SHA-256";

  private final OpsShardReadinessRouteCleanupFixtureCoverageIndexService
      fixtureCoverageIndexService;

  private final OpsShardReadinessRouteCleanupTagManifestService tagManifestService;

  private final OpsShardReadinessRouteCleanupArchiveHandoffReceiptService
      archiveHandoffReceiptService;

  public OpsShardReadinessRouteCleanupCompletionAuditDigestService(
      OpsShardReadinessRouteCleanupFixtureCoverageIndexService fixtureCoverageIndexService,
      OpsShardReadinessRouteCleanupTagManifestService tagManifestService,
      OpsShardReadinessRouteCleanupArchiveHandoffReceiptService archiveHandoffReceiptService) {
    this.fixtureCoverageIndexService = fixtureCoverageIndexService;
    this.tagManifestService = tagManifestService;
    this.archiveHandoffReceiptService = archiveHandoffReceiptService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupCompletionAuditDigestResponse digest() {
    OpsShardReadinessRouteCleanupFixtureCoverageIndexResponse coverage =
        fixtureCoverageIndexService.index();
    OpsShardReadinessRouteCleanupTagManifestResponse tags = tagManifestService.manifest();
    OpsShardReadinessRouteCleanupArchiveHandoffReceiptResponse receipt =
        archiveHandoffReceiptService.receipt();
    List<String> sources =
        List.of(
            OpsShardReadinessRouteCleanupFixtureCoverageIndexService.ENDPOINT,
            OpsShardReadinessRouteCleanupTagManifestService.ENDPOINT,
            OpsShardReadinessRouteCleanupArchiveHandoffReceiptService.ENDPOINT);
    String input =
        String.join(
            "|",
            OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
            coverage.status(),
            String.valueOf(coverage.coverageItemCount()),
            tags.status(),
            String.valueOf(tags.tagCount()),
            receipt.status(),
            receipt.receiptId(),
            String.join(",", sources));
    boolean passed =
        coverage.status().equals("passed")
            && tags.status().equals("passed")
            && receipt.status().equals("passed");
    return new OpsShardReadinessRouteCleanupCompletionAuditDigestResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        ALGORITHM,
        sha256(input),
        sources.size(),
        sources,
        passed ? "passed" : "blocked");
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
