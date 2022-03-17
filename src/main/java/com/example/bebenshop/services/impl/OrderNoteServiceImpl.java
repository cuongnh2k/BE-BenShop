package com.example.bebenshop.services.impl;

import com.example.bebenshop.dto.consumes.OrderNoteConsumeDto;
import com.example.bebenshop.dto.produces.OrderNoteProduceDto;
import com.example.bebenshop.entities.OrderEntity;
import com.example.bebenshop.entities.OrderNoteEntity;
import com.example.bebenshop.entities.UserEntity;
import com.example.bebenshop.exceptions.BadRequestException;
import com.example.bebenshop.mapper.OrderMapper;
import com.example.bebenshop.mapper.OrderNoteMapper;
import com.example.bebenshop.repository.OrderNoteRepository;
import com.example.bebenshop.repository.OrderRepository;
import com.example.bebenshop.services.OrderNoteService;
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
    @Override
    public OrderNoteProduceDto addOrderNote(Long id , OrderNoteConsumeDto orderNoteConsumeDto) {

        OrderEntity orderEntity = mOrderRepository.findByIdAndDeletedFlagFalse(id);

        if(orderEntity == null){
            throw new BadRequestException("No order exists: "+id);
        }
        OrderNoteEntity orderNoteEntity = orderNoteConsumeDto.toOrderNoteEntity();
        orderNoteEntity.setOrder(orderEntity);
        mOrderNoteRepository.save(orderNoteEntity);
        return mOrderNoteMapper.toOrderNoteProduceDto(orderNoteEntity);
    }
}
