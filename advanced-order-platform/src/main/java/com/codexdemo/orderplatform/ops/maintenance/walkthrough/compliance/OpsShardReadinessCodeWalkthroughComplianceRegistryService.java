package com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessCodeWalkthroughComplianceRegistryService {

  static final String RESPONSE_VERSION = "Java v1747";
  public static final String ENDPOINT =
      OpsShardReadinessCodeWalkthroughComplianceRoutePaths.BASE_PATH
          + OpsShardReadinessCodeWalkthroughComplianceRoutePaths
              .CODE_WALKTHROUGH_COMPLIANCE_REGISTRY;
  static final String PROFILE = "java-shard-readiness-code-walkthrough-compliance-registry.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessCodeWalkthroughComplianceRegistryResponse registry() {
    var versions = OpsShardReadinessCodeWalkthroughComplianceVersionCatalog.versions();
    var requiredHeadings =
        OpsShardReadinessCodeWalkthroughComplianceRequiredHeadingCatalog.requiredHeadings();
    var archiveRanges =
        OpsShardReadinessCodeWalkthroughComplianceArchiveRangeCatalog.archiveRanges();
    var documentationRules =
        OpsShardReadinessCodeWalkthroughComplianceDocumentationRuleCatalog.documentationRules();
    var boundaryRules =
        OpsShardReadinessCodeWalkthroughComplianceBoundaryRuleCatalog.boundaryRules();
    var testCoverages =
        OpsShardReadinessCodeWalkthroughComplianceTestCoverageCatalog.testCoverages();
    return OpsShardReadinessCodeWalkthroughComplianceRegistrySupport.response(
        RESPONSE_VERSION,
        ENDPOINT,
        PROFILE,
        versions,
        requiredHeadings,
        archiveRanges,
        documentationRules,
        boundaryRules,
        testCoverages,
        OpsShardReadinessCodeWalkthroughComplianceRegistryRenderer.render(
            versions,
            requiredHeadings,
            archiveRanges,
            documentationRules,
            boundaryRules,
            testCoverages));
  }
}
