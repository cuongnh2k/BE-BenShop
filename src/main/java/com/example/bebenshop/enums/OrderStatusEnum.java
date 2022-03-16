package com.example.bebenshop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatusEnum {
    IN_CART,
    PENDING,
    RESOLVED,
    COMPLETED,
    CANCELED
}
