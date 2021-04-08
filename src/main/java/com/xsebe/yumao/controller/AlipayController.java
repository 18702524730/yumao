package com.xsebe.yumao.controller;

import com.xsebe.api.docking.alibaba.alipay.AlipaySubmitAPI;
import com.xsebe.api.docking.alibaba.alipay.SignUtil;
import com.xsebe.api.docking.alibaba.alipay.excaption.AlipayException;
import com.xsebe.api.docking.alibaba.alipay.log.BusinessLogger;
import com.xsebe.api.utility.DataUtility;
import com.xsebe.api.utility.HttpUtility;
import com.xsebe.api.utility.HttpUtility.Parameter;
import com.xsebe.api.utility.PriceUtility;
import com.xsebe.api.utility.URLDeEncodeUtility;
import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.log.ErrorLogger;
import com.xsebe.yumao.log.InfoLogger;
import com.xsebe.yumao.model.User;
import com.xsebe.yumao.model.UserOrder;
import com.xsebe.yumao.model.UserOrderServicePricePackage;
import com.xsebe.yumao.service.CommonService;
import com.xsebe.yumao.service.ServiceService;
import com.xsebe.yumao.service.UserOrderService;
import com.xsebe.yumao.service.impl.CommonServiceImpl.SmsCategory;
import com.xsebe.yumao.utility.WebUtility;
import org.apache.commons.codec.CharEncoding;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping("/alipay")
public final class AlipayController {

    private static final Logger INFO_LOG = LogManager.getLogger(InfoLogger.class);
    private static final Logger ERROR_LOG = LogManager.getLogger(ErrorLogger.class);
    private static final Logger ALIPAY_BUSINESS_LOG = LogManager.getLogger(BusinessLogger.class);

    @Value("#{settings.external_url}")
    private String settingsExternalUrl;

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private UserOrderService userOrderService;
    @Autowired
    private CommonService commonService;

    @RequestMapping("/submit.html")
    public void openSubmit(@RequestParam(value = "ids",required = false) final String sids,
                           @RequestParam(value = "orderType",required = false) String orderType,
                           final HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        String _sids = "f0c568f8d0434276b3729c1fb78d2195";
//        _sids="f0c568f8d0434276b3729c1fb78d2195";
        if (DataUtility.isEmptyString(_sids, true)) {
            return;
        }

        // 1. 操作权限检查
        Subject currentUser = SecurityUtils.getSubject();
        if (false == currentUser.isAuthenticated()) {
            return;
        }
        User current = (User) currentUser.getPrincipal();

        // 2. 业务功能
        UserOrder userOrder;
        try {
            if (DataUtility.isEmptyString(orderType, true)) {
                orderType = "1";//默认1开通  2 续费
            }
            userOrder = userOrderService.placeOrder(current, _sids.split(";"),Integer.valueOf(orderType));
        } catch (YumaoException ex) {
            ex.printStackTrace();
            // 下订单失败
            return;
        }

        PrintWriter out = resp.getWriter();

        try {
            String notifyUrl = settingsExternalUrl + "alipay/notify-url.html";
            String returnUrl = settingsExternalUrl + "alipay/return-url.html";
            out.print(AlipaySubmitAPI.createDirectPayByUserHTML(userOrder.getOutTradeNo(), userOrder.getSubject(),
                    PriceUtility.toString(userOrder.getTotalPrice()), userOrder.getBody(), notifyUrl, returnUrl));
        } catch (AlipayException ex) {
            ex.printStackTrace();
        }
    }

    private void setPayment(final String outTradeNo) throws YumaoException {
        UserOrder userOrder = userOrderService.getByOutTradeNo(outTradeNo);
        if (DataUtility.isNotNull(userOrder)) {
            switch (userOrder.getStatus()) {
            case UserOrder.STATUS_PAYMENT_WAITING:
                userOrderService.updatePaymentStatus(userOrder.getId());
            case UserOrder.STATUS_PAYMENT:
                break;
            case UserOrder.STATUS_PAYMENT_CLOSED:
                ERROR_LOG.warn("交易不合法。outTradeNo={}，交易已关闭", outTradeNo);
                break;
            }
        } else {
            ERROR_LOG.warn("交易不合法。outTradeNo={}，未在库里找到", outTradeNo);
        }
    }

