package com.app.forum;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.forum.entity.Role;
import com.app.forum.entity.User;
import com.app.forum.exception.RoleNotInitializedException;
import com.app.forum.repository.RoleRepository;
import com.app.forum.repository.UserRepository;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
@EnableScheduling
public class ForumAppApplication {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ForumAppApplication.class, args);
	}
		
	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository, UserRepository userRepository) {
	    return args -> {
	        initializeRole(roleRepository, "USER");
	        initializeRole(roleRepository, "ROLE_ADMIN");

	        initializeAdminUser(userRepository, roleRepository);
	    };
	}

	private void initializeRole(RoleRepository roleRepository, String roleName) {
	    roleRepository.findByName(roleName).orElseGet(() -> {
	        Role newRole = Role.builder().name(roleName).build();
	        return roleRepository.save(newRole);
	    });
	}

	private void initializeAdminUser(UserRepository userRepository, RoleRepository roleRepository) {
	    if (userRepository.findByUsername("admin1").isEmpty()) {
	        Role userRole = roleRepository.findByName("USER")
	                .orElseThrow(() -> new RoleNotInitializedException("ROLE USER was not initialized"));

	        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
	                .orElseThrow(() -> new RoleNotInitializedException("ROLE ADMIN was not initialized"));

	        User adminUser = User.builder()
	                .username("admin1")
	                .email("admin1@test.com")
	                .displayName("admin1")
	                .dateOfBirth(LocalDate.now())
	                .password(passwordEncoder.encode("password"))
	                .accountLocked(false)
	                .enabled(true)
	                .roles(List.of(userRole, adminRole))
	                .build();

	        userRepository.save(adminUser);
	    }
	}

}
