package com.cytek2.cytek.audit.controller.auth;

import com.cytek2.cytek.audit.model.EnergyData;
import com.cytek2.cytek.audit.services.service.EnergyDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class DataByUser {
    private final EnergyDataService energyDataService;

    public DataByUser(EnergyDataService energyDataService) {
        this.energyDataService = energyDataService;
    }

    @GetMapping("/data/user/{userId}")
    public ResponseEntity<List<EnergyData>> dataByUserId(@PathVariable Integer userId) {
        // Logic to fetch data for the specified userId from your database or source
        List<EnergyData> dataByUserId = energyDataService.dataByUserId(userId);
        return ResponseEntity.ok(dataByUserId);
    }
}

