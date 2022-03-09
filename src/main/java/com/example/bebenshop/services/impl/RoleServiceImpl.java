package com.example.bebenshop.services.impl;

import com.example.bebenshop.entities.RoleEntity;
import com.example.bebenshop.repository.RoleRepository;
import com.example.bebenshop.services.RoleService;
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
