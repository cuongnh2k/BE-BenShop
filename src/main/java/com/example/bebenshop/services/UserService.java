package com.example.bebenshop.services;


import com.example.bebenshop.dto.consumes.UserConsumeDto;
import com.example.bebenshop.dto.produces.UserProduceDto;
import com.example.bebenshop.entities.UserEntity;

public interface UserService {

    void createAdmin(UserEntity userEntity);

    void createUser(UserEntity userEntity);

    String getUserName();

    Boolean isRoleAdmin();

    UserEntity getCurrentUser();

    UserProduceDto getUserDetail();

    UserProduceDto createRegister(UserConsumeDto userConsumeDto);
}
