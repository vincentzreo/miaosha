package com.zzq.service;

import com.zzq.error.BusinessException;
import com.zzq.service.model.OrderModel;

public interface OrderService {
    OrderModel createOrder(Integer userId,Integer itemId,Integer amount) throws BusinessException;
    void deleteOrder(String id);
}
