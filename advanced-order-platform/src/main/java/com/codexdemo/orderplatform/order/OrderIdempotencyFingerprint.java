package com.codexdemo.orderplatform.order;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Map;
import java.util.TreeMap;

final class OrderIdempotencyFingerprint {

    static final String VERSION = "order-create-request-sha256.v1";

    private OrderIdempotencyFingerprint() {
    }

    static String create(CreateOrderRequest request) {
        String canonicalRequest = canonicalRequest(request);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(canonicalRequest.getBytes(StandardCharsets.UTF_8));
            return "sha256:" + HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 digest is not available", exception);
        }
    }

    private static String canonicalRequest(CreateOrderRequest request) {
        Map<Long, Integer> quantities = new TreeMap<>();
        request.items().forEach(item -> quantities.merge(item.productId(), item.quantity(), Integer::sum));

        StringBuilder canonical = new StringBuilder()
                .append("version=").append(VERSION).append('\n')
                .append("customerId=").append(request.customerId()).append('\n')
                .append("items=");
        quantities.forEach((productId, quantity) -> canonical
                .append(productId)
                .append(':')
                .append(quantity)
                .append(';'));
        return canonical.toString();
    }
}
