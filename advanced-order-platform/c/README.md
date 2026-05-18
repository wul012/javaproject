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
```
