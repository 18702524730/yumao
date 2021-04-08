package com.xsebe.yumao.service;

import java.util.List;

import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.Role;

public interface RoleService {
    
    List<Role> getsByUserId(String userId) throws YumaoException;
    
//    Role get(String id) throws YumaoException;
//    
//    Pageable<Role> gets(int currentPageOffset, int pageSize) throws YumaoException;
//    
//    void add(String name, String label) throws YumaoException;
//    
//    void update(String id, String name, String label) throws YumaoException;
//    
//    void delete(String id) throws YumaoException;
//    
//    List<Map<String, Object>> getAllRoles(String userId,String label)throws YumaoException;
//    
//    void addUserRole(String userId, String roleId) throws YumaoException;
}
