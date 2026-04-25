package com.myseals.service;

import com.myseals.dto.OfficeRequestDTO;
import com.myseals.dto.OfficeResponseDTO;
import com.myseals.model.Office;
import com.myseals.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfficeService {

    private final OfficeRepository officeRepository;

    public List<OfficeResponseDTO> findAll() {
        return officeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<OfficeResponseDTO> findById(@NonNull UUID id) {
        return officeRepository.findById(id).map(this::convertToDto);
    }

    public Optional<Office> findEntityById(@NonNull UUID id) {
        return officeRepository.findById(id);
    }

    public OfficeResponseDTO createOffice(@NonNull OfficeRequestDTO officeRequestDTO) {
        if (officeRepository.findByOfficeCode(officeRequestDTO.getOfficeCode()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Office code already exists");
        }

        Office office = new Office();
        office.setOfficeName(officeRequestDTO.getOfficeName());
        office.setOfficeCode(officeRequestDTO.getOfficeCode());
        office.setAddress(officeRequestDTO.getAddress());
        office.setContactEmail(officeRequestDTO.getContactEmail());
        office.setContactPhone(officeRequestDTO.getContactPhone());

        UUID parentId = officeRequestDTO.getParentOfficeId();
        if (parentId != null) {
            Office parent = officeRepository.findById(parentId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent office not found"));
            office.setParentOffice(parent);
        }

        Office savedOffice = officeRepository.save(office);
        return convertToDto(savedOffice);
    }

    public OfficeResponseDTO updateOffice(@NonNull UUID id, @NonNull OfficeRequestDTO officeRequestDTO) {
        Office existingOffice = officeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found"));

        existingOffice.setOfficeName(officeRequestDTO.getOfficeName());
        existingOffice.setOfficeCode(officeRequestDTO.getOfficeCode());
        existingOffice.setAddress(officeRequestDTO.getAddress());
        existingOffice.setContactEmail(officeRequestDTO.getContactEmail());
        existingOffice.setContactPhone(officeRequestDTO.getContactPhone());

        UUID parentId = officeRequestDTO.getParentOfficeId();
        if (parentId != null) {
            Office parent = officeRepository.findById(parentId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent office not found"));
            existingOffice.setParentOffice(parent);
        } else {
            existingOffice.setParentOffice(null);
        }

        Office updatedOffice = officeRepository.save(existingOffice);
        return convertToDto(updatedOffice);
    }

    public void deleteById(@NonNull UUID id) {
        officeRepository.deleteById(id);
    }

    private OfficeResponseDTO convertToDto(Office office) {
        OfficeResponseDTO dto = new OfficeResponseDTO();
        dto.setOfficeId(office.getOfficeId());
        dto.setOfficeName(office.getOfficeName());
        dto.setOfficeCode(office.getOfficeCode());
        dto.setAddress(office.getAddress());
        dto.setContactEmail(office.getContactEmail());
        dto.setContactPhone(office.getContactPhone());
        
        if (office.getParentOffice() != null) {
            dto.setParentOfficeId(office.getParentOffice().getOfficeId());
            dto.setParentOfficeName(office.getParentOffice().getOfficeName());
        }
        
        return dto;
    }
}
