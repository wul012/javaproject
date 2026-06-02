package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffArchiveArtifactByteFloorTests {

    private static final Path ARCHIVE_ROOT = Paths.get("e");

    @Test
    void keepsRecentArchiveArtifactsAboveByteFloor() throws IOException {
        for (int version = 275; version <= 286; version++) {
            Path versionRoot = ARCHIVE_ROOT.resolve(String.valueOf(version));

            assertThat(Files.size(versionedArtifact(versionRoot, version, ".json"))).isGreaterThan(500L);
            assertThat(Files.size(versionedArtifact(versionRoot, version, ".html"))).isGreaterThan(600L);
            assertThat(Files.size(versionedArtifact(versionRoot, version, "-browser-snapshot.md"))).isGreaterThan(200L);
            assertThat(Files.size(versionedArtifact(versionRoot, version, ".png"))).isGreaterThan(1_000L);
        }
    }

    @Test
    void keepsArchiveArtifactByteFloorPathVersionedToV286() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_ARCHIVE_ARTIFACT_BYTE_FLOOR_EVIDENCE_PATH)
                .isEqualTo(
                        "e/286/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "archive-artifact-byte-floor-v286.json"
                );
    }

    private static Path versionedArtifact(Path versionRoot, int version, String suffix) throws IOException {
        String versionToken = "-v" + version;
        try (Stream<Path> paths = Files.walk(versionRoot)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> {
                        String name = path.getFileName().toString();
                        return name.contains(versionToken) && name.endsWith(suffix);
                    })
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Missing " + suffix + " artifact for v" + version));
        }
    }
}