    @RequestMapping("/notify-url.html")
    public @ResponseBody String openNotifyURL(final HttpServletRequest req) throws IOException {
        // 获取参数
        String outTradeNo = WebUtility.getParameter("out_trade_no", CharEncoding.UTF_8, req);
        String tradeNo = WebUtility.getParameter("trade_no", CharEncoding.UTF_8, req);
        String tradeStatus = WebUtility.getParameter("trade_status", CharEncoding.UTF_8, req);
        String discount = WebUtility.getParameter("discount", CharEncoding.UTF_8, req);
        String paymentType = WebUtility.getParameter("payment_type", CharEncoding.UTF_8, req);

        String body = URLDecoder.decode(req.getParameter("body"), CharEncoding.UTF_8);
        String subject = URLDecoder.decode(req.getParameter("subject"), CharEncoding.UTF_8);

//        String subject = WebUtility.getParameter("subject", CharEncoding.UTF_8, req);
        String buyerEmail = WebUtility.getParameter("buyer_email", CharEncoding.UTF_8, req);
        String gmtCreate = WebUtility.getParameter("gmt_create", CharEncoding.UTF_8, req);
        String notifyType = WebUtility.getParameter("notify_type", CharEncoding.UTF_8, req);
        String quantity = WebUtility.getParameter("quantity", CharEncoding.UTF_8, req);
        String sellerId = WebUtility.getParameter("seller_id", CharEncoding.UTF_8, req);
        String notifyTime = WebUtility.getParameter("notify_time", CharEncoding.UTF_8, req);
//        String body = WebUtility.getParameter("body", CharEncoding.UTF_8, req);
        String isTotalFeeAdjust = WebUtility.getParameter("is_total_fee_adjust", CharEncoding.UTF_8, req);
        String totalFee = WebUtility.getParameter("total_fee", CharEncoding.UTF_8, req);
        String gmtPayment = WebUtility.getParameter("gmt_payment", CharEncoding.UTF_8, req);
        String sellerEmail = WebUtility.getParameter("seller_email", CharEncoding.UTF_8, req);
        String price = WebUtility.getParameter("price", CharEncoding.UTF_8, req);
        String buyerId = WebUtility.getParameter("buyer_id", CharEncoding.UTF_8, req);
        String notifyId = WebUtility.getParameter("notify_id", CharEncoding.UTF_8, req);
        String useCoupon = WebUtility.getParameter("use_coupon", CharEncoding.UTF_8, req);
        String signType = WebUtility.getParameter("sign_type", CharEncoding.UTF_8, req);
        String signReq = WebUtility.getParameter("sign", CharEncoding.UTF_8, req);

        List<Parameter> params = new ArrayList<HttpUtility.Parameter>();
        params.add(new Parameter("discount", discount));
        params.add(new Parameter("payment_type", paymentType));
        params.add(new Parameter("subject", subject));
        params.add(new Parameter("buyer_email", buyerEmail));
        params.add(new Parameter("gmt_create", gmtCreate));
        params.add(new Parameter("notify_type", notifyType));
        params.add(new Parameter("quantity", quantity));
        params.add(new Parameter("seller_id", sellerId));
        params.add(new Parameter("notify_time", notifyTime));
        params.add(new Parameter("body", body));
        params.add(new Parameter("is_total_fee_adjust", isTotalFeeAdjust));
        params.add(new Parameter("total_fee", totalFee));
        params.add(new Parameter("gmt_payment", gmtPayment));
        params.add(new Parameter("seller_email", sellerEmail));
        params.add(new Parameter("price", price));
        params.add(new Parameter("buyer_id", buyerId));
        params.add(new Parameter("notify_id", notifyId));
        params.add(new Parameter("use_coupon", useCoupon));
        params.add(new Parameter("out_trade_no", outTradeNo));
        params.add(new Parameter("trade_no", tradeNo));
        params.add(new Parameter("trade_status", tradeStatus));

        ALIPAY_BUSINESS_LOG.info("[out_trade_no={}] 来自【支付宝】的回调： 付款通知", outTradeNo);
        ALIPAY_BUSINESS_LOG.info("[out_trade_no={}] 回调参数：trade_no={}, discount={}, payment_type={}, subject={}, buyer_email={}, gmt_create={}, "
                + "notify_type={}, quantity={}, seller_id={}, notify_time={}, body={}, is_total_fee_adjust={}, total_fee={}, "
                + "gmt_payment={}, seller_email={}, price={}, buyer_id={}, " + "notify_id={}, use_coupon={}, out_trade_no={}, trade_no={}, trade_status={}",
                outTradeNo, tradeNo, discount, paymentType, subject, buyerEmail, gmtCreate, notifyType, quantity, sellerId, notifyTime, body, isTotalFeeAdjust,
                totalFee, gmtPayment, sellerEmail, price, buyerId, notifyId, useCoupon, outTradeNo, tradeNo, tradeStatus);
        
        boolean verify = SignUtil.verify(params, signReq, "e6jt7uvr3u8wmdrcm24g84kbdnwadosd", "utf-8");
        ALIPAY_BUSINESS_LOG.warn("验签成功还是失败？？？？？？" + verify);
//        if (!verify) {
//            ALIPAY_BUSINESS_LOG.warn("验签失败： 付款通知");
//            return "fail";
//        }

        if ("TRADE_FINISHED".equals(tradeStatus)) {
            // 判断该笔订单是否在商户网站中已经做过处理
            // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
            // 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
            // 如果有做过处理，不执行商户的业务程序

            // 注意：
            // 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            try {
                setPayment(outTradeNo);
            } catch (YumaoException ex) {
            }
        } else if ("TRADE_SUCCESS".equals(tradeStatus)) {
            // 判断该笔订单是否在商户网站中已经做过处理
            // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
            // 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
            // 如果有做过处理，不执行商户的业务程序

            // 注意：
            // 付款完成后，支付宝系统发送该交易状态通知
            try {
                setPayment(outTradeNo);
            } catch (YumaoException ex) {
            }
        }

