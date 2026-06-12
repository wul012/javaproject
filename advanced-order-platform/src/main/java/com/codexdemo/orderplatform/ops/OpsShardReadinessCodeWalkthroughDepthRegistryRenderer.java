package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessCodeWalkthroughDepthRegistryRenderer {

    private OpsShardReadinessCodeWalkthroughDepthRegistryRenderer() {
    }

    static List<OpsShardReadinessCodeWalkthroughDepthRegistryResponse.MarkdownSection> render(
            List<OpsShardReadinessCodeWalkthroughDepthRegistryResponse.DepthRule> depthRules,
            List<OpsShardReadinessCodeWalkthroughDepthRegistryResponse.LanguageRule> languageRules,
            List<OpsShardReadinessCodeWalkthroughDepthRegistryResponse.EvidenceRule> evidenceRules,
            List<OpsShardReadinessCodeWalkthroughDepthRegistryResponse.BoundaryRule> boundaryRules,
            List<OpsShardReadinessCodeWalkthroughDepthRegistryResponse.VerificationStep>
                    verificationSteps
    ) {
        return List.of(
                section("Depth Rules", depthRules.stream()
                        .map(rule -> "- " + rule.code()
                                + ": minimumChineseCharacters="
                                + rule.minimumChineseCharacters()
                                + ", required=" + rule.required()
                                + ", requirement=" + rule.requirement())
                        .toList()),
                section("Language Rules", languageRules.stream()
                        .map(rule -> "- " + rule.code()
                                + ": required=" + rule.required()
                                + ", rejectionSignal=" + rule.rejectionSignal()
                                + ", requirement=" + rule.requirement())
                        .toList()),
                section("Evidence Rules", evidenceRules.stream()
                        .map(rule -> "- " + rule.code()
                                + ": minimumMentions=" + rule.minimumMentions()
                                + ", evidence=" + rule.requiredEvidence()
                                + ", question=" + rule.maintainerQuestion())
                        .toList()),
                section("Boundary Rules", boundaryRules.stream()
                        .map(rule -> "- " + rule.code()
                                + ": allowed=" + rule.allowed()
                                + ", forbiddenAction=" + rule.forbiddenAction())
                        .toList()),
                section("Verification Steps", verificationSteps.stream()
                        .map(step -> "- " + step.name()
                                + ": required=" + step.required()
                                + ", commandOrClass=" + step.commandOrClass()
                                + ", scope=" + step.scope())
                        .toList())
        );
    }

    private static OpsShardReadinessCodeWalkthroughDepthRegistryResponse.MarkdownSection section(
            String heading,
            List<String> lines
    ) {
        return new OpsShardReadinessCodeWalkthroughDepthRegistryResponse.MarkdownSection(
                heading,
                List.copyOf(new ArrayList<>(lines))
        );
    }
}
