package com.unibuc.fresh_market.mapper;
import com.unibuc.fresh_market.domain.security.Role;
import com.unibuc.fresh_market.dto.RoleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDTO mapToDTO(Role role);
    Role mapToEntity(RoleDTO roleDTO);
}
