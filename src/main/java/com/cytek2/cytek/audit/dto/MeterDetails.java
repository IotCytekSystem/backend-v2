package com.cytek2.cytek.audit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class MeterDetails {
    private String serialNumber;
    private Long userId;

    public MeterDetails(String serialNumber, Long userId) {
        this.serialNumber = serialNumber;
        this.userId = userId;
    }

    // Getters and setters
}

