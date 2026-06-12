package com.codexdemo.orderplatform.ops.maintenance.readability;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ReadabilityUpkeepRoutePaths.BASE_PATH)
public class ReadabilityUpkeepAuditController {

    private final ReadabilityUpkeepAuditService service;

    public ReadabilityUpkeepAuditController(ReadabilityUpkeepAuditService service) {
        this.service = service;
    }

    @GetMapping(ReadabilityUpkeepRoutePaths.UPKEEP_AUDIT)
    public ReadabilityUpkeepAuditResponse audit() {
        return service.audit();
    }
}