        return "success";
    }

    private static String returnHTML(final String action) {
        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"alipayreturn\" name=\"alipayreturn\" action=\"" + action + "\" method=\"get\">");

        sbHtml.append("<input type=\"submit\" value=\"跳转\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['alipayreturn'].submit();</script>");

        return sbHtml.toString();
    }

    @RequestMapping("/return-url.html")
    public void openReturnURL(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        // 获取参数
        String outTradeNo = WebUtility.getParameter("out_trade_no", CharEncoding.UTF_8, req);
        String tradeNo = WebUtility.getParameter("trade_no", CharEncoding.UTF_8, req);
        String tradeStatus = WebUtility.getParameter("trade_status", CharEncoding.UTF_8, req);
        String paymentType = WebUtility.getParameter("payment_type", CharEncoding.UTF_8, req);
        String body = URLDecoder.decode(req.getParameter("body"), CharEncoding.UTF_8);
        String subject = URLDecoder.decode(req.getParameter("subject"), CharEncoding.UTF_8);
//        String subject = WebUtility.getParameter("subject", CharEncoding.UTF_8, req);
        String buyerEmail = WebUtility.getParameter("buyer_email", CharEncoding.UTF_8, req);
        String notifyType = WebUtility.getParameter("notify_type", CharEncoding.UTF_8, req);
        String sellerId = WebUtility.getParameter("seller_id", CharEncoding.UTF_8, req);
        String notifyTime = WebUtility.getParameter("notify_time", CharEncoding.UTF_8, req);
//        String body = WebUtility.getParameter("body", CharEncoding.UTF_8, req);
        String exterface = WebUtility.getParameter("exterface", CharEncoding.UTF_8, req);
        String isSuccess = WebUtility.getParameter("is_success", CharEncoding.UTF_8, req);
        String totalFee = WebUtility.getParameter("total_fee", CharEncoding.UTF_8, req);
        String sellerEmail = WebUtility.getParameter("seller_email", CharEncoding.UTF_8, req);
        String buyerId = WebUtility.getParameter("buyer_id", CharEncoding.UTF_8, req);
        String notifyId = WebUtility.getParameter("notify_id", CharEncoding.UTF_8, req);
        String signType = WebUtility.getParameter("sign_type", CharEncoding.UTF_8, req);
        String signReq = WebUtility.getParameter("sign", CharEncoding.UTF_8, req);

        List<Parameter> params = new ArrayList<HttpUtility.Parameter>();
        params.add(new Parameter("payment_type", paymentType));
        params.add(new Parameter("subject", subject));
        params.add(new Parameter("buyer_email", buyerEmail));
        params.add(new Parameter("notify_type", notifyType));
        params.add(new Parameter("seller_id", sellerId));
        params.add(new Parameter("notify_time", notifyTime));
        params.add(new Parameter("body", body));
        params.add(new Parameter("exterface", exterface));
        params.add(new Parameter("is_success", isSuccess));
        params.add(new Parameter("total_fee", totalFee));
        params.add(new Parameter("seller_email", sellerEmail));
        params.add(new Parameter("buyer_id", buyerId));
        params.add(new Parameter("notify_id", notifyId));
        params.add(new Parameter("out_trade_no", outTradeNo));
        params.add(new Parameter("trade_no", tradeNo));
        params.add(new Parameter("trade_status", tradeStatus));

        ALIPAY_BUSINESS_LOG.info("[out_trade_no={}] 来自【支付宝】的回调：返回url ", outTradeNo);
        ALIPAY_BUSINESS_LOG.info("[out_trade_no={}] 回调参数：trade_no={}, payment_type={}, subject={}, buyer_email={}, "
                + "notify_type={}, seller_id={}, notify_time={}, body={}, " + "total_fee={}, seller_email={}, buyer_id={}, "
                + "notify_id={}, out_trade_no={}, trade_no={}, trade_status={}", outTradeNo, tradeNo, paymentType, subject, buyerEmail, notifyType, sellerId,
                notifyTime, body, totalFee, sellerEmail, buyerId, notifyId, outTradeNo, tradeNo, tradeStatus);
        
        boolean verify = SignUtil.verify(params, signReq, "e6jt7uvr3u8wmdrcm24g84kbdnwadosd", "utf-8");
        if (!verify) {
            ALIPAY_BUSINESS_LOG.warn("验签失败： 返回url");
            return;
        }
        
        resp.setContentType("text/html; charset=UTF-8");

        String _returnMapping = "original-works/index.html";
        if (tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")) {
            // 判断该笔订单是否在商户网站中已经做过处理
            // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
            // 如果有做过处理，不执行商户的业务程序
            Date currentTime = new Date();
            // 订单下的所有商品
            try {
                List<UserOrderServicePricePackage> ist = userOrderService.getOrderServicePricePackagePage(outTradeNo);
                for (UserOrderServicePricePackage p : ist) {
//                    if (currentTime.after(p.getStartTime())) {
                        try {
                            userOrderService.updateOrderServicePricePackageOpenedStatus(p.getId());
                            String mobile = p.getUserOrder().getUser().getMobile();
                            commonService.sendSms(UUID.randomUUID().toString(), mobile, SmsCategory.OPENSERVICE, p.getUserOrder().getUser().getUsername(),
                                    "原创作品保护");
                        } catch (YumaoException ex) {

                        }
                        break;
//                    }
                }
            } catch (YumaoException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                // 标记已支付
                setPayment(outTradeNo);
            } catch (YumaoException ex) {
            }
        }

        PrintWriter out = resp.getWriter();


        if (subject.endsWith("_续费")) {
            out.print(returnHTML(settingsExternalUrl + "user/account.html"));
        }else {
            out.print(returnHTML(settingsExternalUrl + "service/open.html?return_mapping=" + URLDeEncodeUtility.encode(_returnMapping, CharEncoding.UTF_8)
                    + "&step=2&out_trade_no=" + URLDeEncodeUtility.encode(outTradeNo, CharEncoding.UTF_8)));
        }

    }
}