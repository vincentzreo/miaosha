package com.zzq.controller;

import com.zzq.error.BusinessException;
import com.zzq.error.EmBusinessError;
import com.zzq.response.CommonReturnType;
import com.zzq.service.OrderService;
import com.zzq.service.model.OrderModel;
import com.zzq.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller("order")
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    //封装下单请求
    @RequestMapping(value = "/createorder",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType createOrde(@RequestParam(name = "itemId") Integer bookId,
                                       @RequestParam(name = "amount") Integer amount) throws BusinessException {
        //获取用户的登录信息
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户未登录,不能下单");
        }
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        OrderModel orderModel = orderService.createOrder(userModel.getId(),bookId,amount);
        return CommonReturnType.create(null);
    }
    @RequestMapping(value = "/deleteorder",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType deleteOrder(@RequestParam(name = "id")String id){
        orderService.deleteOrder(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping(value = "/list",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listOrder(){
        List<OrderModel> orderModelList = orderService.listOrder();
        return CommonReturnType.create(orderModelList);
    }
}
