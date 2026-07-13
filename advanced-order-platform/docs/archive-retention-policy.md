# Archive Retention Policy

Java 的历史证据目录是跨项目只读依赖，不是可以随意整理的临时输出。
`a/`、`b/`、`c/`、`d/`、`d_runtime_screenshot_archive_next/`、`e/`、
`f/` 与所有 `代码讲解记录*` 目录中的现有文件，在 v1867 进入精确保留集合。

## Mechanical Contract

- `scripts/archive-retention-census.ps1` 是唯一 census 口径，默认只读。
- 显式传入 `-WriteManifest` 才会重建 `docs/archive-retention-manifest.txt`。
- manifest 每行记录仓库相对路径与 SHA-256，按 ordinal 路径排序。
- `.md`、`.json`、`.html` 在计算摘要前只将 CRLF 规范为 LF，使 Windows 与 Linux
  checkout 得到同一内容摘要；`.png` 等二进制文件仍按原始字节计算。
- `ArchiveRetentionTests` 要求实际文件集合与 manifest 完全相等，并重算每个摘要。
- 文件数和原始总字节数是只减不增的上限；缺失、正文篡改或未索引新增都会使构建失败。
  文本换行规范化不是内容豁免，除 CRLF/LF 外的任何字节变化都会改变摘要。

## Boundary

本策略不移动、不重命名、不压缩、不删除历史文件，也不修改 Node 已固定的绝对路径。
它不读取 credential value，不启动 Java、Node 或 mini-kv，不连接网络服务，不执行部署、
回滚或 SQL。若未来确有新的归档需求，必须先由新的外部计划明确调整保留策略；不得在普通
功能提交中静默放宽计数或摘要门。

## Authorized v1868 Extension

外部授权的 `docs/readme-exhibition-brief.md` 要求 v1868 按仓库规范产出讲解。
本次只新增
`代码讲解记录_生产雏形阶段8/v1868-v1872/v1868-readme-exhibition.md`，没有移动、
改写或删除任何 v1867 历史文件。重建后的精确基线是
`1,679 files / 19,834,662 raw bytes`；在下一份外部计划明确授权前，这两个上限继续
只减不增。
