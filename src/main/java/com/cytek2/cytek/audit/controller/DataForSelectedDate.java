package com.cytek2.cytek.audit.controller;

import com.cytek2.cytek.audit.dto.EnergyDataResponseDTO;
import com.cytek2.cytek.audit.model.EnergyData;
import com.cytek2.cytek.audit.repository.EnergyDataRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class DataForSelectedDate {

    @Autowired
    private EnergyDataRepository energyDataRepository;

    @GetMapping("/selected")
    public ResponseEntity<?> getDataForSelectedDate(
            @RequestParam Long meterId,
            @RequestParam Integer userId,
            @RequestParam("selectedDate") String selectedDateString) {

        try {

            System.out.println("TRYING DATE SEARCH");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate selectedDate = LocalDate.parse(selectedDateString, formatter);

            List<EnergyData> energyDataList = energyDataRepository.findAllDataByMeterIdAndUserId(meterId, userId, selectedDate);


            if (!energyDataList.isEmpty()) {
                List<EnergyDataResponseDTO> responseDataList = energyDataList.stream()
                        .map(EnergyDataResponseDTO::new)
                        .collect(Collectors.toList());

                return ResponseEntity.ok(responseDataList);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No data found for the specified meter ID and date");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error retrieving data for the specified meter ID and date " + selectedDateString);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
