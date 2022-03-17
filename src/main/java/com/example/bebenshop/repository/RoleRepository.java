package com.example.bebenshop.repository;

import com.example.bebenshop.entities.RoleEntity;
import com.example.bebenshop.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Collection<RoleEntity> findByName(RoleEnum roleEnum);
}
