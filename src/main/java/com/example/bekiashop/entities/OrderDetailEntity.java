package com.example.bekiashop.entities;

import com.example.bekiashop.bases.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OrderDetailEntity extends BaseEntity {

    private Integer quantity;

    private BigDecimal price;

    private Integer discount;

    @ManyToOne(targetEntity = OrderEntity.class)
    @JoinColumn(columnDefinition = "order_id")
    private OrderEntity order;

    @ManyToOne(targetEntity = ProductEntity.class)
    @JoinColumn(columnDefinition = "product_id")
    private ProductEntity product;

    @OneToMany(targetEntity = OrderDetailNoteEntity.class, mappedBy = "orderDetail")
    private Collection<OrderDetailNoteEntity> orderDetailNotes;
}
