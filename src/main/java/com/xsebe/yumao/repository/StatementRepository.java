package com.xsebe.yumao.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.xsebe.yumao.model.Statement;
import com.xsebe.yumao.model.User;

@Repository
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public interface StatementRepository {

    Statement get(@Param("loginUser") User loginUser, @Param("id") String id);
    
    List<Statement> getsByUserId(@Param("loginUser") User loginUser, @Param("userId")String userId);
    
    int add(@Param("loginUser") User loginUser, @Param("statement") Statement statement);

    void updateShopUrl(@Param("id") String id, @Param("url") String url);
}