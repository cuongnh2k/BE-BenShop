package com.example.bebenshop.repository;

import com.example.bebenshop.entities.OrderEntity;
import com.example.bebenshop.enums.OrderStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity , Long> {
    OrderEntity findByIdAndDeletedFlagFalse(Long id);

    OrderEntity findByStatusAndUserIdAndDeletedFlagFalse(OrderStatusEnum orderStatusEnum, Long userId);


    @Modifying
    @Query(nativeQuery = true
            , value = "UPDATE order_entity SET deleted_flag = 1 WHERE `status` = ?1")
    void deleteOrder(String status);

    Page<OrderEntity> findByStatusAndUserIdAndDeletedFlagFalse(OrderStatusEnum orderStatusEnum, Long userId, Pageable pageable);
}
