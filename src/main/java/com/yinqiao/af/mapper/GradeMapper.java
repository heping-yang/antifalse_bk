package com.yinqiao.af.mapper;

import com.yinqiao.af.model.Grade;
import java.util.List;

public interface GradeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FJB_GRADE
     *
     * @mbg.generated Sun Jan 21 10:59:04 CST 2018
     */
    int deleteByPrimaryKey(Long gradeid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FJB_GRADE
     *
     * @mbg.generated Sun Jan 21 10:59:04 CST 2018
     */
    int insert(Grade record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FJB_GRADE
     *
     * @mbg.generated Sun Jan 21 10:59:04 CST 2018
     */
    Grade selectByPrimaryKey(Long gradeid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FJB_GRADE
     *
     * @mbg.generated Sun Jan 21 10:59:04 CST 2018
     */
    List<Grade> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FJB_GRADE
     *
     * @mbg.generated Sun Jan 21 10:59:04 CST 2018
     */
    int updateByPrimaryKey(Grade record);
    
    List<Grade> selectByIdcard(String idcard);
}