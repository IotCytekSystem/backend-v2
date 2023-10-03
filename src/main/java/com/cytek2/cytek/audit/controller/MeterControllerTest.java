package com.cytek2.cytek.audit.controller;

import com.cytek2.cytek.audit.model.Meter;
import com.cytek2.cytek.audit.repository.MeterRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/meters")
public class MeterControllerTest {

    private final MeterRepository meterRepository;



    public MeterControllerTest(MeterRepository meterRepository) {
        this.meterRepository = meterRepository;
    }

    @GetMapping("/findMeterBySerialNumber")
    public ResponseEntity<Meter> findMeterBySerialNumber(@RequestParam("serialNumber") String serialNumber) {
        Optional<Meter> meter = meterRepository.findBySerialNumber(serialNumber);
        return meter.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
