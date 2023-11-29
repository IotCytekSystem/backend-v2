package com.cytek2.cytek.audit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddMeterRequest {
    private String macAddress;
    private String serialNumber;
    private String version;
    private String status;
    private String username;
    private String password;
    private String broker;
    private int port;
    private String meterType;
    private String meterCT;
    private String DateTimeAdded;
    private String meterStatus;
    private Long userId; // Assuming you want to include the user ID in the DTO

    // Add getters and setters if not using Lombok's @Data
}
