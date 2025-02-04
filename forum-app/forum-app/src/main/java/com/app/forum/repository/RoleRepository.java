package com.app.forum.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.forum.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	Optional<Role> findByName(String role);

}
