package com.example.bebenshop.entities;

import com.example.bebenshop.bases.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OrderNoteEntity extends BaseEntity {

    @Column(columnDefinition = "text")
    private String content;

    @ManyToOne(targetEntity = OrderEntity.class)
    @JoinColumn(columnDefinition = "order_id")
    private OrderEntity order;
}
