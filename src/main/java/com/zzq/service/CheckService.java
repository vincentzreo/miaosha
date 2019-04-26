package com.zzq.service;

import com.zzq.error.BusinessException;
import com.zzq.service.model.CheckModel;
import com.zzq.service.model.UserModel;

public interface CheckService {
    //创建
    CheckModel createCheck(CheckModel checkModel, UserModel userModel) throws BusinessException;
}
