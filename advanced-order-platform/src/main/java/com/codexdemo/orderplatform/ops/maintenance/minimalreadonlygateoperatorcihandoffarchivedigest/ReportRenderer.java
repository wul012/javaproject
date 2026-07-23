package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse.BoundaryLock;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse.ConsumerPacket;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse.DigestSection;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse.ReplayInstruction;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse.ScorecardEntry;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse.SourceArchiveSnapshot;
import com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownSections;
import java.util.List;

final class ReportRenderer {

  private ReportRenderer() {}

  static List<MarkdownSection> render(DigestCatalog.Evidence evidence) {
    return List.of(
        sources(evidence.sourceArchiveSnapshots()),
        digests(evidence.digestSections()),
        packets(evidence.consumerPackets()),
        instructions(evidence.replayInstructions()),
        locks(evidence.boundaryLocks()),
        scorecard(evidence.scorecard()));
  }

  private static MarkdownSection sources(List<SourceArchiveSnapshot> entries) {
    return MarkdownSections.counted(
        "Source Archive",
        "source-archive-count",
        entries,
        source ->
            source.version()
                + " | "
                + source.endpoint()
                + " | "
                + source.archiveState()
                + " | status="
                + source.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection digests(List<DigestSection> entries) {
    return MarkdownSections.counted(
        "Digest Sections",
        "digest-section-count",
        entries,
        section ->
            section.name()
                + "="
                + section.sourcePassed()
                + "/"
                + section.sourceTotal()
                + " | "
                + section.evidence()
                + " | status="
                + section.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection packets(List<ConsumerPacket> entries) {
    return MarkdownSections.counted(
        "Consumer Packets",
        "consumer-packet-count",
        entries,
        packet ->
            packet.packet()
                + " | "
                + packet.owner()
                + " | digest="
                + packet.includesDigest()
                + " | boundary-locks="
                + packet.includesBoundaryLocks()
                + " | status="
                + packet.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection instructions(List<ReplayInstruction> entries) {
    return MarkdownSections.counted(
        "Replay Instructions",
        "replay-instruction-count",
        entries,
        instruction ->
            instruction.order()
                + ". "
                + instruction.batch()
                + " | "
                + instruction.commandFamily()
                + " | read-only="
                + instruction.readOnly()
                + " | status="
                + instruction.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection locks(List<BoundaryLock> entries) {
    return MarkdownSections.counted(
        "Boundary Locks",
        "boundary-lock-count",
        entries,
        lock -> lock.code() + " | locked=" + lock.locked() + " | " + lock.reason(),
        MarkdownSection::new);
  }

  private static MarkdownSection scorecard(List<ScorecardEntry> entries) {
    return MarkdownSections.counted(
        "Scorecard",
        "scorecard-entry-count",
        entries,
        score ->
            score.name()
                + "="
                + score.actual()
                + "/"
                + score.expected()
                + " | status="
                + score.status(),
        MarkdownSection::new);
  }
}
