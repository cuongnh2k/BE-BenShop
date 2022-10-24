package com.example.bekiashop.services.impl;

import com.example.bekiashop.entities.RoleEntity;
import com.example.bekiashop.repository.RoleRepository;
import com.example.bekiashop.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository mRoleRepository;

    @Override
    public void create(RoleEntity roleEntity) {
        mRoleRepository.save(roleEntity);
    }
}
