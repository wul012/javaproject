package com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffScorecardCatalog {

  private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffScorecardCatalog() {}

  static List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.ScorecardEntry>
      scorecard(
          List<
                  OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                      .SourceArchiveSnapshot>
              snapshots,
          List<
                  OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                      .VerificationRequirement>
              requirements,
          List<
                  OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                      .ArtifactCrossCheck>
              artifacts,
          List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.RouteHandoff>
              routes,
          List<
                  OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                      .OperatorInstruction>
              operators,
          List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.CiProof>
              ciProofs,
          List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.BoundaryGuard>
              boundaries,
          List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.RetentionGuard>
              retentions,
          List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.CloseoutHandoff>
              closeouts) {
    return List.of(
        score("source-archive", 1, passedSnapshots(snapshots)),
        score("verification-requirements", 8, passedRequirements(requirements)),
        score("artifact-cross-checks", 7, matchedArtifacts(artifacts)),
        score("route-handoffs", 4, readyRoutes(routes)),
        score("operator-instructions", 4, readyOperators(operators)),
        score("ci-proofs", 5, passedCi(ciProofs)),
        score("boundary-guards", 8, lockedBoundaries(boundaries)),
        score("retention-guards", 5, readyRetentions(retentions)),
        score("closeout-handoffs", 6, readyCloseouts(closeouts)));
  }

  private static int passedSnapshots(
      List<
              OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                  .SourceArchiveSnapshot>
          snapshots) {
    return (int) snapshots.stream().filter(snapshot -> "passed".equals(snapshot.status())).count();
  }

  private static int passedRequirements(
      List<
              OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                  .VerificationRequirement>
          requirements) {
    return (int)
        requirements.stream()
            .filter(
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                        .VerificationRequirement
                    ::passed)
            .count();
  }

  private static int matchedArtifacts(
      List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.ArtifactCrossCheck>
          artifacts) {
    return (int)
        artifacts.stream()
            .filter(
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                        .ArtifactCrossCheck
                    ::matched)
            .count();
  }

  private static int readyRoutes(
      List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.RouteHandoff>
          routes) {
    return (int)
        routes.stream()
            .filter(
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.RouteHandoff
                    ::ready)
            .count();
  }

  private static int readyOperators(
      List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.OperatorInstruction>
          operators) {
    return (int)
        operators.stream()
            .filter(
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                        .OperatorInstruction
                    ::ready)
            .count();
  }

  private static int passedCi(
      List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.CiProof> ciProofs) {
    return (int) ciProofs.stream().filter(ci -> "passed".equals(ci.status())).count();
  }

  private static int lockedBoundaries(
      List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.BoundaryGuard>
          boundaries) {
    return (int)
        boundaries.stream()
            .filter(
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.BoundaryGuard
                    ::locked)
            .count();
  }

  private static int readyRetentions(
      List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.RetentionGuard>
          retentions) {
    return (int)
        retentions.stream()
            .filter(
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.RetentionGuard
                    ::ready)
            .count();
  }

  private static int readyCloseouts(
      List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.CloseoutHandoff>
          closeouts) {
    return (int)
        closeouts.stream()
            .filter(
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.CloseoutHandoff
                    ::ready)
            .count();
  }

  private static OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.ScorecardEntry
      score(String name, int expected, int actual) {
    return new OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.ScorecardEntry(
        name, expected, actual, expected == actual ? "passed" : "blocked");
  }
}
