package com.zzq.dao;

import com.zzq.dataobject.OrderDo;

public interface OrderDoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Mon Apr 01 13:36:17 CST 2019
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Mon Apr 01 13:36:17 CST 2019
     */
    int insert(OrderDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Mon Apr 01 13:36:17 CST 2019
     */
    int insertSelective(OrderDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Mon Apr 01 13:36:17 CST 2019
     */
    OrderDo selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Mon Apr 01 13:36:17 CST 2019
     */
    int updateByPrimaryKeySelective(OrderDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_info
     *
     * @mbg.generated Mon Apr 01 13:36:17 CST 2019
     */
    int updateByPrimaryKey(OrderDo record);
}