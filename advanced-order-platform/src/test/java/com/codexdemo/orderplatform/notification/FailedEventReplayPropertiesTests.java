package com.codexdemo.orderplatform.notification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

class FailedEventReplayPropertiesTests {

    @Test
    void validatesDefaultRolePolicy() {
        FailedEventReplayProperties properties = new FailedEventReplayProperties();

        properties.validateRolePolicy();

        assertThat(properties.isAllowedFor(FailedEventOperatorAction.REVIEW_REPLAY_APPROVAL, "sre")).isTrue();
        assertThat(properties.isAllowedFor(FailedEventOperatorAction.REVIEW_REPLAY_APPROVAL, "order_support")).isFalse();
        assertThat(properties.isAllowedFor(FailedEventOperatorAction.REPLAY_FAILED_EVENT, "system")).isTrue();
    }

    @Test
    void rejectsActionRoleOutsideGlobalAllowedRoles() {
        FailedEventReplayProperties properties = new FailedEventReplayProperties();
        properties.setAllowedRoles(List.of("SRE", "SYSTEM"));
        properties.setReplayApprovalRequestRoles(List.of("ORDER_SUPPORT", "SRE"));

        assertThatThrownBy(properties::validateRolePolicy)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("invalid failed-event.replay role policy")
                .hasMessageContaining("replay-approval-request-roles")
                .hasMessageContaining("ORDER_SUPPORT");
    }

    @Test
    void rejectsSystemRoleThatCannotReplay() {
        FailedEventReplayProperties properties = new FailedEventReplayProperties();
        properties.setSystemRole("automation");
        properties.setAllowedRoles(List.of("ORDER_SUPPORT", "SRE", "SYSTEM", "AUTOMATION"));
        properties.setReplayRoles(List.of("ORDER_SUPPORT", "SRE", "SYSTEM"));

        assertThatThrownBy(properties::validateRolePolicy)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("system-role AUTOMATION must be included in replay-roles");
    }

    @Test
    void rejectsEmptyGlobalAllowedRoles() {
        FailedEventReplayProperties properties = new FailedEventReplayProperties();
        properties.setAllowedRoles(List.of(" ", ""));

        assertThatThrownBy(properties::validateRolePolicy)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("allowed-roles must contain at least one role");
    }
}
