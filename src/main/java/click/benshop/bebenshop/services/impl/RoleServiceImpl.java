package click.benshop.bebenshop.services.impl;

import click.benshop.bebenshop.entities.RoleEntity;
import click.benshop.bebenshop.repository.RoleRepository;
import click.benshop.bebenshop.services.RoleService;
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
