package com.codexdemo.orderplatform.ops.maintenance.prototype;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEchoResponse;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEchoService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessResponse;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1Contract;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessPrototypeEvidenceService {

  public static final String CATALOG_ENDPOINT = PrototypeRoutes.BASE_PATH + PrototypeRoutes.CATALOG;
  static final String FIXTURE_ECHO_ENDPOINT =
      PrototypeRoutes.BASE_PATH + PrototypeRoutes.FIXTURE_ECHO;
  public static final String FIELD_ALIGNMENT_ENDPOINT =
      PrototypeRoutes.BASE_PATH + PrototypeRoutes.FIELD_ALIGNMENT;
  static final String READ_ONLY_INTEGRATION_BRIDGE_ENDPOINT =
      PrototypeRoutes.BASE_PATH + PrototypeRoutes.READ_ONLY_BRIDGE;
  static final String ROUTE_CLEANUP_BRIDGE_ENDPOINT =
      PrototypeRoutes.BASE_PATH + PrototypeRoutes.CLEANUP_BRIDGE;
  static final String READ_WINDOW_HANDOFF_ENDPOINT =
      PrototypeRoutes.BASE_PATH + PrototypeRoutes.READ_WINDOW_HANDOFF;
  static final String CONSUMER_GATE_PACKET_ENDPOINT =
      PrototypeRoutes.BASE_PATH + PrototypeRoutes.CONSUMER_GATE_PACKET;
  static final String OPERATOR_CI_HANDOFF_ENDPOINT =
      PrototypeRoutes.BASE_PATH + PrototypeRoutes.OPERATOR_CI_HANDOFF;
  static final String AUDIT_DIGEST_ENDPOINT =
      PrototypeRoutes.BASE_PATH + PrototypeRoutes.AUDIT_DIGEST;
  static final String CLOSEOUT_ENDPOINT = PrototypeRoutes.BASE_PATH + PrototypeRoutes.CLOSEOUT;

  private static final String PROJECT = "advanced-order-platform";

  private static final String CATALOG_PROFILE = "java-shard-readiness-prototype-catalog.v1";

  private final OpsShardReadinessService readinessService;

  private final OpsShardReadinessEchoService echoService;

  private final CloseoutSource closeoutSource;

  public OpsShardReadinessPrototypeEvidenceService(
      OpsShardReadinessService readinessService,
      OpsShardReadinessEchoService echoService,
      CloseoutSource closeoutSource) {
    this.readinessService = readinessService;
    this.echoService = echoService;
    this.closeoutSource = closeoutSource;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessPrototypeCatalogResponse catalog() {
    List<OpsShardReadinessPrototypeEvidenceCatalog.Entry> entries =
        OpsShardReadinessPrototypeEvidenceCatalog.entries();
    return new OpsShardReadinessPrototypeCatalogResponse(
        PROJECT,
        entries.getLast().version(),
        true,
        false,
        CATALOG_ENDPOINT,
        CATALOG_PROFILE,
        OpsShardReadinessV1Contract.CONTRACT_NAME,
        entries.size(),
        entries,
        OpsShardReadinessV1Contract.minimalFields(),
        forbiddenOperations(),
        entries.stream().allMatch(entry -> !entry.checks().isEmpty()) ? "passed" : "blocked");
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessPrototypeEvidenceResponse fixtureEcho() {
    return evidence("prototype-fixture-echo");
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessPrototypeEvidenceResponse fieldAlignment() {
    return evidence("prototype-field-alignment");
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessPrototypeEvidenceResponse readOnlyIntegrationBridge() {
    return evidence("prototype-read-only-integration-bridge");
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessPrototypeEvidenceResponse routeCleanupBridge() {
    return evidence("prototype-route-cleanup-bridge");
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessPrototypeEvidenceResponse readWindowHandoff() {
    return evidence("prototype-read-window-handoff");
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessPrototypeEvidenceResponse consumerGatePacket() {
    return evidence("prototype-consumer-gate-packet");
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessPrototypeEvidenceResponse operatorCiHandoff() {
    return evidence("prototype-operator-ci-handoff");
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessPrototypeEvidenceResponse auditDigest() {
    return evidence("prototype-audit-digest");
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessPrototypeEvidenceResponse closeout() {
    return evidence("prototype-closeout");
  }

  OpsShardReadinessPrototypeEvidenceResponse evidence(String key) {
    OpsShardReadinessPrototypeEvidenceCatalog.Entry entry =
        OpsShardReadinessPrototypeEvidenceCatalog.entryFor(key);
    OpsShardReadinessResponse readiness = readinessService.readiness();
    OpsShardReadinessEchoResponse echo = echoService.echo();
    CloseoutSnapshot closeout = closeoutSource.snapshot();
    List<String> evidenceRefs =
        List.of(
            "root-readiness:" + readiness.evidencePath(),
            "echo:" + echo.evidencePath(),
            "route-cleanup-closeout:" + closeout.postCompletionCloseoutEndpoint());
    String status = evidenceStatus(readiness, echo, closeout);
    return new OpsShardReadinessPrototypeEvidenceResponse(
        PROJECT,
        entry.version(),
        true,
        false,
        entry.endpoint(),
        entry.profile(),
        entry.key(),
        entry.phase(),
        entry.nodePlanVersion(),
        OpsShardReadinessV1Contract.CONTRACT_NAME,
        readiness.shardEnabled(),
        readiness.shardCount(),
        readiness.slotCount(),
        readiness.routingMode(),
        readiness.version(),
        echo.version(),
        closeout.version(),
        OpsShardReadinessV1Contract.minimalFields(),
        evidenceRefs,
        entry.checks(),
        forbiddenOperations(),
        digest(entry, readiness, echo, closeout),
        entry.evidencePath(),
        status);
  }

  private List<String> forbiddenOperations() {
    return List.of(
        "write-routing",
        "active-shard-router",
        "credential-value-read",
        "raw-endpoint-parse",
        "managed-audit-connection",
        "deployment-or-rollback",
        "node-start-or-stop-java-or-mini-kv");
  }

  private String evidenceStatus(
      OpsShardReadinessResponse readiness,
      OpsShardReadinessEchoResponse echo,
      CloseoutSnapshot closeout) {
    boolean passed =
        OpsShardReadinessV1Contract.alignsWithReadOnlyContract(readiness)
            && "passed".equals(echo.status())
            && "passed".equals(closeout.status())
            && !echo.executionAllowed()
            && !closeout.executionAllowed();
    return passed ? "passed" : "blocked";
  }

  private String digest(
      OpsShardReadinessPrototypeEvidenceCatalog.Entry entry,
      OpsShardReadinessResponse readiness,
      OpsShardReadinessEchoResponse echo,
      CloseoutSnapshot closeout) {
    String material =
        String.join(
            "|",
            entry.version(),
            entry.key(),
            entry.profile(),
            readiness.version(),
            echo.version(),
            closeout.version(),
            entry.evidencePath());
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      return HexFormat.of().formatHex(digest.digest(material.getBytes(StandardCharsets.UTF_8)));
    } catch (NoSuchAlgorithmException ex) {
      throw new IllegalStateException("SHA-256 digest is not available", ex);
    }
  }

  public interface CloseoutSource {

    CloseoutSnapshot snapshot();
  }

  public record CloseoutSnapshot(
      String version,
      boolean executionAllowed,
      String postCompletionCloseoutEndpoint,
      String status) {}

  public static final class PrototypeRoutes {

    public static final String BASE_PATH = OpsShardReadinessService.BASE_PATH;
    public static final String CATALOG = "/prototype-catalog";
    public static final String FIXTURE_ECHO = "/prototype-fixture-echo";
    public static final String FIELD_ALIGNMENT = "/prototype-field-alignment";
    public static final String READ_ONLY_BRIDGE = "/prototype-read-only-integration-bridge";
    public static final String CLEANUP_BRIDGE = "/prototype-route-cleanup-bridge";
    public static final String READ_WINDOW_HANDOFF = "/prototype-read-window-handoff";
    public static final String CONSUMER_GATE_PACKET = "/prototype-consumer-gate-packet";
    public static final String OPERATOR_CI_HANDOFF = "/prototype-operator-ci-handoff";
    public static final String AUDIT_DIGEST = "/prototype-audit-digest";
    public static final String CLOSEOUT = "/prototype-closeout";
    public static final String HANDOFF_CATALOG = "/prototype-handoff-catalog";
    public static final String HANDOFF_ENDPOINT_INVENTORY = "/prototype-handoff-endpoint-inventory";
    public static final String HANDOFF_BOUNDARY_MATRIX = "/prototype-handoff-boundary-matrix";
    public static final String HANDOFF_CONSUMER_CHECKLIST =
        "/prototype-handoff-consumer-verification-checklist";
    public static final String HANDOFF_READ_WINDOW_CHECKLIST =
        "/prototype-handoff-read-window-checklist";
    public static final String HANDOFF_DIGEST_MANIFEST = "/prototype-handoff-digest-manifest";
    public static final String HANDOFF_CI_MANIFEST = "/prototype-handoff-ci-manifest";
    public static final String HANDOFF_ARCHIVE_MANIFEST = "/prototype-handoff-archive-manifest";
    public static final String HANDOFF_OPERATOR_SIGNOFF =
        "/prototype-handoff-operator-signoff-packet";
    public static final String HANDOFF_CLOSEOUT = "/prototype-handoff-closeout";
    public static final String CONSUMER_CATALOG = "/prototype-consumer-gate-catalog";
    public static final String CONSUMER_SOURCE_INVENTORY =
        "/prototype-consumer-gate-source-inventory";
    public static final String CONSUMER_FIELD_CHECKLIST =
        "/prototype-consumer-gate-minimal-field-checklist";
    public static final String CONSUMER_ROUTE_PREVIEW =
        "/prototype-consumer-gate-route-topology-preview";
    public static final String CONSUMER_BOUNDARY_MATRIX =
        "/prototype-consumer-gate-boundary-matrix";
    public static final String CONSUMER_DIGEST_ACCEPTANCE =
        "/prototype-consumer-gate-digest-acceptance";
    public static final String CONSUMER_CI_PLAN = "/prototype-consumer-gate-ci-batch-plan";
    public static final String CONSUMER_ARCHIVE_MANIFEST =
        "/prototype-consumer-gate-archive-manifest";
    public static final String CONSUMER_OPERATOR_SIGNOFF =
        "/prototype-consumer-gate-operator-signoff";
    public static final String CONSUMER_CLOSEOUT = "/prototype-consumer-gate-closeout";

    private PrototypeRoutes() {}
  }
}
