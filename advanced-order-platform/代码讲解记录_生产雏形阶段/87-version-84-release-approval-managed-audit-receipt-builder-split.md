# version 84 release approval managed audit receipt builder split

## 1. 版本目标

v84 是纯重构版本：继续拆 `OpsEvidenceService`，把 release approval rehearsal 中已经稳定的 managed-audit receipt 构造和 warning digest 计算迁到专用 builder。

本次不新增响应字段，不升级 schema，不改变 warning digest 语义。

## 2. 新增类

```java
ReleaseApprovalDigestSupport
ReleaseApprovalVerificationWarningDigestBuilder
ReleaseApprovalManagedAuditAdapterBoundaryReceiptBuilder
ReleaseApprovalManagedAuditProductionAdapterPrerequisiteReceiptBuilder
ReleaseApprovalOpsEvidenceServiceQualitySplitReceiptBuilder
ReleaseApprovalManagedAuditAdapterImplementationGuardReceiptBuilder
```

职责划分：

- `ReleaseApprovalDigestSupport` 统一 SHA-256 digest 和 canonical `key=value` line 生成。
- `ReleaseApprovalVerificationWarningDigestBuilder` 负责 verification hint 的 `warningDigest`。
- `ReleaseApprovalManagedAuditAdapterBoundaryReceiptBuilder` 负责 v77 adapter boundary receipt。
- `ReleaseApprovalManagedAuditProductionAdapterPrerequisiteReceiptBuilder` 负责 v78 production adapter prerequisite receipt。
- `ReleaseApprovalOpsEvidenceServiceQualitySplitReceiptBuilder` 负责 v79 quality split receipt。
- `ReleaseApprovalManagedAuditAdapterImplementationGuardReceiptBuilder` 负责 v80 implementation guard receipt。

## 3. 主服务变化

`OpsEvidenceService` 现在只保留 receipt chain 的编排：

```java
managedAuditAdapterBoundaryReceipt =
    new ReleaseApprovalManagedAuditAdapterBoundaryReceiptBuilder()
        .build(approvalHandoffVerificationMarker);
```

类似地，v78-v80 receipt 都改为 builder 委派。主服务不再保存这些 receipt 的长布尔条件、warning list 和 constructor 参数链。

## 4. 契约保护

测试暴露并修正了迁移时最容易出错的点：schema/profile 字符串必须保持原值，不能因为 builder 拆分改成语义化新名字。

重点保持：

- v77 source schema: `java-release-approval-rehearsal-response-schema.v10`
- v78 source schema: `java-release-approval-rehearsal-response-schema.v11`
- v79 source schema: `java-release-approval-rehearsal-response-schema.v12`
- v80 source schema: `java-release-approval-rehearsal-response-schema.v13`
- v79 next profile: `managed-audit-adapter-implementation-precheck-packet.v1`

## 5. 代码规模

拆分后：

```text
OpsEvidenceService.java: 2605 lines
ReleaseApprovalVerificationHintBuilder.java: 445 lines
```

相比 v83：

```text
OpsEvidenceService.java: 3110 -> 2605 (-505)
ReleaseApprovalVerificationHintBuilder.java: 693 -> 445 (-248)
```

## 6. 边界

v84 仍然保持：

- 不创建 approval decision
- 不写 approval ledger
- 不持久化 production approval record
- 不连接真实 managed audit
- 不读取 credential value
- 不执行 SQL / deployment / rollback / restore
- 不把 Java evidence 当作生产授权
