package com.xsebe.yumao.service.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;
import com.xsebe.api.docking.faxin.fxservice.CardType;
import com.xsebe.api.docking.faxin.fxservice.EnterpriseIdType;
import com.xsebe.api.docking.faxin.fxservice.UserType;
import com.xsebe.api.docking.faxin.fxservice.response.UserIdAndPublicKeyResponse;
import com.xsebe.api.utility.DataUtility;
import com.xsebe.api.utility.IDUtility;
import com.xsebe.api.utility.ThrowableUtils;
import com.xsebe.yumao.exception.ShiroAccountNotFoundException;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.log.ErrorLogger;
import com.xsebe.yumao.log.InfoLogger;
import com.xsebe.yumao.model.User;
import com.xsebe.yumao.model.UserFxServiceConfig;
import com.xsebe.yumao.model.UserRealnameAuth;
import com.xsebe.yumao.repository.UserRepository;
import com.xsebe.yumao.service.CommonService;
import com.xsebe.yumao.service.FxServiceService;
import com.xsebe.yumao.service.UserService;
import com.xsebe.yumao.service.impl.CommonServiceImpl.SmsCategory;
import com.xsebe.yumao.utility.DataVerifyUtility;
import com.xsebe.yumao.utility.PasswordCipherUtility;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UserServiceImpl implements UserService {

    private static final Logger INFO_LOG = LogManager.getLogger(InfoLogger.class);
    private static final Logger ERROR_LOG = LogManager.getLogger(ErrorLogger.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommonService commonService;
    @Autowired
    private FxServiceService fxServiceService;

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public User getByUsernameOrMobileOrEmail(final String username, final String mobile, final String email) throws YumaoException {
        try {
            return userRepository.getByUsernameOrMobileOrEmail(username, mobile, email);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public User getByUsernameOrEntUniCreditCode(final String username, final String entUniCreditCode) throws YumaoException {
        try {
            return userRepository.getByUsernameOrEntUniCreditCode(username, entUniCreditCode);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public UserRealnameAuth getRealnameAuth(final User user) throws YumaoException {
        try {
            return userRepository.getRealnameAuth(user.getId());
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public UserFxServiceConfig getFxServiceConfigByFxUserId(final String fxUserId) throws YumaoException {
        try {
            return userRepository.getFxServiceConfigByFxUserId(fxUserId);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void register(final JsonObject sessionVerifycodeJSON, final String username, final String loginPassword, final String affirmLoginPassword, final int userType,
            final String mobile, final String entUniCreditCode, final String entContactsRealname, final String entContactsMobile, final String verifycode, final boolean agreement)
            throws YumaoException {
        // ????????????
        String _username = DataVerifyUtility.notEmptyRequired(username, true, 666, "[?????????]????????????");
        if (DataUtility.isNotBoundAt(_username.length(), 2, 30)) {// ??????username??????
            throw new YumaoException(666, "[?????????]?????????2-30?????????");
        }
        String _loginPassword = DataVerifyUtility.notEmptyRequired(loginPassword, false, 666, "[????????????]????????????");
        if (DataUtility.isNotBoundAt(_loginPassword.length(), 6, 18)) {// ??????password??????
            throw new YumaoException(666, "[????????????]?????????6-18?????????");
        }
        String _affirmLoginPassword = DataVerifyUtility.notEmptyRequired(affirmLoginPassword, false, 666, "[????????????]????????????");
        int _userType = (User.USER_TYPE_PERSONAL == userType || User.USER_TYPE_ENTERPRISE == userType) ? userType : User.USER_TYPE_PERSONAL;

        User user = new User();
        user.setId(IDUtility.createID());
        user.setUsername(_username);
        byte[] salt = PasswordCipherUtility.createSalt();
        user.setLoginPasswordCipher(Base64.encodeBase64String(PasswordCipherUtility.getLoginPasswordCipherBySalt(_loginPassword, salt)));
        user.setLoginPasswordSalt(Base64.encodeBase64String(salt));
        user.setUserType(userType);
        if (User.USER_TYPE_PERSONAL == _userType) {
            // ????????????
            String _mobile = DataVerifyUtility.notEmptyRequired(mobile, true, 666, "[????????????]????????????");
            String _verifycode = DataVerifyUtility.notEmptyRequired(verifycode, true, 666, "[?????????]????????????");

            // ????????????
            DataVerifyUtility.mobileRequired(_mobile, 666, "??????????????????[????????????]");

            // ???????????????
            User existedUser;
            try {
                existedUser = userRepository.getByUsernameOrMobileOrEmail(_username, _mobile, "");
            } catch (Throwable ex) {
                ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
                throw new YumaoException(666, "???????????????????????????", ex);
            }
            if (DataUtility.isNotNull(existedUser)) {
                if (_username.equalsIgnoreCase(existedUser.getUsername())) {
                    throw new YumaoException(666, "[?????????]????????????");
                }
                if (_mobile.equalsIgnoreCase(existedUser.getMobile())) {
                    throw new YumaoException(666, "[????????????]?????????");
                }
            }

            if (false == agreement) {
                throw new YumaoException(666, "????????????????????????");
            }

            // lowercase
            _username = _username.toLowerCase();
            _verifycode = _verifycode.toLowerCase();

            if (false == _loginPassword.equals(_affirmLoginPassword)) {
                throw new YumaoException(666, "[????????????]???[????????????]?????????");
            }

            commonService.checkVerifycode(sessionVerifycodeJSON, _mobile, _verifycode);

            user.setMobile(_mobile);
            user.setEmail(null);
        } else {
            // ????????????
            String _entUniCreditCode = DataVerifyUtility.notEmptyRequired(entUniCreditCode, true, 666, "[????????????????????????]????????????");
            String _entContactsRealname = DataVerifyUtility.notEmptyRequired(entContactsRealname, true, 666, "[???????????????????????????]????????????");
            String _entContactsMobile = DataVerifyUtility.notEmptyRequired(entContactsMobile, true, 666, "[???????????????????????????]????????????");

            String _verifycode = DataVerifyUtility.notEmptyRequired(verifycode, true, 666, "[?????????]????????????");

            // ????????????
            DataVerifyUtility.mobileRequired(_entContactsMobile, 666, "??????????????????[???????????????????????????]");

            // ???????????????
            User existedUser;
            try {
                existedUser = userRepository.getByUsernameOrEntUniCreditCode(_username, _entUniCreditCode);
            } catch (Throwable ex) {
                ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
                throw new YumaoException(666, "???????????????????????????", ex);
            }
            if (DataUtility.isNotNull(existedUser)) {
                if (_username.equalsIgnoreCase(existedUser.getUsername())) {
                    throw new YumaoException(666, "[?????????]????????????");
                }
                if (_entUniCreditCode.equalsIgnoreCase(existedUser.getEntUniCreditCode())) {
                    throw new YumaoException(666, "[????????????????????????]?????????");
                }
            }

            if (false == agreement) {
                throw new YumaoException(666, "????????????????????????");
            }

            // lowercase
            _username = _username.toLowerCase();
            _verifycode = _verifycode.toLowerCase();

            if (false == _loginPassword.equals(_affirmLoginPassword)) {
                throw new YumaoException(666, "[????????????]???[????????????]?????????");
            }

            commonService.checkVerifycode(sessionVerifycodeJSON, _entContactsMobile, _verifycode);

            user.setEntUniCreditCode(_entUniCreditCode);
            user.setEntContactsRealname(_entContactsRealname);
            user.setEntContactsMobile(_entContactsMobile);
        }
        user.setRegisteredTime(new Date());
        try {
            userRepository.add(user);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void login(final String account, final String loginPassword, final int userType) throws YumaoException {
        // empty
        String _account = DataVerifyUtility.notEmptyRequired(account, true, 666, "[??????]????????????");
        String _loginPassword = DataVerifyUtility.notEmptyRequired(loginPassword, false, 666, "[????????????]????????????");

        // lowercase
        _account = _account.toLowerCase();

        Subject currentUser = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setUsername(userType + "/" + _account);
        token.setPassword(_loginPassword.toCharArray());
        try {
            currentUser.login(token);
        } catch (ShiroAccountNotFoundException ex) {
            throw new YumaoException(666, "???????????????", ex);
        } catch (IncorrectCredentialsException ex) {
            throw new YumaoException(666, "?????????????????????", ex);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "????????????", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void logout() throws YumaoException {
        Subject currentUser = SecurityUtils.getSubject();

        currentUser.logout();
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public UserFxServiceConfig realnameAuthQyBeforeRegister(final User user, final String entName) throws YumaoException {
        String _entName = DataVerifyUtility.notEmptyRequired(entName, true, 666, "[????????????]????????????");

        UserIdAndPublicKeyResponse response;
        try {
            response = fxServiceService.register(user.getId(), UserType.enterprise, null, _entName, CardType.UNI_CREDIT_CODE, user.getEntUniCreditCode(),
                    user.getEntContactsRealname(), user.getEntContactsMobile(), user.getEntUniCreditCode());
        } catch (YumaoException ex) {
            if (111 != ex.getHappenCode()) {
                throw new YumaoException(666, "??????????????????????????????????????????");
            }
            UserFxServiceConfig userFxServiceConfig = userRepository.getFxServiceConfig(user.getId());
            if (DataUtility.isNull(userFxServiceConfig)) {
                throw new YumaoException(666, "??????????????????????????????????????????");
            }
            return userFxServiceConfig;
        }

        UserFxServiceConfig userFxServiceConfig = new UserFxServiceConfig();
        userFxServiceConfig.setId(IDUtility.createID());
        userFxServiceConfig.setUser(user);
        userFxServiceConfig.setFxUserId(response.getUserId());
        userFxServiceConfig.setFxUserPublicKey(response.getPublicKey());
        try {
            userRepository.addFxServiceConfig(userFxServiceConfig);
        } catch (Throwable ex) {
            try {
                userRepository.updateFxServiceConfigByUserId(userFxServiceConfig);
            } catch (Throwable ex1) {
                ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex1));
                throw new YumaoException(666, "???????????????????????????", ex1);
            }
        }
        return userFxServiceConfig;
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void realnameAuth(final User user, final String realname, final String idcard, final String bankCardNo, final String bankReservedMobile, final String entName,
            final String entBankName, final String entBankNoDictValue, final String entBankPublicAccountNo, final String entIdPicture,
            final UserFxServiceConfig qyUserFxServiceConfig) throws YumaoException {
        UserRealnameAuth userRealnameAuth;
        try {
            userRealnameAuth = userRepository.getRealnameAuth(user.getId());
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
        if (DataUtility.isNotNull(userRealnameAuth)) {
            if (UserRealnameAuth.STATUS_AUTH_SUCCESS == userRealnameAuth.getStatus()) {
                return;
            }
            if (UserRealnameAuth.STATUS_AUTH_FAILURE == userRealnameAuth.getStatus() || (UserRealnameAuth.STATUS_MODIFY_WAITING == userRealnameAuth.getStatus())) {
                ;
            } else if (User.USER_TYPE_ENTERPRISE == user.getUserType()) {
                // ???????????????
                if (UserRealnameAuth.STATUS_AUDIT_WAITING == userRealnameAuth.getStatus() || (UserRealnameAuth.STATUS_REMITTANCE_WAITING == userRealnameAuth.getStatus())
                        || (UserRealnameAuth.STATUS_REMITTANCE_SUCCESS == userRealnameAuth.getStatus())
                        || (UserRealnameAuth.STATUS_REMITTANCE_FAILURE == userRealnameAuth.getStatus())) {
                    throw new YumaoException(666, "????????????????????????");
                }
            }
        }

        UserIdAndPublicKeyResponse response;
        if (User.USER_TYPE_PERSONAL == user.getUserType()) {
            // ????????????
            String _realname = DataVerifyUtility.notEmptyRequired(realname, true, 666, "[????????????]????????????");
            String _idcard = DataVerifyUtility.notEmptyRequired(idcard, false, 666, "[????????????]????????????");
            String _bankCardNo = DataVerifyUtility.notEmptyRequired(bankCardNo, false, 666, "[????????????]????????????");
            String _bankReservedMobile = DataVerifyUtility.notEmptyRequired(bankReservedMobile, true, 666, "[????????????????????????]????????????");

            // ????????????
            DataVerifyUtility.matchesRequired(_realname, "^[\\u4e00-\\u9fa5]+(\\??[\\u4e00-\\u9fa5]+)?$", 666, "??????????????????[????????????]");
            DataVerifyUtility.idcardRequired(_idcard, 666, "??????????????????[????????????]");
            DataVerifyUtility.bankCardNoRequired(_bankCardNo, 666, "??????????????????[????????????]");
            DataVerifyUtility.mobileRequired(_bankReservedMobile, 666, "??????????????????[????????????????????????]");

            int status = UserRealnameAuth.STATUS_AUTH_SUCCESS;
            String failureCause = "";
            try {
                commonService.realnameAuth(user.getId(), realname, idcard, bankCardNo, _bankReservedMobile);

                // ????????????????????????????????????
                response = fxServiceService.register(user.getId(), UserType.personal, user.getMobile(), _realname, CardType.IDCARD, _idcard, null, null, null);
            } catch (YumaoException ex) {
                status = UserRealnameAuth.STATUS_AUTH_FAILURE;
                failureCause = ex.getHappenMessage();
                throw new YumaoException(666, ex.getHappenMessage());
            }

            UserFxServiceConfig userFxServiceConfig = new UserFxServiceConfig();
            userFxServiceConfig.setId(IDUtility.createID());
            userFxServiceConfig.setUser(user);
            userFxServiceConfig.setFxUserId(response.getUserId());
            userFxServiceConfig.setFxUserPublicKey(response.getPublicKey());
            try {
                userRepository.addFxServiceConfig(userFxServiceConfig);
            } catch (Throwable ex) {
                ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
                throw new YumaoException(666, "???????????????????????????", ex);
            }

            if (DataUtility.isNotNull(userRealnameAuth)) {
                userRealnameAuth.setRealname(_realname);
                userRealnameAuth.setIdcard(_idcard);
                userRealnameAuth.setBankCardNo(_bankCardNo);
                userRealnameAuth.setBankReservedMobile(_bankReservedMobile);
                userRealnameAuth.setStatus(status);
                userRealnameAuth.setFailureCause(failureCause);
                try {
                    userRepository.updateRealnameAuth(user.getUserType(), userRealnameAuth);
                } catch (Throwable ex) {
                    ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
                    throw new YumaoException(666, "???????????????????????????", ex);
                }
            } else {
                userRealnameAuth = new UserRealnameAuth();
                userRealnameAuth.setId(IDUtility.createID());
                userRealnameAuth.setUser(user);
                userRealnameAuth.setRealname(_realname);
                userRealnameAuth.setIdcard(_idcard);
                userRealnameAuth.setBankCardNo(_bankCardNo);
                userRealnameAuth.setBankReservedMobile(_bankReservedMobile);
                userRealnameAuth.setStatus(status);
                userRealnameAuth.setFailureCause(failureCause);
                try {
                    userRepository.addRealnameAuth(userRealnameAuth);
                } catch (Throwable ex) {
                    ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
                    throw new YumaoException(666, "???????????????????????????", ex);
                }
            }
        } else {
            // ????????????
            String _entName = DataVerifyUtility.notEmptyRequired(entName, true, 666, "[????????????]????????????");
            String _entBankName = DataVerifyUtility.notEmptyRequired(entBankName, true, 666, "[????????????]????????????");
            String _entBankNoDictValue = DataVerifyUtility.notEmptyRequired(entBankNoDictValue, true, 666, "[?????????????????????]?????????");
            String _entBankPublicAccountNo = DataVerifyUtility.notEmptyRequired(entBankPublicAccountNo, true, 666, "[??????????????????]????????????");

            // ????????????
            DataVerifyUtility.matchesRequired(_entName, "^[\\u4e00-\\u9fa5()??????]+$", 666, "??????????????????[????????????]");
            DataVerifyUtility.matchesRequired(_entBankName, "^[\\u4e00-\\u9fa5]+$", 666, "??????????????????[????????????]");
            DataVerifyUtility.bankCardNoRequired(_entBankPublicAccountNo, 666, "??????????????????[??????????????????]");

            EnterpriseIdType idType = EnterpriseIdType.uniCreditCode;
            CardType cardType = CardType.UNI_CREDIT_CODE;
            String idCode = user.getEntUniCreditCode();

            // ????????????????????????????????????
            // try {
            // response = fxServiceService.register(user.getId(),
            // UserType.enterprise, null, _entName, cardType, idCode,
            // user.getEntContactsRealname(),
            // user.getEntContactsMobile(), idCode);
            // } catch (YumaoException ex) {
            // if (111 != ex.getHappenCode()) {
            // throw new YumaoException(666, "??????????????????????????????????????????");
            // }
            // UserFxServiceConfig userFxServiceConfig =
            // userRepository.getFxServiceConfig(user.getId());
            // if (DataUtility.isNull(userFxServiceConfig)) {
            // throw new YumaoException(666, "??????????????????????????????????????????");
            // }
            // response = new UserIdAndPublicKeyResponse();
            // response.setPublicKey(userFxServiceConfig.getFxUserPublicKey());
            // response.setUserId(userFxServiceConfig.getFxUserId());
            // }
            //
            // UserFxServiceConfig userFxServiceConfig = new
            // UserFxServiceConfig();
            // userFxServiceConfig.setId(IDUtility.createID());
            // userFxServiceConfig.setUser(user);
            // userFxServiceConfig.setFxUserId(response.getUserId());
            // userFxServiceConfig.setFxUserPublicKey(response.getPublicKey());
            // try {
            // userRepository.addFxServiceConfig(userFxServiceConfig);
            // } catch (Throwable ex) {
            // ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            // throw new YumaoException(666, "???????????????????????????", ex);
            // }

            fxServiceService.enterpriseRealnameAuth(user.getId(), idType, idCode, qyUserFxServiceConfig.getFxUserId(), _entBankPublicAccountNo, _entName, _entBankName,
                    _entBankNoDictValue, entIdPicture);

            if (DataUtility.isNotNull(userRealnameAuth)) {
                userRealnameAuth.setEntName(_entName);
                userRealnameAuth.setEntBankName(_entBankName);
                userRealnameAuth.setEntBankNoDictValue(_entBankNoDictValue);
                userRealnameAuth.setEntBankPublicAccountNo(_entBankPublicAccountNo);
                userRealnameAuth.setEntIdPicture(entIdPicture);
                userRealnameAuth.setStatus(UserRealnameAuth.STATUS_AUDIT_WAITING);
                userRealnameAuth.setFailureCause("");
                try {
                    userRepository.updateRealnameAuth(user.getUserType(), userRealnameAuth);
                } catch (Throwable ex) {
                    ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
                    throw new YumaoException(666, "???????????????????????????", ex);
                }
            } else {
                userRealnameAuth = new UserRealnameAuth();
                userRealnameAuth.setId(IDUtility.createID());
                userRealnameAuth.setUser(user);
                userRealnameAuth.setEntName(_entName);
                userRealnameAuth.setEntBankName(_entBankName);
                userRealnameAuth.setEntBankNoDictValue(_entBankNoDictValue);
                userRealnameAuth.setEntBankPublicAccountNo(_entBankPublicAccountNo);
                userRealnameAuth.setEntIdPicture(entIdPicture);
                userRealnameAuth.setStatus(UserRealnameAuth.STATUS_AUDIT_WAITING);
                userRealnameAuth.setFailureCause("");
                try {
                    userRepository.addRealnameAuth(userRealnameAuth);
                } catch (Throwable ex) {
                    ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
                    throw new YumaoException(666, "???????????????????????????", ex);
                }
            }
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public User getByOriginalWorksId(final String originalWorksId) throws YumaoException {
        try {
            return userRepository.getByOriginalWorksId(originalWorksId);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void updateRealnameAuthEnterpriseAuditStatus(final String fxUserId, final int status, final String failureCause) throws YumaoException {
        UserFxServiceConfig ufsc;
        try {
            ufsc = userRepository.getFxServiceConfigByFxUserId(fxUserId);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
        User user = ufsc.getUser();
        UserRealnameAuth ura;
        try {
            ura = userRepository.getRealnameAuth(user.getId());
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
        if (DataUtility.isNull(ura)) {
            throw new YumaoException(666, "?????????????????????");
        }
        ura.setStatus(status);
        ura.setFailureCause(failureCause);
        try {
            userRepository.updateRealnameAuth(user.getUserType(), ura);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }

        if (User.USER_TYPE_ENTERPRISE == user.getUserType()) {
            SmsCategory category = null;
            if (UserRealnameAuth.STATUS_REMITTANCE_SUCCESS == status) {
                category = SmsCategory.REALNAME_AUTH_REMITTANCE_SUCCESS;
            } else if (UserRealnameAuth.STATUS_REMITTANCE_FAILURE == status) {
                category = SmsCategory.REALNAME_AUTH_REMITTANCE_FAILURE;
            }
            if (DataUtility.isNotNull(category)) {
                try {
                    String mobile = user.getEntContactsMobile();
                    commonService.sendSms(UUID.randomUUID().toString(), mobile, category, user.getUsername());
                } catch (YumaoException ex) {
                }
            }
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void realnameAuthValidate(final User user, final String vcode, final String amount) throws YumaoException {
        // 1. ????????????????????????
        UserRealnameAuth ura;
        try {
            ura = userRepository.getRealnameAuth(user.getId());
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
        if (DataUtility.isNull(ura)) {
            throw new YumaoException(666, "?????????????????????");
        }
        // 1.1. ?????????????????????????????????
        if (UserRealnameAuth.STATUS_AUTH_SUCCESS == ura.getStatus() || UserRealnameAuth.STATUS_AUTH_FAILURE == ura.getStatus()) {
            return;
        }
        // 1.2. ?????????????????????????????????
        if (UserRealnameAuth.STATUS_REMITTANCE_SUCCESS != ura.getStatus()) {
            throw new YumaoException(666, "?????????????????????");
        }

        // 2. ????????????????????????
        UserFxServiceConfig cfg;
        try {
            cfg = userRepository.getFxServiceConfig(user.getId());
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }

        int status = UserRealnameAuth.STATUS_AUTH_SUCCESS;
        String failureCause = "";

        // 3. ????????????????????????
        try {
            fxServiceService.enterpriseRealnameAuthValid(user.getId(), cfg.getFxUserId(), vcode, amount);
        } catch (YumaoException ex) {
            if (661 == ex.getHappenCode()) {
                status = UserRealnameAuth.STATUS_REMITTANCE_SUCCESS;
            } else {
                status = UserRealnameAuth.STATUS_AUTH_FAILURE;
                failureCause = ex.getHappenMessage();
            }
        }

        ura.setStatus(status);
        ura.setFailureCause(failureCause);
        // 4. ????????????????????????
        try {
            userRepository.updateRealnameAuth(user.getUserType(), ura);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public User getMobileByUsername(final String username, final int userType, final String businessId, final String... args) throws YumaoException {
        // ???????????????
        String _username = DataVerifyUtility.notEmptyRequired(username, true, 666, "??????????????????");
        User userMap;
        String mobile;
        // ???????????????????????????
        if (User.USER_TYPE_PERSONAL == userType) {
            userMap = getByUsernameOrMobileOrEmail(username, username, username);
            if (DataUtility.isNull(userMap)) {
                throw new YumaoException(666, "???????????????");
            }
            mobile = userMap.getMobile();
        } else if (User.USER_TYPE_ENTERPRISE == userType) {
            userMap = getByUsernameOrEntUniCreditCode(_username, _username);
            if (DataUtility.isNull(userMap)) {
                throw new YumaoException(666, "???????????????");
            }
            mobile = userMap.getEntContactsMobile();
        } else {
            throw new YumaoException(666, "?????????????????????");
        }
        commonService.sendSms(businessId, mobile, SmsCategory.VERIFYCODE, args);
        return userMap;
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void toUpdateUserPass(final JsonObject sessionVerifycodeJSON, final String account, final String verifycode, final int userType) throws YumaoException {
        String mobile;
        if (User.USER_TYPE_PERSONAL == userType) {
            User user = getByUsernameOrMobileOrEmail(account, account, account);
            if (DataUtility.isNull(user)) {
                throw new YumaoException(666, "??????????????????");
            }
            mobile = user.getMobile();
        } else if (User.USER_TYPE_ENTERPRISE == userType) {
            User user = getByUsernameOrEntUniCreditCode(account, account);
            if (DataUtility.isNull(user)) {
                throw new YumaoException(666, "??????????????????");
            }
            mobile = user.getEntContactsMobile();
        } else {
            throw new YumaoException(666, "?????????????????????");
        }
        commonService.checkVerifycode(sessionVerifycodeJSON, mobile, verifycode);
    }

    /*
     * ????????????
     */
    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void updateUserPassByIdOrUsernameOrMobile(final String id, final String username, final String mobile, final String updatePassword, final String affirmUpdatePassword)
            throws YumaoException {
        // ????????????
        String _updatePassword = DataVerifyUtility.notEmptyRequired(updatePassword, false, 666, "??????????????????");
        // ????????????
        if (DataUtility.isNotBoundAt(_updatePassword.length(), 6, 12)) {
            throw new YumaoException(666, "???????????????6-14?????????");
        }
        String _affirmUpdatePassword = DataVerifyUtility.notEmptyRequired(affirmUpdatePassword, false, 666, "????????????????????????");

        if (false == _updatePassword.equals(_affirmUpdatePassword)) {
            throw new YumaoException(666, "??????????????????????????????");
        }
        User user = new User();
        byte[] salt = PasswordCipherUtility.createSalt();
        user.setLoginPasswordCipher(Base64.encodeBase64String(PasswordCipherUtility.getLoginPasswordCipherBySalt(_updatePassword, salt)));
        user.setLoginPasswordSalt(Base64.encodeBase64String(salt));
        // ???????????????????????????
        user.setUsername(username);
        userRepository.updateUserPass(user);

    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void updateMobile(User current, String mobile) throws YumaoException {
        try {
            userRepository.updateMobile(current, mobile);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "???????????????????????????", ex);
        }
        if(current.getUserType() == User.USER_TYPE_PERSONAL){
            current.setMobile(mobile);
        } else {
            current.setEntContactsMobile(mobile);
        }
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principalCollection = subject.getPrincipals(); 
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection = 
                new SimplePrincipalCollection(current, realmName);
        subject.runAs(newPrincipalCollection);
    }
}
