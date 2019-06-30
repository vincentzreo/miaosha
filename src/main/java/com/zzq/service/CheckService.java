package com.zzq.service;

import com.zzq.dataobject.CheckDo;
import com.zzq.error.BusinessException;
import com.zzq.service.model.CheckDetailModel;
import com.zzq.service.model.CheckModel;
import com.zzq.service.model.UserModel;

import java.util.List;

public interface CheckService {
    //创建
    CheckModel createCheck(CheckModel checkModel, UserModel userModel) throws BusinessException;
    //列表
    List<CheckDo> listCheck();
    void fahuo(Integer id);
    //根据单号获取所有的商品信息
    List<CheckDetailModel> get(Integer id);
    //根据单号获取订单情况
    CheckModel getcheck(Integer id);

}
