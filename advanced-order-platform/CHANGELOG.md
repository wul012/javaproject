# Changelog

本项目的版本化证据以 git tag 为权威来源。Maven artifact 当前保持
`0.1.0-SNAPSHOT`，因为本仓库仍处在高频工程演进阶段，尚未切换到语义化制品发布。
每个可追溯版本必须有对应 git tag、提交、测试证据和必要的中文代码讲解。

## v1824 - Signed approval artifact draft text package compared package evidence intake extraction

- Moved thirteen compared-package-evidence-intake implementation and endpoint
  reference files into
  `ops.maintenance.signedapprovalartifactdrafttextpackagecomparedpackageevidenceintake`.
  The guard catalog is collocated with the slot catalog, direct root `ops`
  Java files fall from 911 to 897, and total `ops` Java files stay at 1,352.
- Added a public route owner for the five compared-package-evidence-intake
  suffixes. Root route aggregation delegates to it, preserving byte-identical
  paths.
- Kept the controller in root and repaired explicit public imports for
  ComparedPackageReview, ProfileSection, controller tests, route tests, and
  test support. No package acceptance, text parsing, detached-signature parsing,
  approval, write, credential, deployment, rollback, runtime, or archive
  contract changed.
- Added the v1824 extraction note, readability ratchet, SpotBugs FQN
  relocation, progress evidence, and Chinese walkthrough.

## v1823 - Signed approval artifact draft text package comparison acceptance precheck extraction

- Moved seven comparison-acceptance-precheck implementation files into
  `ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonacceptanceprecheck`.
  The guard catalog is collocated with the checkpoint catalog, direct root
  `ops` Java files fall from 919 to 911, and total `ops` Java files stay at
  1,352.
- Added a public route owner for the four comparison-acceptance-precheck
  suffixes. Root route aggregation delegates to it, preserving byte-identical
  paths.
- Kept the controller in root and repaired explicit public imports for
  ComparedPackageEvidenceIntake, ProfileSection, controller tests, route tests,
  and test support. No package acceptance, comparison execution, parsing,
  approval, write, credential, deployment, rollback, or archive contract
  changed.
- Added the v1823 extraction note, readability ratchet, SpotBugs FQN
  relocation, progress evidence, and Chinese walkthrough.

## v1822 - Signed approval artifact draft text package comparison preflight extraction

- Moved twelve physical comparison-preflight implementation files into
  `ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight`.
  The gate catalog is collocated with the acceptance-control catalog, direct
  root `ops` Java files fall from 932 to 919, and total `ops` Java files stay
  at 1,352.
- Added a public route owner for the five comparison-preflight suffixes. Root
  route aggregation delegates to it, preserving byte-identical paths.
- Kept the controller in root and repaired explicit public imports for
  ComparisonAcceptancePrecheck, ComparedPackageEvidenceIntake, ProfileSection,
  controller tests, route tests, and test support. No response component,
  comparison execution, package acceptance, write, credential, deployment,
  rollback, or archive contract changed.
- Added the v1822 extraction note, readability ratchet, SpotBugs FQN
  relocation, progress evidence, and Chinese walkthrough.

## v1821 - Signed approval artifact draft text package submission preflight extraction

- Moved the primary submission-preflight and its Closeout family together into
  `ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight`.
  Twenty-eight physical files move, the package-private gate catalog is
  collocated with the comparison-control catalog, direct root `ops` Java files
  fall from 961 to 932, and total `ops` Java files stay at 1,352.
- Added a public route owner for five primary and six Closeout HTTP suffixes.
  Root route aggregation delegates to it, preserving all route strings. Three
  Closeout detail views remain catalog URI fragments rather than new routes.
- Kept both controllers in root and repaired explicit public imports for
  TextPackageComparisonPreflight, ComparedPackageEvidenceIntake, ProfileSection,
  controller tests, route tests, and test support. No response component,
  write, credential, deployment, rollback, or archive contract changed.
- Added the v1821 extraction note, readability ratchet, four SpotBugs FQN
  relocations for the primary/Closeout responses, progress evidence, and
  Chinese walkthrough.

## v1820 - Signed approval artifact draft text package review preflight registry package extraction

- Moved the signed-approval artifact-draft-text-package-review-preflight family
  into `ops.maintenance.signedapprovalartifactdrafttextpackagereviewpreflight`:
  fifteen physical implementation files moved, while the package-private gate
  catalog was collocated with the rejection-control catalog. Direct root `ops`
  Java files fall from 977 to 961 and total `ops` Java files stay at 1,352.
- Added the public
  `OpsShardReadinessSignedApprovalArtifactDraftTextPackageReviewPreflightRoutePaths`
  owner. Root route aggregation delegates to it, preserving all nine paths.
- Moved services keep reading v1819 TextPackageIntake endpoints. Retained root
  `TextPackageSubmissionPreflight` and ProfileSection readers import the new
  public endpoint/response boundary. No route, response, write, credential,
  deployment, rollback, or archive contract changed.
