package com.xsebe.yumao.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.xsebe.api.docking.alibaba.alicloud.market.Bankcardverify4API;
import com.xsebe.api.docking.alibaba.alicloud.market.CodeNoticeAPI;
import com.xsebe.api.docking.alibaba.alicloud.market.excaption.BankcardNotCorrectException;
import com.xsebe.api.utility.DataUtility;
import com.xsebe.api.utility.JsonUtility;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.service.CommonService;
import com.xsebe.yumao.utility.DataVerifyUtility;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CommonServiceImpl implements CommonService {

    public enum SmsCategory {
        VERIFYCODE, REALNAME_AUTH_REMITTANCE_SUCCESS, REALNAME_AUTH_REMITTANCE_FAILURE, OPENSERVICE
    }

    @Value("#{sms.uri}")
    private String smsUri;
    @Value("#{sms.app_code}")
    private String smsAppCode;
    @Value("#{sms.verifycode_sign_id}")
    private String smsVerifycodeSignId;
    @Value("#{sms.verifycode_skin_id}")
    private String smsVerifycodeSkinId;
    @Value("#{sms.verifycode_expiry_minutes}")
    private int smsVerifycodeExpiryMinutes;
    @Value("#{sms.realname_auth_remittance_success_sign_id}")
    private String smsRealnameAuthRemittanceSuccessSignId;
    @Value("#{sms.realname_auth_remittance_success_skin_id}")
    private String smsRealnameAuthRemittanceSuccessSkinId;
    @Value("#{sms.realname_auth_remittance_failure_sign_id}")
    private String smsRealnameAuthRemittanceFailureSignId;
    @Value("#{sms.realname_auth_remittance_failure_skin_id}")
    private String smsRealnameAuthRemittanceFailureSkinId;
    @Value("#{sms.openservice_sign_id}")
    private String smsOpenServiceSignId;
    @Value("#{sms.openservice_skin_id}")
    private String smsOpenServiceSkinId;
    @Value("#{realnameAuth.uri}")
    private String realnameAuthUri;
    @Value("#{realnameAuth.app_code}")
    private String realnameAuthAppCode;

    @Override
    public void checkVerifycode(final JsonObject sessionVerifycodeJSON, final String mobile, final String verifycode) throws YumaoException {
        String sessionMobile = JsonUtility.getString("mobile", sessionVerifycodeJSON);
        String sessionVerifycode = JsonUtility.getString("verifycode", sessionVerifycodeJSON);
        long sessionCreateTimeMillis = JsonUtility.getLong("create_time_millis", sessionVerifycodeJSON);
        long currentTimeMillis = System.currentTimeMillis();

        // 有效期范围内，进行比对
        if (((currentTimeMillis - sessionCreateTimeMillis) / 1000F / 60) < smsVerifycodeExpiryMinutes) {
            if ((sessionMobile + "").equalsIgnoreCase(mobile) && (sessionVerifycode + "").equalsIgnoreCase(verifycode)) {
                return;
            }
            throw new YumaoException(666, "[验证码]不正确");
        }
        throw new YumaoException(666, "请先获取验证码");
    }

    private String getParams(final String... ss) {
        StringBuffer string = new StringBuffer();
        if (DataUtility.isNotNull(ss)) {
            for (String s : ss) {
                string.append('|');
                string.append(s);
            }
            if (string.length() > 0) {
                string.delete(0, 1);
            }
        }
        return string.toString();
    }

    @Override
    public void sendSms(final String businessId, final String mobile, final SmsCategory category, final String... args) throws YumaoException {
        // 为空校验
        String _mobile = DataVerifyUtility.notEmptyRequired(mobile, true, 666, "[手机号码]不能为空");

        // 格式校验
        DataVerifyUtility.mobileRequired(_mobile, 666, "请填写正确的[手机号码]");

        try {
            String skinId;
            String signId;
            switch (category) {
            case VERIFYCODE:
                skinId = smsVerifycodeSkinId;
                signId = smsVerifycodeSignId;
                break;
            case REALNAME_AUTH_REMITTANCE_SUCCESS:
                skinId = smsRealnameAuthRemittanceSuccessSkinId;
                signId = smsRealnameAuthRemittanceSuccessSignId;
                break;
            case REALNAME_AUTH_REMITTANCE_FAILURE:
                skinId = smsRealnameAuthRemittanceFailureSkinId;
                signId = smsRealnameAuthRemittanceFailureSignId;
                break;
            case OPENSERVICE:
                skinId = smsOpenServiceSkinId;
                signId = smsOpenServiceSignId;
                break;
            default:
                throw new YumaoException(666, "短信类别不支持");
            }
            CodeNoticeAPI.send(businessId, smsUri, smsAppCode, mobile, skinId, signId, getParams(args));
        } catch (Throwable ex) {
            throw new YumaoException(666, "短信发送失败，请联系管理员", ex);
        }
    }

    @Override
    public void realnameAuth(final String businessId, final String realname, final String idcard, final String bankCardNo, final String bankReservedMobile)
            throws YumaoException {
        try {
            if (false == Bankcardverify4API.verify(businessId, realnameAuthUri, realnameAuthAppCode, realname, idcard, bankCardNo, bankReservedMobile)) {
                throw new YumaoException(666, "实名验证不通过");
            }
        } catch (Throwable ex) {
            if (ex instanceof BankcardNotCorrectException) {
                throw new YumaoException(666, "[银行卡号]不正确");
            }
            throw new YumaoException(666, "实名验证失败", ex);
        }
    }
}
