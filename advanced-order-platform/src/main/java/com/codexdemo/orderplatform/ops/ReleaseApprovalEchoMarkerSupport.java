package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class ReleaseApprovalEchoMarkerSupport {

    private ReleaseApprovalEchoMarkerSupport() {
    }

    static List<String> warningInputNames(String markerWarningsInputName) {
        return List.of(markerWarningsInputName);
    }

    static List<String> warningLines(String markerWarningsInputName, List<String> markerWarnings) {
        return List.of(
                ReleaseApprovalDigestSupport.line(markerWarningsInputName, markerWarnings)
        );
    }

    static WarningCondition warningIf(boolean condition, String warningCode) {
        return new WarningCondition(condition, warningCode);
    }

    static List<String> warnings(WarningCondition... conditions) {
        List<String> warnings = new ArrayList<>();
        for (WarningCondition condition : conditions) {
            if (condition.condition()) {
                warnings.add(condition.warningCode());
            }
        }
        return List.copyOf(warnings);
    }

    record WarningCondition(boolean condition, String warningCode) {
    }
}
