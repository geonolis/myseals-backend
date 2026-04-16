package com.myseals.service;

import com.myseals.model.Role;
import com.myseals.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Optional<Role> findById(UUID id) {
        return roleRepository.findById(id);
    }

    public Optional<Role> findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public void deleteById(UUID id) {
        roleRepository.deleteById(id);
    }
}
