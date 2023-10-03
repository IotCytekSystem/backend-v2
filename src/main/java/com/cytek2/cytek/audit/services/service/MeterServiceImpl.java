package com.cytek2.cytek.audit.services.service;

import com.cytek2.cytek.audit.model.Meter;
import com.cytek2.cytek.audit.repository.MeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MeterServiceImpl implements MeterService {

    private final MeterRepository meterRepository;

    @Autowired
    public MeterServiceImpl(MeterRepository meterRepository) {
        this.meterRepository = meterRepository;
    }

    @Override
    public List<Meter> getAllMeters() {

        return meterRepository.findAll();
    }

    @Override
    public Optional<Meter> getMeterById(Long id) {

        return meterRepository.findById(id);
    }



    @Override
    @Transactional
    public Meter addMeter(Meter meter) {
        try {
            // Attempt to save the meter
            System.out.println("trying to save meter");
            return meterRepository.save(meter);
        } catch (Exception e) {

            throw new RuntimeException("Failed to add meter", e);
        }
    }

    @Override
    public void deleteMeter(Long id) {
        meterRepository.deleteById(id);
    }

    @Override
    public Meter updateMeter(Long id, Meter updatedMeter) {
        return null;
    }


}
