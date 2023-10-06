package com.cytek2.cytek.audit.services.service;


import com.cytek2.cytek.audit.model.EnergyData;
import com.cytek2.cytek.audit.repository.EnergyDataRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Data
public class EnergyDataService {

    private final EnergyDataRepository energyDataRepository;

    @Autowired
    public EnergyDataService(EnergyDataRepository energyDataRepository) {
        this.energyDataRepository = energyDataRepository;
    }

    public void saveEnergyData(String json) {
        EnergyData energyData = new EnergyData();
        energyDataRepository.save(energyData);
    }

    public void saveUserData(String json) {
        EnergyData energyData = new EnergyData();
        energyDataRepository.save(energyData);
    }

    public void saveMeterData(String json) {
        EnergyData energyData = new EnergyData();
        energyDataRepository.save(energyData);
    }

    public List<EnergyData> getRedCurrents() {

        return  energyDataRepository.findAll();
    }

    public List<EnergyData> getBlueCurrents() {
        return  energyDataRepository.findAll();
    }

    public List<EnergyData> getYellowCurrents() {
        return  energyDataRepository.findAll();
    }

    public List<Object[]> getRedPower() {
        return  energyDataRepository.findRedPowerData();
    }

    public List<EnergyData> getBluePower() {
        return  energyDataRepository.findBluePowerData();
    }

    public List<EnergyData> getYellowPower() {
        return  energyDataRepository.findAll();
    }


    public List<EnergyData> getRedVoltages() {
        return  energyDataRepository.findAll();
    }

    public List<EnergyData> getBlueVoltages() {
        return  energyDataRepository.findAll();
    }

    public List<EnergyData> getYellowVoltages() {
        return  energyDataRepository.findAll();
    }






    public List<Double> findHighestPower() {
        List<Double> highestRedPowers = energyDataRepository.findHighestRedPower();
        List<Double> highestYellowPowers = energyDataRepository.findHighestYellowPower();
        List<Double> highestBluePowers = energyDataRepository.findHighestBluePower();

        // Get the first element from each list, if it exists
        Double highestRedPower = highestRedPowers.isEmpty() ? null : highestRedPowers.get(0);
        Double highestYellowPower = highestYellowPowers.isEmpty() ? null : highestYellowPowers.get(0);
        Double highestBluePower = highestBluePowers.isEmpty() ? null : highestBluePowers.get(0);

        // Check for null values and calculate totalPower
        double totalPower = 0.0;
        if (highestRedPower != null) {
            totalPower += highestRedPower;
        }
        if (highestYellowPower != null) {
            totalPower += highestYellowPower;
        }
        if (highestBluePower != null) {
            totalPower += highestBluePower;
        }

        return Arrays.asList(highestRedPower, highestYellowPower, highestBluePower, totalPower);
    }





    public List<Double> findHighestCurrent() {
        List<Double> highestRedCurrents = energyDataRepository.findHighestRedCurrent();
        List<Double> highestYellowCurrents = energyDataRepository.findHighestYellowCurrent();
        List<Double> highestBlueCurrents = energyDataRepository.findHighestBlueCurrent();

        // Get the first element from each list, if it exists
        Double highestRedCurrent = highestRedCurrents.isEmpty() ? null : highestRedCurrents.get(0);
        Double highestYellowCurrent = highestYellowCurrents.isEmpty() ? null : highestYellowCurrents.get(0);
        Double highestBlueCurrent = highestBlueCurrents.isEmpty() ? null : highestBlueCurrents.get(0);

        // Check for null values and calculate totalCurrent
        double totalCurrent = 0.0;
        if (highestRedCurrent != null) {
            totalCurrent += highestRedCurrent;
        }
        if (highestYellowCurrent != null) {
            totalCurrent += highestYellowCurrent;
        }
        if (highestBlueCurrent != null) {
            totalCurrent += highestBlueCurrent;
        }

        return Arrays.asList(highestRedCurrent, highestYellowCurrent, highestBlueCurrent, totalCurrent);
    }


    public List<EnergyData> getAllEnergyDataByUserId(long clientID) {
        return null;
    }

    public List<EnergyData> dataByUserId(Integer userId) {
        return energyDataRepository.findByUserId(userId);
    }

}
