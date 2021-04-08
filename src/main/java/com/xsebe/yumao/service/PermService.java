package com.xsebe.yumao.service;

import java.util.List;

import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.Perm;

public interface PermService {

    List<Perm> getsByRoleId(String roleId) throws YumaoException;
}
