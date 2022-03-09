package com.example.bebenshop.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class ConvertUtil {

    public List<Long> toArray(String s) {
        List<Long> list = new ArrayList<>();
        String[] arr = s.split(",");
        for (String i : arr) {
            list.add(Long.parseLong(i));
        }
        return list;
    }

    public Pageable buildPageable(Integer page, Integer size, String orderString) {
        Sort sort = Sort.by(buildOrderList(orderString));
        return PageRequest.of(page, size, sort);
    }

    public List<Sort.Order> buildOrderList(String orderString) {
        List<Sort.Order> orders = new LinkedList<>();
        if (StringUtils.isBlank(orderString)) {
            orders.add(Sort.Order.desc("id"));
        } else {
            orderString = StringUtils.deleteWhitespace(orderString);
            if (orderString.contains(",")) {
                String[] orderArray = orderString.split(",");
                for (String orderChildString : orderArray) {
                    appendOrderList(orders, orderChildString);
                }
            } else {
                appendOrderList(orders, orderString);
            }
        }
        orders.forEach(Sort.Order::ignoreCase);
        return orders;
    }

    public void appendOrderList(List<Sort.Order> orders, String orderChildString) {
        if (orderChildString.contains(":")) {
            String[] orderChildArray = orderChildString.split(":");
            Optional<Sort.Direction> optionalDirection = Sort.Direction.fromOptionalString(orderChildArray[1]);
            if (optionalDirection.isPresent()) {
                switch (optionalDirection.get()) {
                    case ASC:
                        orders.add(Sort.Order.asc(orderChildArray[0]));
                        break;
                    case DESC:
                        orders.add(Sort.Order.desc(orderChildArray[0]));
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
