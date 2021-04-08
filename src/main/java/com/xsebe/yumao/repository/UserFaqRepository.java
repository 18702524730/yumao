package com.xsebe.yumao.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xsebe.yumao.model.UserFaq;

/**
 * @Description:TODO
 * @date:2019年3月1日 下午3:44:45
 * @author:周伯通
 */
@Repository
public interface UserFaqRepository {

    long total();

    List<UserFaq> listPage(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    UserFaq get(@Param("id") String id);

    List<UserFaq> list();

}
