package com.example.bebenshop.repository;

import com.example.bebenshop.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity , Long> {
    OrderEntity findByIdAndDeletedFlagFalse(Long id);
}
