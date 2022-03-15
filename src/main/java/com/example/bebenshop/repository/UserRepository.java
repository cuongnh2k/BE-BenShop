package com.example.bebenshop.repository;

import com.example.bebenshop.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    UserEntity findByUsernameAndDeletedFlagFalse(String username);

    UserEntity findByEmail(String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
