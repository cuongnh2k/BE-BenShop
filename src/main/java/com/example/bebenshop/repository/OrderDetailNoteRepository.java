package com.example.bebenshop.repository;

import com.example.bebenshop.entities.OrderDetailNoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailNoteRepository extends JpaRepository<OrderDetailNoteEntity, Long> {
}
