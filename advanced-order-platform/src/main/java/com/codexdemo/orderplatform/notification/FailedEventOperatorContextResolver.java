package com.codexdemo.orderplatform.notification;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FailedEventOperatorContextResolver {

    public static final String OPERATOR_ID_HEADER = "X-Operator-Id";

    public static final String OPERATOR_ROLE_HEADER = "X-Operator-Role";

    private final FailedEventReplayProperties failedEventReplayProperties;

    public FailedEventOperatorContextResolver(FailedEventReplayProperties failedEventReplayProperties) {
        this.failedEventReplayProperties = failedEventReplayProperties;
    }

    public FailedEventOperatorContext resolve(HttpHeaders headers) {
        return resolve(headers.getFirst(OPERATOR_ID_HEADER), headers.getFirst(OPERATOR_ROLE_HEADER));
    }

    public FailedEventOperatorContext resolve(String operatorId, String operatorRole) {
        return new FailedEventOperatorContext(normalizeOperatorId(operatorId), requireAllowedOperatorRole(operatorRole));
    }

    public List<String> allowedRoles() {
        return failedEventReplayProperties.getAllowedRoles()
                .stream()
                .filter(StringUtils::hasText)
                .map(failedEventReplayProperties::normalize)
                .distinct()
                .toList();
    }

    private String normalizeOperatorId(String operatorId) {
        if (!StringUtils.hasText(operatorId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, OPERATOR_ID_HEADER + " header is required");
        }
        return truncate(operatorId.strip(), 80);
    }

    private String requireAllowedOperatorRole(String operatorRole) {
        if (!StringUtils.hasText(operatorRole)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, OPERATOR_ROLE_HEADER + " header is required");
        }
        String normalizedRole = failedEventReplayProperties.normalize(operatorRole);
        if (!failedEventReplayProperties.isAllowedRole(normalizedRole)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "operator role is not allowed to replay failed events");
        }
        return truncate(normalizedRole, 80);
    }

    private String truncate(String value, int maxLength) {
        if (value == null || value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength);
    }
}
