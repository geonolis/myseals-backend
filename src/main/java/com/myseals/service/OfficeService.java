package com.myseals.service;

import com.myseals.model.Office;
import com.myseals.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OfficeService {

    @Autowired
    private OfficeRepository officeRepository;

    public List<Office> findAll() {
        return officeRepository.findAll();
    }

    public Optional<Office> findById(UUID id) {
        return officeRepository.findById(id);
    }

    public Optional<Office> findByOfficeCode(String officeCode) {
        return officeRepository.findByOfficeCode(officeCode);
    }

    public Office save(Office office) {
        return officeRepository.save(office);
    }

    public void deleteById(UUID id) {
        officeRepository.deleteById(id);
    }
}
