package com.codexdemo.orderplatform.notification;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "failed-event.replay")
public class FailedEventReplayProperties {

    private List<String> allowedRoles = new ArrayList<>(List.of("ORDER_SUPPORT", "SRE", "SYSTEM"));

    private List<String> managementRoles = new ArrayList<>(List.of("ORDER_SUPPORT", "SRE", "SYSTEM"));

    private List<String> replayApprovalRequestRoles = new ArrayList<>(List.of("ORDER_SUPPORT", "SRE", "SYSTEM"));

    private List<String> replayApprovalReviewRoles = new ArrayList<>(List.of("SRE", "SYSTEM"));

    private List<String> replayRoles = new ArrayList<>(List.of("ORDER_SUPPORT", "SRE", "SYSTEM"));

    private String systemRole = "SYSTEM";

    public boolean isAllowedRole(String role) {
        return isRoleIn(role, allowedRoles);
    }

    public boolean isAllowedFor(FailedEventOperatorAction action, String role) {
        return isAllowedRole(role) && isRoleIn(role, rolesFor(action));
    }

    public List<String> rolesFor(FailedEventOperatorAction action) {
        return switch (action) {
            case MANAGE_FAILED_EVENT -> managementRoles;
            case REQUEST_REPLAY_APPROVAL -> replayApprovalRequestRoles;
            case REVIEW_REPLAY_APPROVAL -> replayApprovalReviewRoles;
            case REPLAY_FAILED_EVENT -> replayRoles;
        };
    }

    private boolean isRoleIn(String role, List<String> roles) {
        if (role == null || role.isBlank()) {
            return false;
        }
        String normalizedRole = normalize(role);
        return roles.stream()
                .map(this::normalize)
                .anyMatch(normalizedRole::equals);
    }

    public String normalize(String role) {
        return role == null ? null : role.strip().toUpperCase();
    }

    public List<String> getAllowedRoles() {
        return allowedRoles;
    }

    public void setAllowedRoles(List<String> allowedRoles) {
        this.allowedRoles = allowedRoles;
    }

    public List<String> getManagementRoles() {
        return managementRoles;
    }

    public void setManagementRoles(List<String> managementRoles) {
        this.managementRoles = managementRoles;
    }

    public List<String> getReplayApprovalRequestRoles() {
        return replayApprovalRequestRoles;
    }

    public void setReplayApprovalRequestRoles(List<String> replayApprovalRequestRoles) {
        this.replayApprovalRequestRoles = replayApprovalRequestRoles;
    }

    public List<String> getReplayApprovalReviewRoles() {
        return replayApprovalReviewRoles;
    }

    public void setReplayApprovalReviewRoles(List<String> replayApprovalReviewRoles) {
        this.replayApprovalReviewRoles = replayApprovalReviewRoles;
    }

    public List<String> getReplayRoles() {
        return replayRoles;
    }

    public void setReplayRoles(List<String> replayRoles) {
        this.replayRoles = replayRoles;
    }

    public String getSystemRole() {
        return systemRole;
    }

    public void setSystemRole(String systemRole) {
        this.systemRole = systemRole;
    }
}
