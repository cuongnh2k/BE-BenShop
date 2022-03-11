package com.example.bebenshop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatusEnum {
    PENDING,
    RESOLVED,
    COMPLETED,
    CANCELED
}
