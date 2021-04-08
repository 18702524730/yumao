package com.xsebe.yumao.service;

import com.google.gson.JsonObject;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.User;
import com.xsebe.yumao.model.UserFxServiceConfig;
import com.xsebe.yumao.model.UserRealnameAuth;

public interface UserService {

    User getByUsernameOrMobileOrEmail(String username, String mobile, String email) throws YumaoException;

    /**
     * @Title: 通过用户名查询手机号
     * @Description: TODO
     * @param id
     * @return User
     * @date 2018年8月13日上午9:46:11
     */
    User getMobileByUsername(String username, int userType, String businessId, String... args) throws YumaoException;

    User getByUsernameOrEntUniCreditCode(String username, String entUniCreditCode) throws YumaoException;

    UserRealnameAuth getRealnameAuth(User user) throws YumaoException;

    UserFxServiceConfig getFxServiceConfigByFxUserId(String fxUserId) throws YumaoException;

    void register(JsonObject sessionVerifycodeJSON, String username, String loginPassword, String affirmLoginPassword, int userType, String mobile, String entUniCreditCode,
            String entContactsRealname, String entContactsMobile, String verifycode, boolean agreement) throws YumaoException;

    void login(String account, String loginPassword, int userType) throws YumaoException;

    void logout() throws YumaoException;

    UserFxServiceConfig realnameAuthQyBeforeRegister(User user, String entName) throws YumaoException;

    void realnameAuth(User user, String realname, String idcard, String bankCardNo, String bankReservedMobile, String entName, String entBankName, String entBankNo,
            String entBankPublicAccountNo, String entIdPicture, UserFxServiceConfig userFxServiceConfig) throws YumaoException;

    User getByOriginalWorksId(String originalWorksId) throws YumaoException;

    void updateRealnameAuthEnterpriseAuditStatus(String fxUserId, int status, String failureCause) throws YumaoException;

    void realnameAuthValidate(User user, String vcode, String amount) throws YumaoException;

    void updateUserPassByIdOrUsernameOrMobile(String id, String username, String mobile, String updatePassword, String affirmUpdatePassword) throws YumaoException;

    void toUpdateUserPass(JsonObject sessionVerifycodeJSON, String account, String verifycode, int userType) throws YumaoException;

    void updateMobile(User current, String mobile) throws YumaoException;

}
