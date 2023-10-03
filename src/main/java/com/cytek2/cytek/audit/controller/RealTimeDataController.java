package com.cytek2.cytek.audit.controller;

import com.cytek2.cytek.audit.model.EnergyData;
import com.cytek2.cytek.audit.repository.EnergyDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/realtime")
public class RealTimeDataController {

    private final EnergyDataRepository energyDataRepository;

    @Autowired
    public RealTimeDataController(EnergyDataRepository energyDataRepository) {
        this.energyDataRepository = energyDataRepository;
    }

    @GetMapping("/data")
    public ResponseEntity<String> getRealtimeData(@RequestParam(required = false) Integer maxResults) {
        try {
            List<EnergyData> realtimeData = energyDataRepository.getRealtimeData();

            if (realtimeData.isEmpty()) {
                // Handle the case where there is no data to return.
                return ResponseEntity.noContent().build();
            } else {
                // Fetch the first result from the list
                EnergyData firstResult = realtimeData.get(0);

                return ResponseEntity.ok(firstResult.toString());
            }
        } catch (IndexOutOfBoundsException e) {
            // Handle the IndexOutOfBoundsException here, e.g., return a 500 Internal Server Error response.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An internal server error occurred.");
        }
    }


}
