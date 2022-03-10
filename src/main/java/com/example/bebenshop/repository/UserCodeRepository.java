package com.example.bebenshop.repository;

import com.example.bebenshop.entities.UserCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCodeRepository extends JpaRepository<UserCodeEntity, Long> {
}
