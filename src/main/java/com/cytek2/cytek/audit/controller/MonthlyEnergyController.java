package com.cytek2.cytek.audit.controller;

import com.cytek2.cytek.audit.repository.EnergyDataRepository;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/monthlyEnergy")
public class MonthlyEnergyController {

    private final EnergyDataRepository energyDataRepository;

    public MonthlyEnergyController(EnergyDataRepository energyDataRepository) {
        this.energyDataRepository = energyDataRepository;
    }

    @GetMapping("/{meterId}")
    public ResponseEntity<?> getMonthlyEnergyConsumption(@PathVariable Long meterId) {
        try {
            // Corrected the return statement
            List<Object[]> resultObjects=energyDataRepository.findMonthlyPowerConsumption(meterId);


            if (!resultObjects.isEmpty()) {
                Object[] resultArray = resultObjects.get(0);

                // Create a response object
                Map<String, Double> response = new HashMap<>();
                response.put("redPhase", roundToTwoDecimalPlaces((Double) resultArray[0]));
                response.put("yellowPhase", roundToTwoDecimalPlaces((Double) resultArray[1]));
                response.put("bluePhase", roundToTwoDecimalPlaces((Double) resultArray[2]));
                Double total = roundToTwoDecimalPlaces(((Double) resultArray[0]) + ((Double) resultArray[1]) + ((Double) resultArray[2]));
                response.put("total", total);

                return ResponseEntity.ok(response);
            } else {
                return new ResponseEntity<>("No data found for the specified meter ID", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving monthly energy consumption data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}

