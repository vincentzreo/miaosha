package com.zzq.service.impl;

import com.zzq.dao.AdminDoMapper;
import com.zzq.dataobject.AdminDo;
import com.zzq.error.BusinessException;
import com.zzq.error.EmBusinessError;
import com.zzq.service.AdminService;
import com.zzq.service.model.AdminModel;
import com.zzq.service.model.UserModel;
import com.zzq.validator.ValidationResult;
import com.zzq.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private ValidatorImpl validator;
    @Autowired
    private AdminDoMapper adminDoMapper;
    @Override
    @Transactional
    public void register(AdminModel adminModel) throws BusinessException {
        if (adminModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        ValidationResult result = validator.validate(adminModel);

        if (result.isHsaErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        AdminDo adminDo = this.convertFromAdminModel(adminModel);
        try{
            adminDoMapper.insertSelective(adminDo);
        }catch (DuplicateKeyException ex){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户名");
        }

    }

    @Override
    public AdminModel login(String username, String password) throws BusinessException {
        //通过账号获取用户信息
        AdminDo adminDo = adminDoMapper.selectByUserName(username);
        if (adminDo == null){
            throw new BusinessException(EmBusinessError.ADMIN_LOGIN_FAIL);
        }
        AdminModel adminModel = this.convertFromAdminDo(adminDo);
        //对比用户密码
        if (!StringUtils.equals(password,adminModel.getPassword())){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return adminModel;
    }

    private AdminDo convertFromAdminModel(AdminModel adminModel){
        AdminDo adminDo = new AdminDo();
        BeanUtils.copyProperties(adminModel,adminDo);
        return adminDo;
    }
    private AdminModel convertFromAdminDo(AdminDo adminDo){
        AdminModel adminModel = new AdminModel();
        BeanUtils.copyProperties(adminDo,adminModel);
        return adminModel;
    }
}
