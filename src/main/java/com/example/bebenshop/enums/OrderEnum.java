package com.example.bebenshop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderEnum {
    ORDER_PROCESSING,
    ORDER_ON_HOLD,
    ORDER_COMPLETED,
    ORDER_CANCELLED,
    ORDER_ADD_TO_CARD
}
