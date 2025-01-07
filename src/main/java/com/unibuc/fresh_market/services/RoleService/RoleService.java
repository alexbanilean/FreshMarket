package com.unibuc.fresh_market.services.RoleService;

import com.unibuc.fresh_market.domain.security.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role createRole(Role role);
    Optional<Role> getRoleById(Integer id);
    List<Role> getAllRoles();
    Optional<Role> updateRole(Integer id, Role role);
    Optional<Role> deleteRole(Integer id);
}