- Added the v1820 extraction note, readability test, SpotBugs FQN relocation,
  count-ratchet updates, progress evidence, and Chinese walkthrough.
## v1819 - Signed approval artifact draft text package intake registry package extraction

- Moved the operator-evidence-value-supply signed-approval
  artifact-draft-text-package-intake registry family into the new
  `ops.maintenance.signedapprovalartifactdrafttextpackageintake` subpackage:
  fifteen physical implementation files moved, while the package-private gate
  catalog was collocated with the guard catalog to offset the new route owner.
  Direct root `ops` Java files fall from 993 to 977 and total `ops` Java files
  stay at 1,352.
- Added the public
  `OpsShardReadinessSignedApprovalArtifactDraftTextPackageIntakeRoutePaths`
  owner for the artifact-draft-text-package-intake suffixes. The root
  `OpsShardReadinessRoutePaths` aggregator delegates to that owner, so route
  strings remain byte-identical while implementation ownership leaves root.
- Cross-family endpoint sub-recipe: the moved field catalogs keep reading
  already-public v1818 `ArtifactDraftInstructionPreflight` endpoint constants,
  and retained root readers (`TextPackageReviewPreflight`,
  `SignedApprovalDraftTextPackageProfileSection`) import this family's public
  immutable endpoint strings. No route, response, write boundary, credential
  boundary, deployment, rollback, or archive layout changed.
- Added
  `docs/ops/signed-approval-artifact-draft-text-package-intake-extraction-v1819.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1819Tests`, relocated the
  two `ArtifactDraftTextPackageIntakeResponse` EI_EXPOSE SpotBugs exclude FQNs
  to the new package, and lowered the governance ratchet
  `MAX_ROOT_OPS_MAIN_JAVA_FILES`, the mirrored
  `EXPECTED_ROOT_OPS_MAIN_JAVA_FILES`, and the exact measured root-package
  guard from 993 to 977.
## v1818 - Signed approval artifact draft instruction preflight registry package extraction

- Moved the operator-evidence-value-supply signed-approval
  artifact-draft-instruction-preflight registry family into the new
  `ops.maintenance.signedapprovalartifactdraftinstructionpreflight` subpackage:
  fifteen physical implementation files moved, while the package-private gate
  catalog was collocated with the guard catalog to offset the new route owner.
  Direct root `ops` Java files fall from 1,009 to 993 and total `ops` Java
  files stay at 1,352.
- Added the public
  `OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths`
  owner for the artifact-draft-instruction-preflight suffixes. The root
  `OpsShardReadinessRoutePaths` aggregator delegates to that owner, so route
  strings remain byte-identical while implementation ownership leaves root.
- Cross-family endpoint sub-recipe: the moved slot catalogs keep reading
  already-public v1817 `ArtifactDraftAuthoringReadiness` endpoint constants,
  and retained root readers (`TextPackageIntake`,
  `SignedApprovalDraftProfileSection`) import this family's public immutable
  endpoint strings. No route, response, write boundary, credential boundary,
  deployment, rollback, or archive layout changed.
- Added
  `docs/ops/signed-approval-artifact-draft-instruction-preflight-extraction-v1818.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1818Tests`, relocated the
  two `ArtifactDraftInstructionPreflightResponse` EI_EXPOSE spotbugs-exclude
  FQNs to the new package, and lowered the governance ratchet
  `MAX_ROOT_OPS_MAIN_JAVA_FILES`, the mirrored
  `EXPECTED_ROOT_OPS_MAIN_JAVA_FILES`, and the exact measured root-package guard
  from 1009 to 993.
## v1817 - Signed approval artifact draft authoring readiness registry package extraction

- Moved the operator-evidence-value-supply signed-approval
  artifact-draft-authoring-readiness registry family into the new
  `ops.maintenance.signedapprovalartifactdraftauthoringreadiness` subpackage:
  fifteen physical implementation files moved, while the package-private gate
  catalog was collocated with the blocker catalog to offset the new route
  owner. Direct root `ops` Java files fall from 1,025 to 1,009 and total `ops`
  Java files stay at 1,352.
- Added the public
  `OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths`
  owner for the artifact-draft-authoring-readiness suffixes. The root
  `OpsShardReadinessRoutePaths` aggregator delegates to that owner, so route
  strings remain byte-identical while implementation ownership leaves root.
- Cross-family endpoint sub-recipe: the moved requirement catalogs keep reading
  already-public v1816 `ArtifactDraftReviewPackagePreflight` endpoint constants,
  and retained root readers (`InstructionPreflight`,
  `SignedApprovalDraftProfileSection`) import this family's public immutable
  endpoint strings. No route, response, write boundary, credential boundary,
  deployment, rollback, or archive layout changed.
