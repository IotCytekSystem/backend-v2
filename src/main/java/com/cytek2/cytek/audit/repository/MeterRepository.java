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

    @Query("SELECT m.id, CAST(m.serialNumber AS string) FROM Meter m")
    List<Object[]> findMeterDetails();

    @Query("SELECT m.serialNumber FROM Meter m")
    List<String> findSerialNumbers();


    List<Meter> findByStatusIn(List<String> list);

    List<Meter> findByMeterStatus(MeterStatus meterStatus);
}
