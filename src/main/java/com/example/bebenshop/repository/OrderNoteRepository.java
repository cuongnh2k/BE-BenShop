package com.example.bebenshop.repository;

import com.example.bebenshop.entities.OrderEntity;
import com.example.bebenshop.entities.OrderNoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderNoteRepository extends JpaRepository<OrderNoteEntity, Long> {

}
