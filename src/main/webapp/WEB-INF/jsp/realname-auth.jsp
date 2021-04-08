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
<title>实名认证 - 预猫</title>
<link href='${EXTERNAL_URL }favicon.png' rel='shortcut icon' type='image/png'>
<link href="${EXTERNAL_URL }assets/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-select/1.12.4/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
<link href="${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-fileinput/4.4.8/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
<link href='${EXTERNAL_URL }css/main.css' rel='stylesheet' type='text/css'>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src='${EXTERNAL_URL }assets/html5shiv/3.7.3/html5shiv.min.js' type='text/javascript'></script>
  <script src='${EXTERNAL_URL }assets/respond.js/1.4.2/respond.min.js' type='text/javascript'></script>
<![endif]-->
<script src='${EXTERNAL_URL }assets/jquery/1.12.4/jquery.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }assets/bootstrap/3.3.7/js/bootstrap.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-select/1.12.4/js/bootstrap-select.min.js' type='text/javascript'></script>
<script src="${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-fileinput/4.4.8/js/fileinput.min.js"></script>
<script src="${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-fileinput/4.4.8/js/plugins/purify.min.js"></script>
<script src="${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-fileinput/4.4.8/js/plugins/piexif.min.js"></script>
<script src="${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-fileinput/4.4.8/themes/fa/theme.min.js"></script>
<script src="${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-fileinput/4.4.8/js/locales/zh.js"></script>
<script src='${EXTERNAL_URL }js/common.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/register.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/login.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/realname-auth.js' type='text/javascript'></script>
</head>
<body>
    <input id="external-url" type="hidden" value="${EXTERNAL_URL }">
    <input id="return-mapping" type="hidden" value="${return_mapping }">
    <input id="user-type" type="hidden" value="${requestScope.userType }">

    <div class='content-provider'>
        <jsp:include page="widget/top-navbar.jsp" />

        <div class="container-fluid page-content">
            <div class="container realname-auth-frame">
                <div class="row container-page-content">
                    <div class="col-lg-12 container-page-content-column">
                        <div class="row">
                            <div class="col-lg-12">
                                <span class="content-title"><span style="font-size: 16px; width: 10px; background-color: #005E9E;">&nbsp;</span>&nbsp;实名认证</span>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-lg-offset-3 col-lg-6">
                                <form class="col-lg-12 form-horizontal pb-60">
                                    <c:choose>
                                        <c:when test="${requestScope.userType eq 1 }">
                                            <div class="form-group mt-30">
                                                <label for="realname-auth-gr-realname" class="col-lg-4 control-label">真实姓名：</label>
                                                <div class="col-lg-8 pull-right pr-20">
                                                    <input id="realname-auth-gr-realname" type="text" class="form-control keydown-focus realname-auth-realname" placeholder="请填写真实姓名">
                                                </div>
                                            </div>
                                            <div class="form-group mt-30">
                                                <label for="realname-auth-gr-idcard-no" class="col-lg-4 control-label pull-left">身份证号：</label>
                                                <div class="col-lg-8">
                                                    <input id="realname-auth-gr-idcard-no" type="text" class="form-control keydown-focus realname-auth-idcard-no" placeholder="请填写身份证号">
                                                </div>
                                            </div>
                                            <div class="form-group mt-30">
                                                <label for="realname-auth-gr-bank-card-no" class="col-lg-4 control-label">银行卡号：</label>
                                                <div class="col-lg-8">
                                                    <input id="realname-auth-gr-bank-card-no" type="text" style="width: 100%;" class="form-control keydown-focus realname-auth-bank-card-no" placeholder="请填写银行卡号">
                                                </div>
                                            </div>
                                            <div class="form-group mt-30">
                                                <label for="realname-auth-gr-bank-reserved-mobile" class="col-lg-4 control-label">银行预留手机号码：</label>
                                                <div class="col-lg-8">
                                                    <input id="realname-auth-gr-bank-reserved-mobile" type="text" style="width: 100%;" class="form-control keydown-focus realname-auth-bank-reserved-mobile" placeholder="请填写银行预留手机号码">
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="form-group mt-30">
                                                <label for="realname-auth-qy-ent-name" class="col-lg-4 control-label">企业名称：</label>
                                                <div class="col-lg-8 pull-right pr-20">
                                                    <input id="realname-auth-qy-ent-name" type="text" class="form-control keydown-focus realname-auth-ent-name" placeholder="请填写企业名称">
                                                </div>
                                            </div>
                                            <div class="form-group mt-30">
                                                <label for="realname-auth-qy-ent-bank-name" class="col-lg-4 control-label pull-left">开户银行：</label>
                                                <div class="col-lg-8">
                                                    <input id="realname-auth-qy-ent-bank-name" type="text" class="form-control keydown-focus realname-auth-ent-bank-name" placeholder="请填写开户银行">
                                                </div>
                                            </div>
                                            <div class="form-group mt-30">
                                                <label for="realname-auth-qy-ent-bank-no" class="col-lg-4 control-label">开户银行联行号：</label>
                                                <div class="col-lg-8">
                                                    <select id="realname-auth-qy-ent-bank-no" class="form-control keydown-focus selectpicker realname-auth-ent-bank-no">
                                                        <option value="-1">请选择开户银行联行号</option>
                                                        <c:forEach var="entBankNoDict" items="${entBankNoDicts }">
                                                            <option value="${entBankNoDict.value }">${entBankNoDict.text }</option>
                                                        </c:forEach>
                                                    </select>
                                                    <script type="text/javascript">
                                                                                                                                                                                                                    $('.selectpicker').selectpicker({
                                                                                                                                                                                                                        liveSearchPlaceholder : '请输入查找的开户银行联行号',
                                                                                                                                                                                                                        liveSearch : true,
                                                                                                                                                                                                                        size : 20
                                                                                                                                                                                                                    });
                                                                                                                                                                                                                </script>
                                                </div>
                                            </div>
                                            <div class="form-group mt-30">
                                                <label for="realname-auth-qy-ent-bank-public-account-no" class="col-lg-4 control-label">对公银行账号：</label>
                                                <div class="col-lg-8">
                                                    <input id="realname-auth-qy-ent-bank-public-account-no" type="text" style="width: 100%;" class="form-control keydown-focus realname-auth-ent-bank-public-account-no" placeholder="请填写对公银行账号">
                                                </div>
                                            </div>
                                            <div class="form-group mt-30">
                                                <label for="realname-auth-qy-id-picture" class="col-lg-4 control-label">营业执照：</label>
                                                <div class="col-lg-8 pr-20">
                                                    <div class="filebox">
                                                    <input id="realname-auth-qy-id-picture" type="file" class="form-control keydown-focus realname-auth-id-picture"></div>
                                                    <div id="kartik-file-errors"></div>
                                                </div>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="form-group mt-40">
                                        <div class="col-lg-12 text-center">
                                            <button id="keydown-focus-target" type="button" class="btn btn-link color-btn form-control submit-realname-auth" style="width: 200px;cursor: pointer">提交</button>
                                        </div>
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
