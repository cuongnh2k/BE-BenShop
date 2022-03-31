package com.example.bebenshop.entities;

import com.example.bebenshop.bases.BaseEntity;
import com.example.bebenshop.enums.OrderStatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OrderEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OrderStatusEnum status;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(columnDefinition = "user_id")
    private UserEntity user;

    @OneToMany(targetEntity = OrderDetailEntity.class, mappedBy = "order")
    private Collection<OrderDetailEntity> orderDetails;
}
