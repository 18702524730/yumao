package com.xsebe.yumao.task;

import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.Pageable;
import com.xsebe.yumao.model.User;
import com.xsebe.yumao.model.UserOrderServicePricePackage;
import com.xsebe.yumao.service.CommonService;
import com.xsebe.yumao.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("openServiceTask")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class OpenServiceTask {

    @Autowired
    private UserOrderService userOrderService;
    @Autowired
    private CommonService commonService;

    public void openOrExpire() {
        // 查询所有开通等待状态的，开始时间早于当前时间的，状态修改为开通
        Date currentTime = new Date();
        List<UserOrderServicePricePackage> list = new ArrayList<UserOrderServicePricePackage>(0);
        try {
            Pageable<UserOrderServicePricePackage> page = userOrderService.getOrderServicePricePackagePageByPayment(0, 100);
            list = page.getCurrentPageList();
        } catch (YumaoException ex) {
        }
        for (UserOrderServicePricePackage p : list) {
            User current = p.getUserOrder().getUser();
            switch (p.getStatus()) {
            case UserOrderServicePricePackage.STATUS_WAITING:
//                // 等待开通状态的处理：如果已支付且到了开始时间。标记为已开通
//                if (UserOrder.STATUS_PAYMENT == p.getUserOrder().getStatus()) {
//                    if (currentTime.after(p.getStartTime())) {
//                        try {
//                            userOrderService.updateOrderServicePricePackageOpenedStatus(p.getId());
//                            String mobile = p.getUserOrder().getUser().getMobile();
//                            // 刘海|原创作品保护
//                            commonService.sendSms(UUID.randomUUID().toString(), mobile, SmsCategory.OPENSERVICE, current.getUsername(), "原创作品保护");
//                        } catch (YumaoException ex) {
//                        }
//                    }
//                }
                break;
            case UserOrderServicePricePackage.STATUS_OPENED:
                // 已开通状态的处理：如果超过结束时间。标记为已过期
                if (currentTime.after(p.getEndTime())) {
                    try {
                        userOrderService.updateOrderServicePricePackageExpiredStatus(p.getId());
                    } catch (YumaoException ex) {
                    }
                }
                break;
            case UserOrderServicePricePackage.STATUS_EXPIRED:
                // 已过期状态的处理：无
                break;
            }
        }
    }
}
