package com.example.bebenshop.services;

import com.example.bebenshop.dto.consumes.OrderNoteConsumeDto;
import com.example.bebenshop.dto.produces.OrderNoteProduceDto;

public interface OrderNoteService {
    OrderNoteProduceDto addOrderNote(Long id, OrderNoteConsumeDto orderNoteConsumeDto);
    OrderNoteProduceDto editOrderNote(Long id, OrderNoteConsumeDto orderNoteConsumeDto);
}
