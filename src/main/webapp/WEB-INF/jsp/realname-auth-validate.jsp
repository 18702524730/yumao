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
<title>实名认证确认 - 预猫</title>
<link href='${EXTERNAL_URL }favicon.png' rel='shortcut icon' type='image/png'>
<link href="${EXTERNAL_URL }assets/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href='${EXTERNAL_URL }css/main.css' rel='stylesheet' type='text/css'>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src='${EXTERNAL_URL }assets/html5shiv/3.7.3/html5shiv.min.js' type='text/javascript'></script>
  <script src='${EXTERNAL_URL }assets/respond.js/1.4.2/respond.min.js' type='text/javascript'></script>
<![endif]-->
<script src='${EXTERNAL_URL }assets/jquery/1.12.4/jquery.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }assets/bootstrap/3.3.7/js/bootstrap.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/common.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/register.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/login.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/realname-auth-validate.js' type='text/javascript'></script>
</head>
<body>
    <input id="external-url" type="hidden" value="${EXTERNAL_URL }">
    <input id="return-mapping" type="hidden" value="${return_mapping }">

    <div class='content-provider'>
        <jsp:include page="widget/top-navbar.jsp" />

        <div class="container-fluid page-content">
            <div class="container realname-auth-validate-frame">
                <div class="row container-page-content">
                    <div class="col-lg-12 container-page-content-column">
                        <div class="row">
                            <div class="col-lg-12">
                                <span class="content-title"><span style="font-size: 16px; width: 10px; background-color: #005E9E;">&nbsp;</span>&nbsp;实名认证-审核中</span>
                            </div>
                        </div>
                        <div class="row mt-50">
                            <div class="col-lg-12 text-center">
                                  <span style="font-weight: bold;">系统已向您的企业账户打款，请填写收到的打款金额，以及备注中的验证码信息</span>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-offset-3 col-lg-6">
                                <form class="col-lg-12 form-horizontal pb-60">
                                    <div class="form-group mt-30">
                                        <label for="realname-auth-validate-amount" class="col-lg-3 control-label">收款金额：</label>
                                        <div class="col-lg-9 pr-20">
                                            <input id="realname-auth-validate-amount" type="text" class="form-control keydown-focus realname-auth-validate-amount" placeholder="请填写收款金额" autocomplete="off" style="width:80%;">
                                        </div>
                                    </div>
                                    <div class="form-group mt-30">
                                        <label for="realname-auth-validate-vcode" class="col-lg-3 control-label">验证码：</label>
                                        <div class="col-lg-9 pr-20">
                                            <input id="realname-auth-validate-vcode" type="text" class="form-control keydown-focus realname-auth-validate-vcode" placeholder="请填写验证码" autocomplete="off" style="width:80%;">
                                        </div>
                                    </div>
                                    <div class="form-group mt-40 text-center">
                                        <button id="keydown-focus-target" type="button" class="btn btn-link color-btn form-control submit-realname-auth-validate" style="background-color: #005E9E; color: #FFFFFF; width: 160px; height: 42px;">确&nbsp;&nbsp;&nbsp;&nbsp;定</button>
                                    </div>
                                </form>
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

