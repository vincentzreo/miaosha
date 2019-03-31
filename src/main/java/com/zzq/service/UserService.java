package com.zzq.service;

import com.zzq.error.BusinessException;
import com.zzq.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;

public interface UserService {
    //通过用户ID获取用户对象的方法
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
    UserModel validateLogin(String telphone, String password) throws BusinessException;
}
