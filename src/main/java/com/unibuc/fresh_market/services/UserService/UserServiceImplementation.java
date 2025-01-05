package com.unibuc.fresh_market.services.UserService;

import com.unibuc.fresh_market.domain.security.Role;
import com.unibuc.fresh_market.domain.security.User;
import com.unibuc.fresh_market.repositories.security.RoleRepository;
import com.unibuc.fresh_market.repositories.security.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImplementation(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
}
