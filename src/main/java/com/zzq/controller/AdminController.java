package com.zzq.controller;

import com.zzq.error.BusinessException;
import com.zzq.error.EmBusinessError;
import com.zzq.response.CommonReturnType;
import com.zzq.service.AdminService;
import com.zzq.service.model.AdminModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.zzq.controller.BaseController.CONTENT_TYPE_FROMED;

@Controller("admin")
@RequestMapping("/admin")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class AdminController {
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private AdminService adminService;
    //用户登录接口
    @RequestMapping(value = "/login",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "username")String username,
                                  @RequestParam(name = "password")String password) throws BusinessException{
        //入参校验
        if (org.apache.commons.lang3.StringUtils.isEmpty(username)||
                org.apache.commons.lang3.StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //用户登录服务,用来校验用户登录是否合法
        AdminModel adminModel = adminService.login(username,password);
        this.httpServletRequest.getSession().setAttribute("IS_LOGINA",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USERA",adminModel);
        return CommonReturnType.create(null);
    }
    //获取用户信息
    @RequestMapping(value = "/info",method = {RequestMethod.GET},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType info() throws BusinessException{
        //获取登录信息
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGINA");
        if (isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户未登录");
        }
        AdminModel adminModel = (AdminModel) httpServletRequest.getSession().getAttribute("LOGIN_USERA");
        return CommonReturnType.create(adminModel);
    }
    //用户注册接口
    @RequestMapping(value = "/register",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "fullname")String fullname,
                                     @RequestParam(name = "email")String email,
                                     @RequestParam(name = "address")String address,
                                     @RequestParam(name = "username")String username,
                                     @RequestParam(name = "password")String password) throws BusinessException {
        AdminModel adminModel = new AdminModel();
        adminModel.setFullName(fullname);
        adminModel.setEmail(email);
        adminModel.setAddress(address);
        adminModel.setUserName(username);
        adminModel.setPassword(password);
        adminService.register(adminModel);
        return CommonReturnType.create(null);
    }
}
