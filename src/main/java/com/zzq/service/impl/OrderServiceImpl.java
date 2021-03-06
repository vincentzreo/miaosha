package com.zzq.service.impl;

import com.zzq.dao.OrderDoMapper;
import com.zzq.dao.SequenceDoMapper;
import com.zzq.dataobject.OrderDo;
import com.zzq.dataobject.SequenceDo;
import com.zzq.error.BusinessException;
import com.zzq.error.EmBusinessError;
import com.zzq.service.BookService;
import com.zzq.service.ItemService;
import com.zzq.service.OrderService;
import com.zzq.service.UserService;
import com.zzq.service.model.BookModel;
import com.zzq.service.model.ItemModel;
import com.zzq.service.model.OrderModel;
import com.zzq.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ItemService itemService;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDoMapper orderDoMapper;
    @Autowired
    private SequenceDoMapper sequenceDoMapper;
    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer bookId, Integer amount) throws BusinessException {
        //1.校验下单状态，商品是否存在，用户是否合法，购买数量是否正确
        /*ItemModel itemModel = itemService.getItemById(itemId);*/
        BookModel bookModel = bookService.getBookById(bookId);
        if (bookId == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品信息不存在");
        }
        UserModel userModel = userService.getUserById(userId);
        if (userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户信息不存在");
        }
        if (amount <= 0 || amount >99){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"数量信息不正确");
        }




        //2.落单减库存,支付减库存
       /* boolean result = itemService.decreaseStock(itemId,amount);*/
        boolean result = bookService.decreaseStock(bookId,amount);
        if (!result){
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }
        //3.订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(bookId);
        orderModel.setAmount(amount);
        orderModel.setItemPrice(bookModel.getPrice());
        orderModel.setOrderPrice(bookModel.getPrice().multiply(new BigDecimal(amount)));

        //生成交易流水号，订单号
        orderModel.setId(generateOrderNo());
        OrderDo orderDo = convertFromOrderModel(orderModel);
        orderDoMapper.insertSelective(orderDo);
        //怎加商品的销量
       /* itemService.increaseSales(itemId,amount);*/
        bookService.increaseSales(bookId,amount);
        //4.返回前端

        return orderModel;
    }

    @Override
    public void deleteOrder(String id) {
        OrderDo orderDo = orderDoMapper.selectByPrimaryKey(id);
        if (orderDo == null){
            return;
        }
        orderDoMapper.deleteByPrimaryKey(id);


    }

    @Override
    public List<OrderModel> listOrder() {
        List<OrderDo> orderDoList = orderDoMapper.listOrder();
        List<OrderModel> orderModelList = orderDoList.stream().map(orderDo -> {
            OrderModel orderModel = this.convertFromOrderDo(orderDo);
            return orderModel;
        }).collect(Collectors.toList());
        return orderModelList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private String generateOrderNo(){
        //订单号有16位
        StringBuilder stringBuilder = new StringBuilder();
        //前8位为时间信息，年月日
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        stringBuilder.append(nowDate);
        //中间6位为自增序列
        //获取当前sequence
        int sequence = 0;
        SequenceDo sequenceDo = sequenceDoMapper.getSequenceByName("order_info");
        sequence = sequenceDo.getCurrentValue();
        sequenceDo.setCurrentValue(sequenceDo.getCurrentValue() + sequenceDo.getStep());
        sequenceDoMapper.updateByPrimaryKeySelective(sequenceDo);

        String sequencestr = String.valueOf(sequence);
        for (int i = 0 ; i < 6 - sequencestr.length() ; i ++){
            stringBuilder.append(0);
        }
        stringBuilder.append(sequencestr);

        //最后两位为分库分表位
        stringBuilder.append("00");
        return stringBuilder.toString();
    }
    private OrderDo convertFromOrderModel(OrderModel orderModel){
        if (orderModel == null){
            return null;
        }
        OrderDo orderDo = new OrderDo();
        BeanUtils.copyProperties(orderModel,orderDo);
        orderDo.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDo.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderDo;
    }
    private OrderModel convertFromOrderDo(OrderDo orderDo){
        if (orderDo == null){
            return null;
        }
        OrderModel orderModel = new OrderModel();
        BeanUtils.copyProperties(orderDo,orderModel);
        orderModel.setItemPrice(new BigDecimal(orderDo.getItemPrice()));
        orderModel.setOrderPrice(new BigDecimal(orderDo.getOrderPrice()));
        return orderModel;
    }
}
