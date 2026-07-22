package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.FieldEntry;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ModuleEntry;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ProfileSection;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.RouteFieldLock;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.SectionSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

final class ProfileCatalog {

  private ProfileCatalog() {}

  static Evidence from(
      OpsShardReadinessCandidateDocumentRequestPackageResponse requestPackage,
      OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse submissionPrecheck,
      OpsShardReadinessCandidateDocumentIntakePacketResponse intakePacket,
      OpsShardReadinessCandidateDocumentMaterialRequestResponse materialRequest,
      OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse materialPrecheck) {
    var sources =
        sources(
            requestPackage, submissionPrecheck, intakePacket, materialRequest, materialPrecheck);
    var sections = sections(sources);
    return new Evidence(
        modules(), sources, sections, fieldEntries(sections), routeFieldLocks(sections), gates());
  }

  private static List<ModuleEntry> modules() {
    return List.of(
        module(
            219,
            "candidate-document-profile-section-types",
            "defines candidate document profile section records"),
        module(
            220,
            "candidate-document-profile-section-source-catalog",
            "collects the five route-facing candidate document sources"),
        module(
            221,
            "candidate-document-profile-section-field-catalog",
            "locks endpoint, profile, version, status, and boundary fields"),
        module(
            222,
            "candidate-document-profile-section-renderer",
            "renders stable Markdown for the extracted section group"),
        module(
            223,
            "candidate-document-profile-section-registry-route",
            "exposes the registry without changing runtime behavior"));
  }

  private static ModuleEntry module(int order, String code, String responsibility) {
    return new ModuleEntry(order, code, responsibility, "passed");
  }

  private static List<SectionSource> sources(
      OpsShardReadinessCandidateDocumentRequestPackageResponse requestPackage,
      OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse submissionPrecheck,
      OpsShardReadinessCandidateDocumentIntakePacketResponse intakePacket,
      OpsShardReadinessCandidateDocumentMaterialRequestResponse materialRequest,
      OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse materialPrecheck) {
    return List.of(
        source(
            1,
            "candidate-document-request-package",
            requestPackage.version(),
            requestPackage.endpoint(),
            requestPackage.profile(),
            requestPackage.status()),
        source(
            2,
            "candidate-document-submission-precheck",
            submissionPrecheck.version(),
            submissionPrecheck.endpoint(),
            submissionPrecheck.profile(),
            submissionPrecheck.status()),
        source(
            3,
            "candidate-document-intake-packet",
            intakePacket.version(),
            intakePacket.endpoint(),
            intakePacket.profile(),
            intakePacket.status()),
        source(
            4,
            "candidate-document-material-request",
            materialRequest.version(),
            materialRequest.endpoint(),
            materialRequest.profile(),
            materialRequest.status()),
        source(
            5,
            "candidate-document-material-submission-precheck",
            materialPrecheck.version(),
            materialPrecheck.endpoint(),
            materialPrecheck.profile(),
            materialPrecheck.status()));
  }

  private static SectionSource source(
      int order,
      String code,
      String sourceVersion,
      String endpoint,
      String profile,
      String sourceStatus) {
    return new SectionSource(order, code, sourceVersion, endpoint, profile, sourceStatus, "passed");
  }

  private static List<ProfileSection> sections(List<SectionSource> sources) {
    return sources.stream()
        .map(
            source ->
                new ProfileSection(
                    source.order(),
                    source.code() + "-section",
                    heading(source.code()),
                    source.sourceVersion(),
                    source.endpoint(),
                    source.profile(),
                    5,
                    "candidate-document-profile-section-renderer",
                    "passed"))
        .toList();
  }

  private static String heading(String code) {
    return switch (code) {
      case "candidate-document-request-package" -> "Candidate Document Request Package";
      case "candidate-document-submission-precheck" -> "Candidate Document Submission Precheck";
      case "candidate-document-intake-packet" -> "Candidate Document Intake Packet";
      case "candidate-document-material-request" -> "Candidate Document Material Request";
      case "candidate-document-material-submission-precheck" ->
          "Candidate Document Material Submission Precheck";
      default -> "Candidate Document Section";
    };
  }

  private static List<FieldEntry> fieldEntries(List<ProfileSection> sections) {
    List<FieldEntry> entries = new ArrayList<>();
    int order = 1;
    for (var section : sections) {
      entries.add(entry(order++, section.code(), "version", section.sourceVersion(), true));
      entries.add(entry(order++, section.code(), "endpoint", section.endpoint(), true));
      entries.add(entry(order++, section.code(), "profile", section.profile(), true));
      entries.add(entry(order++, section.code(), "status", section.status(), true));
      entries.add(entry(order++, section.code(), "boundary", "read-only-no-runtime", false));
    }
    return entries;
  }

  private static FieldEntry entry(
      int order, String sectionCode, String fieldName, String fieldValue, boolean routeFacing) {
    return new FieldEntry(order, sectionCode, fieldName, fieldValue, routeFacing, "passed");
  }

  private static List<RouteFieldLock> routeFieldLocks(List<ProfileSection> sections) {
    return sections.stream()
        .map(
            section ->
                new RouteFieldLock(
                    section.code(),
                    section.endpoint(),
                    section.profile(),
                    section.sourceVersion(),
                    3,
                    "fail-closed-route-facing-fields",
                    "passed"))
        .toList();
  }

  private static List<String> gates() {
    return IntStream.rangeClosed(
            1, OpsShardReadinessCandidateDocumentProfileSectionRegistrySupport.EXPECTED_GATE_COUNT)
        .mapToObj(index -> "candidate-document-profile-section-registry-no-runtime-gate-" + index)
        .toList();
  }

  record Evidence(
      List<ModuleEntry> modules,
      List<SectionSource> sources,
      List<ProfileSection> sections,
      List<FieldEntry> fieldEntries,
      List<RouteFieldLock> routeFieldLocks,
      List<String> gates) {
    Evidence {
      modules = List.copyOf(modules);
      sources = List.copyOf(sources);
      sections = List.copyOf(sections);
      fieldEntries = List.copyOf(fieldEntries);
      routeFieldLocks = List.copyOf(routeFieldLocks);
      gates = List.copyOf(gates);
    }
  }
}