- Added
  `docs/ops/signed-approval-artifact-draft-authoring-readiness-extraction-v1817.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1817Tests`, relocated the
  two `ArtifactDraftAuthoringReadinessResponse` EI_EXPOSE spotbugs-exclude FQNs
  to the new package, and lowered the governance ratchet
  `MAX_ROOT_OPS_MAIN_JAVA_FILES`, the mirrored
  `EXPECTED_ROOT_OPS_MAIN_JAVA_FILES`, and the exact measured root-package guard
  from 1025 to 1009.

## v1816 - Signed approval artifact draft review package preflight registry package extraction

- Moved the operator-evidence-value-supply signed-approval
  artifact-draft-review-package-preflight registry family into the new
  `ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight`
  subpackage: fifteen physical implementation files moved, while the
  package-private gate catalog was collocated with the guard catalog to offset
  the new route owner. Direct root `ops` Java files fall from 1,041 to 1,025
  and total `ops` Java files stay at 1,352.
- Added the public
  `OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths`
  owner for the artifact-draft-review-package-preflight suffixes. The root
  `OpsShardReadinessRoutePaths` aggregator delegates to that owner, so route
  strings remain byte-identical while implementation ownership leaves root.
- Cross-family endpoint sub-recipe: the moved slot catalogs keep reading
  already-public v1815 `ArtifactDraftReadinessLane` endpoint constants, and
  retained root readers (`AuthoringReadiness`,
  `SignedApprovalDraftProfileSection`) import this family's public immutable
  endpoint strings. No route, response, write boundary, credential boundary,
  deployment, rollback, or archive layout changed.
- Added
  `docs/ops/signed-approval-artifact-draft-review-package-preflight-extraction-v1816.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1816Tests`, relocated the
  two `ArtifactDraftReviewPackagePreflightResponse` EI_EXPOSE spotbugs-exclude
  FQNs to the new package, and lowered the governance ratchet
  `MAX_ROOT_OPS_MAIN_JAVA_FILES`, the mirrored
  `EXPECTED_ROOT_OPS_MAIN_JAVA_FILES`, and the exact measured root-package guard
  from 1041 to 1025.

## v1815 - Signed approval artifact draft readiness lane registry package extraction

- Moved the operator-evidence-value-supply signed-approval
  artifact-draft-readiness-lane registry family into the new
  `ops.maintenance.signedapprovalartifactdraftreadinesslane` subpackage:
  fifteen physical implementation files moved, while the package-private gate
  catalog was collocated with the blocker catalog to offset the new route owner.
  Direct root `ops` Java files fall from 1,057 to 1,041 and total `ops` Java
  files stay at 1,352.
- Added the public
  `OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths` owner
  for the artifact-draft-readiness-lane suffixes. The root
  `OpsShardReadinessRoutePaths` aggregator delegates to that owner, so route
  strings remain byte-identical while implementation ownership leaves root.
- Cross-family endpoint sub-recipe: the moved lane catalogs keep reading
  already-public v1814 `ArtifactDraftPreflight` endpoint constants, and retained
  root `ReviewPackagePreflight` slot catalogs import this family's public
  immutable endpoint strings. No route, response, write boundary, credential
  boundary, deployment, rollback, or archive layout changed.
- Added
  `docs/ops/signed-approval-artifact-draft-readiness-lane-extraction-v1815.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1815Tests`, relocated the
  two `ArtifactDraftReadinessLaneResponse` EI_EXPOSE spotbugs-exclude FQNs to
  the new package, and lowered the governance ratchet
  `MAX_ROOT_OPS_MAIN_JAVA_FILES`, the mirrored
  `EXPECTED_ROOT_OPS_MAIN_JAVA_FILES`, and the exact measured root-package guard
  from 1057 to 1041.

## v1814 - Signed approval artifact draft preflight registry package extraction

- Moved the operator-evidence-value-supply signed-approval
  artifact-draft-preflight registry family into the new
  `ops.maintenance.signedapprovalartifactdraftpreflight` subpackage: fifteen
  physical implementation files moved, while the package-private gate catalog
  was collocated with the guard catalog to offset the new route owner. Direct
  root `ops` Java files fall from 1,073 to 1,057 and total `ops` Java files stay
  at 1,352.
- Added the public
  `OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths` owner for
  the artifact-draft-preflight suffixes. The root `OpsShardReadinessRoutePaths`
  aggregator delegates to that owner, so route strings remain byte-identical
  while implementation ownership leaves root.
- Cross-family endpoint sub-recipe: the moved field catalogs keep reading
  already-public v1813 `ArtifactDraftReadiness` endpoint constants, and retained
  root readers (`ArtifactDraftReadinessLane`,
  `SignedApprovalDraftProfileSection`) import this family's public immutable
  endpoint strings. No route, response, write boundary, credential boundary,
  deployment, rollback, or archive layout changed.
