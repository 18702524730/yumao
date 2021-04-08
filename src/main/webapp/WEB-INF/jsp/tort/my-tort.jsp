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
<title>我的维权 - 预猫</title>
<link href='${EXTERNAL_URL }favicon.png' rel='shortcut icon' type='image/png'>
<link href="${EXTERNAL_URL }assets/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href='${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-table/1.12.1/bootstrap-table.min.css' rel='stylesheet' type="text/css">
<link href='${EXTERNAL_URL }css/main.css' rel='stylesheet' type='text/css'>
<link href='${EXTERNAL_URL }css/pages3/save.css' rel='stylesheet' type='text/css'>
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
<script src='${EXTERNAL_URL }js/tort-info/tort-info-dialog.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/tort-info.js' type='text/javascript'></script>

<style type="text/css">
ul.nav-tabs>li>div {
  background: transparent !important;
}

ul.nav-tabs>li.active>div {
  background: #4D72FE !important;
}

ul.nav-tabs>li>a>b {
  color: #979797;
}

ul.nav-tabs>li.active>a>b {
  color: #000;
}
</style>
</head>
<body>
  <input id="external-url" type="hidden" value="${EXTERNAL_URL }">
  <input id="return-mapping" type="hidden" value="${return_mapping }">
  <jsp:include page="dialog/add-tort-info.jsp"></jsp:include>
  <!--    导航-->
  <div class="container-fluid" style="box-shadow: 0 1px 18px 0 rgba(175, 175, 175, 0.1); height: 88px;">
    <div class="container">
      <div class="logo">
        <a href="${EXTERNAL_URL }index.html"><img src="${EXTERNAL_URL }images/logo.png" alt=""></a>
      </div>
      <ul class="top-nav">
        <li><a href="${EXTERNAL_URL }index.html">预猫首页</a></li>
        <li><a href="${EXTERNAL_URL }user/account.html" style="color: #4d72fe"><%--<shiro:principal property="username" />--%>我的预猫</a></li>
        <li style="margin-right: 0px;"><a href="${EXTERNAL_URL }logout.html">退出登录</a></li>
      </ul>
    </div>
  </div>
  <div class="container" style="height: 885px; border: 1px solid #eee; margin-top: 30px; padding: 0px;">
    <div class="left-nav">
      <div class="l-n-user text-center">
        <div style="width: 70px; height: 70px; background: #fff; border-radius: 50%; margin: 16px 0 10px 60px; overflow: hidden">
          <div style="width: 66px; height: 66px; margin: 2px 0 0 2px">
            <img src="${EXTERNAL_URL }images/account/head.png" alt="">
          </div>
        </div>
        <b style="font-size: 16px; color: #fff;display: block;padding: 0 5px; overflow: hidden;text-overflow: ellipsis;white-space: nowrap;margin-bottom: 5px;"><shiro:principal property="username" /></b>
        <c:if test="${hasRealName }">
          <p style="font-size: 12px; color: #fff; margin-top: 0px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;padding: 0 5px;margin-bottom: 5px">${entName }</p>
        </c:if>
        <c:choose>
          <c:when test="${hasServiceOpened }">
            <p style="font-size: 12px; color: #fff; margin-top: 0px;margin-bottom: 5px">服务到期：${endTime }</p>
            <div style="width: 91px; height: 25px; border-radius: 13px; background: #fff; text-align: center; margin-left: 50px">
              <a href="${EXTERNAL_URL }alipay/submit.html?orderType=2" style="display: inline-block;width:100%;height: 100%;font-size: 12px; color: #444444; line-height: 24px;cursor: pointer;">立即续费</a>
            </div>
          </c:when>
          <c:otherwise>
            <div style="width: 91px; height: 25px; border-radius: 13px; background: #fff; text-align: center; margin-top: 16px; margin-left: 50px">
              <a href="${EXTERNAL_URL }service/open.html" style="display: inline-block;width:100%;height: 100%;font-size: 12px; color: #444444; line-height: 24px;">立即开通服务</a>
            </div>
          </c:otherwise>
        </c:choose>
      </div>
      <div class="l-n-service">
        <div class="account">
          <div style="float: left; margin-left: 40px;">
            <img src="${EXTERNAL_URL }images/account/account-h.png" alt="">
          </div>
          <a style="font-size: 16px; color: #333333; margin-left: 15px;" href="${EXTERNAL_URL }user/account.html">账号管理</a>
        </div>
        <div class="save">
          <div style="float: left; margin-left: 40px;">
            <img src="${EXTERNAL_URL }images/account/save.svg" alt="">
          </div>
          <a style="font-size: 16px; color: #333333; margin-left: 15px;" href="${EXTERNAL_URL }original-works/my-save.html">我的原创</a>
        </div>
<%--        <div class="chain">--%>
<%--          <div style="float: left; margin-left: 40px;">--%>
<%--            <img src="${EXTERNAL_URL }images/account/chain.svg" alt="">--%>
<%--          </div>--%>
<%--          <a style="font-size: 16px; color: #333333; margin-left: 15px;" href="#&lt;%&ndash;${EXTERNAL_URL }evid-chain/my-chain.html&ndash;%&gt;">我的证据链</a>--%>
<%--        </div>--%>
        <div class="protect" style="border-left: 6px solid #4D72FE;">
          <div style="float: left; margin-left: 40px;">
            <img src="${EXTERNAL_URL }images/account/protect-b.png" alt="">
          </div>
          <a style="font-size: 16px; color: #4D72FE; margin-left: 15px;" href="${EXTERNAL_URL }tort/my-tort.html">我的维权</a>
        </div>
      </div>
    </div>
    <div class="right-content">
      <h1 style="margin: 38px 0 0 48px">
        <b style="color: #040404; font-size: 20px">我的维权</b>
      </h1>
      <div style="margin-top: 44px">
        <div class="add-tort-info"
          style="display:inline-block;cursor:pointer;width: 140px; height:45px; background: #96BFFF; line-height: 45px; text-align: center; float: left; margin-left: 48px; color: #fff">添加维权</div>
        <div class="search" style="float: right; margin-right: 30px;">
          <p style="display: inline-block; height: 45px; line-height: 45px; font-size: 16px; color: #979797">作品名称：</p>
          <input type="text" name="search-tort-name" id="search-tort-name" style="height: 45px; width: 266px; border: 1px solid #EEEEEE; border-radius: 4px; display: inline-block">
          <img src="${EXTERNAL_URL }images/account/search.png" alt="" class="search-tort-btn" style="position: relative; left: -40px">
        </div>
      </div>
      <p style="margin: -60px 0 5px 220px; color: #979797; font-size: 10px">规则：</p>
      <p style="margin: 5px 0 5px 220px; color: #979797; font-size: 10px">1、以预猫平台所存文件为维权依据</p>
      <p style="margin: 5px 0 5px 220px; color: #979797; font-size: 10px">2、存证日之前的侵权行为，维权成功率低</p>
      <p style="margin: 5px 0 5px 220px; color: #979797; font-size: 10px">3、辅助维权包含侵权商品下架或提供维权解决方案</p>
      <p style="margin: 5px 0 5px 220px; color: #979797; font-size: 10px">4、若诉讼评估后可操作，权利人需配合授权诉讼</p>

        <div style="padding: 0 50px;" class="myselftable">
            <table id="tort-info-table" class="table">
            </table>
        </div>

    </div>
  </div>
  <div class="foot" style="width: 100%; height: 240px; background: #FCFCFC; margin-top: 130px;">
    <jsp:include page="../widget/footer-bottom.jsp" />
  </div>
</body>
</html>