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
<title>实名认证结果 - 预猫</title>
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
<script src='${EXTERNAL_URL }js/realname-auth-result.js' type='text/javascript'></script>
</head>
<body>
    <input id="external-url" type="hidden" value="${EXTERNAL_URL }">
    <input id="return-mapping" type="hidden" value="${return_mapping }">

    <div class='content-provider'>
        <jsp:include page="widget/top-navbar.jsp" />

        <div class="container-fluid page-content">
            <div class="container realname-auth-result-frame">
                <div class="row container-page-content">
                    <div class="col-lg-12 container-page-content-column">
                        <div class="row">
                            <div class="col-lg-12">
                                <span class="content-title"><span style="font-size: 16px; width: 10px; background-color: #005E9E;">&nbsp;</span>&nbsp;实名认证-审核结果</span>
                            </div>
                        </div>
                        <div class="row mt-80">
                            <div class="col-lg-12 text-center">
                                <img alt="" src="${EXTERNAL_URL }images/icon_realname-auth-processing.svg" style="width: 100px;">
                            </div>
                        </div>
                        <div class="row title mt-30">
                            <div class="col-lg-12 text-center">
                                <span style="font-weight: bold;">${auth_caption }</span>
                            </div>
                        </div>
                        <div class="row content mt-30">
                            <div class="col-lg-offset-3 col-lg-6 text-center">
                                <span style="color:#aaaaaa;">${auth_message }</span>
                            </div>
                        </div>
                        <div class="row mt-100">
                            <div class="col-lg-12 text-center">
                                <c:choose>
                                    <c:when test="${authed_success }">
                                        <button type="button" class="btn btn-link color-btn go-index">首页</button>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="button" class="btn btn-link color-btn submit-realname-auth">重新认证</button>
                                        <button type="button" class="btn btn-link color-btn go-index ml-10">首页</button>
                                    </c:otherwise>
                                </c:choose>
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