- Added `docs/ops/signed-approval-artifact-draft-preflight-extraction-v1814.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1814Tests`, relocated the
  two `ArtifactDraftPreflightResponse` EI_EXPOSE spotbugs-exclude FQNs to the
  new package, and lowered the governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES`,
  the mirrored `EXPECTED_ROOT_OPS_MAIN_JAVA_FILES`, and the exact measured
  root-package guard from 1073 to 1057.

## v1813 - Signed approval artifact draft readiness registry package extraction

- Moved the operator-evidence-value-supply signed-approval
  artifact-draft-readiness registry family - sixteen non-controller
  implementation files - into the new
  `ops.maintenance.signedapprovalartifactdraftreadiness` subpackage, reducing
  direct root `ops` Java files from 1,089 to 1,073. The two public
  `@RestController` classes and the global `OpsShardReadinessRoutePaths`
  aggregator stay in root.
- The moved services were repointed from the package-private root aggregator to
  the public family route owner
  `OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths`, which already
  lived in `ops.maintenance.signedapproval` from the v1804 route-path
  consolidation; v1813 only adds a public `BASE_PATH` to that owner. The root
  aggregator still delegates to the same owner, keeping every endpoint string
  byte-identical.
- Cross-family endpoint sub-recipe: the moved item catalogs read ten
  `CaptureArtifactPreflight` endpoint constants already publicized in v1810 (no
  new outbound change); this family's own service endpoint constants are
  publicized as immutable read-only strings for three retained-root sibling
  readers (`ArtifactDraftPreflight`, `ArtifactDraftReviewPackagePreflight`,
  `SignedApprovalDraftProfileSection`). No route, response, write boundary,
  credential boundary, deployment, rollback, or archive layout changed.
- Added `docs/ops/signed-approval-artifact-draft-readiness-extraction-v1813.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1813Tests`, relocated the two
  `ArtifactDraftReadinessResponse` EI_EXPOSE spotbugs-exclude FQNs to the new
  package, and lowered the governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES`, the
  mirrored `EXPECTED_ROOT_OPS_MAIN_JAVA_FILES`, and the exact measured root guard
  from 1089 to 1073.

## v1812 - Approval preflight registry package extraction

- Moved the operator-evidence-value-supply approval-preflight registry family
  into the new `ops.maintenance.approvalpreflight` subpackage: fifteen physical
  implementation files moved, while the package-private policy catalog was
  collocated with the item catalog to offset the new route owner. Direct root
  `ops` Java files fall from 1105 to 1089 and total `ops` Java files stay at
  1352.
- Added the public
  `OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePaths`
  owner for the approval-preflight suffixes. The root
  `OpsShardReadinessRoutePaths` aggregator delegates to that owner, so route
  strings remain byte-identical while implementation ownership leaves root.
- Applied the endpoint-only cross-family recipe: the moved `ItemCatalog` imports
  seven upstream value-supply or adapter-preflight endpoint constants, now
  public immutable strings, and the v1811
  `SignedApprovalCapturePreflightInputCatalog` imports approval-preflight
  endpoint constants from the new package. No route, response, write boundary,
  credential boundary, deployment, rollback, or archive layout changed.
- Added `docs/ops/approval-preflight-extraction-v1812.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1812Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES`, the mirrored
  `EXPECTED_ROOT_OPS_MAIN_JAVA_FILES`, and the exact measured root-package
  guard from 1105 to 1089.

## v1811 - Signed approval capture preflight registry package extraction

- Moved the operator-evidence-value-supply signed-approval capture-preflight
  registry family - sixteen non-controller implementation files - into the new
  `ops.maintenance.signedapprovalcapturepreflight` subpackage, reducing direct
  root `ops` Java files from 1121 to 1105.
- Left the two public controllers and the root `OpsShardReadinessRoutePaths`
  aggregator in the root package. Services now build `ENDPOINT` values from the
  public `OpsShardReadinessSignedApprovalCapturePreflightRoutePaths` owner,
  which already lived in `ops.maintenance.signedapproval` from the v1804
  route-path consolidation; v1811 only adds its public `BASE_PATH`.
- Applied the endpoint-only cross-family recipe in both directions:
  `InputCatalog` imports eleven upstream `ApprovalPreflight` endpoint constants
  that are now public immutable strings, and the v1810
  `CaptureArtifactPreflightFragmentCatalog` imports ten sibling
  `CapturePreflight` endpoint constants from the new package. No route,
  response, write boundary, credential boundary, deployment, rollback, or
  archive layout changed.
- Added `docs/ops/signed-approval-capture-preflight-extraction-v1811.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1811Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES`, the mirrored
  `EXPECTED_ROOT_OPS_MAIN_JAVA_FILES`, and the exact measured root-package
  guard from 1121 to 1105.

## v1810 - Signed approval capture artifact preflight registry package extraction

