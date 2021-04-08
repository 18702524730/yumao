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
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<meta name="keywords" content="预猫,信拾贝,怕侵权,原创保护,被侵权,电商反侵权,电商维权,知识产权严密保护">
<meta name="description" content="预猫是一个致力于原创知识产权严密保护、侵权监测和维权诉讼的互联网平台，服务于电商、设计、文创等行业权利人；怕被侵权，就上预猫，享受省心又严密的知识产权保护。">
<title>预猫 - 保！我省心</title>



<link href='${EXTERNAL_URL }favicon.png' rel='shortcut icon' type='image/png'>
<link href="${EXTERNAL_URL }assets/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href='${EXTERNAL_URL }css/main.css' rel='stylesheet' type='text/css'>
<link href='${EXTERNAL_URL }css/evidence.css' rel='stylesheet' type='text/css'>

<jsp:include page="/WEB-INF/jsp/widget/embedded/baidu-tongji-script.jsp" />
<script src='${EXTERNAL_URL }assets/html5shiv/3.7.3/html5shiv.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }assets/respond.js/1.4.2/respond.min.js' type='text/javascript'></script>

</head>
<body>
  <input id="external-url" type="hidden" value="${EXTERNAL_URL }">

  <div class="evidenceTitle">
    <div class="header">
      <div class="logoWrap">
        <a href="${EXTERNAL_URL }index.html"><img src="${EXTERNAL_URL }images/evidence/logo.png" alt=""></a>
      </div>
      <div class="loginWrap">
        <a href="${EXTERNAL_URL }index.html">首页</a> <a class="current" href="javascript:viod(0);">证据王</a> <%-- <a href="${EXTERNAL_URL }market.html">营销保</a>  --%>
        <shiro:notAuthenticated>
          <a class="btn" href="${EXTERNAL_URL }login.html">登录</a>
          <a class="btn" href="${EXTERNAL_URL }register.html">注册</a>
        </shiro:notAuthenticated>
        <shiro:authenticated>
          <span><a class="" style="color: #FFFFFF;" href="${EXTERNAL_URL }user/account.html"> <%--<shiro:principal property="username" />--%>我的预猫</a> <a class="btn btn-link hide"
            style="color: #FFFFFF;" href="${EXTERNAL_URL }original-works/index.html">管理控制台</a> <a class="btn btn-link" style="color: #FFFFFF;" href="${EXTERNAL_URL }logout.html">退出登录</a></span>
        </shiro:authenticated>
        <a class="help" href="${EXTERNAL_URL }faq/index.html"><img src="${EXTERNAL_URL }images/evidence/help.png" alt=""></a>
      </div>
    </div>
  </div>
  <div class="evidenceMain">
    <div class="evidenceMainContainer">
      <h2>怎么做才能形成原创完整证据链</h2>
      <p class="des">原创存证是权利在先的原则，一定要在证据产生时就做存证固定</p>
      <div class="mianPic">
        <img src="${EXTERNAL_URL }images/evidence/pic1.png" alt=""> <img src="${EXTERNAL_URL }images/evidence/pic2.png" alt=""> <img
          src="${EXTERNAL_URL }images/evidence/pic3.png" alt="">
      </div>
      <div class="numWrap">
        <div class="numItem" style="padding-right: 30px;">
          <div class="num">1</div>
          <h3>设计稿</h3>
          <p>设计的源头，灵感的见证</p>
        </div>
        <div class="numItem">
          <div class="num num2">2</div>
          <h3>样品图</h3>
          <p>头脑的实物，设计的诞生</p>
        </div>
        <div class="numItem" style="padding-left: 54px;">
          <div class="num num3">3</div>
          <h3>实物场景、主图</h3>
          <p>买卖的宣传，收益的开端</p>
        </div>
      </div>
      <div class="mainDes">
        拥有这样完整的证据链，打击侵权就很简单了<br> 侵权人能抄袭你的产品，但没有你的设计稿，你胜诉<br> 侵权人能制作假冒的设计稿，但你的时间在先，你胜诉<br> 做这么严密的证据固定，不仅保护了自己 ，更为了让维权轻松一点
      </div>
      <h2>“预猫因你而生，如你所愿”</h2>
      <a class="btn" href="./login.html?return_mapping=original-works/my-save.html" type="button">立即保存证据</a>
    </div>
  </div>
  <footer class="index_footer">
    <div class="content">
      <div class="img_box">
        <img src="${EXTERNAL_URL }images/index/erweima.png" alt="">
        <p class="li">网页取证</p>
        <p class="li">手机APP</p>
        <p class="li">见证实录</p>
      </div>
      <div class="entry">
        <%--<p class="tit">中国知识产权公证服务平台查验入口</p>--%>
        <%--<a href="https://www.ipnotary.com/czcx.htm" target="_black" class="link">输入公证保管函编号，即可查询</a>--%>
      </div>
      <div class="kefu_box">
        <p class="li">
          客服：<br>
          <a target="_blank"
             href="https://awp.taobao.com/bs/wwlight.html?spm=a1z13.2196529.0.0.71015acaqVKdr6&ver=3&touid=%E4%BF%A1%E6%8B%BE%E8%B4%9D&siteid=cntaobao&status=2&charset=utf-8"><img
                src="${EXTERNAL_URL }images/icon_ww.gif" /></a>咨询客服
          <a target="_blank"
             href="https://awp.taobao.com/bs/wwlight.html?spm=a1z13.2196529.0.0.71015acaqVKdr6&ver=3&touid=%E4%BF%A1%E6%8B%BE%E8%B4%9D&siteid=cntaobao&status=2&charset=utf-8"><img
                  src="${EXTERNAL_URL }images/icon_ww.gif" /></a>使用咨询
        </p>
        <p class="li">周一到周五8:30-18:00</p>
      </div>
      <p class="copy_info">
        <span class="icp"><a href="//beian.miit.gov.cn" target="_blank" style="color: #000;">浙 ICP备 18028232号</a></span> <img src="${EXTERNAL_URL }images/index/beian.png" alt="" class="icon"> <span class="right"><a style="color: #000;" target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=33010402003546">浙公网安备 33010402003546号</a>
          ©2018 信拾贝官网 All Rights Reserved.</span>
      </p>
    </div>
  </footer>


</body>
<script src='${EXTERNAL_URL }assets/jquery/1.12.4/jquery.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }assets/bootstrap/3.3.7/js/bootstrap.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/common.js' type='text/javascript'></script>
</html>
