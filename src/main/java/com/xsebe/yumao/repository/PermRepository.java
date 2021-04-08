package com.xsebe.yumao.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.xsebe.yumao.model.Perm;

@Repository
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public interface PermRepository {

    List<Perm> getsByRoleId(@Param("roleId") String roleId);
}