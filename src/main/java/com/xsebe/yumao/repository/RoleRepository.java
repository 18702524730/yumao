package com.xsebe.yumao.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.xsebe.yumao.model.Role;

@Repository
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public interface RoleRepository {

    List<Role> getsByUserId(@Param("userId") String userId);
}