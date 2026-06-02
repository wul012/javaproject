package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffArchiveTestSupport.versionedArtifact;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertEvidencePath;

import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffArchiveArtifactByteFloorTests {

    @Test
    void keepsRecentArchiveArtifactsAboveByteFloor() throws IOException {
        for (int version = 275; version <= 286; version++) {
            assertThat(Files.size(versionedArtifact(version, ".json"))).isGreaterThan(500L);
            assertThat(Files.size(versionedArtifact(version, ".html"))).isGreaterThan(600L);
            assertThat(Files.size(versionedArtifact(version, "-browser-snapshot.md"))).isGreaterThan(200L);
            assertThat(Files.size(versionedArtifact(version, ".png"))).isGreaterThan(1_000L);
        }
    }

    @Test
    void keepsArchiveArtifactByteFloorPathVersionedToV286() {
        assertEvidencePath(
                OpsShardReadinessV1ContractConsumerReadinessHandoffService
                        .CONSUMER_READINESS_HANDOFF_ARCHIVE_ARTIFACT_BYTE_FLOOR_EVIDENCE_PATH,
                286,
                "archive-artifact-byte-floor"
        );
    }

}
