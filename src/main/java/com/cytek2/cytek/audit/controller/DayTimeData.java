package com.cytek2.cytek.audit.controller;

import com.cytek2.cytek.audit.model.EnergyData;
import com.cytek2.cytek.audit.repository.EnergyDataRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DayTimeData {
    private final EnergyDataRepository energyDataRepository;

    public DayTimeData(EnergyDataRepository energyDataRepository) {
        this.energyDataRepository = energyDataRepository;
    }

    // Endpoint to get daytime data
    @GetMapping("/day-time-data")
    public ResponseEntity<String> getDaytimeData() {


        try {
            List<EnergyData> daytimeData = energyDataRepository.findDaytimeData();

            if (daytimeData.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(daytimeData.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
