package com.cytek2.cytek.audit.controller;

import com.cytek2.cytek.audit.model.EnergyData;
import com.cytek2.cytek.audit.repository.EnergyDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/realtime")
public class RealTimeDataController {

    @Autowired
    private  EnergyDataRepository energyDataRepository;




    @GetMapping("/data/{meterId}")
    public ResponseEntity<Map<String, Object>> getRealtimeData( @PathVariable Long meterId) {
        try {
            List<EnergyData> realtimeData = energyDataRepository.getRealtimeData(meterId);

            if (realtimeData.isEmpty()) {
                // Handle the case where there is no data to return.
                return ResponseEntity.noContent().build();
            } else {
                // Fetch the first result from the list
                EnergyData firstResult = realtimeData.get(0);


                // Create a response object
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("id", (double) firstResult.getId()); // "id" is a double
                dataMap.put("redVoltage", firstResult.getRedVoltage());
                dataMap.put("yellowVoltage", firstResult.getYellowVoltage());
                dataMap.put("blueVoltage", firstResult.getBlueVoltage());
                dataMap.put("redCurrent", firstResult.getRedCurrent());
                dataMap.put("yellowCurrent", firstResult.getYellowCurrent());
                dataMap.put("blueCurrent", firstResult.getBlueCurrent());
                dataMap.put("redPower", firstResult.getRedPower());
                dataMap.put("yellowPower", firstResult.getYellowPower());
                dataMap.put("bluePower", firstResult.getBluePower());
                dataMap.put("redPowerConsumption", firstResult.getRedPowerConsumption());
                dataMap.put("yellowPowerConsumption", firstResult.getYellowPowerConsumption());
                dataMap.put("bluePowerConsumption", firstResult.getBluePowerConsumption());
                dataMap.put("redExportedEnergy", firstResult.getRedExportedEnergy());
                dataMap.put("yellowExportedEnergy", firstResult.getYellowExportedEnergy());
                dataMap.put("blueExportedEnergy", firstResult.getBlueExportedEnergy());
                dataMap.put("redFrequency", firstResult.getRedFrequency());
                dataMap.put("yellowFrequency", firstResult.getYellowFrequency());
                dataMap.put("blueFrequency", firstResult.getBlueFrequency());
                dataMap.put("redPowerFactor", firstResult.getRedPowerFactor());
                dataMap.put("yellowPowerFactor", firstResult.getYellowPowerFactor());
                dataMap.put("bluePowerFactor", firstResult.getBluePowerFactor());
                dataMap.put("date",firstResult.getDate());
                dataMap.put("day", firstResult.getDay());
                dataMap.put("time", firstResult.getTime());


                return ResponseEntity.ok(dataMap);
            }
        } catch (IndexOutOfBoundsException e) {
            // Handle the IndexOutOfBoundsException here, e.g., return a 500 Internal Server Error response.
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("error", 0.0); // An arbitrary double value indicating an error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
        }
    }
}
