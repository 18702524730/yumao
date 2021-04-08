package com.xsebe.yumao.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
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
import com.xsebe.yumao.model.Statement;
import com.xsebe.yumao.model.User;
import com.xsebe.yumao.repository.StatementRepository;
import com.xsebe.yumao.service.StatementService;
import com.xsebe.yumao.utility.DataVerifyUtility;
import com.xsebe.yumao.utility.ImageCreationUtility;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class StatementServiceImpl implements StatementService {

    private static final Logger INFO_LOG = LogManager.getLogger(InfoLogger.class);
    private static final Logger ERROR_LOG = LogManager.getLogger(ErrorLogger.class);

    @Autowired
    private StatementRepository statementRepository;

    @Override
    public Statement get(final User loginUser, final String id) throws YumaoException {
        try {
            return statementRepository.get(loginUser, id);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }
    
    @Override
    public List<Statement> getsByUserId(final User loginUser, final String userId) throws YumaoException {
        try {
            return statementRepository.getsByUserId(loginUser, userId);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }
    
    private synchronized String generateNo() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
        }
        
        String postfix = String.format("%03d".intern(), (int)(Math.random()*1000));
        return new SimpleDateFormat("yyyyMMddHHmmssSSS'".intern() + postfix + '\'').format(new Date());
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void add(final User loginUser, final User user, final String stopName, final String shopUrl) throws YumaoException {
        String _stopName = DataVerifyUtility.notEmptyRequired(stopName, true, 999, "请填写店铺名称");
        String _shopUrl = DataVerifyUtility.notEmptyRequired(shopUrl, true, 999, "请填写店铺链接");
        
        if (_stopName.length() > 10) {
            throw new YumaoException(999, "店铺名称最多10个字");
        }
        
        if (false == getsByUserId(loginUser, user.getId()).isEmpty()) {
            throw new YumaoException(999, "声明函已存在，无需重新提交");
        }
        
        String no = generateNo();
        String content = "所展示的原创内容已完成原创公证保管保护，一经发现盗用本店设计、图片、款式等原创内容，必将追究其法律责任。";
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            ImageCreationUtility.statementImageJpgOutput(no, _stopName, content, output);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(999, "暂时无法生成声明函", ex);
        } finally {
            try {
                output.close();
            } catch (IOException ex) {
            }
        }
        String fileName = "声明函-".intern() + no + ".jpg".intern();
        String fileContentHex = Hex.encodeHexString(output.toByteArray());
        
        Statement statement = new Statement();
        statement.setId(IDUtility.createID());
        statement.setUser(loginUser);
        statement.setStopName(_stopName);
        statement.setNo(no);
        statement.setFileName(fileName);
        statement.setFileContentHex(fileContentHex);
        statement.setCreatedTime(new Date());
        statement.setShopUrl(_shopUrl);
        try {
            statementRepository.add(loginUser, statement);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(999, "服务忙，请稍候再试", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void updateShopUrl(User user, String url) throws YumaoException {
        List<Statement> getsByUserId = getsByUserId(user, user.getId());
        if(DataUtility.isNull(getsByUserId) || getsByUserId.size() == 0){
            throw new YumaoException(500, "请先生成声明函");
        }
        String id = getsByUserId.get(0).getId();
        try {
            statementRepository.updateShopUrl(id, url);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(999, "服务忙，请稍候再试", ex);
        }
    }
}
