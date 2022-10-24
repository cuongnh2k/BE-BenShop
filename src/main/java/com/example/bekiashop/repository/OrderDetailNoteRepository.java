package com.example.bekiashop.repository;

import com.example.bekiashop.entities.OrderDetailNoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailNoteRepository extends JpaRepository<OrderDetailNoteEntity, Long> {
}
