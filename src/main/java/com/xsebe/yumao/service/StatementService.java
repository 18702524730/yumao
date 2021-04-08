package com.xsebe.yumao.service;

import java.util.List;

import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.Statement;
import com.xsebe.yumao.model.User;

public interface StatementService {

    Statement get(User loginUser, String id) throws YumaoException;
    
    List<Statement> getsByUserId(User loginUser, String userId) throws YumaoException;

    void add(User loginUser, User user, String stopName, String shopUrl) throws YumaoException;

    void updateShopUrl(User user, String url) throws YumaoException;
}
