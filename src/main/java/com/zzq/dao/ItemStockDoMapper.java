package com.zzq.dao;

import com.zzq.dataobject.ItemStockDo;
import org.apache.ibatis.annotations.Param;

public interface ItemStockDoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Fri Mar 29 15:55:19 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Fri Mar 29 15:55:19 CST 2019
     */
    int insert(ItemStockDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Fri Mar 29 15:55:19 CST 2019
     */
    int insertSelective(ItemStockDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Fri Mar 29 15:55:19 CST 2019
     */
    ItemStockDo selectByPrimaryKey(Integer id);

    ItemStockDo selectByItemId(Integer itemid);

    int decreaseStock(@Param("itemId") Integer itemId,@Param("amount") Integer amount);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Fri Mar 29 15:55:19 CST 2019
     */
    int updateByPrimaryKeySelective(ItemStockDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Fri Mar 29 15:55:19 CST 2019
     */
    int updateByPrimaryKey(ItemStockDo record);
}