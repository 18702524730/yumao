package com.xsebe.yumao.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.xsebe.api.utility.DataUtility;
import com.xsebe.api.utility.IDUtility;
import com.xsebe.api.utility.ThrowableUtils;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.log.ErrorLogger;
import com.xsebe.yumao.log.InfoLogger;
import com.xsebe.yumao.model.User;
import com.xsebe.yumao.model.UserProvideMessage;
import com.xsebe.yumao.repository.OpinionRepository;
import com.xsebe.yumao.service.OpinionService;
import com.xsebe.yumao.utility.DataVerifyUtility;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class OpinionServiceImpl implements OpinionService {

    private static final Logger INFO_LOG = LogManager.getLogger(InfoLogger.class);
    private static final Logger ERROR_LOG = LogManager.getLogger(ErrorLogger.class);

    @Autowired
    private OpinionRepository opinionRepository;

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void addOpinion(final User user, final String opinionTitle, final String opinionContent, final String opinionEmail, final String opinionTelphone)
            throws YumaoException {
        // 1、判断为空性
        String _opinionTitle = DataVerifyUtility.notEmptyRequired(opinionTitle, true, 666, "标题不能为空");// 理解：为true的话去掉字符串前后空格，并抛出异常
        String _opinionContent = DataVerifyUtility.notEmptyRequired(opinionContent, true, 666, "内容不能为空");
        String _opinionEmail = DataVerifyUtility.notEmptyNoRequired(opinionEmail, true);// 判断为空性，不抛异常
        String _opinionTelphone = DataVerifyUtility.notEmptyNoRequired(opinionTelphone, true);

        // 2、判断数据格式
        DataVerifyUtility.lengthAtBoundRequired(_opinionTitle, 1, 15, 666, "标题长度应在1-15之间");// 最大值最小值判断，抛异常
        DataVerifyUtility.lengthAtBoundRequired(_opinionContent, 1, 150, 666, "内容长度应在1-150之间");
        if (DataUtility.isNotEmptyString(_opinionEmail, true)) {// 如果不为null（判断是否为null）
            DataVerifyUtility.emailRequired(_opinionEmail, 555, "邮箱格式不正确");
        }
        if (DataUtility.isNotEmptyString(_opinionTelphone, true)) {
            DataVerifyUtility.fixAndMobilePequired(_opinionTelphone, 555, "联系方式格式不正确");
        }

        // 3、存数据
        UserProvideMessage userProvideMessage = new UserProvideMessage();
        userProvideMessage.setId(IDUtility.createID());
        userProvideMessage.setUserId(user.getId());
        userProvideMessage.setTitle(_opinionTitle);
        userProvideMessage.setContent(_opinionContent);
        userProvideMessage.setContantsEmail(_opinionEmail);
        userProvideMessage.setContantsTelphone(_opinionTelphone);
        try {
            opinionRepository.addUserProvideMessage(userProvideMessage);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }
}
