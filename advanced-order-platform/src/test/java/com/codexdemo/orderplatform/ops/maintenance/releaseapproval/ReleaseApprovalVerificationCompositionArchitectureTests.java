package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class ReleaseApprovalVerificationCompositionArchitectureTests {

  private static final Path SOURCE_ROOT =
      Path.of(
          "src",
          "main",
          "java",
          "com",
          "codexdemo",
          "orderplatform",
          "ops",
          "maintenance",
          "releaseapproval");

  @Test
  void hintCompositionUsesOneContextAndOneReceiptChain() throws IOException {
    String hintBuilder = read("ReleaseApprovalVerificationHintBuilder.java");
    String responseBuilder = read("ReleaseApprovalRehearsalResponseBuilder.java");

    assertThat(hintBuilder)
        .contains(
            "ReleaseApprovalVerificationHintContext context)",
            "ContributionCatalog.build(context.receiptChain())",
            "WarningDigestBuilder(context.receiptChain()).build(context)",
            "NoLedgerWriteProofEvaluator().evaluate(context)")
        .doesNotContain("sandboxEndpointCredentialResolver");
    assertThat(responseBuilder)
        .contains("new ReleaseApprovalVerificationHintContext(")
        .doesNotContain("sandboxAdapterApprovalSchemaGuardReceiptBuilder()");
  }

  @Test
  void parameterExplosionCannotReturnThroughConstructorsOrBuildMethods() {
    assertThat(ReleaseApprovalVerificationHintContext.class.isRecord()).isTrue();
    assertThat(ReleaseApprovalVerificationHintContext.class.getRecordComponents()).hasSize(10);
    assertThat(ReleaseApprovalVerificationWarningDigestBuilder.class.getDeclaredConstructors())
        .allSatisfy(constructor -> assertThat(constructor.getParameterCount()).isEqualTo(1));

    Method hintBuild = declaredMethod(ReleaseApprovalVerificationHintBuilder.class, "build");
    Method digestBuild =
        declaredMethod(ReleaseApprovalVerificationWarningDigestBuilder.class, "build");
    Method noLedgerEvaluate =
        declaredMethod(ReleaseApprovalNoLedgerWriteProofEvaluator.class, "evaluate");
    assertThat(hintBuild.getParameterTypes())
        .containsExactly(ReleaseApprovalVerificationHintContext.class);
    assertThat(digestBuild.getParameterTypes())
        .containsExactly(ReleaseApprovalVerificationHintContext.class);
    assertThat(noLedgerEvaluate.getParameterTypes())
        .containsExactly(ReleaseApprovalVerificationHintContext.class);
  }

  @Test
  void splitFilesStayBelowTheirTightenedBudgets() throws IOException {
    assertThat(lineCount("ReleaseApprovalVerificationHintBuilder.java")).isLessThanOrEqualTo(70);
    assertThat(lineCount("ReleaseApprovalVerificationWarningDigestBuilder.java"))
        .isLessThanOrEqualTo(421);
    assertThat(lineCount("ReleaseApprovalRehearsalResponseBuilder.java")).isLessThanOrEqualTo(421);
    assertThat(lineCount("ReleaseApprovalVerificationSupport.java")).isLessThanOrEqualTo(412);
  }

  private static Method declaredMethod(Class<?> owner, String name) {
    return Arrays.stream(owner.getDeclaredMethods())
        .filter(method -> method.getName().equals(name))
        .findFirst()
        .orElseThrow();
  }

  private static String read(String fileName) throws IOException {
    return Files.readString(SOURCE_ROOT.resolve(fileName), StandardCharsets.UTF_8);
  }

  private static long lineCount(String fileName) throws IOException {
    try (var lines = Files.lines(SOURCE_ROOT.resolve(fileName), StandardCharsets.UTF_8)) {
      return lines.count();
    }
  }
}
