package com.xsebe.yumao.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.Pageable;
import com.xsebe.yumao.model.UserOrder;
import com.xsebe.yumao.service.UserOrderService;

@Component("clearTimeoutPaymentWaitingTask")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ClearTimeoutPaymentWaitingTask {

    private static final long USER_ORDER_TIMEOUT_MILLIS = 2 * 24 * 60 * 60 * 1000;
    private static final int pageSize = 50;

    @Autowired
    private UserOrderService userOrderService;

    public void clear() {
        // 查询所有未支付状态的订单，超过2天的，状态修改为交易关闭
        Date currentTimeoutTime = new Date(System.currentTimeMillis() - USER_ORDER_TIMEOUT_MILLIS);
        try {
            Pageable<UserOrder> page = userOrderService.getPageForTask(UserOrder.STATUS_PAYMENT_WAITING, 0, pageSize);
            for (UserOrder userOrder : page.getCurrentPageList()) {
                if (currentTimeoutTime.after(userOrder.getCreationTime())) {
                    userOrderService.updatePaymentClosedStatus(userOrder.getId());
                }
            }
        } catch (YumaoException ex) {
            ex.printStackTrace();
        }
    }
}
