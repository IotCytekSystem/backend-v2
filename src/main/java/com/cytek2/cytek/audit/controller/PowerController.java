package com.cytek2.cytek.audit.controller;

import com.cytek2.cytek.audit.model.EnergyData;
import com.cytek2.cytek.audit.services.service.EnergyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/power")
public class PowerController {
    @Autowired
    private EnergyDataService energyDataService;


    @GetMapping("/red")
    public ResponseEntity<List<Object[]>> getRedPower() {
        // Logic to fetch red power data from your database or source
       List<Object[]> redPower = energyDataService.getRedPower();
        return ResponseEntity.ok(redPower);
    }

    @GetMapping("/peak")
    public ResponseEntity<List<Double>> findHighestPower() {
        // Fetch the highest power data as a Map
        Map<String, Double> highestPowerMap = energyDataService.findHighestPower();

        // Extract the values from the map
        List<Double> result = new ArrayList<>();
        result.add(highestPowerMap.get("red"));
        result.add(highestPowerMap.get("yellow"));
        result.add(highestPowerMap.get("blue"));

        return ResponseEntity.ok(result);
    }



    @GetMapping("/blue")
    public ResponseEntity<List<EnergyData>> getBluePower() {
        // Logic to fetch blue power data from your database or source
     List<EnergyData> bluePower = energyDataService.getBluePower();
        return ResponseEntity.ok(bluePower);
    }

    @GetMapping("/yellow")
    public ResponseEntity<List<EnergyData>> getYellowPower() {
        // Logic to fetch yellow power data from your database or source
     List<EnergyData> yellowPower = energyDataService.getYellowPower();
        return ResponseEntity.ok(yellowPower);
    }
}
