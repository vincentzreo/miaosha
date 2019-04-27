package com.zzq.service;

import com.zzq.error.BusinessException;
import com.zzq.service.model.AdminModel;

public interface AdminService {
    void register(AdminModel adminModel) throws BusinessException;
    //登录
    AdminModel login(String username,String password) throws BusinessException;
}
