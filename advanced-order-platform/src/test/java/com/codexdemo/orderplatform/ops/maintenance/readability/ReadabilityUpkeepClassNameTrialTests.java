package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepClassNameTrialTests {

    private static final Path SOURCE_ROOT = Path.of(
            "src",
            "main",
            "java",
            "com",
            "codexdemo",
            "orderplatform",
            "ops",
            "maintenance",
            "readability"
    );

    @Test
    void newReadabilitySubpackageAvoidsRepeatedShardReadinessPrefix() throws IOException {
        List<String> fileNames;
        try (Stream<Path> paths = Files.list(SOURCE_ROOT)) {
            fileNames = paths
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .toList();
        }

        assertThat(fileNames)
                .isNotEmpty()
                .noneSatisfy(name -> assertThat(name).startsWith("OpsShardReadiness"));
        assertThat(fileNames)
                .contains(
                        "ReadabilityUpkeepRegistryService.java",
                        "ReadabilityUpkeepRegistryController.java",
                        "ReadabilityBoundaryCatalog.java"
                );
        assertThat(fileNames)
                .allSatisfy(name -> assertThat(name.replace(".java", "").length())
                        .isLessThanOrEqualTo(48));
    }

    @Test
    void classNameTrialDocumentRemainsLinkedFromOpsIndex() throws IOException {
        String readme = Files.readString(Path.of("docs", "ops", "README.md"),
                StandardCharsets.UTF_8);
        String trial = Files.readString(Path.of("docs", "ops", "class-name-trial.md"),
                StandardCharsets.UTF_8);

        assertThat(readme).contains("class-name-trial.md");
        assertThat(trial)
                .contains(
                        "not a bulk rename",
                        "ReadabilityUpkeepRegistryService",
                        "OpsShardReadinessReadabilityUpkeepRegistryService"
                );
    }
}
