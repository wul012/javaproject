package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessCodeWalkthroughComplianceArchiveRangeCatalog {

    private OpsShardReadinessCodeWalkthroughComplianceArchiveRangeCatalog() {
    }

    static List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.ArchiveRange>
            archiveRanges() {
        return List.of(
                range(
                        "original-walkthrough-root",
                        "代码讲解记录",
                        "historical",
                        "kept as source history; non-standard files must stay legacy-marked"
                ),
                range(
                        "production-prototype-phase",
                        "代码讲解记录_生产雏形阶段",
                        "historical",
                        "kept as source history; future additions should use continuation directories"
                ),
                range(
                        "production-prototype-continuation",
                        "代码讲解记录_生产雏形阶段_续",
                        "historical",
                        "kept as source history with legacy markers where needed"
                ),
                range(
                        "production-prototype-phase3",
                        "代码讲解记录_生产雏形阶段3",
                        "v153-v289",
                        "split into range directories and covered by the compliance gate"
                ),
                range(
                        "production-prototype-phase4",
                        "代码讲解记录_生产雏形阶段4/v1728-v1747",
                        "v1728-v1747",
                        "new walkthroughs must use all required headings and no legacy marker"
                )
        );
    }

    private static OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.ArchiveRange
            range(
                    String name,
                    String directory,
                    String versionRange,
                    String retentionRule
            ) {
        return new OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.ArchiveRange(
                name,
                directory,
                versionRange,
                retentionRule
        );
    }
}
