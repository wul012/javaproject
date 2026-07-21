package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldrafttextpackageprofilesection;

import com.codexdemo.orderplatform.ops.maintenance.rendering.ProfileSections;
import com.codexdemo.orderplatform.ops.maintenance.rendering.ProfileSections.Field;
import com.codexdemo.orderplatform.ops.maintenance.rendering.ProfileSections.Rendered;
import com.codexdemo.orderplatform.ops.maintenance.rendering.ProfileSections.Section;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovaldrafttextpackageprofilesection.OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.FieldEntry;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovaldrafttextpackageprofilesection.OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.RenderedSection;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovaldrafttextpackageprofilesection.OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.TextPackageProfileSection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

final class ProfileRenderer {

  private static final Set<String> GROUPS = Set.of("submission", "compared-evidence");

  private ProfileRenderer() {}

  static List<RenderedSection> render(
      List<TextPackageProfileSection> sections, List<FieldEntry> fieldEntries) {
    var sectionViews =
        sections.stream()
            .filter(section -> GROUPS.contains(section.rendererGroup()))
            .map(ProfileRenderer::view)
            .toList();
    return ProfileSections.render(
            sectionViews, fieldEntries.stream().map(ProfileRenderer::view).toList())
        .stream()
        .sorted(Comparator.comparingInt(Rendered::order))
        .map(ProfileRenderer::response)
        .toList();
  }

  private static Section view(TextPackageProfileSection section) {
    return new Section(section.order(), section.code(), section.rendererGroup(), section.heading());
  }

  private static Field view(FieldEntry field) {
    return new Field(field.sectionCode(), field.fieldName(), field.fieldValue());
  }

  private static RenderedSection response(Rendered section) {
    return new RenderedSection(
        section.order(),
        section.code(),
        section.group(),
        section.markdownHeading(),
        section.markdownBody(),
        "passed");
  }
}
