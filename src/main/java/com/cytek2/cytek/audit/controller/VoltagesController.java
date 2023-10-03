package com.cytek2.cytek.audit.controller;

import com.cytek2.cytek.audit.model.EnergyData;
import com.cytek2.cytek.audit.services.service.EnergyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/voltages")
public class VoltagesController {
    @Autowired
    private EnergyDataService energyDataService;

    @GetMapping("/red")
    public ResponseEntity<List<EnergyData>> getRedVoltages() {

         List<EnergyData> redVoltages = energyDataService.getRedVoltages();
        return ResponseEntity.ok(redVoltages);
    }

    @GetMapping("/blue")
    public ResponseEntity<List<EnergyData>> getBlueVoltages() {
        // Logic to fetch blue voltages from your database or source
     List<EnergyData> blueVoltages = energyDataService.getBlueVoltages();
        return ResponseEntity.ok(blueVoltages);
    }

    @GetMapping("/yellow")
    public ResponseEntity<List<EnergyData>> getYellowVoltages() {
        // Logic to fetch yellow voltages from your database or source
  List<EnergyData> yellowVoltages = energyDataService.getYellowVoltages();
        return ResponseEntity.ok(yellowVoltages);
    }
}
