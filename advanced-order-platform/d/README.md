# Java runtime archive d

This folder is the historical runtime screenshot/explanation archive for Java versions after v115 through v152.

Do not add new screenshot or explanation files directly to this root. Keep the existing version folders in place for traceability.

## Historical Shape

```text
d/<version>/解释/说明.md
d/<version>/图片/*.png
```

## Active Continuation

New screenshot/explanation work should continue in the segmented sibling root:

```text
d_runtime_screenshot_archive_next/v<start>-v<end>/<version>/explanations/summary.md
d_runtime_screenshot_archive_next/v<start>-v<end>/<version>/images/*.png
```

Use a version-range segment first, then a version folder. Do not put screenshots or explanation markdown directly under `d/` or directly under `d_runtime_screenshot_archive_next/`.

## Preserved Range

```text
v116-v152: historical runtime screenshots and explanations remain in this folder.
```

Keep `a/`, `b/`, and `c/` as historical archives. Do not move old archive records unless explicitly requested.
