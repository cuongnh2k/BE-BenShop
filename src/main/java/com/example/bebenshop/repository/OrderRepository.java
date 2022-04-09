package com.example.bebenshop.repository;

import com.example.bebenshop.entities.OrderEntity;
import com.example.bebenshop.enums.OrderStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Page<OrderEntity> findByStatusAndUpdatedDateGreaterThanEqualAndUpdatedDateLessThanEqualAndUserId(
            OrderStatusEnum orderStatusEnum
            , LocalDateTime startTime
            , LocalDateTime endTime
            , Long userId
            , Pageable pageable);

    Page<OrderEntity> findByStatusAndUpdatedDateGreaterThanEqualAndUpdatedDateLessThanEqual(
            OrderStatusEnum orderStatusEnum
            , LocalDateTime startTime
            , LocalDateTime endTime
            , Pageable pageable);

    @Query(nativeQuery = true
            , value = "SELECT sum(od.price * (100 - od.discount) * od.quantity) totalRevenue FROM order_entity o " +
            "INNER JOIN order_detail_entity od " +
            "ON o.id = od.order_id " +
            "WHERE o.status = ?1 AND (o.updated_date BETWEEN ?2 AND ?3) ")
    Optional<Long> totalRevenue(String orderStatusEnum, LocalDateTime startTime, LocalDateTime endTime);
}
