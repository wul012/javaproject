package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepDocsTests {

    private static final Path DOCS_ROOT = Path.of("docs", "ops");

    @Test
    void docsOpsEntryMapsRemainDiscoverable() {
        assertThat(Files.isRegularFile(DOCS_ROOT.resolve("README.md"))).isTrue();
        assertThat(Files.isRegularFile(DOCS_ROOT.resolve("shard-readiness-map.md"))).isTrue();
        assertThat(Files.isRegularFile(DOCS_ROOT.resolve("walkthrough-registry-map.md")))
                .isTrue();
        assertThat(Files.isRegularFile(DOCS_ROOT.resolve("archive-layout-map.md"))).isTrue();
        assertThat(Files.isRegularFile(DOCS_ROOT.resolve("registry-template.md"))).isTrue();
    }

    @Test
    void registryTemplateKeepsRequiredLayersAndBoundaries() throws IOException {
        String template = Files.readString(
                DOCS_ROOT.resolve("registry-template.md"),
                StandardCharsets.UTF_8
        );

        assertThat(template)
                .contains(
                        "RESPONSE_VERSION",
                        "ENDPOINT",
                        "PROFILE",
                        "@Transactional(readOnly = true)",
                        "Route paths",
                        "Response",
                        "Catalog",
                        "Renderer",
                        "Support",
                        "Service",
                        "Controller",
                        "route path test",
                        "service test",
                        "renderer test",
                        "boundary test",
                        "controller test"
                );
        assertThat(template)
                .contains(
                        "write routing",
                        "active shard router",
                        "credential value reads",
                        "raw endpoint URL resolution",
                        "managed audit HTTP/TCP connection",
                        "deployment or rollback",
                        "Java autostart",
                        "mini-kv autostart"
                );
    }

    @Test
    void mapsKeepTopicSpecificReadingSignals() throws IOException {
        String readme = read("README.md");
        String shard = read("shard-readiness-map.md");
        String walkthrough = read("walkthrough-registry-map.md");
        String archive = read("archive-layout-map.md");

        assertThat(readme).contains("shard-readiness-map.md", "walkthrough-registry-map.md",
                "archive-layout-map.md");
        assertThat(shard).contains("Controller", "Service", "Response", "read-only");
        assertThat(walkthrough).contains("code-walkthrough-depth-registry",
                "Chinese longform", "3000 Chinese characters");
        assertThat(archive).contains("OpsCodeWalkthroughArchiveComplianceTests",
                "OpsScreenshotExplanationFArchiveLayoutInventoryTests");
    }

    private static String read(String fileName) throws IOException {
        return Files.readString(DOCS_ROOT.resolve(fileName), StandardCharsets.UTF_8);
    }
}
