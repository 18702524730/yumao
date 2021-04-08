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
<title>原创内容管理 - 预猫</title>
<link href='${EXTERNAL_URL }favicon.png' rel='shortcut icon' type='image/png'>
<link href="${EXTERNAL_URL }assets/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-datetimepicker/2.4.4/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css">
<link href="${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-fileinput/4.4.8/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
<link href='${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-table/1.12.1/bootstrap-table.min.css' rel='stylesheet' type="text/css">
<link href='${EXTERNAL_URL }css/main.css' rel='stylesheet' type='text/css'>
<link href='${EXTERNAL_URL }css/profile.css' rel='stylesheet' type='text/css'>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src='${EXTERNAL_URL }assets/html5shiv/3.7.3/html5shiv.min.js' type='text/javascript'></script>
  <script src='${EXTERNAL_URL }assets/respond.js/1.4.2/respond.min.js' type='text/javascript'></script>
<![endif]-->
<script src='${EXTERNAL_URL }assets/jquery/1.12.4/jquery.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }assets/bootstrap/3.3.7/js/bootstrap.min.js' type='text/javascript'></script>
<script src="${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-datetimepicker/2.4.4/js/bootstrap-datetimepicker.min.js"></script>
<script src="${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-datetimepicker/2.4.4/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-fileinput/4.4.8/js/fileinput.min.js"></script>
<script src="${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-fileinput/4.4.8/themes/fa/theme.min.js"></script>
<script src="${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-fileinput/4.4.8/js/locales/zh.js"></script>
<script src='${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-table/1.12.1/bootstrap-table.min.js'></script>
<script src='${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-table/1.12.1/locale/bootstrap-table-zh-CN.min.js'></script>
<script src='${EXTERNAL_URL }js/common.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/register.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/login.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/original-works.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/original-works/add-dialog.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/original-works/view-credential-file-dialog.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/tort-info/tort-info-dialog.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/tort-info.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/profile/index.js' type='text/javascript'></script>
<style type="text/css">
#tort-info-table thead tr th {
  background-color: red;
}
</style>
</head>
<body>
  <input id="external-url" type="hidden" value="${EXTERNAL_URL }">
  <input id="return-mapping" type="hidden" value="${return_mapping }">
  <!--		导航-->
  <div id="nav" class="interval-bottom">
    <div>
      <div class="logo">
        <img src="${EXTERNAL_URL }images/logo.png" alt="">
      </div>
      <ul class="top-nav">
        <li><a href="${EXTERNAL_URL }index.html">预猫首页</a></li>
        <li><a href="${EXTERNAL_URL }user/account.html" style="color: #4d72fe"><%--<shiro:principal property="username" />--%>我的预猫</a></li>
        <li style="margin-right: 0px;"><a href="${EXTERNAL_URL }logout.html">退出登录</a></li>
      </ul>
    </div>
  </div>
  <!--	数据面板-->
  <div id="Data-panel">
    <div class="user">
      <div class="u-head">
        <div>
          <img src="${EXTERNAL_URL }images/profile/head.png" alt="">
        </div>
      </div>
      <c:choose>
        <c:when test="${userType eq 1 }">
          <div class="u-namer" style="margin: 78px 0 0 20px">
        </c:when>
        <c:otherwise>
          <div class="u-namer" style="margin: 61px 0 0 20px">
        </c:otherwise>
      </c:choose>
      <b><shiro:principal property="username" /></b>
      <p>
        <c:if test="${not empty entName }">${entName }</c:if>
      </p>
      <c:choose>
        <c:when test="${hasServiceOpened }">
          <p>服务到期：${endTime }</p>
          <a href="javascript:void(0)" style="position: relative; left: 221px; top: -118px; color: #fff;">续费</a>
          <c:if test="${empty realNameSuccess}">
            <div>
              <a href="${EXTERNAL_URL }realname-auth.html">实名认证</a>
            </div>
          </c:if>
        </c:when>
        <c:otherwise>
          <div>
            <a href="javascript:void(0)" class="to-open-service">立即开通服务</a>
          </div>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
  <div class="u-data">
    <div class="u-d-1 my-save">
      <b>${originalWorksNum }</b>
      <p>我的存证</p>
    </div>
    <div class="u-d-1 my-evid-chain" style="border-left: 1px solid #eee; border-right: 1px solid #eee">
      <b>${evidChainNum }</b>
      <p>我的证据链</p>
    </div>
    <div class="u-d-1 my-tort">
      <b>${tortNum }</b>
      <p>我的维权</p>
    </div>
  </div>
  </div>
  <div id="service">
    <h2 style="font-size: 18px；color:#000; margin-bottom: 51px;">
      <b style="font-size: 18px;">保护服务</b>
    </h2>
    <div class="s-content my-save">
      <div class="s-c-title">
        <b>存证</b>
      </div>
      <div class="s-c-icon">
        <img src="${EXTERNAL_URL }images/profile/save.svg" alt="">
      </div>
      <p>
        可上传需要保护的单独作品文件，比如<br>产品主图、海报图、文字作品、视频<br>等，形成固化证据
      </p>
    </div>
    <div class="s-content my-evid-chain">
      <div class="s-c-title">
        <b>证据链</b>
      </div>
      <div class="s-c-icon">
        <img src="${EXTERNAL_URL }images/profile/chain.svg" alt="">
      </div>
      <p>
        可上传作品各创作节点文件，比如设计<br>图节点、样品图节点、成品图节点等，<br>形成证据链条
      </p>
    </div>
    <div class="s-content my-tort" style="margin-right: 0px;">
      <div class="s-c-title">
        <b>维权</b>
      </div>
      <div class="s-c-icon">
        <img src="${EXTERNAL_URL }images/profile/protect.svg" alt="">
      </div>
      <p>
        完成存证后，发生的作品被侵权，可提<br>交侵权链接，预猫进行辅助维权。
      </p>
    </div>
  </div>
  <div class="foot">
    <jsp:include page="../widget/footer-bottom.jsp" />
  </div>
</body>
</html>