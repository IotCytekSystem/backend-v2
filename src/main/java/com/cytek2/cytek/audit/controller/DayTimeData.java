package com.cytek2.cytek.audit.controller;

import com.cytek2.cytek.audit.repository.EnergyDataRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class DayTimeData {
    private final EnergyDataRepository energyDataRepository;

    public DayTimeData(EnergyDataRepository energyDataRepository) {
        this.energyDataRepository = energyDataRepository;
    }

    // DTO class to represent the response structure
    public static class DaytimeDataResponse {
        private Double red;
        private Double blue;
        private Double yellow;
        private Double peakCurrent;
        private Double peakPower;

        public DaytimeDataResponse(Double red, Double blue, Double yellow, Double peakCurrent, Double peakPower) {
            this.red = red;
            this.blue = blue;
            this.yellow = yellow;
            this.peakCurrent = peakCurrent;
            this.peakPower = peakPower;
        }

        public Double getRed() {
            return red;
        }

        public Double getBlue() {
            return blue;
        }

        public Double getYellow() {
            return yellow;
        }

        public Double getPeakCurrent() {
            return peakCurrent;
        }

        public Double getPeakPower() {
            return peakPower;
        }
    }

    // Endpoint to get daytime data
    @GetMapping("/day-time-data")
    public ResponseEntity<Map<String, Double>> getDaytimeData(
            @RequestParam Long meterId,
            @RequestParam Integer userId,
            @RequestParam("selectedDate") String selectedDateString
    ) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate selectedDate = LocalDate.parse(selectedDateString, formatter);
            List<Object[]> daytimeData = energyDataRepository.findDaytimeData(meterId, userId, selectedDate);

            if (daytimeData.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                // Convert the result to a list of DaytimeDataResponse
                List<DaytimeDataResponse> responseList = convertToDaytimeDataResponse(daytimeData);

                // Build the response map
                Map<String, Double> response = new HashMap<>();
                response.put("redPhase", roundToTwoDecimalPlaces(responseList.get(0).getRed()));
                response.put("yellowPhase", roundToTwoDecimalPlaces(responseList.get(0).getYellow()));
                response.put("bluePhase", roundToTwoDecimalPlaces(responseList.get(0).getBlue()));
                response.put("peakCurrent", roundToTwoDecimalPlaces(responseList.get(0).getPeakCurrent()));
                response.put("peakPower", roundToTwoDecimalPlaces(responseList.get(0).getPeakPower()));
                Double total = roundToTwoDecimalPlaces(responseList.get(0).getRed() + responseList.get(0).getYellow() + responseList.get(0).getBlue());
                response.put("total", total);

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
                        (Double) data[0],  // red
                        (Double) data[1],  // blue
                        (Double) data[2],  // yellow
                        (Double) data[3],  // peakCurrent
                        (Double) data[4]   // peakPower
                ))
                .collect(Collectors.toList());
    }


    private double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}

