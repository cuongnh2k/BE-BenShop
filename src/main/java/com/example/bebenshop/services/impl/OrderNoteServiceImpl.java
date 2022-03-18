package com.example.bebenshop.services.impl;

import com.example.bebenshop.entities.OrderNoteEntity;
import com.example.bebenshop.exceptions.BadRequestException;
import com.example.bebenshop.exceptions.ForbiddenException;
import com.example.bebenshop.mapper.OrderNoteMapper;
import com.example.bebenshop.repository.OrderNoteRepository;
import com.example.bebenshop.services.OrderNoteService;
import com.example.bebenshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderNoteServiceImpl implements OrderNoteService {
    private final OrderNoteRepository mOrderNoteRepository;
    private final OrderNoteMapper mOrderNoteMapper;
    private final UserService mUserService;

    @Override
    public void deleteOderNoteById(Long id) {
        OrderNoteEntity orderNoteEntity = mOrderNoteRepository.findById(id).orElse(null);
        if (orderNoteEntity == null) {
            throw new BadRequestException("Id " + id + " not does exits ");
        }
        if (orderNoteEntity.getOrder().getUser().getId() != mUserService.getCurrentUser().getId()) {
            throw new ForbiddenException(" not authorithor delete order note");
        }
        mOrderNoteRepository.deleteOrderNoteById(id);
    }
}
