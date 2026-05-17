package com.codexdemo.orderplatform.ops;

import java.util.List;

record ContextHeaderField(String value, String source, boolean echoed) {

    private static final String NOT_SUPPLIED_SOURCE = "NOT_SUPPLIED";

    static ContextHeaderField from(String normalizedValue, String headerName, String placeholder) {
        if (normalizedValue == null) {
            return new ContextHeaderField(placeholder, NOT_SUPPLIED_SOURCE, false);
        }
        return new ContextHeaderField(normalizedValue, headerName, true);
    }

    void addMissingWarning(List<String> warnings, String warning) {
        if (!echoed) {
            warnings.add(warning);
        }
    }

    static boolean allEchoed(ContextHeaderField... fields) {
        for (ContextHeaderField field : fields) {
            if (!field.echoed()) {
                return false;
            }
        }
        return true;
    }
}
