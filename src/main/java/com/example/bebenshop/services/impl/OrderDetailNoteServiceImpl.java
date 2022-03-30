package com.example.bebenshop.services.impl;

import com.example.bebenshop.dto.consumes.OrderDetailNoteConsumeDto;
import com.example.bebenshop.dto.produces.OrderDetailNoteProduceDto;
import com.example.bebenshop.entities.OrderDetailNoteEntity;
import com.example.bebenshop.exceptions.BadRequestException;
import com.example.bebenshop.exceptions.ForbiddenException;
import com.example.bebenshop.mapper.OrderDetailNoteMapper;
import com.example.bebenshop.repository.OrderDetailNoteRepository;
import com.example.bebenshop.services.OrderDetailNoteService;
import com.example.bebenshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDetailNoteServiceImpl implements OrderDetailNoteService {
    private final OrderDetailNoteRepository mOrderNoteRepository;
    private final OrderDetailNoteMapper mOrderNoteMapper;
    private final UserService mUserService;


    @Override
    public OrderDetailNoteProduceDto editOrderDetailNote(Long id, OrderDetailNoteConsumeDto orderNoteConsumeDto) {
        OrderDetailNoteEntity orderDetailNoteEntity = mOrderNoteRepository.findById(id).orElse(null);
        if (orderDetailNoteEntity == null) {
            throw new BadRequestException("Order note does not  exist");
        }
        if (!orderDetailNoteEntity.getCreatedBy().equals(mUserService.getUserName())) {
            throw new ForbiddenException("Forbidden");
        }
        orderDetailNoteEntity.setContent(orderNoteConsumeDto.toOrderNoteEntity().getContent());
        return mOrderNoteMapper.toOrderNoteProduceDto(mOrderNoteRepository.save(orderDetailNoteEntity));
    }
}
