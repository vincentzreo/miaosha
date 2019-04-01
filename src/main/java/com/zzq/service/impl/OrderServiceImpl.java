package com.zzq.service.impl;

import com.zzq.service.OrderService;
import com.zzq.service.model.OrderModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount) {
        //1.校验下单状态，商品是否存在，用户是否合法，购买数量是否正确


        //2.落单减库存
        return null;
    }
}
