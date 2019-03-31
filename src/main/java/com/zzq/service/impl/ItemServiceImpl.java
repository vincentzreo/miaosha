package com.zzq.service.impl;

import com.alibaba.druid.sql.ast.expr.SQLCaseExpr;
import com.zzq.dao.ItemDoMapper;
import com.zzq.dao.ItemStockDoMapper;
import com.zzq.dataobject.ItemDo;
import com.zzq.dataobject.ItemStockDo;
import com.zzq.error.BusinessException;
import com.zzq.error.EmBusinessError;
import com.zzq.service.ItemService;
import com.zzq.service.model.ItemModel;
import com.zzq.validator.ValidationResult;
import com.zzq.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ValidatorImpl validator;
    @Autowired
    private ItemDoMapper itemDoMapper;
    @Autowired
    private ItemStockDoMapper itemStockDoMapper;

    private ItemDo convertItemDOFromItemModel(ItemModel itemModel){
        if (itemModel == null){
            return null;
        }
        ItemDo itemDo = new ItemDo();
        BeanUtils.copyProperties(itemModel,itemDo);
        itemDo.setPrice(itemModel.getPrice().doubleValue());
        return itemDo;

    }
    private ItemStockDo convertItemStockDOFromItemModel(ItemModel itemModel){
        if (itemModel == null){
            return null;
        }
        ItemStockDo itemStockDo = new ItemStockDo();
        itemStockDo.setItemId(itemModel.getId());
        itemStockDo.setSotck(itemModel.getStock());
        return itemStockDo;
    }

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        //校验入参
        ValidationResult result = validator.validate(itemModel);
        if (result.isHsaErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        //转化itemmodel->dataobject
        ItemDo itemDo = this.convertItemDOFromItemModel(itemModel);
        //写入数据库
        itemDoMapper.insertSelective(itemDo);
        itemModel.setId(itemDo.getId());
        ItemStockDo itemStockDo = this.convertItemStockDOFromItemModel(itemModel);
        itemStockDoMapper.insertSelective(itemStockDo);
        //返回创建完成的对象

        return this.getItemById(itemModel.getId());
    }

    @Override
    public List<ItemModel> listItem() {
        List<ItemDo> itemDoList = itemDoMapper.listItem();
        List<ItemModel> itemModelList = itemDoList.stream().map(itemDo -> {
            ItemStockDo itemStockDo = itemStockDoMapper.selectByItemId(itemDo.getId());
            ItemModel itemModel = this.convertModelFromDataObject(itemDo,itemStockDo);
            return itemModel;
        }).collect(Collectors.toList());
        return itemModelList;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDo itemDo = itemDoMapper.selectByPrimaryKey(id);
        if (itemDo == null){
            return null;
        }
        //操作获得库存数量
        ItemStockDo itemStockDo = itemStockDoMapper.selectByItemId(itemDo.getId());

        //将dataobject->model
        ItemModel itemModel = convertModelFromDataObject(itemDo,itemStockDo);
        return itemModel;
    }
    private ItemModel convertModelFromDataObject(ItemDo itemDo,ItemStockDo itemStockDo){
         ItemModel itemModel = new ItemModel();
         BeanUtils.copyProperties(itemDo,itemModel);
         itemModel.setPrice(new BigDecimal(itemDo.getPrice()));
         itemModel.setStock(itemStockDo.getSotck());
         return itemModel;
    }
}
