package com.zzq.dao;

import com.zzq.dataobject.CheckDo;

import java.util.List;

public interface CheckDoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table check_info
     *
     * @mbg.generated Mon Apr 29 18:11:37 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table check_info
     *
     * @mbg.generated Mon Apr 29 18:11:37 CST 2019
     */
    int insert(CheckDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table check_info
     *
     * @mbg.generated Mon Apr 29 18:11:37 CST 2019
     */
    int insertSelective(CheckDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table check_info
     *
     * @mbg.generated Mon Apr 29 18:11:37 CST 2019
     */
    CheckDo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table check_info
     *
     * @mbg.generated Mon Apr 29 18:11:37 CST 2019
     */
    int updateByPrimaryKeySelective(CheckDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table check_info
     *
     * @mbg.generated Mon Apr 29 18:11:37 CST 2019
     */
    int updateByPrimaryKey(CheckDo record);
    List<CheckDo> listCheck();
}