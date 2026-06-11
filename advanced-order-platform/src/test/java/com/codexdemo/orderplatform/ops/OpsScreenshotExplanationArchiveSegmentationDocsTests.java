package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class OpsScreenshotExplanationArchiveSegmentationDocsTests {

    private static final Path WORKING_ROOT = Path.of("").toAbsolutePath();
    private static final Path NEXT_ROOT =
            WORKING_ROOT.resolve("d_runtime_screenshot_archive_next");

    @Test
    void keepsScreenshotExplanationArchivesInSegmentedNextRoot() {
        assertThat(Files.isDirectory(NEXT_ROOT)).isTrue();
        assertThat(Files.isRegularFile(NEXT_ROOT.resolve("README.md"))).isTrue();
        assertThat(Files.isDirectory(NEXT_ROOT.resolve("v1759-v1763"))).isTrue();
        assertThat(Files.isRegularFile(NEXT_ROOT.resolve("v1759-v1763").resolve("README.md")))
                .isTrue();
    }

    @Test
    void documentsNoRootDumpingPolicy() throws Exception {
        String rootReadme = Files.readString(NEXT_ROOT.resolve("README.md"));
        String segmentReadme = Files.readString(NEXT_ROOT.resolve("v1759-v1763").resolve("README.md"));

        assertThat(rootReadme)
                .contains("Do not place screenshots or explanation markdown directly in this root.");
        assertThat(segmentReadme)
                .contains("images", "explanations", "v1759-v1763");
    }
}
