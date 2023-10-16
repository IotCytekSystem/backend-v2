package com.cytek2.cytek.audit.services.service;


import com.cytek2.cytek.audit.model.EnergyData;
import com.cytek2.cytek.audit.repository.EnergyDataRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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






    public Map<String, Double> findHighestPower() {
        List<Double> highestRedPowers = energyDataRepository.findHighestRedPower();
        List<Double> highestYellowPowers = energyDataRepository.findHighestYellowPower();
        List<Double> highestBluePowers = energyDataRepository.findHighestBluePower();

        Map<String, Double> result = new HashMap<>();

        if (!highestRedPowers.isEmpty()) {
            result.put("red", highestRedPowers.get(0));
        }

        if (!highestYellowPowers.isEmpty()) {
            result.put("yellow", highestYellowPowers.get(0));
        }

        if (!highestBluePowers.isEmpty()) {
            result.put("blue", highestBluePowers.get(0));
        }

        return result;
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