- Moved the operator-evidence-value-supply signed-approval
  capture-artifact-preflight registry family - sixteen non-controller
  implementation files - into the new
  `ops.maintenance.signedapprovalcaptureartifactpreflight` subpackage, reducing
  direct root `ops` Java files from 1,137 to 1,121. The two public
  `@RestController` classes and the global `OpsShardReadinessRoutePaths`
  aggregator stay in root.
- The moved services were repointed from the package-private root aggregator to
  the public family route owner
  `OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths`, which
  already lived in `ops.maintenance.signedapproval` from the v1804 route-path
  consolidation; v1810 only adds a public `BASE_PATH` to that owner. The root
  aggregator still delegates to the same owner, keeping every endpoint string
  byte-identical.
- Applied the cross-family endpoint sub-recipe: the family `FragmentCatalog`
  reads ten sibling `CapturePreflight` endpoint constants, which are now
  publicized as immutable read-only strings and imported into the moved file;
  the retained-root `ArtifactDraftReadiness` item catalogs read this family's
  endpoint constants, which are likewise publicized. No route, response, write
  boundary, credential boundary, deployment, rollback, or archive layout changed.
- Added `docs/ops/signed-approval-capture-artifact-preflight-extraction-v1810.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1810Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` and the mirrored
  `EXPECTED_ROOT_OPS_MAIN_JAVA_FILES` from 1137 to 1121.

## v1809 - Manual evidence worksheet registry package extraction

- Moved the manual-evidence-worksheet registry family - fifteen non-controller
  implementation files plus the family route-path class
  `OpsShardReadinessManualEvidenceWorksheetRoutePaths` - into the new
  `ops.maintenance.manualevidenceworksheet` subpackage, reducing direct root
  `ops` Java files from 1,152 to 1,137. The two public `@RestController`
  classes and the global `OpsShardReadinessRoutePaths` aggregator stay in root.
- The family route-path class was made public with a public `BASE_PATH` and
  public suffix constants; relocated services were repointed from the
  package-private aggregator to the family route-path class. The root aggregator
  imports and delegates to the moved owner, keeping every endpoint string
  byte-identical.
- Continued the cross-family endpoint sub-recipe upstream of
  `OperatorEvidenceImportPreflight`: downstream import-preflight services now
  import worksheet endpoint constants from the new package, and the worksheet
  services publicize only immutable `RuntimeExecution` endpoint strings they
  already referenced. No route, response, write boundary, credential boundary,
  deployment, rollback, or archive layout changed.
- Added `docs/ops/manual-evidence-worksheet-extraction-v1809.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1809Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1152 to 1137.

## v1808 - Operator evidence import preflight registry package extraction

- Moved the operator-evidence-import-preflight registry family - fifteen
  non-controller implementation files plus the family route-path class
  `OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths` - into the new
  `ops.maintenance.operatorevidenceimportpreflight` subpackage, reducing direct
  root `ops` Java files from 1,167 to 1,152. The two public `@RestController`
  classes and the global `OpsShardReadinessRoutePaths` aggregator stay in root.
- The family route-path class was made public with a public `BASE_PATH` and
  public suffix constants; relocated services were repointed from the
  package-private aggregator to the family route-path class. The root aggregator
  now imports and delegates to the moved owner, keeping every endpoint string
  byte-identical.
- Continued the cross-family endpoint sub-recipe: the moved ImportPreflight
  services import immutable `ManualEvidenceWorksheet` and
  `RuntimeExecutionLiveReadGate` endpoint constants, so those upstream
  `ENDPOINT` constants are now public. No route, response, write boundary,
  credential boundary, deployment, rollback, or archive layout changed.
- Added `docs/ops/operator-evidence-import-preflight-extraction-v1808.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1808Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1167 to 1152.

## v1806 - Java extraction quality closeout

- Added `docs/ops/java-extraction-quality-closeout-v1806.md` to record the
  current v1805 green baseline, the historical v1798 tag exception, the v1799
  remediation boundary, and the policy that historical tags must not be
  rewritten or force-moved.
- Added `ReadabilityUpkeepOpsConsolidationQualityCloseoutV1806Tests` so the
  closeout evidence, current root-package count (`1,183`), progress table, ops
  index, and changelog stay discoverable.
- No business code, route string, response schema, runtime profile, archive
  layout, deployment, rollback, or evidence contract changed.

## v1805 - Candidate document registry package extraction

- Moved the entire candidate-document registry family — 57 non-controller
  implementation files plus the family route-path class
  `OpsShardReadinessCandidateDocumentRoutePaths` — into the new
  `ops.maintenance.candidatedocument` subpackage, reducing direct root `ops`
  Java files from 1,240 to 1,183 (the largest single reduction in the
  consolidation program). The eight public `@RestController` classes and the
  global `OpsShardReadinessRoutePaths` aggregator stay in root.
- The family route-path class was made public with a public `BASE_PATH` and
  public suffix constants; the relocated services were repointed from the
  package-private aggregator to the family route-path class. Dependency injection
  is intra-family, so the family moved as one unit with no cross-package wiring.
