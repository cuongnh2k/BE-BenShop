package com.example.bekiashop.entities;

import com.example.bekiashop.bases.BaseEntity;
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
public class ProductCommentEntity extends BaseEntity {

    @Column(columnDefinition = "text")
    private String content;

    private Long parentId;

    @ManyToOne(targetEntity = ProductEntity.class)
    @JoinColumn(columnDefinition = "product_id")
    private ProductEntity product;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(columnDefinition = "user_id")
    private UserEntity user;
}
