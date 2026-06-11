package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessScreenshotExplanationArchiveRegistryRenderer {

    private OpsShardReadinessScreenshotExplanationArchiveRegistryRenderer() {
    }

    static List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.MarkdownSection>
            render(
                    List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse
                            .CurrentArchiveAssessment> currentArchiveAssessments,
                    List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.ArchiveSegmentPlan>
                            segmentPlans,
                    List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.NamingRule>
                            namingRules,
                    List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.BoundaryRule>
                            boundaryRules,
                    List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.VerificationStep>
                            verificationSteps
            ) {
        List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.MarkdownSection> sections =
                new ArrayList<>();
        sections.add(currentArchiveSection(currentArchiveAssessments));
        sections.add(segmentPlanSection(segmentPlans));
        sections.add(namingRuleSection(namingRules));
        sections.add(boundaryRuleSection(boundaryRules));
        sections.add(verificationStepSection(verificationSteps));
        return List.copyOf(sections);
    }

    private static OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.MarkdownSection
            currentArchiveSection(
                    List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse
                            .CurrentArchiveAssessment> currentArchiveAssessments
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("current-archive-assessment-count=" + currentArchiveAssessments.size());
        currentArchiveAssessments.forEach(assessment -> lines.add(assessment.root()
                + " | version-directories="
                + assessment.versionDirectoryCount()
                + " | files="
                + assessment.fileCount()
                + " | status="
                + assessment.status()
                + " | next="
                + assessment.nextAction()));
        return section("Current Archive Assessments", lines);
    }

    private static OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.MarkdownSection
            segmentPlanSection(
                    List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.ArchiveSegmentPlan>
                            segmentPlans
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("segment-plan-count=" + segmentPlans.size());
        segmentPlans.forEach(segment -> lines.add(segment.segment()
                + " | path="
                + segment.path()
                + " | range="
                + segment.versionRange()
                + " | active="
                + segment.active()
                + " | "
                + segment.purpose()));
        return section("Archive Segment Plans", lines);
    }

    private static OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.MarkdownSection
            namingRuleSection(
                    List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.NamingRule>
                            namingRules
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("naming-rule-count=" + namingRules.size());
        namingRules.forEach(rule -> lines.add(rule.code()
                + " | required="
                + rule.required()
                + " | pattern="
                + rule.pattern()
                + " | "
                + rule.rationale()));
        return section("Naming Rules", lines);
    }

    private static OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.MarkdownSection
            boundaryRuleSection(
                    List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.BoundaryRule>
                            boundaryRules
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("boundary-rule-count=" + boundaryRules.size());
        boundaryRules.forEach(rule -> lines.add(rule.code()
                + " | forbidden="
                + rule.forbiddenAction()
                + " | allowed="
                + rule.allowed()
                + " | "
                + rule.rationale()));
        return section("Boundary Rules", lines);
    }

    private static OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.MarkdownSection
            verificationStepSection(
                    List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.VerificationStep>
                            verificationSteps
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("verification-step-count=" + verificationSteps.size());
        verificationSteps.forEach(step -> lines.add(step.name()
                + " | required="
                + step.required()
                + " | command="
                + step.commandOrClass()
                + " | scope="
                + step.scope()));
        return section("Verification Steps", lines);
    }

    private static OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.MarkdownSection
            section(String heading, List<String> lines) {
        return new OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.MarkdownSection(
                heading,
                List.copyOf(lines)
        );
    }
}