- Handled the one genuine cross-family edge: two candidate-document catalogs
  reference the compared-evidence candidate-intake-preflight catalog route, which
  the aggregator previously defined inline. That constant now lives in the
  candidate-document route-path class and the aggregator delegates to it, so the
  compared-evidence family keeps the same value through the aggregator.
- Relocated 19 SpotBugs EI_EXPOSE_REP/REP2 exclusions across 9 candidate-document
  response classes to the new fully-qualified names. Two shared test-support
  classes used by retained root tests were made public.
- Added `docs/ops/candidate-document-extraction-v1805.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1805Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1240 to 1183.

## v1807 - Operator evidence value draft registry package extraction

- Moved the operator-evidence-value-draft registry family — 16 non-controller
  implementation files plus the family route-path class
  `OpsShardReadinessOperatorEvidenceValueDraftRoutePaths` — into the new
  `ops.maintenance.operatorevidencevaluedraft` subpackage, reducing direct root
  `ops` Java files from 1,183 to 1,167. The two public `@RestController` classes
  and the global `OpsShardReadinessRoutePaths` aggregator stay in root.
- First application of the cross-family endpoint sub-recipe (visibility only, no
  route change): made seven `OperatorEvidenceImportPreflight` service `ENDPOINT`
  constants public (read outbound by the relocated value-draft files) and the
  value-draft service `ENDPOINT` constants public (read inbound by the root
  `OperatorEvidenceValueSupplySlotCatalog` and a value-draft route guard test),
  adding imports across the new package boundary.
- The family route-path class was made public with a public `BASE_PATH` and
  public suffix constants; relocated services were repointed from the
  package-private aggregator to the family route-path class. Relocated 2 SpotBugs
  EI_EXPOSE exclusions to the new fully-qualified names.
- Added `docs/ops/operator-evidence-value-draft-extraction-v1807.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1807Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1183 to 1167.

## v1804 - Signed approval route-path consolidation

- Moved three signed-approval route-path classes
  (`OpsShardReadinessSignedApproval{ArtifactDraftReadiness,CaptureArtifactPreflight,CapturePreflight}RoutePaths`)
  into the new `ops.maintenance.signedapproval` subpackage, reducing direct root
  `ops` Java files from 1,243 to 1,240 while keeping the total ops file count
  stable. This is the first pure route-path leaf consolidation (no service,
  controller, or response moves) and stands up the signedapproval subpackage for
  later migration of the operator-evidence-value-supply signed-approval registry
  families.
- Made the three route-path classes and their
  `OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_*` suffix constants public
  (behaviour-neutral; values unchanged). Each class is referenced directly only
  by the root `OpsShardReadinessRoutePaths` aggregator (which still delegates the
  matching public suffix) and its single `...RoutePathsTests` guard; both were
  repointed by import only. The registry services/controllers that own those
  routes stay in root and continue to read the suffixes through the aggregator,
  so every endpoint string is byte-identical.
- Added `docs/ops/signed-approval-route-path-consolidation-v1804.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1804Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1243 to 1240.

## v1803 - Sandbox connection registry package extraction

- Moved twenty-six sandbox connection implementation files (two sibling registry
  sub-clusters — the blocked-execution-context dossier and the precheck
  upstream-receipt verification manifest — that share one route-path class) into
  `ops.maintenance.sandboxconnection`, reducing direct root `ops` Java files from
  1,269 to 1,243 while keeping the total ops file count stable. This is the
  second dependency-injected "evidence" registry family extracted and the largest
  single root-pressure reduction so far.
- Made the family route-path class public with its public `BASE_PATH`/suffixes;
  both root controllers and the aggregator import it; the moved
  services/catalogs/support import the public `OpsEvidenceService` and
  `ReleaseApprovalRehearsalResponse` types they previously referenced same-package.
  The routes
  `/api/v1/ops/shard-readiness/sandbox-connection-blocked-execution-context-normalization-dossier`
  and
  `/api/v1/ops/shard-readiness/sandbox-connection-precheck-upstream-receipt-verification-manifest`,
  both response shapes, and read-only flags are byte-identical.
- Made the single shared schema-version constant
  `RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_CONNECTION_PRECHECK_PACKET_ECHO_RECEIPT_SCHEMA_VERSION`
  on `OpsEvidenceService` public (immutable string, behaviour-neutral) so the
  moved support can read it; the moved test supports reuse the already-public
  `OpsEvidenceServiceTestFixtures`, and both route/controller tests stay in root
  and construct their service directly through that fixture.
- Relocated the moved Responses' accepted `EI_EXPOSE_REP/REP2` exclusions in
  `config/spotbugs-exclude.xml` (eleven entries) to the new FQN (same accepted
  findings, none new); the `ReleaseApproval*SandboxConnection*Records` exclusions
  stay in root because those records do not move.
- Added `docs/ops/sandbox-connection-extraction-v1803.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1803Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1269 to 1243.

