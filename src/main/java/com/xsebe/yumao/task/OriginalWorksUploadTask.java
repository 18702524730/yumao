package com.xsebe.yumao.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xsebe.yumao.model.OriginalWorks;
import com.xsebe.yumao.model.Pageable;
import com.xsebe.yumao.service.OriginalWorksService;

@Component("originalWorksUploadTask")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class OriginalWorksUploadTask {

    private static final int pageSize = 50;

    @Autowired
    private OriginalWorksService originalWorksService;

    private static final long ORIGINAL_WORKS_TIMEOUT_MILLIS = 2 * 60 * 1000;

    // 上传状态的原创作品，要么刚创建，要么重试状态过来。
    // 超出超时时间说明，上传到法信一直失败，状态变更为上传失败，让列表有重试的机会
    public void clearTimeout() {
        Date currentTimeoutTime = new Date(System.currentTimeMillis() - ORIGINAL_WORKS_TIMEOUT_MILLIS);
        try {
            Pageable<OriginalWorks> page = originalWorksService.getPageForTask(OriginalWorks.PROTECTION_STATUS_UPLOADING, 0, pageSize);
            for (OriginalWorks originalWorks : page.getCurrentPageList()) {
                if (currentTimeoutTime.after(originalWorks.getUploadingTime())) {
                    originalWorksService.updateUploadingFailureProtectionStatus(originalWorks.getId());
                }
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
}
