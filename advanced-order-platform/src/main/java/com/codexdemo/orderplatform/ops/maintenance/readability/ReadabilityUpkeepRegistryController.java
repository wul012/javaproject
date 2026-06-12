package com.codexdemo.orderplatform.ops.maintenance.readability;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ReadabilityUpkeepRoutePaths.BASE_PATH)
public class ReadabilityUpkeepRegistryController {

    private final ReadabilityUpkeepRegistryService service;

    public ReadabilityUpkeepRegistryController(ReadabilityUpkeepRegistryService service) {
        this.service = service;
    }

    @GetMapping(ReadabilityUpkeepRoutePaths.UPKEEP_REGISTRY)
    public ReadabilityUpkeepRegistryResponse registry() {
        return service.registry();
    }
}
