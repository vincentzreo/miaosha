package com.zzq.service.impl;

import com.zzq.dao.OrderDoMapper;
import com.zzq.dao.UserDOMapper;
import com.zzq.dao.UserPasswordDOMapper;
import com.zzq.dataobject.OrderDo;
import com.zzq.dataobject.UserDO;
import com.zzq.dataobject.UserPasswordDO;
import com.zzq.error.BusinessException;
import com.zzq.error.EmBusinessError;
import com.zzq.service.UserService;
import com.zzq.service.model.OrderModel;
import com.zzq.service.model.UserModel;
import com.zzq.validator.ValidationResult;
import com.zzq.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;
    @Autowired
    private ValidatorImpl validator;
    @Autowired
    private OrderDoMapper orderDoMapper;
    @Override
    public UserModel getUserById(Integer id) {
        //调用userdomapper获取到对应的用户dataobject
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if (userDO == null){
            return null;
        }
        //通过用户id获取对应的用户加密密码信息
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        return conventFromDataObhect(userDO,userPasswordDO);
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if (userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
/*        if (StringUtils.isEmpty(userModel.getName())
                || userModel.getGender() == null
                || userModel.getAge() == null
                || StringUtils.isEmpty(userModel.getTelphone())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }*/
        ValidationResult result = validator.validate(userModel);

        if (result.isHsaErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        /*UserDO userDO = new UserDO();*/
        //实现model->dataobject方法
        UserDO userDO = convertFromModel(userModel);
        try {
            userDOMapper.insertSelective(userDO);
        }catch (DuplicateKeyException ex){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"手机号重复");
        }

        userModel.setId(userDO.getId());
        UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
        return;
    }

    @Override
    public UserModel validateLogin(String telphone, String password) throws BusinessException {
        //通过用户的手机获取用户信息
        UserDO userDO = userDOMapper.selectByTelphone(telphone);
        if (userDO == null){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = conventFromDataObhect(userDO,userPasswordDO);
        //比对用户信息内的加密密码是否与传输进来的密码相匹配
        if (!StringUtils.equals(password,userModel.getEncrptPassword())){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    @Override
    public UserModel recomposeUserInfo(UserModel userModel) throws BusinessException {
        if (userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        UserDO userDO = userDOMapper.selectByTelphone(userModel.getTelphone());
        userDO.setGender(userModel.getGender());
        userDO.setAge(userModel.getAge());
        userDO.setName(userModel.getName());
        userDOMapper.updateByPrimaryKeySelective(userDO);
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel1 = conventFromDataObhect(userDO,userPasswordDO);
        return userModel1;
    }

    private UserPasswordDO convertPasswordFromModel(UserModel userModel){
        if (userModel == null){
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }
    private UserDO convertFromModel(UserModel userModel){
        if (userModel == null){
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        return userDO;
    }

    private UserModel conventFromDataObhect(UserDO userDO, UserPasswordDO userPasswordDO){
        if (userDO == null){
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        if (userPasswordDO!=null){
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }
        return userModel;
    }
    @Override
    public List<OrderModel> orderInfo(Integer id){
        List<OrderDo> orderDoList = orderDoMapper.selectByUserId(id);
        List<OrderModel> orderModels = orderDoList.stream().map(orderDo -> {
            OrderModel orderModel = convertFromDataObject(orderDo);
            return orderModel;
        }).collect(Collectors.toList());
        return orderModels;
    }
    private OrderModel convertFromDataObject(OrderDo orderDo){
        if (orderDo == null){
            return null;
        }
        OrderModel orderModel = new OrderModel();
        BeanUtils.copyProperties(orderDo,orderModel);
        orderModel.setOrderPrice(new BigDecimal(orderDo.getOrderPrice().doubleValue()));
        orderModel.setItemPrice(new BigDecimal(orderDo.getItemPrice()));
        return orderModel;
    }
}
