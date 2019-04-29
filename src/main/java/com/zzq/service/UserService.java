package com.zzq.service;

import com.zzq.error.BusinessException;
import com.zzq.service.model.OrderModel;
import com.zzq.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public interface UserService {
    //通过用户ID获取用户对象的方法
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
    UserModel validateLogin(String telphone, String password) throws BusinessException;
    //修改用户信息
    UserModel recomposeUserInfo(UserModel userModel) throws BusinessException;
    //查看用户订单
    List<OrderModel> orderInfo(Integer id);
    //用户信息列表
    List<UserModel> listUser();
}
