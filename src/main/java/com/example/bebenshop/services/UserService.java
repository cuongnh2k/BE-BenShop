package com.example.bebenshop.services;


import com.example.bebenshop.dto.consumes.UserConsumeDto;
import com.example.bebenshop.dto.produces.UserProduceDto;
import com.example.bebenshop.entities.UserEntity;

import javax.mail.MessagingException;
import java.util.HashMap;

public interface UserService {

    void createAdmin(UserEntity userEntity);

    String getUserName();

    Boolean isRoleAdmin();

    UserEntity getCurrentUser();

    UserProduceDto getUserDetail();

    UserProduceDto createRegister(UserConsumeDto userConsumeDto);

    UserProduceDto editById(HashMap<String, Object> map);

    void resetPassword(String username, String email) throws MessagingException;
}
