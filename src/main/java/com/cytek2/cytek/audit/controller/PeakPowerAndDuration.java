package com.cytek2.cytek.audit.controller;

import com.cytek2.cytek.audit.repository.EnergyDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PeakPowerAndDuration {

    private final EnergyDataRepository energyDataRepository;

    @GetMapping("/peak/power-and-duration")
    public ResponseEntity<?> findPeakPower(@RequestParam Long meterId, @RequestParam Integer userId) {
        try {
            List<Object[]> daytimeData = energyDataRepository.findPeakPowerAndDuration(userId, meterId);

            if (daytimeData.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                // Extract data directly in the response map
                Map<String, Object> response = new HashMap<>();
                response.put("peakCurrent", roundToTwoDecimalPlaces((Double) daytimeData.get(0)[0]));
                response.put("peakPower", roundToTwoDecimalPlaces((Double) daytimeData.get(0)[1]));

                // Include LocalDate directly in the response map
                response.put("date", daytimeData.get(0)[2]);
                response.put("exactdate", daytimeData.get(0)[3]);
                response.put("exacttime", daytimeData.get(0)[4]);

                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    private double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
