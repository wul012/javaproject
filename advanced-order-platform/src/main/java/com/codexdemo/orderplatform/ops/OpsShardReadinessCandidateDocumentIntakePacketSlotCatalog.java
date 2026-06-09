package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessCandidateDocumentIntakePacketSlotCatalog {

    private static final List<Integer> CHECKPOINT_GROUP_SIZES = List.of(3, 3, 3, 3, 3, 2, 2, 2, 2, 2);

    private OpsShardReadinessCandidateDocumentIntakePacketSlotCatalog() {
    }

    static List<OpsShardReadinessCandidateDocumentIntakePacketResponse.IntakeSlot> slots(
            OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse sourcePrecheck
    ) {
        List<OpsShardReadinessCandidateDocumentIntakePacketResponse.IntakeSlot> slots = new ArrayList<>();
        int cursor = 0;
        for (int index = 0; index < CHECKPOINT_GROUP_SIZES.size(); index++) {
            int groupSize = CHECKPOINT_GROUP_SIZES.get(index);
            var checkpointCodes = sourcePrecheck.checkpoints().subList(cursor, cursor + groupSize).stream()
                    .map(OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Checkpoint::code)
                    .toList();
            slots.add(slot(index + 1, groupSize, checkpointCodes));
            cursor += groupSize;
        }
        return List.copyOf(slots);
    }

    private static OpsShardReadinessCandidateDocumentIntakePacketResponse.IntakeSlot slot(
            int order,
            int coveredCheckpointCount,
            List<String> checkpointCodes
    ) {
        return new OpsShardReadinessCandidateDocumentIntakePacketResponse.IntakeSlot(
                order,
                "candidate-intake-slot-" + order,
                order <= 5 ? "request-material" : "boundary-material",
                String.join(",", checkpointCodes),
                coveredCheckpointCount,
                2,
                "reviewed-real-document-envelope-placeholder-" + order,
                "passed"
        );
    }
}
