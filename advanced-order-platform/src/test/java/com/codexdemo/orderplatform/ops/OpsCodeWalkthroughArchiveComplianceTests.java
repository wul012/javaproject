package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class OpsCodeWalkthroughArchiveComplianceTests {

    private static final Path WORKING_ROOT = Path.of("").toAbsolutePath();
    private static final Pattern VERSION_TOKEN = Pattern.compile("version-(\\d+)-");
    private static final int LEGACY_MARKER_CUTOFF_VERSION = 289;
    private static final int CHINESE_DEPTH_CUTOFF_VERSION = 1773;
    private static final int MINIMUM_CHINESE_CHARACTER_COUNT = 3000;
    private static final String LEGACY_MARKER = "legacy-nonstandard-walkthrough";
    private static final List<String> REQUIRED_HEADINGS = List.of(
            "## 入口路由",
            "## 响应模型",
            "## 上游证据配置",
            "## 服务层核心流程",
            "## Java 证据检查",
            "## mini-kv 证据检查",
            "## 阻断与安全边界",
            "## 测试覆盖",
            "## 一句话总结"
    );
    private static final String REQUIRED_WORKLOAD_HEADING = "## 实际工作量说明";

    @Test
    void keepsHistoricalWalkthroughsEitherStandardOrMarkedLegacy() throws IOException {
        List<String> unsettled = new ArrayList<>();

        for (Path walkthrough : walkthroughFiles()) {
            String text = Files.readString(walkthrough, StandardCharsets.UTF_8);

            if (!isStandard(text) && !text.contains(LEGACY_MARKER)) {
                unsettled.add(relative(walkthrough));
            }
        }

        assertThat(unsettled)
                .as("non-standard historical walkthroughs must be explicitly cleared as legacy")
                .isEmpty();
    }

    @Test
    void rejectsFutureWalkthroughsWithoutRequiredStructure() throws IOException {
        List<String> futureNonStandard = new ArrayList<>();

        for (Path walkthrough : walkthroughFiles()) {
            OptionalInt version = version(walkthrough);
            if (version.isPresent() && version.getAsInt() > LEGACY_MARKER_CUTOFF_VERSION) {
                String text = Files.readString(walkthrough, StandardCharsets.UTF_8);
                if (!isStandard(text)) {
                    futureNonStandard.add(relative(walkthrough));
                }
            }
        }

        assertThat(futureNonStandard)
                .as("future walkthroughs after v" + LEGACY_MARKER_CUTOFF_VERSION + " must use the standard sections")
                .isEmpty();
    }

    @Test
    void rejectsLegacyMarkerOnFutureWalkthroughs() throws IOException {
        List<String> futureLegacyMarkers = new ArrayList<>();

        for (Path walkthrough : walkthroughFiles()) {
            OptionalInt version = version(walkthrough);
            if (version.isPresent() && version.getAsInt() > LEGACY_MARKER_CUTOFF_VERSION) {
                String text = Files.readString(walkthrough, StandardCharsets.UTF_8);
                if (text.contains(LEGACY_MARKER)) {
                    futureLegacyMarkers.add(relative(walkthrough));
                }
            }
        }

        assertThat(futureLegacyMarkers)
                .as("legacy marker is only for already-cleared historical walkthroughs")
                .isEmpty();
    }

    @Test
    void requiresChineseLongformDepthForNewWalkthroughs() throws IOException {
        List<String> shallowWalkthroughs = new ArrayList<>();

        for (Path walkthrough : walkthroughFiles()) {
            OptionalInt version = version(walkthrough);
            if (version.isPresent() && version.getAsInt() > CHINESE_DEPTH_CUTOFF_VERSION) {
                String text = Files.readString(walkthrough, StandardCharsets.UTF_8);
                if (cjkCharacterCount(text) < MINIMUM_CHINESE_CHARACTER_COUNT
                        || !hasChineseMajority(text)
                        || !text.contains(REQUIRED_WORKLOAD_HEADING)) {
                    shallowWalkthroughs.add(relative(walkthrough)
                            + " cjk=" + cjkCharacterCount(text)
                            + " text=" + letterOrCjkCount(text));
                }
            }
        }

        assertThat(shallowWalkthroughs)
                .as("walkthroughs after v" + CHINESE_DEPTH_CUTOFF_VERSION
                        + " must be Chinese longform explanations with at least "
                        + MINIMUM_CHINESE_CHARACTER_COUNT + " CJK characters")
                .isEmpty();
    }

    @Test
    void requiresWorkloadExplanationForNewWalkthroughs() throws IOException {
        List<String> missingWorkloadExplanation = new ArrayList<>();

        for (Path walkthrough : walkthroughFiles()) {
            OptionalInt version = version(walkthrough);
            if (version.isPresent() && version.getAsInt() > CHINESE_DEPTH_CUTOFF_VERSION) {
                String text = Files.readString(walkthrough, StandardCharsets.UTF_8);
                if (!text.contains(REQUIRED_WORKLOAD_HEADING)
                        || !text.contains("禁止硬凑")
                        || !text.contains("本项目")) {
                    missingWorkloadExplanation.add(relative(walkthrough));
                }
            }
        }

        assertThat(missingWorkloadExplanation)
                .as("new walkthroughs must explain actual project-local workload instead of padding")
                .isEmpty();
    }

    private static List<Path> walkthroughFiles() throws IOException {
        List<Path> files = new ArrayList<>();
        for (Path root : walkthroughRoots()) {
            try (Stream<Path> paths = Files.walk(root)) {
                paths
                        .filter(Files::isRegularFile)
                        .filter(path -> path.getFileName().toString().endsWith(".md"))
                        .filter(path -> !path.getFileName().toString().equals("README.md"))
                        .forEach(files::add);
            }
        }
        return files;
    }

    private static List<Path> walkthroughRoots() throws IOException {
        try (Stream<Path> paths = Files.list(WORKING_ROOT)) {
            return paths
                    .filter(Files::isDirectory)
                    .filter(path -> path.getFileName().toString().startsWith("代码讲解记录"))
                    .toList();
        }
    }

    private static OptionalInt version(Path path) {
        Matcher matcher = VERSION_TOKEN.matcher(path.getFileName().toString());
        if (!matcher.find()) {
            return OptionalInt.empty();
        }
        return OptionalInt.of(Integer.parseInt(matcher.group(1)));
    }

    private static boolean isStandard(String text) {
        return REQUIRED_HEADINGS.stream().allMatch(text::contains);
    }

    private static long cjkCharacterCount(String text) {
        return text.codePoints()
                .filter(codePoint -> Character.UnicodeScript.of(codePoint)
                        == Character.UnicodeScript.HAN)
                .count();
    }

    private static long letterOrCjkCount(String text) {
        return text.codePoints()
                .filter(codePoint -> Character.isAlphabetic(codePoint)
                        || Character.UnicodeScript.of(codePoint)
                        == Character.UnicodeScript.HAN)
                .count();
    }

    private static boolean hasChineseMajority(String text) {
        long total = letterOrCjkCount(text);
        return total > 0 && cjkCharacterCount(text) * 2 >= total;
    }

    private static String relative(Path path) {
        return WORKING_ROOT.relativize(path.toAbsolutePath()).toString();
    }
}
