package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessCodeWalkthroughQualityGateRegistryRenderer {

    private OpsShardReadinessCodeWalkthroughQualityGateRegistryRenderer() {
    }

    static List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.MarkdownSection>
            render(
                    List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.VersionRule>
                            versionRules,
                    List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.ExplanationRubric>
                            explanationRubrics,
                    List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.EvidenceAnchor>
                            evidenceAnchors,
                    List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.ReviewChecklist>
                            reviewChecklists,
                    List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.BoundaryRule>
                            boundaryRules
            ) {
        List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.MarkdownSection> sections =
                new ArrayList<>();
        sections.add(versionRulesSection(versionRules));
        sections.add(explanationRubricsSection(explanationRubrics));
        sections.add(evidenceAnchorsSection(evidenceAnchors));
        sections.add(reviewChecklistsSection(reviewChecklists));
        sections.add(boundaryRulesSection(boundaryRules));
        return List.copyOf(sections);
    }

    private static OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.MarkdownSection
            versionRulesSection(
                    List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.VersionRule>
                            versionRules
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("version-rule-count=" + versionRules.size());
        versionRules.forEach(rule -> lines.add(rule.code()
                + " | required="
                + rule.required()
                + " | minimum="
                + rule.minimumScope()
                + " | explanation="
                + rule.explanationRequirement()
                + " | split="
                + rule.splitGuidance()));
        return section("Version Granularity Rules", lines);
    }

    private static OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.MarkdownSection
            explanationRubricsSection(
                    List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.ExplanationRubric>
                            explanationRubrics
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("explanation-rubric-count=" + explanationRubrics.size());
        explanationRubrics.forEach(rubric -> lines.add(rubric.section()
                + " | minimum-evidence-points="
                + rubric.minimumEvidencePoints()
                + " | must="
                + rubric.mustExplain()
                + " | standout="
                + rubric.standoutSignal()));
        return section("Explanation Rubric", lines);
    }

    private static OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.MarkdownSection
            evidenceAnchorsSection(
                    List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.EvidenceAnchor>
                            evidenceAnchors
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("evidence-anchor-count=" + evidenceAnchors.size());
        evidenceAnchors.forEach(anchor -> lines.add(anchor.anchor()
                + " | owner="
                + anchor.owner()
                + " | runtime-free="
                + anchor.runtimeFree()
                + " | source="
                + anchor.source()
                + " | proof="
                + anchor.requiredProof()));
        return section("Evidence Anchors", lines);
    }

    private static OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.MarkdownSection
            reviewChecklistsSection(
                    List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.ReviewChecklist>
                            reviewChecklists
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("review-checklist-count=" + reviewChecklists.size());
        reviewChecklists.forEach(checklist -> lines.add(checklist.item()
                + " | blocks-release="
                + checklist.blocksRelease()
                + " | question="
                + checklist.reviewerQuestion()
                + " | blocker="
                + checklist.releaseBlocker()));
        return section("Review Checklist", lines);
    }

    private static OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.MarkdownSection
            boundaryRulesSection(
                    List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.BoundaryRule>
                            boundaryRules
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("boundary-rule-count=" + boundaryRules.size());
        boundaryRules.forEach(rule -> lines.add(rule.code()
                + " | owner="
                + rule.owner()
                + " | forbidden="
                + rule.forbiddenAction()
                + " | allowed="
                + rule.allowed()
                + " | "
                + rule.rationale()));
        return section("Runtime Boundary Rules", lines);
    }

    private static OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.MarkdownSection
            section(String heading, List<String> lines) {
        return new OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.MarkdownSection(
                heading,
                List.copyOf(lines)
        );
    }
}
