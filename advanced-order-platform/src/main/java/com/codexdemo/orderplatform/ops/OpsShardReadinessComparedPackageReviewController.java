package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.comparedpackagereview.OpsShardReadinessComparedPackageReviewCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.comparedpackagereview.OpsShardReadinessComparedPackageReviewComparisonOutcomeService;
import com.codexdemo.orderplatform.ops.maintenance.comparedpackagereview.OpsShardReadinessComparedPackageReviewHandoffCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.comparedpackagereview.OpsShardReadinessComparedPackageReviewIdentityDigestService;
import com.codexdemo.orderplatform.ops.maintenance.comparedpackagereview.OpsShardReadinessComparedPackageReviewPolicyArchiveService;
import com.codexdemo.orderplatform.ops.maintenance.comparedpackagereview.OpsShardReadinessComparedPackageReviewResponse;
import com.codexdemo.orderplatform.ops.maintenance.comparedpackagereview.OpsShardReadinessComparedPackageReviewRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.comparedpackagereview.OpsShardReadinessComparedPackageReviewSourceEvidenceService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessComparedPackageReviewController {

  private final OpsShardReadinessComparedPackageReviewCatalogService catalogService;
  private final OpsShardReadinessComparedPackageReviewSourceEvidenceService sourceEvidenceService;
  private final OpsShardReadinessComparedPackageReviewComparisonOutcomeService
      comparisonOutcomeService;
  private final OpsShardReadinessComparedPackageReviewIdentityDigestService identityDigestService;
  private final OpsShardReadinessComparedPackageReviewPolicyArchiveService policyArchiveService;
  private final OpsShardReadinessComparedPackageReviewHandoffCloseoutService handoffCloseoutService;

  public OpsShardReadinessComparedPackageReviewController(
      OpsShardReadinessComparedPackageReviewCatalogService catalogService,
      OpsShardReadinessComparedPackageReviewSourceEvidenceService sourceEvidenceService,
      OpsShardReadinessComparedPackageReviewComparisonOutcomeService comparisonOutcomeService,
      OpsShardReadinessComparedPackageReviewIdentityDigestService identityDigestService,
      OpsShardReadinessComparedPackageReviewPolicyArchiveService policyArchiveService,
      OpsShardReadinessComparedPackageReviewHandoffCloseoutService handoffCloseoutService) {
    this.catalogService = catalogService;
    this.sourceEvidenceService = sourceEvidenceService;
    this.comparisonOutcomeService = comparisonOutcomeService;
    this.identityDigestService = identityDigestService;
    this.policyArchiveService = policyArchiveService;
    this.handoffCloseoutService = handoffCloseoutService;
  }

  @GetMapping(OpsShardReadinessComparedPackageReviewRoutePaths.COMPARED_PACKAGE_REVIEW_CATALOG)
  public OpsShardReadinessComparedPackageReviewResponse catalog() {
    return catalogService.catalog();
  }

  @GetMapping(
      OpsShardReadinessComparedPackageReviewRoutePaths.COMPARED_PACKAGE_REVIEW_SOURCE_EVIDENCE)
  public OpsShardReadinessComparedPackageReviewResponse sourceEvidence() {
    return sourceEvidenceService.sourceEvidence();
  }

  @GetMapping(
      OpsShardReadinessComparedPackageReviewRoutePaths.COMPARED_PACKAGE_REVIEW_COMPARISON_OUTCOME)
  public OpsShardReadinessComparedPackageReviewResponse comparisonOutcome() {
    return comparisonOutcomeService.comparisonOutcome();
  }

  @GetMapping(
      OpsShardReadinessComparedPackageReviewRoutePaths.COMPARED_PACKAGE_REVIEW_IDENTITY_DIGEST)
  public OpsShardReadinessComparedPackageReviewResponse identityDigest() {
    return identityDigestService.identityDigest();
  }

  @GetMapping(
      OpsShardReadinessComparedPackageReviewRoutePaths.COMPARED_PACKAGE_REVIEW_POLICY_ARCHIVE)
  public OpsShardReadinessComparedPackageReviewResponse policyArchive() {
    return policyArchiveService.policyArchive();
  }

  @GetMapping(
      OpsShardReadinessComparedPackageReviewRoutePaths.COMPARED_PACKAGE_REVIEW_HANDOFF_CLOSEOUT)
  public OpsShardReadinessComparedPackageReviewResponse handoffCloseout() {
    return handoffCloseoutService.handoffCloseout();
  }
}
