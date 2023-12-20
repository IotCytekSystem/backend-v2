package com.cytek2.cytek.audit.repository;


import com.cytek2.cytek.audit.dto.MeterDetails;
import com.cytek2.cytek.audit.model.Meter;
import com.cytek2.cytek.audit.model.MeterStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MeterRepository extends JpaRepository<Meter, Long> {


    Optional<Meter> findBySerialNumber( String serialNumber);

    static List<Meter> findByUserId(int intExact) {
        return null;
    }

    @Query("SELECT m.id, m.serialNumber,m.userId FROM Meter m")
    List<Object[]> findMeterDetails();


    @Query("SELECT m.serialNumber FROM Meter m")
    List<String> findSerialNumbers();


    List<Meter> findByStatusIn(List<String> list);

    List<Meter> findByMeterStatus(MeterStatus meterStatus);

    // Query to find meterIds based on userId from the Meter table
    @Query("SELECT m.id FROM Meter m WHERE m.userId = :userId")
    String findMeterIdByUserId(@Param("userId") Integer userId);

    @Query("SELECT m FROM Meter m WHERE m.userId = :userId")
    Meter findMeterByUserId(@Param("userId") Integer userId);


}
