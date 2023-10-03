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

        // Sort the power values in descending order for each category
        highestRedPowers.sort(Collections.reverseOrder());
        highestYellowPowers.sort(Collections.reverseOrder());
        highestBluePowers.sort(Collections.reverseOrder());

        // Get the top 3 power values for each category
        List<Double> top3RedPowers = highestRedPowers.subList(0, Math.min(highestRedPowers.size(), 3));
        List<Double> top3YellowPowers = highestYellowPowers.subList(0, Math.min(highestYellowPowers.size(), 3));
        List<Double> top3BluePowers = highestBluePowers.subList(0, Math.min(highestBluePowers.size(), 3));

        // Find the highest power value among the top 3 in each category
        Double highestRedPower = top3RedPowers.stream().max(Double::compareTo).orElse(null);
        Double highestYellowPower = top3YellowPowers.stream().max(Double::compareTo).orElse(null);
        Double highestBluePower = top3BluePowers.stream().max(Double::compareTo).orElse(null);

        // Find the overall highest power value among the highest in each category
        List<Double> allHighestPowers = Arrays.asList(highestRedPower, highestYellowPower, highestBluePower);
        Double overallHighestPower = allHighestPowers.stream().filter(Objects::nonNull).max(Double::compareTo).orElse(null);

        return Arrays.asList(highestRedPower, highestYellowPower, highestBluePower, overallHighestPower);
    }


    public List<Double> findHighestCurrent() {
        List<Double> highestRedPowers = energyDataRepository.findHighestRedCurrent();
        List<Double> highestYellowPowers = energyDataRepository.findHighesYellowCurrent();
        List<Double> highestBluePowers = energyDataRepository.findHighestBlueCurrent();

        // Sort the power values in descending order for each category
        highestRedPowers.sort(Collections.reverseOrder());
        highestYellowPowers.sort(Collections.reverseOrder());
        highestBluePowers.sort(Collections.reverseOrder());

        // Get the top 3 power values for each category
        List<Double> top3RedPowers = highestRedPowers.subList(0, Math.min(highestRedPowers.size(), 3));
        List<Double> top3YellowPowers = highestYellowPowers.subList(0, Math.min(highestYellowPowers.size(), 3));
        List<Double> top3BluePowers = highestBluePowers.subList(0, Math.min(highestBluePowers.size(), 3));

        // Find the highest power value among the top 3 in each category
        Double highestRedPower = top3RedPowers.stream().max(Double::compareTo).orElse(null);
        Double highestYellowPower = top3YellowPowers.stream().max(Double::compareTo).orElse(null);
        Double highestBluePower = top3BluePowers.stream().max(Double::compareTo).orElse(null);

        // Find the overall highest power value among the highest in each category
        List<Double> allHighestPowers = Arrays.asList(highestRedPower, highestYellowPower, highestBluePower);
        Double overallHighestPower = allHighestPowers.stream().filter(Objects::nonNull).max(Double::compareTo).orElse(null);

        // Calculate the sum of the three highest powers
        double sumOfHighestPowers =highestRedPower + highestYellowPower + highestBluePower;

        return Arrays.asList(highestRedPower, highestYellowPower, highestBluePower, sumOfHighestPowers);
    }

    public List<EnergyData> getAllEnergyDataByUserId(long clientID) {
        return null;
    }
}
