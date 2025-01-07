package com.unibuc.fresh_market.controller;

import com.unibuc.fresh_market.domain.security.Role;
import com.unibuc.fresh_market.service.RoleService.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private RoleController roleController;

    private Role role;
    private List<Role> roleList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        role = Role.builder()
                .id(1)
                .name("Admin")
                .build();

        Role role2 = Role.builder()
                .id(2)
                .name("User")
                .build();

        roleList = List.of(role, role2);
    }

    @Test
    void givenValidRole_whenCreateRole_thenReturnCreatedRole() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(false);
        when(roleService.createRole(any(Role.class))).thenReturn(role);

        // When
        ResponseEntity<?> response = roleController.createRole(role, bindingResult);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(role, response.getBody());
    }

    @Test
    void givenInvalidRole_whenCreateRole_thenReturnBadRequest() {
        // Given
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        ResponseEntity<?> response = roleController.createRole(role, bindingResult);

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation errors found!", response.getBody());
    }

    @Test
    void givenRoles_whenGetAllRoles_thenReturnRoleList() {
        // Given
        when(roleService.getAllRoles()).thenReturn(roleList);

        // When
        ResponseEntity<?> response = roleController.getAllRoles();

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(roleList, response.getBody());
    }

    @Test
    void givenValidRoleId_whenGetRoleById_thenReturnRole() {
        // Given
        Integer roleId = 1;
        when(roleService.getRoleById(roleId)).thenReturn(Optional.of(role));

        // When
        ResponseEntity<?> response = roleController.getRoleById(String.valueOf(roleId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(role, response.getBody());
    }

    @Test
    void givenInvalidRoleId_whenGetRoleById_thenReturnNotFound() {
        // Given
        Integer roleId = 1;
        when(roleService.getRoleById(roleId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = roleController.getRoleById(String.valueOf(roleId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidRole_whenUpdateRole_thenReturnUpdatedRole() {
        // Given
        Integer roleId = 1;
        when(bindingResult.hasErrors()).thenReturn(false);
        when(roleService.updateRole(eq(roleId), any(Role.class))).thenReturn(Optional.of(role));

        // When
        ResponseEntity<?> response = roleController.updateRole(role, bindingResult, String.valueOf(roleId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(role, response.getBody());
    }

    @Test
    void givenInvalidRole_whenUpdateRole_thenReturnBadRequest() {
        // Given
        Integer roleId = 1;
        when(bindingResult.hasErrors()).thenReturn(true);

        // When
        ResponseEntity<?> response = roleController.updateRole(role, bindingResult, String.valueOf(roleId));

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Validation errors found!", response.getBody());
    }

    @Test
    void givenInvalidRoleId_whenUpdateRole_thenReturnNotFound() {
        // Given
        Integer roleId = 1;
        when(bindingResult.hasErrors()).thenReturn(false);
        when(roleService.updateRole(roleId, role)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = roleController.updateRole(role, bindingResult, String.valueOf(roleId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void givenValidRoleId_whenDeleteRole_thenReturnDeletedRole() {
        // Given
        Integer roleId = 1;
        when(roleService.deleteRole(roleId)).thenReturn(Optional.of(role));

        // When
        ResponseEntity<?> response = roleController.deleteRole(String.valueOf(roleId));

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(role, response.getBody());
    }

    @Test
    void givenInvalidRoleId_whenDeleteRole_thenReturnNotFound() {
        // Given
        Integer roleId = 1;
        when(roleService.deleteRole(roleId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = roleController.deleteRole(String.valueOf(roleId));

        // Then
        assertEquals(404, response.getStatusCode().value());
    }
}
