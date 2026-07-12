package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerService {

  static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.MAINTENANCE_ARCHIVE_DIGEST_LEDGER;
  static final String PROFILE =
      "java-shard-readiness-route-cleanup-maintenance-archive-digest-ledger.v1";
  private static final String ALGORITHM = "SHA-256";
  private static final int DIGEST_LENGTH = 16;

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerResponse ledger() {
    List<OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerResponse.LedgerEntry> entries =
        OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.items().stream()
            .map(this::entry)
            .toList();
    List<String> checks =
        List.of(
            "ledger-entry-count-" + entries.size(),
            "digests-use-sha-256-prefix",
            "digests-are-stable-from-catalog-fields",
            "ledger-does-not-read-archive-files",
            "archive-digest-ledger-remains-read-only");
    return new OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerResponse(
        "advanced-order-platform",
        "Java v499",
        true,
        false,
        ENDPOINT,
        PROFILE,
        entries.size(),
        ALGORITHM,
        DIGEST_LENGTH,
        entries,
        checks,
        status(entries));
  }

  private OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerResponse.LedgerEntry entry(
      OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.Item item) {
    return new OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerResponse.LedgerEntry(
        item.name(), item.endpoint(), item.evidencePath(), digest(item), item.status());
  }

  private String digest(OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.Item item) {
    try {
      MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
      byte[] bytes =
          digest.digest(
              (item.name() + "|" + item.endpoint() + "|" + item.evidencePath())
                  .getBytes(StandardCharsets.UTF_8));
      return HexFormat.of().formatHex(bytes).substring(0, DIGEST_LENGTH);
    } catch (NoSuchAlgorithmException exception) {
      throw new IllegalStateException("Missing digest algorithm: " + ALGORITHM, exception);
    }
  }

  private String status(
      List<OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerResponse.LedgerEntry>
          entries) {
    boolean passed =
        entries.size() == 9
            && entries.stream().allMatch(entry -> entry.digest().length() == DIGEST_LENGTH)
            && entries.stream()
                    .map(
                        OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerResponse
                                .LedgerEntry
                            ::digest)
                    .distinct()
                    .count()
                == entries.size()
            && entries.stream().allMatch(entry -> "passed".equals(entry.status()));
    return passed ? "passed" : "blocked";
  }
}
