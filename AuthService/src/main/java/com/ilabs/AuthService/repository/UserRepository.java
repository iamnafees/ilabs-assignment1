package com.ilabs.AuthService.repository;

import com.ilabs.AuthService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("select u from User u where u.emailId=:emailId")
    Optional<User> findByEmail(String emailId);

    @Query("select u from User u join u.roles r where u.emailId=:emailId")
    User findByEmailId(String emailId);

    @Query(value = "SELECT * FROM user WHERE user.id =:userId", nativeQuery = true)
    User findByCustomId(Integer userId);

}