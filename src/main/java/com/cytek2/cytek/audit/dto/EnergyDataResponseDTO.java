package com.cytek2.cytek.audit.dto;

import com.cytek2.cytek.audit.model.EnergyData;

public class EnergyDataResponseDTO {
    // Include all fields from EnergyData
    private final Long id;
    private final Double redVoltage;
    private final Double yellowVoltage;
    private final Double blueVoltage;
    private final Double redCurrent;
    private final Double yellowCurrent;
    private final Double blueCurrent;
    private final Double redPower;
    private final Double yellowPower;
    private final Double bluePower;
    private final Double redPowerConsumption;
    private final Double yellowPowerConsumption;
    private final Double bluePowerConsumption;
    private final Double redExportedEnergy;
    private final Double yellowExportedEnergy;
    private final Double blueExportedEnergy;
    private final Double redFrequency;
    private final Double yellowFrequency;
    private final Double blueFrequency;
    private final Double redPowerFactor;
    private final Double yellowPowerFactor;
    private final Double bluePowerFactor;
    private final String date;
    private final String day;
    private final String time;
    private final Integer userId;
    private final Long meterId;

    public EnergyDataResponseDTO(EnergyData energyData) {
        // Map fields from EnergyData to DTO
        this.id = energyData.getId();
        this.redVoltage = energyData.getRedVoltage();
        this.yellowVoltage = energyData.getYellowVoltage();
        this.blueVoltage = energyData.getBlueVoltage();
        this.redCurrent = energyData.getRedCurrent();
        this.yellowCurrent = energyData.getYellowCurrent();
        this.blueCurrent = energyData.getBlueCurrent();
        this.redPower = energyData.getRedPower();
        this.yellowPower = energyData.getYellowPower();
        this.bluePower = energyData.getBluePower();
        this.redPowerConsumption = energyData.getRedPowerConsumption();
        this.yellowPowerConsumption = energyData.getYellowPowerConsumption();
        this.bluePowerConsumption = energyData.getBluePowerConsumption();
        this.redExportedEnergy = energyData.getRedExportedEnergy();
        this.yellowExportedEnergy = energyData.getYellowExportedEnergy();
        this.blueExportedEnergy = energyData.getBlueExportedEnergy();
        this.redFrequency = energyData.getRedFrequency();
        this.yellowFrequency = energyData.getYellowFrequency();
        this.blueFrequency = energyData.getBlueFrequency();
        this.redPowerFactor = energyData.getRedPowerFactor();
        this.yellowPowerFactor = energyData.getYellowPowerFactor();
        this.bluePowerFactor = energyData.getBluePowerFactor();
        this.date = String.valueOf(energyData.getDate());
        this.day = energyData.getDay();
        this.time = String.valueOf(energyData.getTime());
        this.userId = energyData.getUserId();
        this.meterId = energyData.getMeterId();
    }

    // Getter methods (add getters for all fields)
    public Long getId() {
        return id;
    }

    public Double getRedVoltage() {
        return redVoltage;
    }

    public Double getYellowVoltage() {
        return yellowVoltage;
    }

    public Double getBlueVoltage() {
        return blueVoltage;
    }

    public Double getRedCurrent() {
        return redCurrent;
    }

    public Double getYellowCurrent() {
        return yellowCurrent;
    }

    public Double getBlueCurrent() {
        return blueCurrent;
    }

    public Double getRedPower() {
        return redPower;
    }

    public Double getYellowPower() {
        return yellowPower;
    }

    public Double getBluePower() {
        return bluePower;
    }

    public Double getRedPowerConsumption() {
        return redPowerConsumption;
    }

    public Double getYellowPowerConsumption() {
        return yellowPowerConsumption;
    }

    public Double getBluePowerConsumption() {
        return bluePowerConsumption;
    }

    public Double getRedExportedEnergy() {
        return redExportedEnergy;
    }

    public Double getYellowExportedEnergy() {
        return yellowExportedEnergy;
    }

    public Double getBlueExportedEnergy() {
        return blueExportedEnergy;
    }

    public Double getRedFrequency() {
        return redFrequency;
    }

    public Double getYellowFrequency() {
        return yellowFrequency;
    }

    public Double getBlueFrequency() {
        return blueFrequency;
    }

    public Double getRedPowerFactor() {
        return redPowerFactor;
    }

    public Double getYellowPowerFactor() {
        return yellowPowerFactor;
    }

    public Double getBluePowerFactor() {
        return bluePowerFactor;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public Integer getUserId() {
        return userId;
    }

    public Long getMeterId() {
        return meterId;
    }
}
