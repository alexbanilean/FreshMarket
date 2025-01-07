package com.unibuc.fresh_market.controller;

import com.unibuc.fresh_market.domain.security.Role;
import com.unibuc.fresh_market.service.RoleService.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Roles controller")
@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(summary = "Create a new role")
    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody @Valid Role role, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors found!");
        }

        Role createdRole = roleService.createRole(role);
        return ResponseEntity.ok(createdRole);
    }

    @Operation(summary = "Get all roles")
    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @Operation(summary = "Get a role by id")
    @GetMapping("/{roleId}")
    public ResponseEntity<?> getRoleById(@PathVariable String roleId) {
        Integer id = Integer.parseInt(roleId);
        Optional<Role> role = roleService.getRoleById(id);
        if (role.isPresent()) {
            return ResponseEntity.ok(role.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Update a role")
    @PutMapping("/{roleId}")
    public ResponseEntity<?> updateRole(@RequestBody @Valid Role role, BindingResult bindingResult, @PathVariable String roleId) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors found!");
        }

        Integer id = Integer.parseInt(roleId);
        Optional<Role> updatedRole = roleService.updateRole(id, role);
        if (updatedRole.isPresent()) {
            return ResponseEntity.ok(updatedRole.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a role")
    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable String roleId) {
        Integer id = Integer.parseInt(roleId);
        Optional<Role> deletedRole = roleService.deleteRole(id);
        if (deletedRole.isPresent()) {
            return ResponseEntity.ok(deletedRole.get());
        }

        return ResponseEntity.notFound().build();
    }
}
