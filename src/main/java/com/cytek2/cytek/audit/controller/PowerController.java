package com.cytek2.cytek.audit.controller;

import com.cytek2.cytek.audit.model.EnergyData;
import com.cytek2.cytek.audit.repository.EnergyDataRepository;
import com.cytek2.cytek.audit.services.service.EnergyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/power")
public class PowerController {
    @Autowired
    private EnergyDataService energyDataService;

    @Autowired
    private EnergyDataRepository energyDataRepository;

    @GetMapping("/red")
    public ResponseEntity<List<Object[]>> getRedPower() {
        // Logic to fetch red power data from your database or source
       List<Object[]> redPower = energyDataService.getRedPower();
        return ResponseEntity.ok(redPower);
    }

    @GetMapping("/peaks/today")
    public ResponseEntity<Map<LocalDateTime, Double>> findPeakPowerForLast24Hours() {
        Map<LocalDateTime, Double> peakPowers = energyDataService.findPeakPowerForLast24Hours();
        return ResponseEntity.ok(peakPowers);
    }


    // EnergyDataController.java
    @GetMapping("/peaks")
    public ResponseEntity<Map<String, Double>> findHighestPower() {
        // Fetch the highest power data as a Map
        Map<String, Double> highestPowerMap = energyDataService.findHighestPower();

        return ResponseEntity.ok(highestPowerMap);
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

    @GetMapping("/peak")
    public ResponseEntity<?> findPeakPower(@RequestParam Integer userId, @RequestParam Long meterId) {
        try {
            // Fetch the peak power data
            List<Double> peakPowerList = energyDataRepository.findPeakPower(userId, meterId);

            if (!peakPowerList.isEmpty()) {
                // Extract the single element from the list
                Double peakPower = peakPowerList.get(0);
                return ResponseEntity.ok(peakPower);
            } else {
                return new ResponseEntity<>("No data found for the specified user and meter ID", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            Map<String,String> response=new HashMap<>();
            response.put("message","Error occured while trying to fetch peak power");
            return  ResponseEntity.ok(response);
        }
    }


}
