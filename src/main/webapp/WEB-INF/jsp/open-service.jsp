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
<link href='${EXTERNAL_URL }css/pages3/open-service.css' rel='stylesheet' type='text/css'>
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
<script src='${EXTERNAL_URL }js/open-service.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/common.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/register.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/login.js' type='text/javascript'></script>
<jsp:include page="/WEB-INF/jsp/open-service-payment.jsp"></jsp:include>
<jsp:include page="widget/agreement-dialog/payment.jsp" />
</head>
<body>
  <input id="external-url" type="hidden" value="${EXTERNAL_URL }">
  <input id="return-mapping" type="hidden" value="${return_mapping }">
  <input id="step" type="hidden" value="${step }">
  <!--    导航-->
  <div class="container-fluid" style="box-shadow: 0 1px 18px 0 rgba(175, 175, 175, 0.1); height: 88px;">

    <div class="container">
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
                    <span></span>
                </li>
                <li>
                    <div>3</div>
                    <span></span>
                </li>
                <li>
                    <div>4</div>
                    <span></span>
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

  <div class="container card" style="margin-top: 40px;">
    <div style="height: 466px">
      <div class="open-title">
        <h1>
          <b class="Main-title">年度版</b>
          <p>
            <b class="Subheading">￥999</b>
          </p>
        </h1>
      </div>


      <h1 style="margin: -33px 0 0 50px">
        <b style="font-size: 20px; color: #fff;">开通尊享三大服务:</b>
      </h1>

      <div class="row" style="margin-top: 60px;">
        <div class="col-md-4 text-center">

          <img src="${EXTERNAL_URL }images/open-service/save.png" alt="">
          <p style="margin-top: 18px">
            <b style="font-size: 18px; color: #5A3513">存证</b>
          </p>

        </div>
        <div class="col-md-4 text-center">

          <img src="${EXTERNAL_URL }images/open-service/chain.png" alt="">
          <p style="margin-top: 18px">
            <b style="font-size: 18px; color: #5A3513">保护</b>
          </p>

        </div>
        <div class="col-md-4 text-center">

          <img src="${EXTERNAL_URL }images/open-service/protect.png" alt="">
          <p style="margin-top: 18px">
            <b style="font-size: 18px; color: #5A3513">维权</b>
          </p>

        </div>

      </div>

      <div style="margin-top: 50px; margin-left: 50px;">
        <span style="color: #fff; font-size: 16px; margin-top: 20px;">预猫全年套餐包含以下服务:</span>
        <p style="color: #fff; font-size: 14px; font-weight: 300; margin-top: 10px">
          1、支持含产品主图，详情页面，摄影作品，原创文章、文学作品的电子存证。<br> 2、存证日后产生的侵权行为，预猫进行维权服务。<br> 3、生成一份唯一编码的店铺原创声明函。


        </p>

      </div>

    </div>
    <%--<a href="javascript:void(0)" class="do-open-service" style="font-size: 16px; color: #333333;">--%>
    <div class="text-center" style="width: 650px; margin: 46px auto;background: none;">
        <p style="color: red;font-size: 16px;">因公司战略调整，预猫业务将进行升级，如您有存证需求，请移步至拾贝网：<a href="https://www.ipsebe.com/cms/service/rightoriginal.htm" style="font-size: 16px;text-decoration: underline"> 点击前往</a></p>
    </div>
    <%--</a>--%>

  </div>




  <div class="foot" style="width: 100%; height: 240px; background: #FCFCFC; margin-top: 50px;">
    <jsp:include page="widget/footer-bottom.jsp" />

  </div>

</body>
</html>