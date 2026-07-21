package com.codexdemo.orderplatform.ops.maintenance.walkthrough.depth;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessCodeWalkthroughDepthRegistryService {

  static final String RESPONSE_VERSION = "Java v1778";
  public static final String ENDPOINT =
      OpsShardReadinessCodeWalkthroughDepthRoutePaths.BASE_PATH
          + OpsShardReadinessCodeWalkthroughDepthRoutePaths.CODE_WALKTHROUGH_DEPTH_REGISTRY;
  static final String PROFILE = "java-shard-readiness-code-walkthrough-depth-registry.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessCodeWalkthroughDepthRegistryResponse registry() {
    var depthRules = OpsShardReadinessCodeWalkthroughDepthRuleCatalog.depthRules();
    var languageRules = OpsShardReadinessCodeWalkthroughDepthRuleCatalog.languageRules();
    var evidenceRules = OpsShardReadinessCodeWalkthroughDepthRuleCatalog.evidenceRules();
    var boundaryRules = OpsShardReadinessCodeWalkthroughDepthBoundaryCatalog.boundaryRules();
    var verificationSteps =
        OpsShardReadinessCodeWalkthroughDepthVerificationCatalog.verificationSteps();
    return OpsShardReadinessCodeWalkthroughDepthRegistrySupport.response(
        RESPONSE_VERSION,
        ENDPOINT,
        PROFILE,
        depthRules,
        languageRules,
        evidenceRules,
        boundaryRules,
        verificationSteps,
        ReportRenderer.render(
            depthRules, languageRules, evidenceRules, boundaryRules, verificationSteps));
  }
}
