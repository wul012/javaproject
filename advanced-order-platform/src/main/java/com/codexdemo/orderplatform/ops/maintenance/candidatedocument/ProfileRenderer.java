package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.FieldEntry;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ProfileSection;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.RenderedSection;
import com.codexdemo.orderplatform.ops.maintenance.rendering.ProfileSections;
import com.codexdemo.orderplatform.ops.maintenance.rendering.ProfileSections.Field;
import com.codexdemo.orderplatform.ops.maintenance.rendering.ProfileSections.Rendered;
import com.codexdemo.orderplatform.ops.maintenance.rendering.ProfileSections.Section;
import java.util.List;

final class ProfileRenderer {

  private ProfileRenderer() {}

  static List<RenderedSection> render(
      List<ProfileSection> sections, List<FieldEntry> fieldEntries) {
    return ProfileSections.render(
            sections.stream().map(ProfileRenderer::view).toList(),
            fieldEntries.stream().map(ProfileRenderer::view).toList())
        .stream()
        .map(ProfileRenderer::response)
        .toList();
  }

  private static Section view(ProfileSection section) {
    return Section.ungrouped(section.order(), section.code(), section.heading());
  }

  private static Field view(FieldEntry field) {
    return new Field(field.sectionCode(), field.fieldName(), field.fieldValue());
  }

  private static RenderedSection response(Rendered section) {
    return new RenderedSection(
        section.order(),
        section.code(),
        section.markdownHeading(),
        section.markdownBody(),
        "passed");
  }
}
