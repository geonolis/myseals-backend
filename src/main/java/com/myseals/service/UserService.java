package com.myseals.service;

import com.myseals.model.Office;
import com.myseals.model.Role;
import com.myseals.model.User;
import com.myseals.repository.OfficeRepository;
import com.myseals.repository.RoleRepository;
import com.myseals.repository.UserRepository;
import com.myseals.dto.UserRequestDTO;
import com.myseals.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private OfficeRepository officeRepository;

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<UserResponseDTO> findById(UUID id) {
        return userRepository.findById(id).map(this::convertToDto);
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        // In a real application, auth0UserId would come from Auth0 after user registration/login
        // For now, we'll generate a placeholder or expect it if provided for testing
        String auth0UserId = "auth0|" + UUID.randomUUID().toString().replace("-", "");

        if (userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this email already exists");
        }

        User user = new User();
        user.setAuth0UserId(auth0UserId);
        user.setEmail(userRequestDTO.getEmail());
        user.setFullName(userRequestDTO.getFullName());
        user.setPhoneNumber(userRequestDTO.getPhoneNumber());
        user.setActive(userRequestDTO.getIsActive());

        Office office = officeRepository.findById(userRequestDTO.getOfficeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found"));
        user.setOffice(office);

        Role role = roleRepository.findById(userRequestDTO.getRoleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
        user.setRole(role);

        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    public UserResponseDTO updateUser(UUID id, UserRequestDTO userRequestDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        existingUser.setEmail(userRequestDTO.getEmail());
        existingUser.setFullName(userRequestDTO.getFullName());
        existingUser.setPhoneNumber(userRequestDTO.getPhoneNumber());
        existingUser.setActive(userRequestDTO.getIsActive());

        if (!existingUser.getOffice().getOfficeId().equals(userRequestDTO.getOfficeId())) {
            Office office = officeRepository.findById(userRequestDTO.getOfficeId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found"));
            existingUser.setOffice(office);
        }

        if (!existingUser.getRole().getRoleId().equals(userRequestDTO.getRoleId())) {
            Role role = roleRepository.findById(userRequestDTO.getRoleId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
            existingUser.setRole(role);
        }

        User updatedUser = userRepository.save(existingUser);
        return convertToDto(updatedUser);
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    private UserResponseDTO convertToDto(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setAuth0UserId(user.getAuth0UserId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setIsActive(user.getActive());
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
