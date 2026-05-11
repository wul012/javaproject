# 第三十五版：失败事件动作级角色策略启动期一致性校验

## 本版目标

v30 到 v34 已经把失败事件写操作权限拆成了比较完整的一条线：

```text
动作级角色策略
 -> 操作员上下文
 -> 当前角色动作快照
 -> 页面按钮预检
 -> 页面写操作入口守卫
 -> 动作权限决策明细
```

v35 不继续扩页面，而是补一个更偏工程可靠性的点：

```text
应用启动时校验 failed-event.replay 角色策略
 -> action roles 不能越过 allowed-roles
 -> system-role 必须能执行重放
 -> 空角色列表直接 fail fast
```

这样配置错了会在启动期暴露，而不是运行后才出现页面、探针和后端权限判断互相矛盾。

## 改动文件

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayProperties.java
src/test/java/com/codexdemo/orderplatform/notification/FailedEventReplayPropertiesTests.java
README.md
a/35/解释/说明.md
```

## 一、在配置类里做启动期校验

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayProperties.java`

v35 给配置类增加 `@PostConstruct`：

```java
@PostConstruct
void validateRolePolicy() {
    Set<String> globalAllowedRoles = normalizedRoleSet("allowed-roles", allowedRoles);
    List<String> errors = new ArrayList<>();
    validateActionRoles("management-roles", managementRoles, globalAllowedRoles, errors);
    validateActionRoles("replay-approval-request-roles", replayApprovalRequestRoles, globalAllowedRoles, errors);
    validateActionRoles("replay-approval-review-roles", replayApprovalReviewRoles, globalAllowedRoles, errors);
    Set<String> normalizedReplayRoles = validateActionRoles("replay-roles", replayRoles, globalAllowedRoles, errors);
    String normalizedSystemRole = normalizeRequiredRole("system-role", systemRole, errors);
    ...
}
```

这个方法在 Spring 完成配置绑定后执行：

```text
application.yml / 环境变量绑定完成
 -> FailedEventReplayProperties bean 初始化
 -> validateRolePolicy()
 -> 策略不一致则启动失败
```

这里选 `@PostConstruct`，是因为校验依赖最终绑定后的字段值，而不是字段默认值。

## 二、全局允许角色必须存在

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayProperties.java`

角色列表归一化由 `normalizedRoleSet` 完成：

```java
private Set<String> normalizedRoleSet(String fieldName, List<String> roles) {
    Set<String> normalizedRoles = new LinkedHashSet<>();
    if (roles != null) {
        roles.stream()
                .map(this::normalize)
                .filter(role -> role != null && !role.isBlank())
                .forEach(normalizedRoles::add);
    }
    if (normalizedRoles.isEmpty()) {
        throw new IllegalStateException(
                "invalid failed-event.replay role policy: " + fieldName + " must contain at least one role"
        );
    }
    return normalizedRoles;
}
```

它做了三件事：

```text
trim + upper case
 -> sre 变成 SRE

去掉空白角色
 -> " " 不会被当作合法角色

用 LinkedHashSet 去重并保留顺序
 -> 错误信息稳定
```

如果 `allowed-roles` 是空列表或只有空白字符串，应用直接启动失败。

## 三、动作角色不能越过 allowed-roles

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayProperties.java`

每个动作角色列表都会经过 `validateActionRoles`：

```java
private Set<String> validateActionRoles(
        String fieldName,
        List<String> roles,
        Set<String> globalAllowedRoles,
        List<String> errors
) {
    Set<String> normalizedRoles = normalizedRoleSet(fieldName, roles);
    Set<String> unexpectedRoles = new LinkedHashSet<>(normalizedRoles);
    unexpectedRoles.removeAll(globalAllowedRoles);
    if (!unexpectedRoles.isEmpty()) {
        errors.add(fieldName + " contains roles outside allowed-roles: " + String.join(",", unexpectedRoles));
    }
    return normalizedRoles;
}
```

要防的是这种配置：

```yaml
failed-event:
  replay:
    allowed-roles:
      - SRE
      - SYSTEM
    replay-approval-request-roles:
      - ORDER_SUPPORT
      - SRE
```

这会造成一种隐性矛盾：

