package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessCandidateDocumentRequestPackageService {

  static final String ENDPOINT =
      OpsShardReadinessCandidateDocumentRoutePaths.BASE_PATH
          + OpsShardReadinessCandidateDocumentRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_CANDIDATE_DOCUMENT_REQUEST_PACKAGE;
  static final String PROFILE = "java-shard-readiness-candidate-document-request-package.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessCandidateDocumentRequestPackageResponse packageCatalog() {
    return OpsShardReadinessCandidateDocumentRequestPackageSupport.response(
        "Java v1081",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessCandidateDocumentRequestPackageCatalog.requestItems(),
        OpsShardReadinessCandidateDocumentRequestPackageCatalog.acceptanceChecks(),
        OpsShardReadinessCandidateDocumentRequestPackageCatalog.gates(),
        List.of("candidate-document-request-package-read-only-route"));
  }
}
