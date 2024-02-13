package com.prankishor.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prankishor.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
