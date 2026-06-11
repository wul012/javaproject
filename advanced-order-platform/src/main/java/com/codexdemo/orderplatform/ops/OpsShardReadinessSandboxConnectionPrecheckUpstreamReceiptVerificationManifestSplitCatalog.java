package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSplitCatalog {

    private OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSplitCatalog() {
    }

    static List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.SplitModule>
    modules() {
        return List.of(
                module("v1983", "entrypoint-boundary",
                        "Record the split boundary and keep public loader/render exports stable."),
                module("v1984", "types",
                        "Move profile, receipt, evidence, snippet, message, and check types."),
                module("v1985", "constants",
                        "Move fixed evidence paths, endpoint paths, and Node v245 precheck constants."),
                module("v1986", "references-node-v245",
                        "Move the Node v245 source adapter into references."),
                module("v1987", "references-java-v99",
                        "Move the frozen Java v99 receipt reference builder into references."),
                module("v1988", "references-mini-kv-v108",
                        "Move the mini-kv v108 non-participation reference builder into references."),
                module("v1989", "references-helpers",
                        "Keep evidence-file, snippet, JSON, and field helpers private to references."),
                module("v1990", "policy-checks",
                        "Move receipt verification check construction into policy."),
                module("v1991", "policy-messages",
                        "Move blockers, warnings, and recommendations into policy."),
                module("v1992", "core",
                        "Move digest, receiptVerification, summary, endpoints, and nextActions assembly."),
                module("v1993", "renderer",
                        "Move Markdown rendering into a dedicated renderer module."),
                module("v1994", "entrypoint-orchestration",
                        "Leave the service entrypoint as orchestration only with import compatibility.")
        );
    }

    private static OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.SplitModule
    module(String version, String moduleName, String responsibility) {
        return new OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                .SplitModule(
                version,
                moduleName,
                responsibility,
                true,
                true,
                false
        );
    }
}
