# advanced-order-platform 运行调试归档 c

本目录从后续版本开始，作为新的运行调试归档入口使用，和旧目录 `a/`、`b/` 同级：

```text
D:\javaproj\advanced-order-platform\a
D:\javaproj\advanced-order-platform\b
D:\javaproj\advanced-order-platform\c
```

旧目录 `a/` 和 `b/` 保留历史版本记录，不主动搬迁。

## 写入规则

后续每个 Java 版本的运行解释和截图写入：

```text
c/<版本>/解释/说明.md
c/<版本>/图片/*.png
```

例如：

```text
c/54/解释/说明.md
c/54/图片/01-plan-scope.png
c/54/图片/02-focused-test.png
c/54/图片/03-non-docker-test.png
c/54/图片/04-package-and-http-smoke.png
c/54/图片/05-cleanup.png
```

说明文档模式保持旧 `a/<版本>/解释/说明.md` 和 `b/<版本>/解释/说明.md` 的写法：

```text
计划依据
合理性判断
本版目标
代码改动
验证记录
边界
清理记录
一句话总结
```

图片记录也保持旧模式，覆盖计划范围、聚焦测试、回归测试、打包、HTTP smoke 和清理。

## 一句话总览

`a/` 和 `b/` 作为历史归档保留；后续运行截图和解释统一写入 `c/<版本>/`。

## 最新说明

```text
c/93/解释/说明.md
 -> Java v93 operator window checklist echo receipt，只读回显 Node v238 checklist 字段并继续阻断连接、credential value、SQL、ledger 和 auto-start

c/94/解释/说明.md
 -> Java v94 OpsEvidenceService dispatch table 重构，把 release/static evidence 构建移出主类，外部契约不变

c/95/解释/说明.md
 -> Java v95 静态 release 字符串常量收敛为 enum，继续压缩 OpsEvidenceService 的常量噪音

c/96/解释/说明.md
 -> Java v96 release approval rehearsal request record 重构，消除多层 null overload，外部 HTTP 契约不变

c/97/解释/说明.md
 -> Java v97 release approval rehearsal builder chain 重构，拆出 managed-audit receipt chain 和 normalized request/sections 上下文

c/98/解释/说明.md
 -> Java v98 manual sandbox dry-run command package echo receipt，只读回显 Node v241 六条 disabled command，并继续阻断 credential value、连接、SQL、ledger、auto-start 和 mini-kv 写权限

c/99/解释/说明.md
 -> Java v99 manual sandbox connection precheck packet echo receipt，只读回显 Node v245 七个 precheck item，并继续阻断 credential value、连接、SQL、ledger、auto-start 和 mini-kv 写权限

c/100/解释/说明.md
 -> Java v100 CI bootstrap + large-file guard，新增 GitHub Actions Maven CI 基线，并记录 ReleaseApprovalRehearsalResponse / OpsEvidenceService 后续拆分目标

c/101/解释/说明.md
 -> Java v101 Dependabot/security maintenance，覆盖 Maven + GitHub Actions，不升级依赖、不改业务契约、不打开 managed-audit 边界

c/102/解释/说明.md
 -> Java v102 disabled adapter client precheck echo receipt，只读回显 Node v252 env handles / opt-in gate / failure taxonomy / dry-run response shape，并继续阻断 credential value、真实 client、外部请求、连接、SQL、ledger、auto-start 和 mini-kv 写权限

c/103/解释/说明.md
 -> Java v103 fake transport dry-run packet echo marker，只读回显 Node v255/v256 fake transport packet 的 request / response / timeout / cleanup / side-effect 边界，并继续阻断 credential value、raw endpoint、真实连接、SQL、ledger、临时文件、auto-start 和 mini-kv 写权限
```

## v104 update

```text
c/104/解释/说明.md
 -> Java v104 sandbox endpoint handle preflight echo marker，只读回显 Node v258 endpoint/credential handle review、network/TLS/redaction/operator-window 边界，并继续阻断 credential value、raw endpoint、真实请求、SQL、ledger、auto-start 和 mini-kv 托管存储
```
