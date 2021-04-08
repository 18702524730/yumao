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
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public User getByUsernameOrEntUniCreditCode(final String username, final String entUniCreditCode) throws YumaoException {
        try {
            return userRepository.getByUsernameOrEntUniCreditCode(username, entUniCreditCode);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public UserRealnameAuth getRealnameAuth(final User user) throws YumaoException {
        try {
            return userRepository.getRealnameAuth(user.getId());
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public UserFxServiceConfig getFxServiceConfigByFxUserId(final String fxUserId) throws YumaoException {
        try {
            return userRepository.getFxServiceConfigByFxUserId(fxUserId);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void register(final JsonObject sessionVerifycodeJSON, final String username, final String loginPassword, final String affirmLoginPassword, final int userType,
            final String mobile, final String entUniCreditCode, final String entContactsRealname, final String entContactsMobile, final String verifycode, final boolean agreement)
            throws YumaoException {
        // 为空校验
        String _username = DataVerifyUtility.notEmptyRequired(username, true, 666, "[用户名]不能为空");
        if (DataUtility.isNotBoundAt(_username.length(), 2, 30)) {// 判断username长度
            throw new YumaoException(666, "[用户名]长度为2-30位字符");
        }
        String _loginPassword = DataVerifyUtility.notEmptyRequired(loginPassword, false, 666, "[登录密码]不能为空");
        if (DataUtility.isNotBoundAt(_loginPassword.length(), 6, 18)) {// 判断password长度
            throw new YumaoException(666, "[登录密码]长度为6-18位字符");
        }
        String _affirmLoginPassword = DataVerifyUtility.notEmptyRequired(affirmLoginPassword, false, 666, "[确认密码]不能为空");
        int _userType = (User.USER_TYPE_PERSONAL == userType || User.USER_TYPE_ENTERPRISE == userType) ? userType : User.USER_TYPE_PERSONAL;

        User user = new User();
        user.setId(IDUtility.createID());
        user.setUsername(_username);
        byte[] salt = PasswordCipherUtility.createSalt();
        user.setLoginPasswordCipher(Base64.encodeBase64String(PasswordCipherUtility.getLoginPasswordCipherBySalt(_loginPassword, salt)));
        user.setLoginPasswordSalt(Base64.encodeBase64String(salt));
        user.setUserType(userType);
        if (User.USER_TYPE_PERSONAL == _userType) {
            // 个人流程
            String _mobile = DataVerifyUtility.notEmptyRequired(mobile, true, 666, "[手机号码]不能为空");
            String _verifycode = DataVerifyUtility.notEmptyRequired(verifycode, true, 666, "[验证码]不能为空");

            // 格式校验
            DataVerifyUtility.mobileRequired(_mobile, 666, "请填写正确的[手机号码]");

            // 账户已存在
            User existedUser;
            try {
                existedUser = userRepository.getByUsernameOrMobileOrEmail(_username, _mobile, "");
            } catch (Throwable ex) {
                ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
                throw new YumaoException(666, "服务忙，请稍候再试", ex);
            }
            if (DataUtility.isNotNull(existedUser)) {
                if (_username.equalsIgnoreCase(existedUser.getUsername())) {
                    throw new YumaoException(666, "[用户名]已被占用");
                }
                if (_mobile.equalsIgnoreCase(existedUser.getMobile())) {
                    throw new YumaoException(666, "[手机号码]已存在");
                }
            }

            if (false == agreement) {
                throw new YumaoException(666, "请先同意注册协议");
            }

            // lowercase
            _username = _username.toLowerCase();
            _verifycode = _verifycode.toLowerCase();

            if (false == _loginPassword.equals(_affirmLoginPassword)) {
                throw new YumaoException(666, "[确认密码]和[登录密码]不一致");
            }

            commonService.checkVerifycode(sessionVerifycodeJSON, _mobile, _verifycode);

            user.setMobile(_mobile);
            user.setEmail(null);
        } else {
            // 企业流程
            String _entUniCreditCode = DataVerifyUtility.notEmptyRequired(entUniCreditCode, true, 666, "[统一社会信用代码]不能为空");
            String _entContactsRealname = DataVerifyUtility.notEmptyRequired(entContactsRealname, true, 666, "[企业联系人真实姓名]不能为空");
            String _entContactsMobile = DataVerifyUtility.notEmptyRequired(entContactsMobile, true, 666, "[企业联系人手机号码]不能为空");

            String _verifycode = DataVerifyUtility.notEmptyRequired(verifycode, true, 666, "[验证码]不能为空");

            // 格式校验
            DataVerifyUtility.mobileRequired(_entContactsMobile, 666, "请填写正确的[企业联系人手机号码]");

            // 账户已存在
            User existedUser;
            try {
                existedUser = userRepository.getByUsernameOrEntUniCreditCode(_username, _entUniCreditCode);
            } catch (Throwable ex) {
                ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
                throw new YumaoException(666, "服务忙，请稍候再试", ex);
            }
            if (DataUtility.isNotNull(existedUser)) {
                if (_username.equalsIgnoreCase(existedUser.getUsername())) {
                    throw new YumaoException(666, "[用户名]已被占用");
                }
                if (_entUniCreditCode.equalsIgnoreCase(existedUser.getEntUniCreditCode())) {
                    throw new YumaoException(666, "[统一社会信用代码]已存在");
                }
            }

            if (false == agreement) {
                throw new YumaoException(666, "请先同意注册协议");
            }

            // lowercase
            _username = _username.toLowerCase();
            _verifycode = _verifycode.toLowerCase();

            if (false == _loginPassword.equals(_affirmLoginPassword)) {
                throw new YumaoException(666, "[确认密码]和[登录密码]不一致");
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
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void login(final String account, final String loginPassword, final int userType) throws YumaoException {
        // empty
        String _account = DataVerifyUtility.notEmptyRequired(account, true, 666, "[账号]不能为空");
        String _loginPassword = DataVerifyUtility.notEmptyRequired(loginPassword, false, 666, "[登录密码]不能为空");

        // lowercase
        _account = _account.toLowerCase();

        Subject currentUser = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setUsername(userType + "/" + _account);
        token.setPassword(_loginPassword.toCharArray());
        try {
            currentUser.login(token);
        } catch (ShiroAccountNotFoundException ex) {
            throw new YumaoException(666, "账号不存在", ex);
        } catch (IncorrectCredentialsException ex) {
            throw new YumaoException(666, "登录密码不正确", ex);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "登录失败", ex);
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
        String _entName = DataVerifyUtility.notEmptyRequired(entName, true, 666, "[企业名称]不能为空");

        UserIdAndPublicKeyResponse response;
        try {
            response = fxServiceService.register(user.getId(), UserType.enterprise, null, _entName, CardType.UNI_CREDIT_CODE, user.getEntUniCreditCode(),
                    user.getEntContactsRealname(), user.getEntContactsMobile(), user.getEntUniCreditCode());
        } catch (YumaoException ex) {
            if (111 != ex.getHappenCode()) {
                throw new YumaoException(666, "无法完成实名操作，请稍候再试");
            }
            UserFxServiceConfig userFxServiceConfig = userRepository.getFxServiceConfig(user.getId());
            if (DataUtility.isNull(userFxServiceConfig)) {
                throw new YumaoException(666, "无法完成实名操作，请稍候再试");
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
                throw new YumaoException(666, "服务忙，请稍候再试", ex1);
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
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
        if (DataUtility.isNotNull(userRealnameAuth)) {
            if (UserRealnameAuth.STATUS_AUTH_SUCCESS == userRealnameAuth.getStatus()) {
                return;
            }
            if (UserRealnameAuth.STATUS_AUTH_FAILURE == userRealnameAuth.getStatus() || (UserRealnameAuth.STATUS_MODIFY_WAITING == userRealnameAuth.getStatus())) {
                ;
            } else if (User.USER_TYPE_ENTERPRISE == user.getUserType()) {
                // 审核相关的
                if (UserRealnameAuth.STATUS_AUDIT_WAITING == userRealnameAuth.getStatus() || (UserRealnameAuth.STATUS_REMITTANCE_WAITING == userRealnameAuth.getStatus())
                        || (UserRealnameAuth.STATUS_REMITTANCE_SUCCESS == userRealnameAuth.getStatus())
                        || (UserRealnameAuth.STATUS_REMITTANCE_FAILURE == userRealnameAuth.getStatus())) {
                    throw new YumaoException(666, "已经在走核实流程");
                }
            }
        }

        UserIdAndPublicKeyResponse response;
        if (User.USER_TYPE_PERSONAL == user.getUserType()) {
            // 为空校验
            String _realname = DataVerifyUtility.notEmptyRequired(realname, true, 666, "[真实姓名]不能为空");
            String _idcard = DataVerifyUtility.notEmptyRequired(idcard, false, 666, "[身份证号]不能为空");
            String _bankCardNo = DataVerifyUtility.notEmptyRequired(bankCardNo, false, 666, "[银行卡号]不能为空");
            String _bankReservedMobile = DataVerifyUtility.notEmptyRequired(bankReservedMobile, true, 666, "[银行预留手机号码]不能为空");

            // 格式校验
            DataVerifyUtility.matchesRequired(_realname, "^[\\u4e00-\\u9fa5]+(\\·[\\u4e00-\\u9fa5]+)?$", 666, "请填写正确的[真实姓名]");
            DataVerifyUtility.idcardRequired(_idcard, 666, "请填写正确的[身份证号]");
            DataVerifyUtility.bankCardNoRequired(_bankCardNo, 666, "请填写正确的[银行卡号]");
            DataVerifyUtility.mobileRequired(_bankReservedMobile, 666, "请填写正确的[银行预留手机号码]");

            int status = UserRealnameAuth.STATUS_AUTH_SUCCESS;
            String failureCause = "";
            try {
                commonService.realnameAuth(user.getId(), realname, idcard, bankCardNo, _bankReservedMobile);

                // 调用法信个人用户注册接口
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
                throw new YumaoException(666, "服务忙，请稍候再试", ex);
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
                    throw new YumaoException(666, "服务忙，请稍候再试", ex);
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
                    throw new YumaoException(666, "服务忙，请稍候再试", ex);
                }
            }
        } else {
            // 为空校验
            String _entName = DataVerifyUtility.notEmptyRequired(entName, true, 666, "[企业名称]不能为空");
            String _entBankName = DataVerifyUtility.notEmptyRequired(entBankName, true, 666, "[开户银行]不能为空");
            String _entBankNoDictValue = DataVerifyUtility.notEmptyRequired(entBankNoDictValue, true, 666, "[开户银行联行号]未选择");
            String _entBankPublicAccountNo = DataVerifyUtility.notEmptyRequired(entBankPublicAccountNo, true, 666, "[对公银行账号]不能为空");

            // 格式校验
            DataVerifyUtility.matchesRequired(_entName, "^[\\u4e00-\\u9fa5()（）]+$", 666, "请填写正确的[企业名称]");
            DataVerifyUtility.matchesRequired(_entBankName, "^[\\u4e00-\\u9fa5]+$", 666, "请填写正确的[开户银行]");
            DataVerifyUtility.bankCardNoRequired(_entBankPublicAccountNo, 666, "请填写正确的[对公银行账号]");

            EnterpriseIdType idType = EnterpriseIdType.uniCreditCode;
            CardType cardType = CardType.UNI_CREDIT_CODE;
            String idCode = user.getEntUniCreditCode();

            // 调用法信企业用户注册接口
            // try {
            // response = fxServiceService.register(user.getId(),
            // UserType.enterprise, null, _entName, cardType, idCode,
            // user.getEntContactsRealname(),
            // user.getEntContactsMobile(), idCode);
            // } catch (YumaoException ex) {
            // if (111 != ex.getHappenCode()) {
            // throw new YumaoException(666, "无法完成实名操作，请稍候再试");
            // }
            // UserFxServiceConfig userFxServiceConfig =
            // userRepository.getFxServiceConfig(user.getId());
            // if (DataUtility.isNull(userFxServiceConfig)) {
            // throw new YumaoException(666, "无法完成实名操作，请稍候再试");
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
            // throw new YumaoException(666, "服务忙，请稍候再试", ex);
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
                    throw new YumaoException(666, "服务忙，请稍候再试", ex);
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
                    throw new YumaoException(666, "服务忙，请稍候再试", ex);
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
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
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
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
        User user = ufsc.getUser();
        UserRealnameAuth ura;
        try {
            ura = userRepository.getRealnameAuth(user.getId());
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
        if (DataUtility.isNull(ura)) {
            throw new YumaoException(666, "未找到实名信息");
        }
        ura.setStatus(status);
        ura.setFailureCause(failureCause);
        try {
            userRepository.updateRealnameAuth(user.getUserType(), ura);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
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
        // 1. 用户实名信息查询
        UserRealnameAuth ura;
        try {
            ura = userRepository.getRealnameAuth(user.getId());
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
        if (DataUtility.isNull(ura)) {
            throw new YumaoException(666, "未找到实名信息");
        }
        // 1.1. 认证成功或失败直接通过
        if (UserRealnameAuth.STATUS_AUTH_SUCCESS == ura.getStatus() || UserRealnameAuth.STATUS_AUTH_FAILURE == ura.getStatus()) {
            return;
        }
        // 1.2. 审核状态提示流程过程中
        if (UserRealnameAuth.STATUS_REMITTANCE_SUCCESS != ura.getStatus()) {
            throw new YumaoException(666, "认证流程未完成");
        }

        // 2. 获取法信配置信息
        UserFxServiceConfig cfg;
        try {
            cfg = userRepository.getFxServiceConfig(user.getId());
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }

        int status = UserRealnameAuth.STATUS_AUTH_SUCCESS;
        String failureCause = "";

        // 3. 确认实名验证结果
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
        // 4. 存储实名认证结果
        try {
            userRepository.updateRealnameAuth(user.getUserType(), ura);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public User getMobileByUsername(final String username, final int userType, final String businessId, final String... args) throws YumaoException {
        // 判断为空性
        String _username = DataVerifyUtility.notEmptyRequired(username, true, 666, "账号不能为空");
        User userMap;
        String mobile;
        // 存在判断个人或企业
        if (User.USER_TYPE_PERSONAL == userType) {
            userMap = getByUsernameOrMobileOrEmail(username, username, username);
            if (DataUtility.isNull(userMap)) {
                throw new YumaoException(666, "账号不存在");
            }
            mobile = userMap.getMobile();
        } else if (User.USER_TYPE_ENTERPRISE == userType) {
            userMap = getByUsernameOrEntUniCreditCode(_username, _username);
            if (DataUtility.isNull(userMap)) {
                throw new YumaoException(666, "账号不存在");
            }
            mobile = userMap.getEntContactsMobile();
        } else {
            throw new YumaoException(666, "用户类型不支持");
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
                throw new YumaoException(666, "该用户不存在");
            }
            mobile = user.getMobile();
        } else if (User.USER_TYPE_ENTERPRISE == userType) {
            User user = getByUsernameOrEntUniCreditCode(account, account);
            if (DataUtility.isNull(user)) {
                throw new YumaoException(666, "该用户不存在");
            }
            mobile = user.getEntContactsMobile();
        } else {
            throw new YumaoException(666, "用户类型不支持");
        }
        commonService.checkVerifycode(sessionVerifycodeJSON, mobile, verifycode);
    }

    /*
     * 修改密码
     */
    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void updateUserPassByIdOrUsernameOrMobile(final String id, final String username, final String mobile, final String updatePassword, final String affirmUpdatePassword)
            throws YumaoException {
        // 非空验证
        String _updatePassword = DataVerifyUtility.notEmptyRequired(updatePassword, false, 666, "密码不能为空");
        // 判断长度
        if (DataUtility.isNotBoundAt(_updatePassword.length(), 6, 12)) {
            throw new YumaoException(666, "密码长度为6-14个字符");
        }
        String _affirmUpdatePassword = DataVerifyUtility.notEmptyRequired(affirmUpdatePassword, false, 666, "确认密码不能为空");

        if (false == _updatePassword.equals(_affirmUpdatePassword)) {
            throw new YumaoException(666, "密码和确认密码不一致");
        }
        User user = new User();
        byte[] salt = PasswordCipherUtility.createSalt();
        user.setLoginPasswordCipher(Base64.encodeBase64String(PasswordCipherUtility.getLoginPasswordCipherBySalt(_updatePassword, salt)));
        user.setLoginPasswordSalt(Base64.encodeBase64String(salt));
        // 通过用户名修改密码
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
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
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
