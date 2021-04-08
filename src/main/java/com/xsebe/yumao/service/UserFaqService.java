package com.xsebe.yumao.service;

import java.util.List;

import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.Pageable;
import com.xsebe.yumao.model.UserFaq;

/**
 * @Description:TODO
 * @date:2019年3月1日 下午3:51:38
 * @author:周伯通
 */
public interface UserFaqService {

    long total() throws YumaoException;

    UserFaq get(String id) throws YumaoException;

    List<UserFaq> list() throws YumaoException;

    Pageable<UserFaq> listPage(int pageNo, int pageSize) throws YumaoException;

}
