package com.cytek2.cytek.audit.controller;

import com.cytek2.cytek.audit.model.EnergyData;
import com.cytek2.cytek.audit.repository.EnergyDataRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GetNighttimeData {
    private final EnergyDataRepository energyDataRepository;

    public GetNighttimeData(EnergyDataRepository energyDataRepository) {
        this.energyDataRepository = energyDataRepository;
    }

    // DTO class to represent the response structure
    public static class NighttimeDataResponse {
        private Double red;
        private Double blue;
        private Double yellow;

        public NighttimeDataResponse(Double red, Double blue, Double yellow) {
            this.red = red;
            this.blue = blue;
            this.yellow = yellow;
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
    }

    // Endpoint to get nighttime data
    @GetMapping("/night-time-data")
    public ResponseEntity<List<NighttimeDataResponse>> getNighttimeData(
            @RequestParam Long meterId,
            @RequestParam Integer userId,
            @RequestParam("selectedDate") String selectedDateString
    ) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate selectedDate = LocalDate.parse(selectedDateString, formatter);
            List<Object[]> nighttimeData = energyDataRepository.findNighttimeData(meterId, userId, selectedDate);

            if (nighttimeData.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                // Convert the result to a list of NighttimeDataResponse
                List<NighttimeDataResponse> responseList = convertToNighttimeDataResponse(nighttimeData);
                return ResponseEntity.ok(responseList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Helper method to convert the result to a list of NighttimeDataResponse
    private List<NighttimeDataResponse> convertToNighttimeDataResponse(List<Object[]> nighttimeData) {
        return nighttimeData.stream()
                .map(data -> new NighttimeDataResponse((Double) data[0], (Double) data[1], (Double) data[2]))
                .toList();
    }
}