## v1802 - Credential resolver disabled fake harness evidence archive package extraction

- Moved eleven credential resolver disabled fake harness evidence archive
  implementation files into `ops.maintenance.credentialresolver`, reducing direct
  root `ops` Java files from 1,280 to 1,269 while keeping the total ops file
  count stable. First dependency-injected "evidence" registry extracted (second
  family outside CodeWalkthrough).
- Made the family route-path class public with its public `BASE_PATH`/suffix;
  the root controller and aggregator import it; the moved service/catalogs import
  the public `OpsEvidenceService` and `ReleaseApprovalRehearsalResponse` types
  they previously referenced same-package. The route
  `/api/v1/ops/shard-readiness/credential-resolver-disabled-fake-harness-evidence-archive`,
  response shape, and read-only flags are byte-identical.
- Made the shared test helper `OpsEvidenceServiceTestFixtures` public so the
  moved package-local test support can reuse it; the root service/controller test
  now constructs the service directly through that fixture.
- Relocated the moved Response's accepted `EI_EXPOSE_REP/REP2` exclusions in
  `config/spotbugs-exclude.xml` to the new FQN (same accepted findings, none new).
- Added `docs/ops/credential-resolver-disabled-fake-harness-evidence-archive-extraction-v1802.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1802Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1280 to 1269.

## v1801 - Screenshot explanation archive registry package extraction

- Moved ten screenshot explanation archive registry implementation files into
  `ops.maintenance.screenshotexplanationarchive`, reducing direct root `ops`
  Java files from 1,290 to 1,280 while keeping the total ops file count stable.
  This is the first extraction outside the CodeWalkthrough family.
- Mirrored the v1797–v1800 recipe: made the screenshot explanation archive
  route-path class public (with its own `BASE_PATH`), repointed the moved service
  to it, made `ENDPOINT` public, moved the package-local service/renderer/
  boundary/immutability/closeout/f-root-policy/test-support tests into the
  subpackage; the segmentation docs, controller, and route-path tests stay in
  root (controller/route-path tests construct the service directly). The route
  `/api/v1/ops/shard-readiness/screenshot-explanation-archive-registry`, response
  version, read-only flags, and root controller entry point are byte-identical.
- Relocated the moved Response's accepted `EI_EXPOSE_REP/REP2` exclusions in
  `config/spotbugs-exclude.xml` to the new FQN (same accepted findings, none new).
- Added `docs/ops/screenshot-explanation-archive-extraction-v1801.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1801Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1290 to 1280.

## v1800 - Code walkthrough depth registry package extraction

- Moved eight code walkthrough depth registry implementation files into
  `ops.maintenance.walkthrough.depth`, reducing direct root `ops` Java files
  from 1,298 to 1,290 while keeping the total ops file count stable. This
  completes moving all four CodeWalkthrough registry families (compliance,
  quality gate, quality audit, depth) out of the root package.
- Mirrored the v1797–v1799 recipe: made the depth route-path class public (with
  its own `BASE_PATH`), repointed the moved service to it, made `ENDPOINT`
  public, moved the package-local service/renderer/boundary/test-support tests
  into the subpackage; the root controller and route-path tests construct the
  service directly. The route
  `/api/v1/ops/shard-readiness/code-walkthrough-depth-registry`, response
  version, read-only flags, and root controller entry point are byte-identical.
- Relocated the moved Response's accepted `EI_EXPOSE_REP/REP2` exclusions in
  `config/spotbugs-exclude.xml` to the new FQN (same accepted findings, none new).
- Added `docs/ops/depth-registry-extraction-v1800.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1800Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1298 to 1290.

## v1799 - Code walkthrough quality audit registry package extraction

- Moved eleven code walkthrough quality audit registry implementation files into
  `ops.maintenance.walkthrough.qualityaudit`, reducing direct root `ops` Java
  files from 1,309 to 1,298 while keeping the total ops file count stable.
- Mirrored the v1797/v1798 recipe: made the quality audit route-path class public
  (with its own `BASE_PATH`) and repointed the moved service to it; made
  `ENDPOINT` public; moved the package-local service/renderer/boundary/
  immutability/closeout/test-support tests into the subpackage; the root
  controller and route-path tests construct the service directly. The route
  `/api/v1/ops/shard-readiness/code-walkthrough-quality-audit-registry`, response
  version, read-only flags, and root controller entry point are byte-identical.
- Relocated the moved Response's accepted `EI_EXPOSE_REP/REP2` exclusions in
  `config/spotbugs-exclude.xml` to the new FQN (same accepted findings, none new).
- Added `docs/ops/quality-audit-registry-extraction-v1799.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1799Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1309 to 1298.
- Fixed a latent gate failure inherited from v1798: the v1798 Chinese walkthrough
  had been written after that version's verify and committed without re-running,
  leaving it below the 3000-CJK / Chinese-majority threshold enforced by
  `OpsCodeWalkthroughArchiveComplianceTests`. This version's full verify caught
  it; the v1798 walkthrough was expanded to satisfy the gate and a full
  `mvnw verify` now passes (1495 tests, JaCoCo floors met, SpotBugs/Spotless
  clean).

