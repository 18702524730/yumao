package com.xsebe.yumao.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xsebe.yumao.model.UserProvideMessage;

/** 
 * @Description:意见反馈dao层 
 * @date:   2018年8月2日 下午6:04:30 
 * @ClassName:  周伯通 
 */
@Repository
public interface OpinionRepository {
    
    
    /** 
    * @Title: 用户提交（添加）意见反馈
    * @Description: TODO  void
    * @date 2018年8月2日下午6:11:39
    */
    void addUserProvideMessage(@Param("userProvideMessage") UserProvideMessage userProvideMessage);
}
