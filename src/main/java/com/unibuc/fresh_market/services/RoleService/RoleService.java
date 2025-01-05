package com.unibuc.fresh_market.services.RoleService;

import com.unibuc.fresh_market.domain.security.Role;

import java.util.List;

public interface RoleService {
    Role createRole(Role role);
    Role getRoleById(Integer id);
    List<Role> getAllRoles();
    Role updateRole(Integer id, Role role);
    void deleteRole(Integer id);
}
