package com.example.bebenshop.entities;

import com.example.bebenshop.bases.BaseEntity;
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

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<RoleEntity> roles;

    @OneToMany(targetEntity = DeviceEntity.class, mappedBy = "user")
    private Collection<DeviceEntity> devices;
}