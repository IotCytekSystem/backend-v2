package com.cytek2.cytek.audit.controller;

import com.cytek2.cytek.audit.repository.EnergyDataRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/history")
public class History {
    private final EnergyDataRepository energyDataRepository;

    public History(EnergyDataRepository energyDataRepository) {
        this.energyDataRepository = energyDataRepository;
    }

    @GetMapping("/id-and-red-power")
    public List<Object[]> getIdAndRedPowerForSelectedDate(@RequestParam Long meterId, @RequestParam String selectedDate) {
        // Assuming the date is provided in the format 'yyyy-MM-dd'
        LocalDateTime parsedSelectedDate = LocalDateTime.parse(selectedDate + "T00:00:00"); // Assuming the time is set to midnight

        return energyDataRepository.findIdAndRedPowerForSelectedDate(meterId, parsedSelectedDate);
    }
}
