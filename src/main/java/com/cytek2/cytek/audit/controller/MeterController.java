package com.cytek2.cytek.audit.controller;

import com.cytek2.cytek.audit.dto.AddMeterRequest;
import com.cytek2.cytek.audit.model.*;
import com.cytek2.cytek.audit.repository.EnergyDataRepository;
import com.cytek2.cytek.audit.repository.MeterRepository;
import com.cytek2.cytek.audit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MeterController {

    private final MeterRepository meterRepository;
    private final UserRepository userRepository;

    @Autowired
    public MeterController(MeterRepository meterRepository, UserRepository userRepository, EnergyDataRepository energyDataRepository) {
        this.meterRepository = meterRepository;
        this.userRepository = userRepository;
    }

    // API to add a new meter
    @PostMapping("/meters/add")
    public ResponseEntity<?> addMeter(@RequestBody AddMeterRequest addMeterRequest) {
        try {
            // Create a new Meter instance and populate it with data from addMeterRequest
            Meter newMeter = new Meter();
            newMeter.setMacAddress(addMeterRequest.getMacAddress());
            newMeter.setSerialNumber(addMeterRequest.getSerialNumber());
            newMeter.setVersion(addMeterRequest.getVersion());
            newMeter.setStatus(addMeterRequest.getStatus());
            newMeter.setUsername(addMeterRequest.getUsername());
            newMeter.setPassword(addMeterRequest.getPassword());
            newMeter.setBroker(addMeterRequest.getBroker());
            newMeter.setPort(addMeterRequest.getPort());
            newMeter.setMeterType(MeterType.valueOf(addMeterRequest.getMeterType()));
            newMeter.setMeterCT(MeterCT.valueOf(addMeterRequest.getMeterCT()));

            // Set the user if userId is provided in the request


            // Save the meter to the database
            Meter savedMeter = meterRepository.save(newMeter);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedMeter);
        } catch (IllegalArgumentException e) {
            // Handle invalid enum values or other illegal arguments
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid argument: " + e.getMessage());
        } catch (Exception e) {
            // Handle any unexpected exceptions or errors
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while trying to save Meter");
        }
    }



    // API to view all meters
    @GetMapping("/meters/idle")
    public ResponseEntity<List<Meter>> getIdleMeters() {
        List<Meter> idleMeters = meterRepository.findByMeterStatus(MeterStatus.valueOf("IDLE"));
        return ResponseEntity.ok(idleMeters);
    }

    @GetMapping("/meters")
    public ResponseEntity<List<Meter>> getAllMeters() {
        List<Meter> activeMeters = meterRepository.findAll();
        return ResponseEntity.ok(activeMeters);
    }


    @GetMapping("/meters/active")
    public ResponseEntity<List<Meter>> getActiveMeters() {
        List<Meter> activeMeters = meterRepository.findByMeterStatus(MeterStatus.valueOf("ACTIVE"));
        return ResponseEntity.ok(activeMeters);
    }

    @GetMapping("/meters/archived")
    public ResponseEntity<List<Meter>> getArchivedMeters() {
        List<Meter> archivedMeters = meterRepository.findByMeterStatus(MeterStatus.valueOf("ARCHIVED"));
        return ResponseEntity.ok(archivedMeters);
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

    @GetMapping("/meters/user/{userId}")
    public String getMeterIdByUserId(@PathVariable Integer userId) {
        try {
            return meterRepository.findMeterIdByUserId(userId);
        } catch (Exception e) {
            // Handle any exceptions (e.g., DataAccessException) and log or rethrow as needed
            throw new RuntimeException("Error fetching meter data for user: " + userId, e);
        }
    }
}
