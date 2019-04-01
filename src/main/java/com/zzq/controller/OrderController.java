package com.zzq.controller;

import com.zzq.error.BusinessException;
import com.zzq.response.CommonReturnType;
import com.zzq.service.OrderService;
import com.zzq.service.model.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("order")
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;
    //封装下单请求
    public CommonReturnType createOrde(@RequestParam(name = "itemId") Integer itemId,
                                       @RequestParam(name = "amount") Integer amount) throws BusinessException {
        OrderModel orderModel = orderService.createOrder(null,itemId,amount);
        return CommonReturnType.create(null);
    }
}