## v1798 - Code walkthrough quality gate registry package extraction

- Moved ten code walkthrough quality gate registry implementation files into
  `ops.maintenance.walkthrough.qualitygate`, reducing direct root `ops` Java
  files from 1,319 to 1,309 while keeping the total ops file count stable.
- Made the quality gate route-path class public (with its own `BASE_PATH`) so
  the moved service builds the endpoint from its own subpackage route-path
  class; the root `OpsShardReadinessRoutePaths` table still delegates the public
  suffix, keeping the
  `/api/v1/ops/shard-readiness/code-walkthrough-quality-gate-registry` route,
  response version, read-only runtime flags, and root controller entry point
  byte-identical.
- Moved the package-local service/renderer/boundary/immutability/test-support
  tests into the subpackage; the root controller and route-path tests construct
  the service directly and import the public route-path class (mirroring v1797).
- Added `docs/ops/quality-gate-registry-extraction-v1798.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1798Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1319 to 1309.

## v1797 - Code walkthrough compliance package extraction

- Moved eleven code walkthrough compliance implementation files into
  `ops.maintenance.walkthrough.compliance`, reducing direct root `ops` Java
  files from 1,330 to 1,319 while keeping the total ops file count stable.
- Preserved the existing
  `/api/v1/ops/shard-readiness/code-walkthrough-compliance-registry` route,
  response version, read-only runtime flags, and root controller entry point.
- Added `docs/ops/code-walkthrough-compliance-extraction-v1797.md` plus
  extraction guard tests so future consolidation batches cannot silently move
  archives, reopen runtime boundaries, or grow the root package again.

## v1796 - Ops consolidation inventory baseline

- 新增 `docs/ops/ops-consolidation-inventory-v1796.md`，记录 ops 包当前
  1,352 个主源码文件、1,330 个根包直放文件、1,210 个 Readiness 命名文件。
- 固化 route family、load-bearing archive 和 reduction candidate 清单，为后续
  contract-preserving 拆分提供边界。
- 新增文档守卫测试，确保 J6 盘点、历史归档不搬迁规则和 v1796 不搬类停线可发现。
- 修正本地 Spotless ratchet 默认基准为 `javaproject/master`，与 Java canonical
  remote 规范一致；GitHub Actions 仍按 workflow 显式参数选择 CI 基准。

## v1795 - Production readiness documentation discipline

- 新增 `PRODUCTION_READINESS.md`，集中记录生产边界、运行 profile、消息、支付、
  failed-event replay、release approval rehearsal、credential、SQL、部署和回滚限制。
- 新增 changelog 版本策略，明确 git tag `vNNNN-*` 是当前权威版本号，pom 仍保持
  `0.1.0-SNAPSHOT`。
- 新增文档守卫测试，防止 CHANGELOG、PRODUCTION_READINESS 和 README 指针漂移。

## v1794 - Production observability tracing

- 增加 Micrometer Tracing Brave bridge、trace/span 日志 pattern 和异常处理器日志相关性。
- 明确 actuator 只暴露 health、info、metrics，并补真实 HTTP trace/span 日志测试。

## v1793 - Production profile and request validation hardening

- 新增 `application-prod.yml`，关闭 H2 console 和 SQL debug 输出，启用 graceful shutdown。
- compose 凭据改为环境变量覆盖，新增 `.env.example`。
- 订单与 failed-event 写请求补充 Bean Validation 边界和 ProblemDetail 映射。

## v1792 - Coverage ratchet

- 新增 JaCoCo 基线和 package-level coverage floors。
- CI 上传 JaCoCo artifact，docker profile 不再代表覆盖率门。

## v1791 - Static analysis ratchets

- 新增 Maven Enforcer、Spotless ratchet 和 SpotBugs baseline。
- CI 开始阻断新增格式和静态分析问题。

## v1790 - CI bootstrap

- 新增 Maven wrapper。
- Docker/Testcontainers 测试与默认 headless suite 分离。
- GitHub Actions 工作流开始运行默认 verify 和 docker profile verify。

## v1789 - Java ops governance consolidation roadmap

- 新增 Java ops package 整合路线图和 ratchet 方向。
- 明确不得移动 `a/` 到 `f/` 历史归档及 evidence JSON。

## v1788 - Readability upkeep audit closeout

- 完成 readability upkeep audit closeout 证据。
- 记录 v1784-v1788 可读性保养周期结果。

## v1787 - Readability docs guard

- 增加可读性文档守卫，确保维护地图、归档布局和讲解规则可追踪。

## v1786 - Readability audit registry

- 增加 readability upkeep audit registry，让后期维护入口、边界和测试证据集中可查。
