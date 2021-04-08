package com.xsebe.yumao.service;

import com.google.gson.JsonObject;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.service.impl.CommonServiceImpl.SmsCategory;

public interface CommonService {

    void checkVerifycode(JsonObject sessionVerifycodeJSON, String mobile, String verifycode) throws YumaoException;

    void sendSms(String businessId, String mobile, SmsCategory category, String... args) throws YumaoException;

    void realnameAuth(String businessId, String realname, String idcard, String bankCardNo, String bankReservedMobile) throws YumaoException;
}
