package com.example.bekiashop.repository;

import com.example.bekiashop.entities.RoleEntity;
import com.example.bekiashop.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Collection<RoleEntity> findByName(RoleEnum roleEnum);
}
