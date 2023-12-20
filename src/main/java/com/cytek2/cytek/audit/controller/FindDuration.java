package com.cytek2.cytek.audit.controller;

import com.cytek2.cytek.audit.repository.EnergyDataRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Period;

@RestController
@RequestMapping("/api")
public class FindDuration {

    @Autowired
    private EnergyDataRepository energyDataRepository;

    @GetMapping("/duration")
    public DurationResponse findDurationForUserAndMeter(Integer userId, Long meterId) {
        try {

            LocalDate firstDataDate = energyDataRepository.findFirstDataDateForUserAndMeter(userId, meterId);

            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(firstDataDate, currentDate);


            DurationResponse response = new DurationResponse();
            response.setDays(period.getDays());
            response.setMonths(period.getMonths());
            response.setYears(period.getYears());

            return response;
        } catch (Exception e) {
            // Handle exception or log the error
            return null; // Or throw a custom exception, return an error response, etc.
        }
    }

    @Data
    public class DurationResponse {

        private int days;
        private int months;
        private int years;

    }
}
