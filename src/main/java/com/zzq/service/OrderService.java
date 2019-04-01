package com.zzq.service;

import com.zzq.service.model.OrderModel;

public interface OrderService {
    OrderModel createOrder(Integer userId,Integer itemId,Integer amount);
}
