package com.example.bebenshop.services;

import com.example.bebenshop.dto.consumes.OrderDetailNoteConsumeDto;
import com.example.bebenshop.dto.produces.OrderDetailNoteProduceDto;

public interface OrderDetailNoteService {

    void deleteOderNoteById(Long id);

//    OrderDetailNoteProduceDto addOrderNote(Long id, OrderDetailNoteConsumeDto orderNoteConsumeDto);

    OrderDetailNoteProduceDto editOrderNote(Long id, OrderDetailNoteConsumeDto orderNoteConsumeDto);
}
