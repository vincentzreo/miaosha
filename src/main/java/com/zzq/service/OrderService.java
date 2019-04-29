package com.zzq.service;

import com.zzq.error.BusinessException;
import com.zzq.service.model.OrderModel;

import java.util.List;

public interface OrderService {
    OrderModel createOrder(Integer userId,Integer itemId,Integer amount) throws BusinessException;
    void deleteOrder(String id);
    //向管理员展示所有的订单信息
    List<OrderModel> listOrder();
}
