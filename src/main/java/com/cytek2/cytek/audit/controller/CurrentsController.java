package com.cytek2.cytek.audit.controller;

import com.cytek2.cytek.audit.model.EnergyData;
import com.cytek2.cytek.audit.services.service.EnergyDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/current")
public class CurrentsController {
    private final EnergyDataService energyDataService;

    public CurrentsController(EnergyDataService energyDataService) {
        this.energyDataService = energyDataService;
    }

    @GetMapping("/red")
    public ResponseEntity<List<EnergyData>> getRedCurrents() {
        // Logic to fetch red currents from your database or source
        List<EnergyData> redCurrents = energyDataService.getRedCurrents();
        return ResponseEntity.ok(redCurrents);
    }

    @GetMapping("/blue")
    public ResponseEntity<List<EnergyData>> getBlueCurrents() {
        // Logic to fetch blue currents from your database or source
        List<EnergyData> blueCurrents = energyDataService.getBlueCurrents();
        return ResponseEntity.ok(blueCurrents);
    }

    @GetMapping("/yellow")
    public ResponseEntity<List<EnergyData>> getYellowCurrents() {
        // Logic to fetch yellow currents from your database or source
        List<EnergyData> yellowCurrents = energyDataService.getYellowCurrents();
        return ResponseEntity.ok(yellowCurrents);
    }
    @GetMapping("/peak")
    public ResponseEntity<List<Double>> findHighestCurrent() {
        // Logic to fetch red power data from your database or source
        List<Double> redPower = energyDataService.findHighestCurrent();
        return ResponseEntity.ok(redPower);
    }
}
