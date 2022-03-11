package com.example.bebenshop.entities;

import com.example.bebenshop.bases.BaseEntity;
import com.example.bebenshop.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class UserEntity extends BaseEntity {

    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(columnDefinition = "text")
    private String password;

    @Column(length = 50)
    private String email;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(columnDefinition = "text")
    private String avatar;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private GenderEnum gender;

    @ManyToMany
    private Collection<RoleEntity> roles;

    @OneToMany(targetEntity = DeviceEntity.class, mappedBy = "user")
    private Collection<DeviceEntity> devices;

    @OneToMany(targetEntity = OrderEntity.class, mappedBy = "user")
    private Collection<OrderEntity> orders;

    @OneToMany(targetEntity = ProductCommentEntity.class, mappedBy = "user")
    private Collection<ProductCommentEntity> productComments;

    @OneToOne(targetEntity = UserCodeEntity.class, mappedBy = "user")
    private UserCodeEntity userCodes;

}