```text
replay-approval-request-roles 说 ORDER_SUPPORT 可以申请
allowed-roles 又不允许 ORDER_SUPPORT 进入操作员上下文
```

v35 会在启动期报错：

```text
invalid failed-event.replay role policy:
replay-approval-request-roles contains roles outside allowed-roles: ORDER_SUPPORT
```

## 四、system-role 必须能执行重放

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayProperties.java`

项目里有两个系统重放入口：

```java
operatorContextResolver.resolve(
        "system",
        failedEventReplayProperties.getSystemRole(),
        FailedEventOperatorAction.REPLAY_FAILED_EVENT
)
```

所以 `system-role` 不仅要是全局合法角色，还必须包含在 `replay-roles` 里：

```java
if (normalizedSystemRole != null && !globalAllowedRoles.contains(normalizedSystemRole)) {
    errors.add("system-role " + normalizedSystemRole + " must be included in allowed-roles");
}
if (normalizedSystemRole != null && !normalizedReplayRoles.contains(normalizedSystemRole)) {
    errors.add("system-role " + normalizedSystemRole + " must be included in replay-roles");
}
```

这能避免系统内部重放入口在运行后才因为配置问题报 403。

## 五、错误统一成清晰前缀

文件：`src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayProperties.java`

如果发现多个不一致，v35 会把它们合并成一个异常：

```java
if (!errors.isEmpty()) {
    throw new IllegalStateException("invalid failed-event.replay role policy: " + String.join("; ", errors));
}
```

统一前缀是：

```text
invalid failed-event.replay role policy
```

这样排查启动失败时可以直接定位到失败事件重放角色策略，而不是在长日志里猜是哪组配置坏了。

## 六、单元测试覆盖关键策略

文件：`src/test/java/com/codexdemo/orderplatform/notification/FailedEventReplayPropertiesTests.java`

默认策略必须通过：

```java
properties.validateRolePolicy();

assertThat(properties.isAllowedFor(FailedEventOperatorAction.REVIEW_REPLAY_APPROVAL, "sre")).isTrue();
assertThat(properties.isAllowedFor(FailedEventOperatorAction.REVIEW_REPLAY_APPROVAL, "order_support")).isFalse();
assertThat(properties.isAllowedFor(FailedEventOperatorAction.REPLAY_FAILED_EVENT, "system")).isTrue();
```

动作角色越过全局允许角色时失败：

```java
properties.setAllowedRoles(List.of("SRE", "SYSTEM"));
properties.setReplayApprovalRequestRoles(List.of("ORDER_SUPPORT", "SRE"));

assertThatThrownBy(properties::validateRolePolicy)
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("replay-approval-request-roles")
        .hasMessageContaining("ORDER_SUPPORT");
```

系统角色不能重放时失败：

```java
properties.setSystemRole("automation");
properties.setAllowedRoles(List.of("ORDER_SUPPORT", "SRE", "SYSTEM", "AUTOMATION"));
properties.setReplayRoles(List.of("ORDER_SUPPORT", "SRE", "SYSTEM"));

assertThatThrownBy(properties::validateRolePolicy)
        .hasMessageContaining("system-role AUTOMATION must be included in replay-roles");
```

全局允许角色为空时失败：

```java
properties.setAllowedRoles(List.of(" ", ""));

assertThatThrownBy(properties::validateRolePolicy)
        .hasMessageContaining("allowed-roles must contain at least one role");
```

## 七、验证范围

本版相关测试跑了两层：

```text
FailedEventReplayPropertiesTests
 -> 直接覆盖配置类校验规则

FailedEventOperatorContextIntegrationTests
 -> 确认默认 Spring Boot 启动、operator-context 和写操作权限链路不受影响
```

完整 `mvn test` 也通过：

```text
Tests run: 37, Failures: 0, Errors: 0, Skipped: 0
```

这说明启动期校验没有破坏默认配置，也不会影响现有失败事件管理链路。

## 一句话总结

v35 给失败事件动作级角色策略补上启动期一致性校验，把“配置写错后运行时才发现”的隐患提前到应用启动阶段暴露，让权限配置、系统重放和页面权限解释保持同一套可信边界。
