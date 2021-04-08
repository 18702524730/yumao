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
<title>实名认证个人 - 预猫</title>
<link href='${EXTERNAL_URL }favicon.png' rel='shortcut icon' type='image/png'>
<link href="${EXTERNAL_URL }assets/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-select/1.12.4/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
<link href="${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-fileinput/4.4.8/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
<link href='${EXTERNAL_URL }css/main.css' rel='stylesheet' type='text/css'>
<link href='${EXTERNAL_URL }css/pages3/Real-name.css' rel='stylesheet' type='text/css'>
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
<script src='${EXTERNAL_URL }js/realname-auth.js' type='text/javascript'></script>
</head>
<body>
  <input id="external-url" type="hidden" value="${EXTERNAL_URL }">
  <input id="return-mapping" type="hidden" value="${return_mapping }">
  <input id="user-type" type="hidden" value="${userType }">
  <!--    导航-->
  <div class="container-fluid" style="box-shadow: 0 1px 18px 0 rgba(175, 175, 175, 0.1); height: 88px;">
    <div class="container">
      <div class="logo">
        <img src="${EXTERNAL_URL }images/profile/logo.png" alt="">
      </div>
      <ul class="top-nav">
        <li><a href="${EXTERNAL_URL }index.html">预猫首页</a></li>
        <li><a href="${EXTERNAL_URL }user/account.html" style="color: #4d72fe"><%--<shiro:principal property="username" />--%>我的预猫</a></li>
        <li style="margin-right: 0px;"><a href="${EXTERNAL_URL }logout.html">退出登录</a></li>
      </ul>
    </div>
  </div>
  <div class="container" style="margin-top: 30px;">
    <div class="top-head">
      <img src="${EXTERNAL_URL }images/open-service/head.png" alt="">
    </div>
    <c:choose>
      <c:when test="${userType eq 1 }">
        <div class="user" style="margin: 36px 0 0 20px">
      </c:when>
      <c:otherwise>
        <div class="user">
      </c:otherwise>
    </c:choose>
    <b><shiro:principal property="username" /></b>
    <p>
      <c:if test="${not empty entName }">${entName }</c:if>
    </p>
  </div>
  </div>
  <div class="container text-center" style="margin-top: 30px;">
<%--    <img src="${EXTERNAL_URL }images/open-service/stepper.png" alt="">--%>
  <div class="progress-tree">
    <div class="posbox">
      <div class="line-middle"></div>
      <ul class="progress-list">
        <li>
          <div>1</div>
          <div class="markbox"><span class="markright"></span></div>
        </li>
        <li>
          <div>2</div>
          <div class="markbox"><span class="markright"></span></div>
        </li>
        <li>
          <div>3</div>
        </li>
        <li>
          <div>4</div>
        </li>
      </ul>
      <ul class="progress-intr">
        <li>注册</li>
        <li>付款</li>
        <li>实名</li>
        <li>开通</li>
      </ul>
    </div>

  </div>
  </div>
  <div class="container" style="margin-top: 40px">
    <div class="name-box">
      <h1 class="text-center">
        <b style="font-size: 20px; color: #333333;">实名认证</b>
      </h1>
      <div class="form-horizontal text-center realname-auth-frame" style="margin-top: 40px">
        <div class="form-group" style="margin-bottom: 30px">
          <label for="inputEmail3" class="col-xs-4 control-label">真实姓名：</label>
          <div class="col-xs-5  real-pad">
            <input type="text" class="form-control realname-auth-realname" id="inputEmail3" placeholder="请输入真实姓名">
          </div>
        </div>
        <div class="form-group" style="margin-bottom: 30px">
          <label for="inputPassword3" class="col-xs-4 control-label">身份证号：</label>
          <div class="col-xs-5 real-pad">
            <input type="text" class="form-control realname-auth-idcard-no" id="inputPassword3" placeholder="请输入身份证号">
          </div>
        </div>
        <div class="form-group" style="margin-bottom: 30px">
          <label for="inputPassword3" class="col-xs-4 control-label">银行卡号：</label>
          <div class="col-xs-5 real-pad">
            <input type="text" class="form-control realname-auth-bank-card-no" id="inputPassword3" placeholder="请输入银行卡号">
          </div>
        </div>
        <div class="form-group" style="margin-bottom: 30px">
          <label for="inputPassword3" class="col-xs-4 control-label">手机预留号：</label>
          <div class="col-xs-5 real-pad">
            <input type="text" class="form-control realname-auth-bank-reserved-mobile" id="inputPassword3" placeholder="请输入预留手机号">
          </div>
        </div>
        <input id="realname-auth-qy-id-picture" type="hidden" class="form-control keydown-focus realname-auth-id-picture">
        <div class="form-group ">

            <button class="submit-realname-auth  mybluebtn" style="width: 217px;height: 50px; border-radius: 25px;background-color: #479CFF;font-size: 16px; color: #fff;">提交</button>

<%--          <div class="col-sm-offset-2 col-xs-10 real-up" style="margin: 40px 0 0 340px;border:none;">--%>
<%--            <div class="submit-realname-auth" style="width: 100%; border:1px solid #707070; border-radius: 25px; height: 50px;cursor: pointer;line-height: 50px;text-align: center;color:#333;">提交</div>--%>
<%--          </div>--%>
        </div>
      </div>
    </div>
  </div>
  <div class="foot" style="width: 100%; height: 240px; background: #FCFCFC; margin-top: 130px;">
    <jsp:include page="../jsp/widget/footer-bottom.jsp" />
  </div>
</body>
</html>