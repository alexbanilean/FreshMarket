package com.unibuc.fresh_market.services.RoleService;

import com.unibuc.fresh_market.domain.security.Role;
import com.unibuc.fresh_market.repositories.security.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImplementation implements RoleService {
    public final RoleRepository roleRepository;

    public RoleServiceImplementation(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Optional<Role> getRoleById(Integer id) {
        Role role = roleRepository.findById(id).orElse(null);
        return Optional.ofNullable(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> updateRole(Integer id, Role role) {
        Role roleToUpdate = roleRepository.findById(id).orElse(null);

        if (roleToUpdate != null) {
            roleToUpdate.setName(role.getName());
            roleRepository.save(roleToUpdate);
            return Optional.of(roleToUpdate);
        }

        return Optional.empty();
    }

    public Optional<Role> deleteRole(Integer id) {
        Role roleToDelete = roleRepository.findById(id).orElse(null);
        if (roleToDelete != null) {
            roleRepository.delete(roleToDelete);
            return Optional.of(roleToDelete);
        }

        return Optional.empty();
    }
}
