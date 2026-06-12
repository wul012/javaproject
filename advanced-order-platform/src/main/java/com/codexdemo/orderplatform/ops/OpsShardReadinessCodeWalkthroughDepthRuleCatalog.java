package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessCodeWalkthroughDepthRuleCatalog {

    private OpsShardReadinessCodeWalkthroughDepthRuleCatalog() {
    }

    static List<OpsShardReadinessCodeWalkthroughDepthRegistryResponse.DepthRule> depthRules() {
        return List.of(
                depthRule(
                        "minimum-3000-chinese-characters",
                        "v1774 以及之后的每篇版本讲解必须至少包含 3000 个中文汉字",
                        3000
                ),
                depthRule(
                        "one-version-one-walkthrough",
                        "每个提交版本都要有独立讲解，不能把多个版本隐藏在一篇收尾说明里",
                        3000
                ),
                depthRule(
                        "implementation-surface-required",
                        "讲解必须点名路由、模型、服务、目录和测试等实际改动面，并解释为什么改",
                        3000
                ),
                depthRule(
                        "boundary-proof-required",
                        "讲解必须说明只读、无运行时、无密钥、无原始端点和不上游自启动边界",
                        3000
                ),
                depthRule(
                        "no-padding-workload-evidence",
                        "字数不够时必须加大本项目实际工程工作量，禁止用重复话术硬凑篇幅",
                        3000
                )
        );
    }

    static List<OpsShardReadinessCodeWalkthroughDepthRegistryResponse.LanguageRule>
            languageRules() {
        return List.of(
                languageRule(
                        "chinese-default",
                        "新的 Java 版本讲解默认使用中文书写",
                        "v1773 之后仍以英文为主体的讲解"
                ),
                languageRule(
                        "section-headings-preserved",
                        "即便某个证据面不适用，也必须保留必需中文章节标题并解释原因",
                        "缺少必需中文章节标题"
                ),
                languageRule(
                        "not-release-receipt",
                        "版本讲解是维护说明，不是短发布收据",
                        "只有要点列表，没有路由、模型、服务、测试讨论"
                ),
                languageRule(
                        "no-padding-prose",
                        "讲解必须用新增代码、测试、文档治理、边界证明和验证结果支撑篇幅",
                        "重复口号、空泛总结或与本项目无关的凑字内容"
                )
        );
    }

    static List<OpsShardReadinessCodeWalkthroughDepthRegistryResponse.EvidenceRule>
            evidenceRules() {
        return List.of(
                evidenceRule(
                        "route-model-service-test-chain",
                        "入口路由、响应模型、服务层核心流程、测试覆盖",
                        "Can a maintainer find the code entry and its proof chain?",
                        4
                ),
                evidenceRule(
                        "upstream-plan-boundary",
                        "上游计划、Java 证据、mini-kv 不消费说明",
                        "Does the walkthrough explain why no runtime integration was opened?",
                        3
                ),
                evidenceRule(
                        "safety-denials",
                        "write routing、credential value、raw endpoint、managed audit、deployment、rollback、autostart",
                        "Are forbidden actions named as concrete blockers?",
                        7
                ),
                evidenceRule(
                        "verification-commands",
                        "定向测试、全量 Maven、CI、cleanup gate",
                        "版本是否说明实际执行过哪些验证？",
                        4
                ),
                evidenceRule(
                        "project-local-workload-proof",
                        "本项目源码、本项目测试、本项目文档、本项目清理、本项目 CI",
                        "讲解篇幅是否主要来自 advanced-order-platform 的实际工程工作？",
                        5
                )
        );
    }

    private static OpsShardReadinessCodeWalkthroughDepthRegistryResponse.DepthRule depthRule(
            String code,
            String requirement,
            int minimumChineseCharacters
    ) {
        return new OpsShardReadinessCodeWalkthroughDepthRegistryResponse.DepthRule(
                code,
                requirement,
                minimumChineseCharacters,
                true
        );
    }

    private static OpsShardReadinessCodeWalkthroughDepthRegistryResponse.LanguageRule languageRule(
            String code,
            String requirement,
            String rejectionSignal
    ) {
        return new OpsShardReadinessCodeWalkthroughDepthRegistryResponse.LanguageRule(
                code,
                requirement,
                rejectionSignal,
                true
        );
    }

    private static OpsShardReadinessCodeWalkthroughDepthRegistryResponse.EvidenceRule evidenceRule(
            String code,
            String requiredEvidence,
            String maintainerQuestion,
            int minimumMentions
    ) {
        return new OpsShardReadinessCodeWalkthroughDepthRegistryResponse.EvidenceRule(
                code,
                requiredEvidence,
                maintainerQuestion,
                minimumMentions
        );
    }
}
