package com.myseals.service;

import com.myseals.model.Office;
import com.myseals.model.Role;
import com.myseals.model.User;
import com.myseals.repository.OfficeRepository;
import com.myseals.repository.RoleRepository;
import com.myseals.repository.UserRepository;
import com.myseals.dto.UserRequestDTO;
import com.myseals.dto.UserResponseDTO;
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
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OfficeRepository officeRepository;

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<UserResponseDTO> findById(@NonNull UUID id) {
        return userRepository.findById(id).map(this::convertToDto);
    }

    public UserResponseDTO createUser(@NonNull UserRequestDTO userRequestDTO) {
        if (userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this email already exists");
        }

        User user = new User();
        user.setAuth0UserId(userRequestDTO.getAuth0UserId());
        user.setEmail(userRequestDTO.getEmail());
        user.setFullName(userRequestDTO.getFullName());
        user.setPhoneNumber(userRequestDTO.getPhoneNumber());
        user.setActive(userRequestDTO.getActive() != null ? userRequestDTO.getActive() : true);

        UUID officeId = userRequestDTO.getOfficeId();
        if (officeId != null) {
            Office office = officeRepository.findById(officeId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found"));
            user.setOffice(office);
        }

        UUID roleId = userRequestDTO.getRoleId();
        if (roleId != null) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
            user.setRole(role);
        }

        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    public UserResponseDTO updateUser(@NonNull UUID id, @NonNull UserRequestDTO userRequestDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        existingUser.setFullName(userRequestDTO.getFullName());
        existingUser.setPhoneNumber(userRequestDTO.getPhoneNumber());
        if (userRequestDTO.getActive() != null) {
            existingUser.setActive(userRequestDTO.getActive());
        }

        UUID officeId = userRequestDTO.getOfficeId();
        if (officeId != null) {
            Office office = officeRepository.findById(officeId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found"));
            existingUser.setOffice(office);
        } else {
            existingUser.setOffice(null);
        }

        UUID roleId = userRequestDTO.getRoleId();
        if (roleId != null) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
            existingUser.setRole(role);
        }

        User updatedUser = userRepository.save(existingUser);
        return convertToDto(updatedUser);
    }

    public void deleteById(@NonNull UUID id) {
        userRepository.deleteById(id);
    }

    private UserResponseDTO convertToDto(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setAuth0UserId(user.getAuth0UserId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setActive(user.getActive());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        if (user.getOffice() != null) {
            dto.setOfficeId(user.getOffice().getOfficeId());
            dto.setOfficeName(user.getOffice().getOfficeName());
        }
        if (user.getRole() != null) {
            dto.setRoleId(user.getRole().getRoleId());
            dto.setRoleName(user.getRole().getRoleName());
        }
        return dto;
    }
}
