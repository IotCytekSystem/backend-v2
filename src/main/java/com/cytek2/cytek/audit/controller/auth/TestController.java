package com.cytek2.cytek.audit.controller.auth;

import com.cytek2.cytek.audit.model.EnergyData;
import com.cytek2.cytek.audit.repository.EnergyDataRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestController {

    // Endpoint to get daytime data
    @GetMapping("/test")
    public ResponseEntity<String> TestEndpoint() {
        return ResponseEntity.status(HttpStatus.FOUND).body("Test Controller is working well!!");
    }
}
