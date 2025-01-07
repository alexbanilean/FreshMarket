package com.unibuc.fresh_market.mapper;

import com.unibuc.fresh_market.domain.security.User;
import com.unibuc.fresh_market.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO mapToDTO(User user);
    User mapToEntity(UserDTO userDTO);
}
