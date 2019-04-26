package com.zzq.controller;

import com.zzq.error.BusinessException;
import com.zzq.error.EmBusinessError;
import com.zzq.response.CommonReturnType;
import com.zzq.service.CheckService;
import com.zzq.service.model.CheckModel;
import com.zzq.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

import static com.zzq.controller.BaseController.CONTENT_TYPE_FROMED;

@Controller("/check")
@RequestMapping("/check")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class CheckController {
    @Autowired
    private CheckService checkService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    //创建支付单
    @RequestMapping(value = "/create",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType createcheck(
                                        @RequestParam(name = "address")String address,
                                        @RequestParam(name = "price")BigDecimal price) throws BusinessException {
        //获取用户的登录信息
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户未登录");
        }
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        CheckModel checkModel = new CheckModel();
        checkModel.setUserId(userModel.getId());
        checkModel.setAddress(address);
        checkModel.setPrice(price);
        CheckModel checkModel1 = checkService.createCheck(checkModel,userModel);
        return CommonReturnType.create(checkModel1);
    }
}
