package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class OpsScreenshotExplanationFArchiveLayoutInventoryTests {

    private static final Path WORKING_ROOT = Path.of("").toAbsolutePath();
    private static final Path F_ROOT = WORKING_ROOT.resolve("f");

    @Test
    void keepsFRootAsIndexOnly() throws Exception {
        try (var entries = Files.list(F_ROOT)) {
            assertThat(entries)
                    .allSatisfy(entry -> assertThat(
                            Files.isDirectory(entry)
                                    || entry.getFileName().toString().equals("README.md")
                    ).isTrue());
        }
    }

    @Test
    void inventoriesExpectedFSegments() throws Exception {
        try (var entries = Files.list(F_ROOT)) {
            assertThat(entries.map(path -> path.getFileName().toString()))
                    .contains("README.md", "v1764-v1768", "v1769-v1773")
                    .doesNotContain("images", "explanations");
        }
    }

    @Test
    void requiresSegmentReadmesToDeclareImagesAndExplanations() throws Exception {
        assertSegmentReadme("v1764-v1768");
        assertSegmentReadme("v1769-v1773");
    }

    private static void assertSegmentReadme(String segment) throws Exception {
        var readme = F_ROOT.resolve(segment).resolve("README.md");

        assertThat(Files.isRegularFile(readme)).isTrue();
        assertThat(Files.readString(readme))
                .contains(
                        "images",
                        "explanations",
                        segment
                );
    }
}
