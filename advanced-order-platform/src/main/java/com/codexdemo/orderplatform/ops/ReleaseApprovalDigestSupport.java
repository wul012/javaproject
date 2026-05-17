package com.codexdemo.orderplatform.ops;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;

final class ReleaseApprovalDigestSupport {

    private ReleaseApprovalDigestSupport() {
    }

    static String digest(List<String> lines) {
        String canonical = String.join("\n", lines) + "\n";
        try {
            byte[] bytes = MessageDigest.getInstance("SHA-256")
                    .digest(canonical.getBytes(StandardCharsets.UTF_8));
            return "sha256:" + HexFormat.of().formatHex(bytes);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 digest algorithm is not available", ex);
        }
    }

    static String line(String key, Object value) {
        return key + "=" + value(value);
    }

    private static String value(Object value) {
        if (value == null) {
            return "<null>";
        }
        if (value instanceof List<?> list) {
            return "[" + String.join(",", list.stream()
                    .map(ReleaseApprovalDigestSupport::value)
                    .toList()) + "]";
        }
        return String.valueOf(value);
    }
}
