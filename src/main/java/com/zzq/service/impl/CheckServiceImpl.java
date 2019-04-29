package com.zzq.service.impl;

import com.zzq.dao.CheckDetailDoMapper;
import com.zzq.dao.CheckDoMapper;
import com.zzq.dao.OrderDoMapper;
import com.zzq.dataobject.CheckDetailDo;
import com.zzq.dataobject.CheckDo;
import com.zzq.dataobject.OrderDo;
import com.zzq.error.BusinessException;
import com.zzq.error.EmBusinessError;
import com.zzq.service.CheckService;
import com.zzq.service.model.CheckDetailModel;
import com.zzq.service.model.CheckModel;
import com.zzq.service.model.UserModel;
import com.zzq.validator.ValidationResult;
import com.zzq.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckServiceImpl implements CheckService {
    @Autowired
    private ValidatorImpl validator;
    @Autowired
    private CheckDoMapper checkDoMapper;
    @Autowired
    private OrderDoMapper orderDoMapper;
    @Autowired
    private CheckDetailDoMapper checkDetailDoMapper;
    private CheckDo convertCheckDoFromCheckModel(CheckModel checkModel){
        if (checkModel == null){
            return null;
        }
        CheckDo checkDo = new CheckDo();
        BeanUtils.copyProperties(checkModel,checkDo);
        checkDo.setPrice(checkModel.getPrice().doubleValue());
        return checkDo;
    }
    @Override
    @Transactional
    public CheckModel createCheck(CheckModel checkModel, UserModel userModel) throws BusinessException {
        //校验入参
        ValidationResult result = validator.validate(checkModel);
        if (result.isHsaErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        CheckDo checkDo = this.convertCheckDoFromCheckModel(checkModel);
        checkDoMapper.insertSelective(checkDo);
        List<OrderDo> orderDoList = orderDoMapper.selectByUserId(userModel.getId());
        List<CheckDetailDo> checkDetailDoList = orderDoList.stream().map(orderDo -> {
            CheckDetailDo checkDetailDo = convertDOfromDO(orderDo);
            checkDetailDo.setInfoId(checkDo.getId());
            return checkDetailDo;
        }).collect(Collectors.toList());
        for (int i = 0 ; i < checkDetailDoList.size();i ++){
            checkDetailDoMapper.insertSelective(checkDetailDoList.get(i));
        }
        orderDoMapper.deleteByUserId(userModel.getId());
        return checkModel;
    }

    @Override
    public List<CheckDo> listCheck() {
        List<CheckDo> checkDoList = checkDoMapper.listCheck();
        return checkDoList;
    }

    @Override
    public void fahuo(Integer id) {
        CheckDo checkDo = checkDoMapper.selectByPrimaryKey(id);
        checkDo.setIsFahuo(1);
        checkDoMapper.updateByPrimaryKeySelective(checkDo);
    }

    @Override
    public List<CheckDetailModel> get(Integer id) {
        CheckDo checkDo = checkDoMapper.selectByPrimaryKey(id);
        return null;
    }

    private CheckDetailDo convertDOfromDO(OrderDo orderDo){
        CheckDetailDo checkDetailDo = new CheckDetailDo();
        BeanUtils.copyProperties(orderDo,checkDetailDo);
        return checkDetailDo;
    }
}
