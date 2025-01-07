package com.unibuc.fresh_market.service;

import com.unibuc.fresh_market.domain.security.Role;
import com.unibuc.fresh_market.repository.security.RoleRepository;
import com.unibuc.fresh_market.service.RoleService.RoleServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplementationTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImplementation roleService;

    private Role role;

    @BeforeEach
    void setUp() {
        role = Role.builder()
                .id(1)
                .name("ADMIN")
                .build();
    }

    @Test
    void whenCreateRole_thenReturnCreatedRole() {
        // Given
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        // When
        Role createdRole = roleService.createRole(role);

        // Then
        assertNotNull(createdRole);
        assertEquals(role.getId(), createdRole.getId());
        assertEquals(role.getName(), createdRole.getName());

        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    void whenGetRoleById_thenReturnRole() {
        // Given
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));

        // When
        Optional<Role> result = roleService.getRoleById(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(role.getId(), result.get().getId());
        assertEquals(role.getName(), result.get().getName());

        verify(roleRepository, times(1)).findById(1);
    }

    @Test
    void whenGetAllRoles_thenReturnListOfRoles() {
        // Given
        when(roleRepository.findAll()).thenReturn(List.of(role));

        // When
        List<Role> roles = roleService.getAllRoles();

        // Then
        assertNotNull(roles);
        assertFalse(roles.isEmpty());
        assertEquals(1, roles.size());
        assertEquals(role.getId(), roles.getFirst().getId());

        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void whenUpdateRole_thenReturnUpdatedRole() {
        // Given
        Role updatedRole = Role.builder()
                .id(1)
                .name("USER")
                .build();
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));
        when(roleRepository.save(any(Role.class))).thenReturn(updatedRole);

        // When
        Optional<Role> result = roleService.updateRole(1, updatedRole);

        // Then
        assertTrue(result.isPresent());
        assertEquals(updatedRole.getName(), result.get().getName());

        verify(roleRepository, times(1)).findById(1);
        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    void whenDeleteRole_thenReturnDeletedRole() {
        // Given
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));

        // When
        Optional<Role> result = roleService.deleteRole(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals(role.getId(), result.get().getId());

        verify(roleRepository, times(1)).findById(1);
        verify(roleRepository, times(1)).delete(role);
    }
}

