package com.cytek2.cytek.audit.controller;

import com.cytek2.cytek.audit.repository.EnergyDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PeakPowerHistoryData {

    @Autowired
    private EnergyDataRepository energyDataRepository;

    // DTO class to represent the response structure
    public static class DaytimeDataResponse {
        private LocalDate date;
        private Double peakCurrent;
        private Double peakPower;

        public DaytimeDataResponse(LocalDate date, Double peakCurrent, Double peakPower) {
            this.date = date;
            this.peakCurrent = peakCurrent;
            this.peakPower = peakPower;
        }

        public LocalDate getDate() {
            return date;
        }

        public Double getPeakCurrent() {
            return peakCurrent;
        }

        public Double getPeakPower() {
            return peakPower;
        }
    }

    @GetMapping("/power/peak/history")
    public ResponseEntity<?> findPeakPower(
            @RequestParam Long meterId,
            @RequestParam Integer userId
    ) {
        try {
            List<Object[]> daytimeData = energyDataRepository.findPeakPowerHistory(userId, meterId);

            if (daytimeData.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                // Convert the result to a list of DaytimeDataResponse
                List<DaytimeDataResponse> responseList = convertToDaytimeDataResponse(daytimeData);

                // Build the response map
                Map<String, Object> response = new HashMap<>();
                response.put("data", responseList);  // List of DaytimeDataResponse

                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private List<DaytimeDataResponse> convertToDaytimeDataResponse(List<Object[]> daytimeData) {
        return daytimeData.stream()
                .map(data -> new DaytimeDataResponse(
                        (LocalDate) data[0],  // date
                        (Double) data[1],     // peakPower
                        (Double) data[2]      // peakCurrent
                ))
                .collect(Collectors.toList());
    }

    private double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
