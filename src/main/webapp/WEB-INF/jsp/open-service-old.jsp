<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8'%>

<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang='zh'>
<head>
<meta charset='UTF-8'>
<meta name='viewport' content='width=device-width, initial-scale=1.0'>
<meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1' />
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
<meta name='renderer' content='webkit'>
<jsp:include page="widget/meta-extends.jsp" />
<title>开通服务 - 预猫</title>
<link href='${EXTERNAL_URL }favicon.png' rel='shortcut icon' type='image/png'>
<link href="${EXTERNAL_URL }assets/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href='${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-table/1.12.1/bootstrap-table.min.css' rel='stylesheet' type="text/css">
<link href='${EXTERNAL_URL }css/main.css' rel='stylesheet' type='text/css'>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src='${EXTERNAL_URL }assets/html5shiv/3.7.3/html5shiv.min.js' type='text/javascript'></script>
  <script src='${EXTERNAL_URL }assets/respond.js/1.4.2/respond.min.js' type='text/javascript'></script>
<![endif]-->
<script src='${EXTERNAL_URL }assets/jquery/1.12.4/jquery.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }assets/bootstrap/3.3.7/js/bootstrap.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-table/1.12.1/bootstrap-table.min.js'></script>
<script src='${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-table/1.12.1/locale/bootstrap-table-zh-CN.min.js'></script>
<script src='${EXTERNAL_URL }js/common.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/register.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/login.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/open-service.js' type='text/javascript'></script>
</head>
<body>
    <input id="external-url" type="hidden" value="${EXTERNAL_URL }">
    <input id="return-mapping" type="hidden" value="${return_mapping }">
    <input id="step" type="hidden" value="${step }">
    <input id="selected_ids" type="hidden" value="${selected_ids }">
    <input id="id" type="hidden" value="${id }">

    <jsp:include page="widget/agreement-dialog/payment.jsp" />

    <div class='content-provider'>
        <jsp:include page="widget/top-navbar.jsp" />

        <div class="container-fluid page-content">
            <div class="container open-service-frame">
                <div class="row container-page-content">
                    <div class="col-lg-12 container-page-content-column">
                        <div class="row">
                            <div class="col-lg-12">
                                <span class="content-title"><span style="font-size: 16px; width: 10px; background-color: #005E9E;">&nbsp;</span>&nbsp;开通服务</span>
                            </div>
                        </div>
                        <div class="tab-content mt-30">
                            <div role="tabpanel" class="tab-pane" id="payment">
                                <div class="row">
                                    <div class="col-lg-12 text-center">
                                        <table id="service-table"></table>
                                    </div>
                                </div>
                                <div class="row mt-50">
                                    <div class="col-lg-8 col-lg-offset-2 text-center">
                                        <span>总计：<label id="all-price">0.00</label> 元
                                        </span>
                                    </div>
                                    <div class="col-lg-8 col-lg-offset-2 text-center">
                                        <span>支付方式：支付宝</span>
                                    </div>
                                </div>
                                <div class="row mt-30">
                                    <div class="col-lg-12 text-center">
                                        <span><input class="payment-agreement" type="checkbox" value="1" aria-label="..."> 我已阅读并同意<a class="color-link" href="javascript:void(0);" data-toggle="modal" data-target="#payment-agreement-modal">《预猫年度服务协议》</a></span>
                                    </div>
                                </div>
                                <div class="row mt-30">
                                    <div class="col-lg-8 col-lg-offset-2 text-center">
                                        <button type="button" class="btn btn-link color-btn btn-step1" style="width: 200px;">确认支付</button>
                                    </div>
                                </div>
                            </div>
                            <div role="tabpanel" class="tab-pane" id="payment-result">
                                <div class="row mt-80">
                                    <div class="col-lg-12 text-center">
                                        <img alt="" src="${EXTERNAL_URL }images/right.png" style="width: 100px;">
                                    </div>
                                </div>
                                <div class="row title mt-30">
                                    <div class="col-lg-12 text-center">
                                        <span>支付成功</span>
                                    </div>
                                </div>
                                <div class="row content mt-10">
                                    <div class="col-lg-12 text-center">
                                        <span>恭喜您成功开通原创保镖服务，请尽快将您需要保护的原创内容，上传至系统中，我们会第一时间为您提供服务。</span>
                                    </div>
                                </div>
                                <div class="row mt-100">
                                    <div class="col-lg-12 text-center">
                                        <button type="button" class="btn btn-link color-btn btn-step2">管理原创内容</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="widget/footer-bottom.jsp" />
</body>
</html>