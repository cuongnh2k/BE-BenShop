package com.example.bebenshop.services.impl;

import com.example.bebenshop.dto.consumes.OrderConsumeDto;
import com.example.bebenshop.dto.consumes.OrderNoteConsumeDto;
import com.example.bebenshop.dto.consumes.UserConsumeDto;
import com.example.bebenshop.dto.produces.OrderNoteProduceDto;
import com.example.bebenshop.entities.OrderEntity;
import com.example.bebenshop.entities.OrderNoteEntity;
import com.example.bebenshop.entities.UserEntity;
import com.example.bebenshop.enums.RoleEnum;
import com.example.bebenshop.exceptions.BadRequestException;
import com.example.bebenshop.mapper.OrderMapper;
import com.example.bebenshop.mapper.OrderNoteMapper;
import com.example.bebenshop.mapper.RoleMapper;
import com.example.bebenshop.repository.OrderNoteRepository;
import com.example.bebenshop.repository.OrderRepository;
import com.example.bebenshop.repository.RoleRepository;
import com.example.bebenshop.services.OrderNoteService;
import com.example.bebenshop.services.UserService;
import com.example.bebenshop.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.tree.RowMapper;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderNoteServiceImpl implements OrderNoteService {
    private final OrderNoteRepository mOrderNoteRepository;
    private final OrderRepository mOrderRepository;
    private final OrderNoteMapper mOrderNoteMapper;
    private final RowMapper rowMapper;
    private final UserService mUserService;
    private final RoleRepository roleRepository;
    @Override
    public OrderNoteProduceDto addOrderNote(Long id , OrderNoteConsumeDto orderNoteConsumeDto) {

        OrderEntity orderEntity = mOrderRepository.findByIdAndDeletedFlagFalse(id);
        if(orderEntity == null){
            throw new BadRequestException("No order exists: "+id);
        }
//        UserEntity userEntity = (UserEntity) roleRepository.findByName(RoleEnum.ROLE_USER);
//        orderEntity.setUser(userEntity);
//
        OrderNoteEntity orderNoteEntity = orderNoteConsumeDto.toOrderNoteEntity();

        orderNoteEntity.setOrder(orderEntity);
        mOrderNoteRepository.save(orderNoteEntity);
        return mOrderNoteMapper.toOrderNoteProduceDto(orderNoteEntity);
    }
}
