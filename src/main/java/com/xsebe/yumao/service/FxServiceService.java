package com.xsebe.yumao.service;

import java.util.Date;
import java.util.List;

import com.google.gson.JsonObject;
import com.xsebe.api.docking.faxin.fxservice.CardType;
import com.xsebe.api.docking.faxin.fxservice.EnterpriseIdType;
import com.xsebe.api.docking.faxin.fxservice.UserType;
import com.xsebe.api.docking.faxin.fxservice.response.APP100050Response;
import com.xsebe.api.docking.faxin.fxservice.response.APP100070Response;
import com.xsebe.api.docking.faxin.fxservice.response.UserIdAndPublicKeyResponse;
import com.xsebe.yumao.exception.YumaoException;

public interface FxServiceService {

    UserIdAndPublicKeyResponse register(String businessId, UserType userType, String mobile, String name, CardType cardType, String cardNo,
            String enterpriseContactsName, String enterpriseContactsPhone, String enterpriseBusinessLicenseId) throws YumaoException;

    void enterpriseRealnameAuth(String businessId, EnterpriseIdType idType, String idCode, String userId, String inAcctNo, String inAcctName,
            String inAcctBankName, String inAcctBankNo, String idPic) throws YumaoException;

    void enterpriseRealnameAuthValid(String businessId, String userId, String validCode, String orderAmount) throws YumaoException;

    APP100050Response submitProtect(final String businessId, final String fxFileKey, final String fxUserId, final String fxUserPublicKey,
            final String username, final String realname, final String idcard, final UserType userType, final String worksId, final String worksName,
            final String worksCategory, final String fileUri, final String forFaxinFileUri, final String fileName, final Date creationTime)
            throws YumaoException;

    APP100070Response downloadVoucherImg(final String businessId, final List<String> evidIds) throws YumaoException;

    /** 
    * @Title: verify 
    * @Description: TODO 
    * @param httpRequestJSON
    * @param sign
    * @throws YumaoException void
    * @date 2018年8月15日下午3:37:42
    */
    void verify(JsonObject httpRequestJSON, String sign) throws YumaoException;
}
