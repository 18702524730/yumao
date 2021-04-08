package com.xsebe.yumao.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.xsebe.api.utility.DataUtility;
import com.xsebe.api.utility.IDUtility;
import com.xsebe.api.utility.PageUtility;
import com.xsebe.api.utility.ThrowableUtils;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.log.ErrorLogger;
import com.xsebe.yumao.log.InfoLogger;
import com.xsebe.yumao.model.OriginalWorks;
import com.xsebe.yumao.model.OriginalWorksFile;
import com.xsebe.yumao.model.Pageable;
import com.xsebe.yumao.model.TortInfo;
import com.xsebe.yumao.model.TortLog;
import com.xsebe.yumao.model.User;
import com.xsebe.yumao.repository.TortRepository;
import com.xsebe.yumao.service.OriginalWorksService;
import com.xsebe.yumao.service.TortService;
import com.xsebe.yumao.utility.DataVerifyUtility;
import com.xsebe.yumao.utility.ZipUtil;

/**
 * @Description:TODO
 * @date:2019年2月15日 下午3:28:50
 * @author:周伯通
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TortServiceImpl implements TortService {

    private static final Logger INFO_LOG = LogManager.getLogger(InfoLogger.class);
    private static final Logger ERROR_LOG = LogManager.getLogger(ErrorLogger.class);

    @Value("#{settings.upload_absolute_path}")
    private String uploadAbsolutePath;

    @Autowired
    private TortRepository repository;

    @Autowired
    private OriginalWorksService originalWorksService;

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public TortInfo addTortInfo(final User user, final String goodsUrl, final List<String> originalWorksIds) throws YumaoException {
        DataVerifyUtility.notEmptyRequired(goodsUrl, true, 666, "请输入商品链接");
        // 1.通过原创作品id得到作品名称
        String worksName = getWorksName(originalWorksIds);

        // 2.添加侵权信息
        TortInfo tortInfo = new TortInfo();
        tortInfo.setId(IDUtility.createID());
        tortInfo.setUser(user);
        tortInfo.setGoodsUrl(goodsUrl);
        tortInfo.setWorksName(worksName);
        tortInfo.setRightsProtectionStatus(TortInfo.RIGHTS_STATUS_TO_ACCEPT);
        try {
            repository.addTortInfo(tortInfo);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }

        // 3.关联侵权信息和原创证据
        relevancyTortInfoAndOriginal(tortInfo.getId(), originalWorksIds);

        return tortInfo;
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public Pageable<TortInfo> getTortInfoPage(String userId, int currentPageOffset, int pageSize, String name) throws YumaoException {
        long dataTotal;
        try {
            dataTotal = repository.getTortInfoTotal(userId, name);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
        List<TortInfo> currentPageList;
        try {
            currentPageList = repository.getsTortInfoLimit(userId, currentPageOffset, pageSize, name);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }

        Pageable<TortInfo> page = new Pageable<TortInfo>();
        page.setPageSize(pageSize);
        page.setPageTotal(PageUtility.computePageTotal(dataTotal, pageSize));
        page.setCurrentPageList(currentPageList);
        page.setDataTotal(dataTotal);
        return page;
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void toWithdraw(final String tortInfoId) throws YumaoException {
        // 1.撤回
        try {
            repository.updateTortInfoStatus(tortInfoId, TortInfo.RIGHTS_STATUS_RECALL);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
        // 2.记录日志
        addTortLog(tortInfoId, TortInfo.RIGHTS_STATUS_RECALL, null);
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void delTortInfoById(final String tortInfoId) throws YumaoException {
        // 1.删除与原创作品关联关系
        try {
            repository.delTortInfoAndOriginal(tortInfoId);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }

        // 2.删除维权日志
        try {
            repository.delTortInfoAndTortLog(tortInfoId);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }

        // 3.删除维权信息
        try {
            repository.delTortInfoById(tortInfoId);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public TortInfo getTortInfo(final String id) throws YumaoException {
        try {
            return repository.getTortInfo(id);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void relevancyTortInfoAndOriginal(final String tortInfoId, final List<String> originalIds) throws YumaoException {
        for (String originalWorksId : originalIds) {
            try {
                repository.addTortInfoAndOriginalWorks(tortInfoId, originalWorksId);
            } catch (Throwable ex) {
                ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
                throw new YumaoException(666, "服务忙，请稍候再试", ex);
            }

        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public TortInfo updateToryInfo(User user, String tortInfoId, String goodsUrl, List<String> originalWorksIds) throws YumaoException {
        DataVerifyUtility.notEmptyRequired(goodsUrl, true, 666, "请输入商品链接");
        // 1.通过原创作品id得到作品名称
        String worksName = getWorksName(originalWorksIds);

        // 2.删除侵权信息与原创作品关联
        try {
            repository.delTortInfoAndOriginal(tortInfoId);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }

        // 3.创建新的侵权信息与原创作品关联
        relevancyTortInfoAndOriginal(tortInfoId, originalWorksIds);

        // 4.修改原创作品
        TortInfo tortInfo = new TortInfo();
        tortInfo.setId(tortInfoId);
        tortInfo.setGoodsUrl(goodsUrl);
        tortInfo.setWorksName(worksName);
        tortInfo.setRightsProtectionStatus(TortInfo.RIGHTS_STATUS_TO_ACCEPT);
        try {
            repository.updateTortInfo(tortInfo);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
        return tortInfo;
    }

    public static void main(String[] args) {
        String goodsUrl = "https://item.taobao.com/item.htm?id=593208604003&ns=1&abbucket=17#detail";
        if (goodsUrl.indexOf("id=") > 0) {
            System.out.println(goodsUrl.indexOf("id="));
            String goodsId = goodsUrl.substring(goodsUrl.indexOf("id=") + 3, goodsUrl.indexOf("&") == -1 ? goodsUrl.length() - 1 : goodsUrl.indexOf("&"));
            goodsId.indexOf("", 1);
            System.out.println(goodsId);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public TortInfo submitTortInfo(User user, String tortInfoId, String goodsUrl, List<String> originalWorksIds) throws YumaoException {
        // 1 判断侵权链接是否合法
        DataVerifyUtility.notEmptyRequired(goodsUrl, true, 666, "侵权链接不能为空");
        // 判断侵权链接是否重复
        List<TortInfo> getsByGoodsUrl = repository.getsByGoodsUrl(user.getId(), goodsUrl);
        if (getsByGoodsUrl != null && getsByGoodsUrl.size() > 0) {
            throw new YumaoException(666, "该侵权链接已存在，请勿重复提交");
        }
        // 判断链接的商品是否存在(需求：淘宝下同一商品，链接可能不同，但是链接中有个参数id是相同的。所以通过id判断商品是否已经存在、提交过)
        List<TortInfo> getsByUserId = getsByUserId(user);
        String goodsId = null;
        if (goodsUrl.indexOf("id=") > 0) {
            int begin = goodsUrl.indexOf("id=") + 3;
            int end = goodsUrl.indexOf("&", begin) == -1 ? goodsUrl.length() - 1 : goodsUrl.indexOf("&", begin);
            goodsId = goodsUrl.substring(begin, end);
        }
        if (goodsId != null) {
            for (TortInfo tortInfo : getsByUserId) {
                if (tortInfo.getGoodsUrl().indexOf(goodsId) > 0) {
                    throw new YumaoException(666, "该商品已经存在，请勿重复提交");
                }
            }
        }
        // 2.维权信息超过5调不能提交(待受理、受理中、维权中)
        int[] status = { TortInfo.RIGHTS_STATUS_TO_ACCEPT, TortInfo.RIGHTS_STATUS_HAVE_ACCEPT, TortInfo.RIGHTS_STATUS_THE_RIGHTS_OF };
        List<TortInfo> listByStatus = null;
        try {
            listByStatus = repository.listByStatus(user.getId(), status);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
        if (listByStatus != null && listByStatus.size() >= 5) {
            throw new YumaoException(666, "频繁操作，维权中的信息最多同时存在5条");
        }
        // 3.添加或修改
        TortInfo tortInfo = null;
        if (DataUtility.isEmptyString(tortInfoId, true)) {
            tortInfo = addTortInfo(user, goodsUrl, originalWorksIds);
        } else {
            tortInfo = updateToryInfo(user, tortInfoId, goodsUrl, originalWorksIds);
        }

        return tortInfo;
    }

    private String getWorksName(List<String> originalWorksIds) throws YumaoException {
        StringBuffer worksName = new StringBuffer();
        List<OriginalWorks> list = originalWorksService.getList(originalWorksIds);
        for (OriginalWorks originalWorks : list) {
            worksName.append(',');
            worksName.append(originalWorks.getName());
        }
        if (worksName.length() > 0) {
            worksName.deleteCharAt(0);
        }
        return worksName.toString();
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class)
    @Override
    public void addTortLog(final String tortInfoId, final int tortInfoStatus, final String remark) throws YumaoException {
        DataVerifyUtility.notEmptyRequired(tortInfoId, true, 666, "服务忙，请稍候再试");

        TortInfo tortInfo = new TortInfo();
        tortInfo.setId(tortInfoId);
        TortLog tortLog = new TortLog();
        tortLog.setId(IDUtility.createID());
        tortLog.setTortInfo(tortInfo);
        tortLog.setTortInfoStatus(tortInfoStatus);
        tortLog.setRemark(remark);
        try {
            repository.addTortLog(tortLog);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public List<TortLog> getTortLog(String tortInfoId) throws YumaoException {
        try {
            return repository.getsTortLogList(tortInfoId);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }

    @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor = Throwable.class, readOnly = true)
    @Override
    public byte[] filesLoad(final String tortInfoId) throws YumaoException {
        ByteArrayOutputStream fout = null;
        try {
            fout = new ByteArrayOutputStream();
            List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
            List<OriginalWorksFile> listByTortInfoId = originalWorksService.listByTortInfoId(tortInfoId);
            Map<String, Object> map;
            for (OriginalWorksFile originalWorksFile : listByTortInfoId) {
                map = new HashMap<String, Object>();
                map.put("fileName", originalWorksFile.getName());
                map.put("fileBytes", getBytesByUrl(originalWorksFile.getUri()));
                fileList.add(map);
            }
            ZipUtil.compress(fileList, fout);
            return fout.toByteArray();
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        } finally {
            try {
                fout.close();
            } catch (Throwable ex) {
            }
        }
    }

    private byte[] getBytesByUrl(final String url) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(uploadAbsolutePath + url.replace("/", File.separator));
            IOUtils.copy(fis, baos);
        } catch (Throwable ex) {
        } finally {
            try {
                fis.close();
            } catch (Throwable ex) {
            }
            try {
                baos.close();
            } catch (Throwable ex) {
            }
        }

        return baos.toByteArray();
    }

    @Override
    public long getTortTotalByUser(User current) throws YumaoException {
        try {
            return repository.getTortInfoTotal(current.getId(), null);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }

    @Override
    public List<TortInfo> getsByUserId(final User user) throws YumaoException {
        try {
            return repository.listByStatus(user.getId(), null);
        } catch (Throwable ex) {
            ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
            throw new YumaoException(666, "服务忙，请稍候再试", ex);
        }
    }

    // @Transactional( /*isolation = Isolation.SERIALIZABLE,*/ rollbackFor =
    // Throwable.class, readOnly = true)
    // @Override
    // public String getTortLog(String tortInfoId) throws YumaoException {
    // List<TortLog> getsTortLogList;
    // try {
    // getsTortLogList = repository.getsTortLogList(tortInfoId);
    // } catch (Throwable ex) {
    // ERROR_LOG.warn(ThrowableUtils.stackTracesToString(ex));
    // throw new YumaoException(666, "服务忙，请稍候再试", ex);
    // }
    // if (getsTortLogList.isEmpty()) {
    // return null;
    // } else {
    // StringBuffer log = new StringBuffer();
    // log.append("<table>");
    // for (TortLog tortLog : getsTortLogList) {
    // log.append("<tr>");
    //
    // log.append("<td>");
    // log.append(DateFormatUtility.format(DateConstants.PATTERN_YYYYMMDDHHMMSSSSS,
    // tortLog.getOperationTime()));
    // log.append("</td>");
    // log.append("<td>");
    // log.append(DateFormatUtility.format(DateConstants.PATTERN_YYYYMMDDHHMMSSSSS,
    // tortLog.getOperationTime()));
    // log.append("</td>");
    // log.append("<td>");
    // log.append(DateFormatUtility.format(DateConstants.PATTERN_YYYYMMDDHHMMSSSSS,
    // tortLog.getOperationTime()));
    // log.append("</td>");
    //
    // log.append("</tr>");
    // }
    // log.append("</table>");
    // return null;
    // }
    // }

}
