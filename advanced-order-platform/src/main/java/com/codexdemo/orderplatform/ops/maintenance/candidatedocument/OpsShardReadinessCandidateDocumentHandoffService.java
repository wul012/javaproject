package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessCandidateDocumentHandoffService {

  static final String RESPONSE_VERSION = "Java v1107";
  static final String ENDPOINT =
      OpsShardReadinessCandidateDocumentRoutePaths.BASE_PATH
          + OpsShardReadinessCandidateDocumentRoutePaths.CANDIDATE_DOCUMENT_REQUEST_PACKAGE_HANDOFF;
  static final String PROFILE =
      "java-shard-readiness-candidate-document-request-package-handoff.v1";

  private final OpsShardReadinessCandidateDocumentRequestPackageService sourcePackageService;

  public OpsShardReadinessCandidateDocumentHandoffService(
      OpsShardReadinessCandidateDocumentRequestPackageService sourcePackageService) {
    this.sourcePackageService = sourcePackageService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessCandidateDocumentHandoffResponse handoff() {
    var sourcePackage = sourcePackageService.packageCatalog();
    return OpsShardReadinessCandidateDocumentHandoffSupport.response(
        RESPONSE_VERSION,
        ENDPOINT,
        PROFILE,
        sourcePackage,
        OpsShardReadinessCandidateDocumentHandoffSourceCatalog.sourceLineage(sourcePackage),
        OpsShardReadinessCandidateDocumentHandoffModuleCatalog.modules(),
        OpsShardReadinessCandidateDocumentHandoffArtifactCatalog.artifactHandles(sourcePackage),
        OpsShardReadinessCandidateDocumentHandoffPolicyCatalog.policyLocks(sourcePackage),
        OpsShardReadinessCandidateDocumentHandoffArchiveCatalog.archiveEntries(),
        OpsShardReadinessCandidateDocumentHandoffConsumerCatalog.consumerRules(),
        OpsShardReadinessCandidateDocumentHandoffGateCatalog.gates(),
        List.of("candidate-document-handoff-service-assembled-from-request-package"));
  }
}
