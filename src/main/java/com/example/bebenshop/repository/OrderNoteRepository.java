package com.example.bebenshop.repository;

import com.example.bebenshop.entities.OrderNoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderNoteRepository extends JpaRepository<OrderNoteEntity, Long> {
    @Modifying
    @Query(nativeQuery = true
            , value = "DELETE FROM order_note_entity WHERE id = ?1 ")
    void deleteOrderNoteById(Long id);
}
