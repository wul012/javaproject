# Java maintainability program

This directory records behavior-preserving maintenance work that is wider than
one `ops` extraction family. Runtime contracts remain owned by source code and
tests; these documents explain the mechanical budgets used to prevent the
largest files from silently growing again.

- `java-maintainability-program-v1834.md`: baseline, ratchets, failure rules,
  and the first four-version optimization sequence.
- `failed-event-query-split-v1835.md`: query orchestration, JPA specifications,
  page/sort validation, behavior evidence, and tightened budgets.
- `failed-event-command-split-v1836.md`: transactional facade, dead-letter
  recording, management, replay approval, replay execution, and tightened
  command-side dependency boundaries.
- `release-approval-verification-composition-v1837.md`: one verification
  context, one canonical receipt chain, isolated no-write proof evaluation,
  digest parity, and parameter-count regression gates.
