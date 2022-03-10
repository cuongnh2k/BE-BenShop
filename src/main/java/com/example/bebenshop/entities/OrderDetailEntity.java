package com.example.bebenshop.entities;

import com.example.bebenshop.bases.BaseEntity;
import com.example.bebenshop.enums.OrderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

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
}
