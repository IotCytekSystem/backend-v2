package com.cytek2.cytek.audit.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Meter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String macAddress;
    private String serialNumber;
    private String version;
    private String status;
    private String username;
    private String password;
    private String broker;
    private int port;
    private Integer userId;
    private MeterType meterType;
    private MeterCT meterCT;


    private LocalDateTime DateTimeAdded;

    @Enumerated(EnumType.STRING)
    private MeterStatus meterStatus = MeterStatus. IDLE;





    @PrePersist
    protected void onCreate() {
        DateTimeAdded = LocalDateTime.now();
    }
    public Meter(Long meterId) {
    }

    public static List<EnergyData> getEnergyData() {
        return null;
    }


}