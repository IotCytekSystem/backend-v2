package com.cytek2.cytek.audit.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "energy")
public class EnergyData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "red_voltage")
    private Double redVoltage;

    @Column(name = "yellow_voltage")
    private Double yellowVoltage;

    @Column(name = "blue_voltage")
    private Double blueVoltage;

    @Column(name = "red_current")
    private Double redCurrent;

    @Column(name = "yellow_current")
    private Double yellowCurrent;

    @Column(name = "blue_current")
    private Double blueCurrent;

    @Column(name = "red_power")
    private Double redPower;

    @Column(name = "yellow_power")
    private Double yellowPower;

    @Column(name = "blue_power")
    private Double bluePower;

    @Column(name = "red_power_consumption")
    private Double redPowerConsumption;

    @Column(name = "yellow_power_consumption")
    private Double yellowPowerConsumption;

    @Column(name = "blue_power_consumption")
    private Double bluePowerConsumption;

    @Column(name = "red_exported_energy")
    private Double redExportedEnergy;

    @Column(name = "yellow_exported_energy")
    private Double yellowExportedEnergy;

    @Column(name = "blue_exported_energy")
    private Double blueExportedEnergy;

    @Column(name = "red_frequency")
    private Double redFrequency;

    @Column(name = "yellow_frequency")
    private Double yellowFrequency;

    @Column(name = "blue_frequency")
    private Double blueFrequency;

    @Column(name = "red_power_factor")
    private Double redPowerFactor;

    @Column(name = "yellow_power_factor")
    private Double yellowPowerFactor;

    @Column(name = "blue_power_factor")
    private Double bluePowerFactor;

    @Column(name = "data_date")
    private Date date;

    @Column(name = "data_day")
    private String day;

    @Column(name = "data_time")
    private Time time;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "meter_id")
    private Meter meter;
}
