package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessCandidateDocumentProfileSectionRegistryService {

  static final String RESPONSE_VERSION = "Java v1212";
  static final String ENDPOINT =
      OpsShardReadinessCandidateDocumentRoutePaths.BASE_PATH
          + OpsShardReadinessCandidateDocumentRoutePaths
              .CANDIDATE_DOCUMENT_PROFILE_SECTION_REGISTRY;
  static final String PROFILE =
      "java-shard-readiness-candidate-document-profile-section-registry.v1";

  private final OpsShardReadinessCandidateDocumentRequestPackageService requestPackageService;
  private final OpsShardReadinessCandidateDocumentSubmissionPrecheckService
      submissionPrecheckService;
  private final OpsShardReadinessCandidateDocumentIntakePacketService intakePacketService;
  private final OpsShardReadinessCandidateDocumentMaterialRequestService materialRequestService;
  private final OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService
      materialSubmissionPrecheckService;

  public OpsShardReadinessCandidateDocumentProfileSectionRegistryService(
      OpsShardReadinessCandidateDocumentRequestPackageService requestPackageService,
      OpsShardReadinessCandidateDocumentSubmissionPrecheckService submissionPrecheckService,
      OpsShardReadinessCandidateDocumentIntakePacketService intakePacketService,
      OpsShardReadinessCandidateDocumentMaterialRequestService materialRequestService,
      OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService
          materialSubmissionPrecheckService) {
    this.requestPackageService = requestPackageService;
    this.submissionPrecheckService = submissionPrecheckService;
    this.intakePacketService = intakePacketService;
    this.materialRequestService = materialRequestService;
    this.materialSubmissionPrecheckService = materialSubmissionPrecheckService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse registry() {
    var evidence =
        ProfileCatalog.from(
            requestPackageService.packageCatalog(),
            submissionPrecheckService.precheck(),
            intakePacketService.intakePacket(),
            materialRequestService.materialRequest(),
            materialSubmissionPrecheckService.materialSubmissionPrecheck());
    return OpsShardReadinessCandidateDocumentProfileSectionRegistrySupport.response(
        RESPONSE_VERSION,
        ENDPOINT,
        PROFILE,
        evidence.modules(),
        evidence.sources(),
        evidence.sections(),
        evidence.fieldEntries(),
        ProfileRenderer.render(evidence.sections(), evidence.fieldEntries()),
        evidence.routeFieldLocks(),
        evidence.gates(),
        List.of(
            "candidate-document-profile-section-registry-service-assembled-from-five-read-only-routes"));
  }
}
