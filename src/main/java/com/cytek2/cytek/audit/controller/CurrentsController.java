package com.cytek2.cytek.audit.controller;

import com.cytek2.cytek.audit.model.EnergyData;
import com.cytek2.cytek.audit.repository.EnergyDataRepository;
import com.cytek2.cytek.audit.services.service.EnergyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/current")
public class CurrentsController {
    private final EnergyDataService energyDataService;
    @Autowired
    private EnergyDataRepository energyDataRepository;

    public CurrentsController(EnergyDataService energyDataService) {
        this.energyDataService = energyDataService;
    }

    @GetMapping("/red")
    public ResponseEntity<List<EnergyData>> getRedCurrents() {
        List<EnergyData> redCurrents = energyDataService.getRedCurrents();
        return ResponseEntity.ok(redCurrents);
    }

    @GetMapping("/blue")
    public ResponseEntity<List<EnergyData>> getBlueCurrents() {
        // Logic to fetch blue currents from your database or source
        List<EnergyData> blueCurrents = energyDataService.getBlueCurrents();
        return ResponseEntity.ok(blueCurrents);
    }

    @GetMapping("/yellow")
    public ResponseEntity<List<EnergyData>> getYellowCurrents() {
        // Logic to fetch yellow currents from your database or source
        List<EnergyData> yellowCurrents = energyDataService.getYellowCurrents();
        return ResponseEntity.ok(yellowCurrents);
    }
//    @GetMapping("/peak")
//    public ResponseEntity<List<Double>> findHighestCurrent() {
//        // Logic to fetch red power data from your database or source
//        List<Double> redPower = energyDataService.findHighestCurrent();
//        return ResponseEntity.ok(redPower);
//    }

    @GetMapping("/peak")
    public ResponseEntity<?> findPeakCurrent(@RequestParam Integer userId, @RequestParam Long meterId) {
        try {
            // Fetch the peak current data
            List<Double> peakCurrentList = energyDataRepository.findPeakCurrent(userId, meterId);

            if (!peakCurrentList.isEmpty()) {

                Double peakCurrent = peakCurrentList.get(0);
                return ResponseEntity.ok(peakCurrent);
            } else {
                return new ResponseEntity<>("No data found for the specified meter ID", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
