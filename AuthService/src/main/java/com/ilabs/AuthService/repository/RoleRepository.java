package com.ilabs.AuthService.repository;

import com.ilabs.AuthService.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    @Query("select r from Role r where r.id = :id")
    Role findByCustomId(Integer id);

    Role findByName(String roleAdmin);
}