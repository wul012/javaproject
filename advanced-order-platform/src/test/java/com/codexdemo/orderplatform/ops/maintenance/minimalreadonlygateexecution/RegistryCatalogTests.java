package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.BoundaryRule;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.GateCheck;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.ReadTarget;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.SourcePlanEntry;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class RegistryCatalogTests {

  @Test
  void capturesNodeV349ThroughV367Lineage() {
    var sourcePlans = RegistryCatalog.evidence().sourcePlans();

    assertThat(sourcePlans)
        .hasSize(
            OpsShardReadinessMinimalReadOnlyGateExecutionRegistrySupport
                .EXPECTED_SOURCE_PLAN_COUNT);
    assertThat(sourcePlans)
        .extracting(SourcePlanEntry::nodeVersion)
        .containsExactly("Node v349", "Node v364", "Node v365", "Node v366", "Node v367");
    assertThat(sourcePlans.get(4).result()).isEqualTo("5/5 read targets and 20/20 checks passed");
    assertThat(sourcePlans.get(4).expectedChecks()).isEqualTo(20);
    assertThat(sourcePlans.get(4).passedChecks()).isEqualTo(20);
  }

  @Test
  void exposesFiveReadTargetsWithoutRawUrls() {
    var readTargets = RegistryCatalog.evidence().readTargets();

    assertThat(readTargets)
        .hasSize(
            OpsShardReadinessMinimalReadOnlyGateExecutionRegistrySupport
                .EXPECTED_READ_TARGET_COUNT);
    assertThat(readTargets)
        .extracting(ReadTarget::target)
        .containsExactly(
            "java-health",
            "java-ops-overview",
            "mini-kv-health",
            "mini-kv-infojson",
            "mini-kv-statsjson");
    assertThat(readTargets)
        .allSatisfy(
            target -> {
              assertThat(target.readOnly()).isTrue();
              assertThat(target.externallyStarted()).isTrue();
              assertThat(target.status()).isEqualTo("passed");
              assertThat(target.addressHandle()).doesNotContain("://");
            });
  }

  @Test
  void keepsChecksPassedAndBoundariesDenied() {
    var evidence = RegistryCatalog.evidence();

    assertThat(evidence.gateChecks())
        .hasSize(
            OpsShardReadinessMinimalReadOnlyGateExecutionRegistrySupport.EXPECTED_GATE_CHECK_COUNT)
        .allSatisfy(check -> assertThat(check.passed()).isTrue())
        .extracting(GateCheck::group)
        .contains("read-target", "runtime-boundary", "archive", "lineage");
    assertThat(evidence.gateChecks())
        .extracting(GateCheck::code)
        .contains("read-targets-five-of-five", "gate-checks-twenty-of-twenty");
    assertThat(evidence.boundaryRules())
        .hasSize(
            OpsShardReadinessMinimalReadOnlyGateExecutionRegistrySupport
                .EXPECTED_BOUNDARY_RULE_COUNT)
        .allSatisfy(rule -> assertThat(rule.allowed()).isFalse())
        .extracting(BoundaryRule::code)
        .contains(
            "no-write-routing",
            "no-credential-value",
            "no-raw-endpoint-url",
            "no-managed-audit-connection",
            "no-java-autostart",
            "no-mini-kv-write-admin");
  }

  @Test
  void evidenceOwnsAllSevenLists() {
    var original = RegistryCatalog.evidence();
    var sourcePlans = new ArrayList<>(original.sourcePlans());
    var readTargets = new ArrayList<>(original.readTargets());
    var gateChecks = new ArrayList<>(original.gateChecks());
    var boundaryRules = new ArrayList<>(original.boundaryRules());
    var ciBatches = new ArrayList<>(original.ciBatches());
    var archiveRequirements = new ArrayList<>(original.archiveRequirements());
    var operatorHandoffs = new ArrayList<>(original.operatorHandoffs());

    var evidence =
        new RegistryCatalog.Evidence(
            sourcePlans,
            readTargets,
            gateChecks,
            boundaryRules,
            ciBatches,
            archiveRequirements,
            operatorHandoffs);

    assertOwned(evidence.sourcePlans(), sourcePlans);
    assertOwned(evidence.readTargets(), readTargets);
    assertOwned(evidence.gateChecks(), gateChecks);
    assertOwned(evidence.boundaryRules(), boundaryRules);
    assertOwned(evidence.ciBatches(), ciBatches);
    assertOwned(evidence.archiveRequirements(), archiveRequirements);
    assertOwned(evidence.operatorHandoffs(), operatorHandoffs);
  }

  private static <T> void assertOwned(List<T> owned, List<T> source) {
    source.clear();
    assertThat(owned).isNotEmpty();
    assertThatThrownBy(() -> owned.add(owned.getFirst()))
        .isInstanceOf(UnsupportedOperationException.class);
  }
}
