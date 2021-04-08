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
<jsp:include page="../widget/meta-extends.jsp" />
<title>找回密码 - 预猫</title>
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
<script src='${EXTERNAL_URL }js/opinion.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/forget/forget.js' type='text/javascript'></script>
</head>
<body>
    <input id="external-url" type="hidden" value="${EXTERNAL_URL }">
    <input id="return-mapping" type="hidden" value="${return_mapping }">
    
    <input id="user-type" type="hidden" value="1">
    
    <div class='content-provider'>
        <jsp:include page="../widget/top-navbar.jsp">
            <jsp:param name="no_display_login" value="true" />
        </jsp:include>

        <div class="container-fluid page-content">
            <div class="container text-center forget-frame">

                <div class="row container-page-content" >
                    <div class="col-lg-12 container-page-content-column" >
                        <div class="row">
                          <div class="col-lg-12"><span class="content-title" style="float: left;"><span style="font-size: 16px; width: 10px; background-color: #005E9E;">&nbsp;</span>&nbsp;找回密码</span></div>
                        </div>
                        <div class="row">
                            <div class="col-lg-offset-4 col-lg-4">
                                <form class="col-lg-12 form-horizontal pb-60">
                                    <div class="form-group mt-30">
                                            <span class="selected-block-box"> <span id="forget-user-type-personal" name="forget-user-type" class="btn col-lg-6 selected-block" ischecked="true">个人用户</span> <span id="forget-user-type-enterprise" name="forget-user-type" class="btn col-lg-6 unselected-block" ischecked="false">企业用户</span>
                                            </span>
                                    </div>
                                    <div class="tab-content">
                                        <div role="tabpanel" class="tab-pane active" id="grpart">
                                            <div class="form-group mt-40">
                                                <input id="forget-account" type="text" class="form-control keydown-focus" placeholder="请输入账户" autocomplete="off" maxlength="14">
                                            </div>lo
                                            <div class="form-group has-feedback mt-30" style="margin-top: 30px;">
                                                <div class="input-group">
                                                    <input id="forget-verifycode" type="text" class="form-control keydown-focus register-mobile-verifycode" placeholder="请填写验证码" autocomplete="off"> <span class="input-group-btn">
                                                        <button class="btn btn-link color-btn get-verifycode" type="button" style="background-color: #005E9E; color: #FFFFFF;">获取验证码</button>
                                                    </span>
                                                </div>
                                                <div class="input-group" style="margin: 10px 0px 0px 5px;"><p class="input-p-display" style="display: none; color: red;">已向手机号：<span></span>发送验证码，请注意查收</p></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group mt-40" style="margin-top: 0px;">
                                        <button id="" type="button" class="btn btn-link color-btn form-control submit-forget" style="background-color: #005E9E; color: #FFFFFF; height: 42px;">找回密码</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 弹出层 -->
    <jsp:include page="../widget/popup-overall.jsp" />
    
    <jsp:include page="../widget/footer-bottom.jsp" />
</body>
</html>