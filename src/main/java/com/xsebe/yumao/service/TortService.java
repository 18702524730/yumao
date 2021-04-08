package com.xsebe.yumao.service;

import java.util.List;

import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.Pageable;
import com.xsebe.yumao.model.TortInfo;
import com.xsebe.yumao.model.TortLog;
import com.xsebe.yumao.model.User;

/**
 * @Description:TODO
 * @date:2019年2月15日 下午3:25:53
 * @author:周伯通
 */
public interface TortService {

    TortInfo getTortInfo(String id) throws YumaoException;

    Pageable<TortInfo> getTortInfoPage(String userId, int currentPageOffset, int pageSize, String name) throws YumaoException;
    
    List<TortInfo> getsByUserId(User user) throws YumaoException;
    
//    String getTortLog(String tortInfoId) throws YumaoException;
    
    List<TortLog> getTortLog(String tortInfoId) throws YumaoException;

    TortInfo addTortInfo(User user, String goodsUrl, List<String> originalWorksIds) throws YumaoException;

    TortInfo submitTortInfo(User user, String tortInfoId, String goodsUrl, List<String> originalWorksIds) throws YumaoException;

    void toWithdraw(String tortInfoId) throws YumaoException;

    void relevancyTortInfoAndOriginal(String tortInfoId, List<String> originalIds) throws YumaoException;

    TortInfo updateToryInfo(User user, String tortInfoId, String goodsUrl, List<String> originalWorksIds) throws YumaoException;

    void delTortInfoById(String tortInfoId) throws YumaoException;

    void addTortLog(String tortInfoId,int tortInfoStatus, String remark) throws YumaoException;
    
    byte[] filesLoad(String tortInfoId) throws YumaoException;

    long getTortTotalByUser(User current) throws YumaoException;
}
