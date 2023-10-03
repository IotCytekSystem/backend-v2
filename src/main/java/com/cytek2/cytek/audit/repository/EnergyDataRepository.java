package com.cytek2.cytek.audit.repository;

import com.cytek2.cytek.audit.model.EnergyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnergyDataRepository extends JpaRepository<EnergyData, Long> {


    @Query("SELECT p.redPower, p.date, p.day, p.time FROM EnergyData p")
    List<Object[]> findRedPowerData();

    @Query("SELECT p.bluePower, p.date, p.day, p.time FROM EnergyData p")
    List<EnergyData> findBluePowerData();

    @Query("SELECT p.redPower FROM EnergyData p ORDER BY p.redPower DESC")
    List<Double> findHighestRedPower();
    @Query("SELECT p.yellowPower FROM EnergyData p ORDER BY p.redPower DESC")
    List<Double> findHighestYellowPower();
    @Query("SELECT p.bluePower FROM EnergyData p ORDER BY p.redPower DESC")
    List<Double> findHighestBluePower();


    //CURRENT
    @Query("SELECT p.redCurrent FROM EnergyData p ORDER BY p.redPower DESC")
    List<Double> findHighestRedCurrent();
    @Query("SELECT p.yellowCurrent FROM EnergyData p ORDER BY p.redPower DESC")
    List<Double> findHighesYellowCurrent();
    @Query("SELECT p.blueCurrent FROM EnergyData p ORDER BY p.redPower DESC")
    List<Double> findHighestBlueCurrent();

//    REAL TIME DATA


    // Define a custom query to retrieve the most recent red_current value

    @Query("SELECT p.redCurrent FROM EnergyData p ORDER BY p.time DESC")
    Double findMostRecentRedCurrent();

    @Query("SELECT p.blueCurrent FROM EnergyData p ORDER BY p.time DESC")
    Double getCurrentBlueVoltage();

    @Query("SELECT p.bluePower FROM EnergyData p ORDER BY p.time DESC")
    Double getCurrentBluePower();

    @Query("SELECT p.blueCurrent FROM EnergyData p ORDER BY p.time DESC")
    Double getCurrentBlueCurrent();

    @Query("SELECT p.yellowVoltage FROM EnergyData p ORDER BY p.time DESC")
    Double getCurrentYellowVoltage();

    @Query("SELECT p.yellowPower FROM EnergyData p ORDER BY p.time DESC ")
    Double getCurrentYellowPower();

    @Query("SELECT p.yellowCurrent FROM EnergyData p ORDER BY p.time DESC")
    Double getCurrentYellowCurrent();

    @Query("SELECT p.redVoltage FROM EnergyData p ORDER BY p.time DESC ")
    Double getCurrentRedVoltage();

    @Query("SELECT p.redPower FROM EnergyData p ORDER BY p.time DESC ")
    Double getCurrentRedPower();

    @Query("SELECT p FROM EnergyData p ORDER BY p.time DESC ")
    List<EnergyData> getRealtimeData();

    List<EnergyData> findByUserId(Long userId);
}
