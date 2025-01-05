package com.unibuc.fresh_market.config;

import com.unibuc.fresh_market.domain.security.Role;
import com.unibuc.fresh_market.domain.security.User;
import com.unibuc.fresh_market.repositories.security.RoleRepository;
import com.unibuc.fresh_market.repositories.security.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private void loadInitialData() {
        Role adminRole = roleRepository.save(Role.builder().name("ADMIN").build());
        Role userRole = roleRepository.save(Role.builder().name("USER").build());
        Role guestRole = roleRepository.save(Role.builder().name("GUEST").build());

        User admin = User.builder().username("admin").email("admin@unibuc.com")
                .password(passwordEncoder.encode("1234")).role(adminRole).build();
        User user = User.builder().username("user").email("user@unibuc.com")
                .password(passwordEncoder.encode("1234")).role(userRole).build();
        User guest = User.builder().username("guest").email("guest@unibuc.com")
                .password(passwordEncoder.encode("1234")).role(guestRole).build();

        userRepository.deleteAll();
        userRepository.save(admin);
        userRepository.save(user);
        userRepository.save(guest);
    }

    @Override
    public void run(String... args) throws Exception {
        loadInitialData();
    }
}
