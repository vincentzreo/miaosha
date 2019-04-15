package com.zzq.controller;

import com.alibaba.druid.util.StringUtils;

import com.zzq.controller.viewobject.OrderVO;
import com.zzq.controller.viewobject.UserVO;
import com.zzq.dao.BookDoMapper;
import com.zzq.dao.UserDOMapper;
import com.zzq.dataobject.BookDo;
import com.zzq.dataobject.UserDO;
import com.zzq.error.BusinessException;
import com.zzq.error.EmBusinessError;
import com.zzq.response.CommonReturnType;
import com.zzq.service.UserService;
import com.zzq.service.model.OrderModel;
import com.zzq.service.model.UserModel;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private BookDoMapper bookDoMapper;

    //用户登录接口
    @RequestMapping(value = "/login",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "telphone")String telphone,
                                  @RequestParam(name = "password")String password) throws BusinessException {
        //入参校验
        if (org.apache.commons.lang3.StringUtils.isEmpty(telphone)||
                org.apache.commons.lang3.StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //用户登录服务,用来校验用户登录是否合法
        UserModel userModel = userService.validateLogin(telphone,password);
        //将登录凭证加入到用户登录成功的session内
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);
        return CommonReturnType.create(null);
    }
    //用户信息接口
    @RequestMapping(value = "/info",method = {RequestMethod.GET},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType userInfo() throws BusinessException {
        //获取用户的登录信息
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户未登录");
        }
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        UserVO userVO = conventFromModel(userModel);
        return CommonReturnType.create(userVO);
    }
    //获取用户订单详情
    @RequestMapping(value = "/orderinfo",method = {RequestMethod.GET},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType userorderInfo() throws BusinessException{
        //获取用户的登录信息
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户未登录");
        }
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");

        List<OrderModel> orderModelList = userService.orderInfo(userModel.getId());
        List<OrderVO> orderVOList = orderModelList.stream().map(orderModel -> {
            OrderVO orderVO = this.convertVOFromOrderModel(orderModel);
            return orderVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(orderVOList);
    }
    public OrderVO convertVOFromOrderModel(OrderModel orderModel){
        if (orderModel == null){
            return null;
        }
        OrderVO orderVO = new OrderVO();
        orderVO.setId(orderModel.getId());
        UserDO userDO = userDOMapper.selectByPrimaryKey(orderModel.getUserId());
        BookDo bookDo = bookDoMapper.selectByPrimaryKey(orderModel.getItemId());
        orderVO.setUserName(userDO.getName());
        orderVO.setBookName(bookDo.getTitle());
        orderVO.setPrice(orderModel.getOrderPrice());
        return orderVO;

    }

    //用户注册接口
    @RequestMapping(value = "/register",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "telphone") String telphone,
                                     @RequestParam(name = "otpCode") String otpCode,
                                     @RequestParam(name = "name") String name,
                                     @RequestParam(name = "gender") Integer gender,
                                     @RequestParam(name = "age") Integer age,
                                     @RequestParam(name = "password") String password) throws BusinessException {
        //验证手机号和对应的otpcode相符合
        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telphone);
        if (!StringUtils.equals(otpCode,inSessionOtpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合");
        }
        //用户的注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setAge(age);
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setTelphone(telphone);
        userModel.setRegisterMode("byphone");
        /*userModel.setEncrptPassword(MD5Encoder.encode(password.getBytes()));*/
        userModel.setEncrptPassword(password);
        userService.register(userModel);
        return CommonReturnType.create(null);
    }
    //用户信息修改
    @RequestMapping(value = "/recomposeinfo",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType recomposeInfo(@RequestParam(name = "name") String name,
                                          @RequestParam(name = "gender") Integer gender,
                                          @RequestParam(name = "age") Integer age,
                                          @RequestParam(name = "telphone") String telphone) throws BusinessException {
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setAge(age);
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setTelphone(telphone);
        UserModel userModel1 = userService.recomposeUserInfo(userModel);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel1);
        return CommonReturnType.create(userModel1);
    }

    //用户获取otp短信接口
    @RequestMapping(value = "/getotp",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telphone") String telphone){
        //需要按照一定的规则生成OTP验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt+=10000;
        String otpCode = String.valueOf(randomInt);

        //将OTP验证码同对应用户的手机号关联,使用httosession的方式绑定他的手机号与OTPCODE
        httpServletRequest.getSession().setAttribute(telphone,otpCode);

        //将OTP验证码通过短信通道发送给用户
        System.out.println("telphone = " + telphone + "& otpCode = " + otpCode);

        return CommonReturnType.create(null);
    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
        //调用service服务获取对应id的用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);
        //将核心领域模型用户对象转化为可供UI使用的viewobject
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        UserVO userVO = conventFromModel(userModel);
        //返回通用对象
        return CommonReturnType.create(userVO);
    }

    private UserVO conventFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }
}


