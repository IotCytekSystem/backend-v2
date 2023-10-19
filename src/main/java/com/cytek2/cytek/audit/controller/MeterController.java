package com.cytek2.cytek.audit.controller;

import com.cytek2.cytek.audit.model.EnergyData;
import com.cytek2.cytek.audit.model.Meter;
import com.cytek2.cytek.audit.repository.EnergyDataRepository;
import com.cytek2.cytek.audit.repository.MeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MeterController {

    private final MeterRepository meterRepository;
    private EnergyDataRepository energyDataRepository;

    @Autowired
    public MeterController(MeterRepository meterRepository) {
        this.meterRepository = meterRepository;
    }

    // API to add a new meter
    @PostMapping("/meters/add")
    public ResponseEntity<Meter> addMeter(@RequestBody Meter newMeter) {
        Meter savedMeter = meterRepository.save(newMeter);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMeter);
    }

    // API to view all meters
    @GetMapping("/meters")
    public ResponseEntity<List<Meter>> getAllMeters() {
        List<Meter> meters = meterRepository.findByStatusIn(Arrays.asList("active", "idle","archived"));
        return ResponseEntity.ok(meters);
    }



    // API to view a meter by ID
    @GetMapping("/meters/{meterId}")
    public ResponseEntity<Meter> getMeterById(@PathVariable Long meterId) {
        Optional<Meter> optionalMeter = meterRepository.findById(meterId);
        if (optionalMeter.isPresent()) {
            Meter meter = optionalMeter.get();
            return ResponseEntity.ok(meter);
        }
        return ResponseEntity.notFound().build();
    }

    // API to delete a meter by ID
    @DeleteMapping("/meters/{meterId}")
    public ResponseEntity<Void> deleteMeter(@PathVariable Long meterId) {
        Optional<Meter> optionalMeter = meterRepository.findById(meterId);

        if (optionalMeter.isPresent()) {
            Meter meter = optionalMeter.get();

            // Update the status of the meter to "deleted"
            meter.setStatus("archived");
            meterRepository.save(meter);
            System.out.println("Arcived successfully");

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }




    // API to update a meter by ID
    @PutMapping("/meters/{meterId}")
    public ResponseEntity<Meter> updateMeter(@PathVariable Long meterId, @RequestBody Meter updatedMeter) {
        Optional<Meter> optionalMeter = meterRepository.findById(meterId);
        if (optionalMeter.isPresent()) {
            Meter meter = optionalMeter.get();
             meter.setMacAddress(updatedMeter.getMacAddress());
             meter.setSerialNumber(updatedMeter.getSerialNumber());
             meter.setBroker(updatedMeter.getBroker());
            Meter savedMeter = meterRepository.save(meter);
            return ResponseEntity.ok(savedMeter);
        }
        return ResponseEntity.notFound().build();
    }
}
