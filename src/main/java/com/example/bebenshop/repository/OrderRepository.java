package com.example.bebenshop.repository;

import com.example.bebenshop.entities.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query(nativeQuery = true
            , value = "SELECT * FROM order_entity " +
            "WHERE IF( ?1 != -1, status = ?1, ?1) " +
            "AND IF( ?2 != -1, id = ?2, ?2) " +
            "AND (updated_date BETWEEN ?3 AND ?4) ")
    Page<OrderEntity> searchOrderAdmin(
            String orderStatusEnum
            , Long orderId
            , LocalDateTime startTime
            , LocalDateTime endTime
            , Pageable pageable);


    @Query(nativeQuery = true
            , value = "SELECT * FROM order_entity " +
            "WHERE IF( ?1 != -1, status = ?1, ?1) " +
            "AND IF( ?2 != -1, id = ?2, ?2) " +
            "AND user_id = ?3 " +
            "AND (updated_date BETWEEN ?4 AND ?5) ")
    Page<OrderEntity> searchOrderUser(
            String orderStatusEnum
            , Long orderId
            , Long userId
            , LocalDateTime startTime
            , LocalDateTime endTime
            , Pageable pageable);


    @Query(nativeQuery = true
            , value = "SELECT sum(od.price * (100 - od.discount) * od.quantity) totalRevenue FROM order_entity o " +
            "INNER JOIN order_detail_entity od " +
            "ON o.id = od.order_id " +
            "WHERE IF( ?1 != -1, o.status = ?1, ?1) " +
            "AND IF( ?2 != -1, o.id = ?2, ?2) " +
            "AND (o.updated_date BETWEEN ?3 AND ?4) ")
    Optional<Long> totalRevenue(
            String orderStatusEnum
            , Long orderId
            , LocalDateTime startTime
            , LocalDateTime endTime);
}
