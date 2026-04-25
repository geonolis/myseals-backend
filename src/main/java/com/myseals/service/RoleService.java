package com.myseals.service;

import com.myseals.model.Role;
import com.myseals.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Optional<Role> findById(@NonNull UUID id) {
        return roleRepository.findById(id);
    }

    public Optional<Role> findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    public Role save(@NonNull Role role) {
        return roleRepository.save(role);
    }

    public void deleteById(@NonNull UUID id) {
        roleRepository.deleteById(id);
    }
}
