> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 第一百版代码讲解：CI bootstrap + large-file guard

本版目标是补 Java 仓库自己的 GitHub Actions Maven CI，并把下一轮大文件拆分目标写清楚。v100 不推进 managed-audit 连接能力，也不新增 release approval 业务字段。

## 本版所处项目进度

最新计划来自：

```text
D:\nodeproj\orderops-node\docs\plans\v245-post-sandbox-precheck-roadmap.md
```

计划要求 Java v100 做：

```text
CI bootstrap + large-file guard
补 .github/workflows Maven compile/test 基线
把 ReleaseApprovalRehearsalResponse.java / OpsEvidenceService.java 的后续拆分目标写入 Java 自己的版本归档
不改业务语义
不写 ledger
不执行 SQL
```

这说明 v100 是质量基线版，不是功能推进版。

## 新增 GitHub Actions

新增文件：

```text
D:\javaproj\.github\workflows\maven-ci.yml
```

仓库根目录是 `D:\javaproj`，workflow 通过 `working-directory: advanced-order-platform` 进入 Maven 项目。

workflow 名称：

```yaml
name: Java Maven CI
```

触发条件：

```yaml
on:
  push:
    branches:
      - master
  pull_request:
```

权限保持最小：

```yaml
permissions:
  contents: read
```

## CI 执行内容

CI 使用：

```text
ubuntu-latest
Java 21
Temurin distribution
Maven cache
```

步骤是：

```text
mvn -B -q -DskipTests compile
mvn -B -q ... test
mvn -B -q -DskipTests package
```

测试命令排除首版 CI 不想依赖 Docker 的 Testcontainers 场景：

```text
PostgresMigrationIntegrationTests
RabbitMqNotificationConsumerIntegrationTests
RabbitMqNotificationFailureIntegrationTests
RabbitMqOutboxPublisherIntegrationTests
```

这不是删除 Docker 测试，只是把 v100 的 CI bootstrap 控制在非 Docker 回归基线。

## 为什么不跑全量 Docker

项目里已有 PostgreSQL / RabbitMQ Testcontainers 测试：

```text
PostgresMigrationIntegrationTests
RabbitMqNotificationConsumerIntegrationTests
RabbitMqNotificationFailureIntegrationTests
RabbitMqOutboxPublisherIntegrationTests
```

这些测试对真实中间件很有价值，但首版 GitHub Actions 如果直接把 Docker 变成硬依赖，失败面会变宽，也更难区分“Java 代码坏了”和“CI 环境还没校准”。v100 先让普通 Maven compile/test/package 在 GitHub 上稳定起来。

## large-file guard

当前只读观察：

```text
OpsEvidenceService.java: 656
ReleaseApprovalRehearsalResponse.java: 1343
ReleaseApprovalVerificationHintBuilder.java: 605
OpsEvidenceServiceTests.java: 3712
OpsOverviewIntegrationTests.java: 3978
```

后续拆分优先级：

```text
1. ReleaseApprovalRehearsalResponse.java
   先拆 managed-audit sandbox receipt records，避免 response model 继续堆到 1500+ 行。

2. OpsEvidenceService.java
   保持入口和常量角色，不再承载 receipt 构建逻辑；后续可以把 Node profile/version 常量按 stage 分组。

3. OpsEvidenceServiceTests.java
   把 release approval rehearsal 的主断言拆成 receipt-specific helpers 或测试类。

4. OpsOverviewIntegrationTests.java
   按 endpoint 或 contract family 拆集成测试，避免 HTTP JSON path 断言持续集中。
```

## 本版不变项

本版没有改变：

```text
/api/v1/ops/release-approval-rehearsal response shape
warning digest 输入顺序
managed-audit receipt digest
read-only/no-ledger/no-SQL/no-connection 边界
HTTP header 名称和 normalize 行为
```

也没有新增：

```text
真实 managed audit connection
credential value 读取
approval ledger 写入
schema migration SQL
Java / mini-kv / external audit service auto-start
```

## 验证

执行：

```text
mvn -q -DskipTests compile
mvn -q "-Dtest=OpsEvidenceServiceTests,OpsOverviewIntegrationTests" test
mvn -q "-Dtest=!PostgresMigrationIntegrationTests,!RabbitMqNotificationConsumerIntegrationTests,!RabbitMqNotificationFailureIntegrationTests,!RabbitMqOutboxPublisherIntegrationTests" "-DargLine=-XX:TieredStopAtLevel=1 -Xmx512m" test
mvn -q -DskipTests package
git diff --check
```

## 一句话总结

v100 给 Java 仓库建立 GitHub Actions Maven CI 基线，并把下一步大文件拆分 guard 固化下来；业务契约和 managed-audit 安全边界不变。
