package com.zzq.dao;

import com.zzq.dataobject.ItemDo;

public interface ItemDoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Fri Mar 29 15:55:19 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Fri Mar 29 15:55:19 CST 2019
     */
    int insert(ItemDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Fri Mar 29 15:55:19 CST 2019
     */
    int insertSelective(ItemDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Fri Mar 29 15:55:19 CST 2019
     */
    ItemDo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Fri Mar 29 15:55:19 CST 2019
     */
    int updateByPrimaryKeySelective(ItemDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Fri Mar 29 15:55:19 CST 2019
     */
    int updateByPrimaryKey(ItemDo record);
}