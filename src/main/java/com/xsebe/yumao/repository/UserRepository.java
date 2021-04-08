package com.xsebe.yumao.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.xsebe.yumao.model.User;
import com.xsebe.yumao.model.UserFxServiceConfig;
import com.xsebe.yumao.model.UserRealnameAuth;

@Repository
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public interface UserRepository {

    User get(@Param("id") String id);
    /**
    * @Title: 通过用户名查询手机号
    * @Description: TODO 
    * @param id
    * @return User
    * @date 2018年8月13日上午9:46:11
     */
    User getByUsername(@Param("username")String username);

    User getByUsernameOrMobileOrEmail(@Param("username") String username, @Param("mobile") String mobile, @Param("email") String email);

    User getByUsernameOrEntUniCreditCode(@Param("username") String username, 
            @Param("entUniCreditCode") String entUniCreditCode);

    UserRealnameAuth getRealnameAuth(@Param("id") String id);

    UserFxServiceConfig getFxServiceConfig(@Param("id") String id);

    UserFxServiceConfig getFxServiceConfigByFxUserId(@Param("fxUserId") String fxUserId);

    User getByOriginalWorksId(@Param("originalWorksId") String originalWorksId);

    void add(@Param("user") User user);

    void addRealnameAuth(@Param("user_realname_auth") UserRealnameAuth userRealnameAuth);

    void addFxServiceConfig(@Param("user_fxservice_config") UserFxServiceConfig userFxServiceConfig);
    void updateFxServiceConfigByUserId(@Param("user_fxservice_config") UserFxServiceConfig userFxServiceConfig);

    void updateRealnameAuth(@Param("userType") int userType, @Param("userRealnameAuth") UserRealnameAuth userRealnameAuth);
    
    void updateUserPass(@Param("user")User user);
    void updateMobile(@Param("user") User current, @Param("mobile") String mobile);
}