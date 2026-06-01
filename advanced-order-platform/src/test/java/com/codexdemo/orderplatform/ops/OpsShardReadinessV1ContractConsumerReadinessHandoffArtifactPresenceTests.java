package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffArtifactPresenceTests {

    @Test
    void keepsV225ReadinessHandoffArtifactSetPresent() {
        Path root = Path.of("").toAbsolutePath();

        assertThat(v225ArtifactPaths(root))
                .allSatisfy(path -> assertThat(Files.exists(path))
                        .as(path.toString())
                        .isTrue());
    }

    @Test
    void keepsV225ArtifactNamesAlignedWithReadinessHandoffVersion() {
        Path root = Path.of("").toAbsolutePath();

        assertThat(v225ArtifactPaths(root))
                .extracting(path -> path.getFileName().toString())
                .allSatisfy(fileName -> assertThat(fileName)
                        .containsAnyOf("v225", "说明.md"));
    }

    private static List<Path> v225ArtifactPaths(Path root) {
        return List.of(
                root.resolve("src/main/resources/static/contracts/"
                        + "java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.fixture.json"),
                root.resolve("e/225/evidence/"
                        + "java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.json"),
                root.resolve("e/225/evidence/"
                        + "java-shard-readiness-v1-contract-consumer-readiness-handoff-v225-browser-snapshot.md"),
                root.resolve("e/225/java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.html"),
                root.resolve("e/225/图片/"
                        + "java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.png"),
                root.resolve("e/225/解释/说明.md")
        );
    }
}
