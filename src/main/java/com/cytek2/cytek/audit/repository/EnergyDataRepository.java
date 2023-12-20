package com.cytek2.cytek.audit.repository;

import com.cytek2.cytek.audit.controller.DayTimeData;
import com.cytek2.cytek.audit.model.EnergyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EnergyDataRepository extends JpaRepository<EnergyData, Long> {


    @Query("SELECT p.redPower, p.date, p.day, p.time FROM EnergyData p")
    List<Object[]> findRedPowerData();

    @Query("SELECT p.bluePower, p.date, p.day, p.time FROM EnergyData p")
    List<EnergyData> findBluePowerData();

    @Query("SELECT p.redPower FROM EnergyData p WHERE p.userId = :userId ORDER BY p.redPower DESC")
    List<Double> findHighestRedPower(@Param("userId") Long userId);

    @Query("SELECT p.yellowPower FROM EnergyData p ORDER BY p.yellowPower DESC")
    List<Double> findHighestYellowPower(@Param("userId") Long userId);

    @Query("SELECT p.bluePower FROM EnergyData p ORDER BY p.bluePower DESC")
    List<Double> findHighestBluePower(@Param("userId") Long userId);


    //CURRENT
    @Query("SELECT p.redCurrent FROM EnergyData p ORDER BY p.redPower DESC")
    List<Double> findHighestRedCurrent();


    @Query("SELECT p.yellowCurrent FROM EnergyData p ORDER BY p.redPower DESC")
    List<Double> findHighestYellowCurrent();

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

    @Query("SELECT p FROM EnergyData p ORDER BY CONCAT(p.date, ' ', p.time) DESC")
    List<EnergyData> getRealtimeData(Long meterId);


    List<EnergyData> findByUserId(Integer user_id);

    // Custom query to fetch daytime data


    @Query("SELECT e.redPower, e.yellowPower, e.bluePower, e.time FROM EnergyData e WHERE e.time >= :start AND e.time < :end")
    List<Object[]> findPowerDataForHour(@Param("start") Timestamp start, @Param("end") Timestamp end);

    @Query("SELECT " +
            "MAX(p.redPowerConsumption) - MIN(p.redPowerConsumption) AS RedPhase, " +
            "MAX(p.yellowPowerConsumption) - MIN(p.yellowPowerConsumption) AS YellowPhase, " +
            "MAX(p.bluePowerConsumption) - MIN(p.bluePowerConsumption) AS BluePhase " +
            "FROM EnergyData p " +
            "WHERE p.meterId = :meterId AND " +
            "FUNCTION('DATE', p.date) = FUNCTION('DATE', CURRENT_DATE()) " +
            "GROUP BY p.meterId, FUNCTION('DATE', p.date)")
    List<Object[]> findThreePhaseData(@Param("meterId") Long meterId);

    @Query("SELECT " +
            "MAX(p.redPowerConsumption) - MIN(p.redPowerConsumption) AS RedPhase, " +
            "MAX(p.yellowPowerConsumption) - MIN(p.yellowPowerConsumption) AS YellowPhase, " +
            "MAX(p.bluePowerConsumption) - MIN(p.bluePowerConsumption) AS BluePhase " +
            "FROM EnergyData p " +
            "WHERE p.meterId = :meterId AND " +
            "FUNCTION('MONTH', p.date) = FUNCTION('MONTH', CURRENT_DATE())")
    List<Object[]> findMonthlyPowerConsumption(@Param("meterId") Long meterId);

    @Query("SELECT " +
            "MAX(p.redPowerConsumption) - MIN(p.redPowerConsumption) AS RedPhase, " +
            "MAX(p.yellowPowerConsumption) - MIN(p.yellowPowerConsumption) AS YellowPhase, " +
            "MAX(p.bluePowerConsumption) - MIN(p.bluePowerConsumption) AS BluePhase " +
            "FROM EnergyData p " +
            "WHERE p.meterId = :meterId AND " +
            "FUNCTION('YEAR', p.date) = FUNCTION('YEAR', CURRENT_DATE())")
    List<Object[]> findAnnualPowerConsumption(@Param("meterId") Long meterId);


    @Query("SELECT " +
            "MAX(p.redPower + p.yellowPower + p.bluePower) AS peakPower, " +
            "DATEDIFF(CURRENT_DATE(), MIN(p.date)) AS totalDays " +
            "FROM EnergyData p " +
            "WHERE p.userId = :userId AND p.meterId = :meterId AND " +
            "FUNCTION('YEAR', p.date) = FUNCTION('YEAR', CURRENT_DATE())")
    List<Double> findPeakPower(@Param("userId") Integer userId, @Param("meterId") Long meterId);


    @Query("SELECT " +
            "MAX(p.redCurrent + p.yellowCurrent + p.blueCurrent) AS peakCurrent " +
            "FROM EnergyData p " +
            "WHERE p.userId = :userId AND p.meterId = :meterId AND " +
            "FUNCTION('YEAR', p.date) = FUNCTION('YEAR', CURRENT_DATE())")
    List<Double> findPeakCurrent(@Param("userId") Integer userId, @Param("meterId") Long meterId);


    @Query("SELECT p.id, p.redPower " +
            "FROM EnergyData p " +
            "WHERE p.meterId = :meterId AND " +
            "FUNCTION('TRUNC', CONCAT(p.date, ' ', p.time), 'HH') >= FUNCTION('TRUNC', :selectedDate, 'HH') AND " +
            "FUNCTION('TRUNC', CONCAT(p.date, ' ', p.time), 'HH') <= FUNCTION('TRUNC', CURRENT_TIMESTAMP, 'HH') " +
            "ORDER BY CONCAT(p.date, ' ', p.time) DESC")
    List<Object[]> findIdAndRedPowerForSelectedDate(@Param("meterId") Long meterId, @Param("selectedDate") LocalDateTime selectedDate);


    @Query("SELECT " +
            "MAX(p.redCurrent + p.yellowCurrent + p.blueCurrent) AS peakCurrent " +
            "FROM EnergyData p " +
            "WHERE p.userId = :userId AND p.meterId = :meterId AND " +
            "FUNCTION('YEAR', p.date) = FUNCTION('YEAR', CURRENT_DATE())")
    Double findPeakCurrentByMeterIdAndUserId(@Param("meterId") Long meterId, @Param("userId") Integer userId);


    @Query("SELECT p " +
            "FROM EnergyData p " +
            "WHERE p.userId = :userId AND p.meterId = :meterId " +
            "AND FUNCTION('YEAR', p.date) = FUNCTION('YEAR', :selectedDate) " +
            "AND FUNCTION('MONTH', p.date) = FUNCTION('MONTH', :selectedDate) " +
            "AND FUNCTION('DAY', p.date) = FUNCTION('DAY', :selectedDate)")
    List<EnergyData> findAllDataByMeterIdAndUserId(
            @Param("meterId") Long meterId,
            @Param("userId") Integer userId,
            @Param("selectedDate") LocalDate selectedDate);



    @Query("SELECT " +
            "MAX(p.redPowerConsumption) - MIN(p.redPowerConsumption) AS red, " +
            "MAX(p.bluePowerConsumption) - MIN(p.bluePowerConsumption) AS blue, " +
            "MAX(p.yellowPowerConsumption) - MIN(p.yellowPowerConsumption) AS yellow, " +
            "MAX(p.redCurrent + p.yellowCurrent + p.blueCurrent) AS peakCurrent, "+
            "MAX(p.redPower + p.yellowPower + p.bluePower) AS peakPower " +
            "FROM EnergyData p " +
            "WHERE p.userId = :userId AND p.meterId = :meterId " +
            "AND FUNCTION('HOUR', p.time) >= 6 AND FUNCTION('HOUR', p.time) < 18 " +
            "AND FUNCTION('YEAR', p.date) = FUNCTION('YEAR', :selectedDate) " +
            "AND FUNCTION('MONTH', p.date) = FUNCTION('MONTH', :selectedDate) " +
            "AND FUNCTION('DAY', p.date) = FUNCTION('DAY', :selectedDate) " +
            "GROUP BY FUNCTION('YEAR', p.date), FUNCTION('MONTH', p.date), FUNCTION('DAY', p.date)")
    List<Object[]> findDaytimeData(
            @Param("meterId") Long meterId,
            @Param("userId") Integer userId,
            @Param("selectedDate") LocalDate selectedDate);




    @Query("SELECT " +
            "MAX(p.redPowerConsumption) - MIN(p.redPowerConsumption) AS red, " +
            "MAX(p.bluePowerConsumption) - MIN(p.bluePowerConsumption) AS blue, " +
            "MAX(p.yellowPowerConsumption) - MIN(p.yellowPowerConsumption) AS yellow, " +
            "MAX(p.redCurrent + p.yellowCurrent + p.blueCurrent) AS peakCurrent, "+
            "MAX(p.redPower + p.yellowPower + p.bluePower) AS peakPower " +
            "FROM EnergyData p " +
            "WHERE p.userId = :userId AND p.meterId = :meterId " +
            "AND FUNCTION('HOUR', p.time) >= 18 AND FUNCTION('HOUR', p.time) < 6 " +
            "AND FUNCTION('YEAR', p.date) = FUNCTION('YEAR', :selectedDate) " +
            "AND FUNCTION('MONTH', p.date) = FUNCTION('MONTH', :selectedDate) " +
            "AND FUNCTION('DAY', p.date) = FUNCTION('DAY', :selectedDate) " +
            "GROUP BY FUNCTION('YEAR', p.date), FUNCTION('MONTH', p.date), FUNCTION('DAY', p.date)")
    List<Object[]> findNighttimeData(
            @Param("meterId") Long meterId,
            @Param("userId") Integer userId,
            @Param("selectedDate") LocalDate selectedDate);

    @Query("SELECT " +
            "MAX(p.redPowerConsumption) - MIN(p.redPowerConsumption) AS red, " +
            "MAX(p.bluePowerConsumption) - MIN(p.bluePowerConsumption) AS blue, " +
            "MAX(p.yellowPowerConsumption) - MIN(p.yellowPowerConsumption) AS yellow, " +
            "MAX(p.redCurrent + p.yellowCurrent + p.blueCurrent) AS peakCurrent, "+
            "MAX(p.redPower + p.yellowPower + p.bluePower) AS peakPower " +
            "FROM EnergyData p " +
            "WHERE p.userId = :userId AND p.meterId = :meterId " +
            "AND FUNCTION('YEAR', p.date) = FUNCTION('YEAR', :selectedDate) " +
            "AND FUNCTION('MONTH', p.date) = FUNCTION('MONTH', :selectedDate) " +
            "AND FUNCTION('DAY', p.date) = FUNCTION('DAY', :selectedDate) " +
            "GROUP BY FUNCTION('YEAR', p.date), FUNCTION('MONTH', p.date), FUNCTION('DAY', p.date)")
    List<Object[]> findFullDayData(Long meterId, Integer userId, LocalDate selectedDate);


    @Query("SELECT " +
            "p.date AS date, " +
            "MAX(p.redPower + p.yellowPower + p.bluePower) AS peakPower, " +
            "MAX(p.redCurrent + p.yellowCurrent + p.blueCurrent) AS peakCurrent " +
            "FROM EnergyData p " +
            "WHERE p.userId = :userId AND p.meterId = :meterId " +
            "GROUP BY p.date")
    List<Object[]> findPeakPowerHistory(@Param("userId") Integer userId, @Param("meterId") Long meterId);

    @Query("SELECT MIN(p.date) FROM EnergyData p WHERE p.userId = :userId AND p.meterId = :meterId")
    LocalDate findFirstDataDateForUserAndMeter(@Param("userId") Integer userId, @Param("meterId") Long meterId);

    @Query("SELECT " +
            "MAX(p.redCurrent + p.yellowCurrent + p.blueCurrent) AS peakCurrent, "+
            "MAX(p.redPower + p.yellowPower + p.bluePower) AS peakPower, " +
            "MIN(p.date) AS theDate, "+
            "(p.date) AS exactDate, "+
            "(p.time) AS exactTime "+
            "FROM EnergyData p " +
            "WHERE p.userId = :userId AND p.meterId = :meterId")
    List<Object[]> findPeakPowerAndDuration(@Param("userId") Integer userId, @Param("meterId") Long meterId);


}
