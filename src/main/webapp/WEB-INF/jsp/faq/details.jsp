<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
<title>${faq.title }</title>
<link href='${EXTERNAL_URL }favicon.png' rel='shortcut icon' type='image/png'>
<link href="${EXTERNAL_URL }assets/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href='${EXTERNAL_URL }assets/bootstrap/plugins/bootstrap-table/1.12.1/bootstrap-table.min.css' rel='stylesheet' type="text/css">
<link href='${EXTERNAL_URL }css/main.css' rel='stylesheet' type='text/css'>
<link href='${EXTERNAL_URL }css/faq-details.css' rel='stylesheet' type='text/css'>
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
</head>
<body>
  <input id="external-url" type="hidden" value="${EXTERNAL_URL }">
  <input id="return-mapping" type="hidden" value="${return_mapping }">
  <div class="header">
    <div>
      <a href="${EXTERNAL_URL }index.html" style="float: right; margin-top: 30px;">??????</a>
    </div>
  </div>
  <div class="bg">
    <div>
      <div class="nav">
        <ul>
          <li><a href="${EXTERNAL_URL }faq/index.html" style="font-size: 12px;">??????</a></li>
          <li>&gt;</li>
          <li>${faq.title }</li>
        </ul>
      </div>
      <div class="bg-center">
        <div class="tl">
          <b class="Title">${faq.title }</b>
          <!--           <p class="Subheading">?????????????????????????????????????????????????????????</p> -->
          <p class="time">
            ?????????
            <fmt:formatDate value="${faq.submitTime }" type="date" pattern="yyyy-MM-dd" />
          </p>
        </div>
        <div class="content">
          <p>${faq.contentHtml }</p>
        </div>
      </div>
    </div>
  </div>
  <footer class="index_footer" style="margin-top: 150px;">
    <div class="content">
      <div class="img_box">
        <img src="${EXTERNAL_URL }images/index/erweima.png" alt="">
        <p class="li">????????????</p>
        <p class="li">??????APP</p>
        <p class="li">????????????</p>
      </div>
      <div class="entry">
        <%--<p class="tit">????????????????????????????????????????????????</p>--%>
        <%--<a href="https://www.ipnotary.com/czcx.htm" target="_black" class="link">??????????????????????????????????????????</a>--%>
      </div>
      <div class="kefu_box">
        <p class="li">
          ?????????<br>
          <a target="_blank"
             href="https://awp.taobao.com/bs/wwlight.html?spm=a1z13.2196529.0.0.71015acaqVKdr6&ver=3&touid=%E4%BF%A1%E6%8B%BE%E8%B4%9D&siteid=cntaobao&status=2&charset=utf-8"><img
                src="${EXTERNAL_URL }images/icon_ww.gif" /></a>????????????
          <a target="_blank"
             href="https://awp.taobao.com/bs/wwlight.html?spm=a1z13.2196529.0.0.71015acaqVKdr6&ver=3&touid=%E4%BF%A1%E6%8B%BE%E8%B4%9D&siteid=cntaobao&status=2&charset=utf-8"><img
                  src="${EXTERNAL_URL }images/icon_ww.gif" /></a>????????????
        </p>
        <p class="li">???????????????8:30-18:00</p>
      </div>
      <p class="copy_info">
        <span class="icp"><a href="//beian.miit.gov.cn" target="_blank" style="color: #fff;">??? ICP??? 18028232???</a></span> <img src="${EXTERNAL_URL }images/index/beian.png" alt="" class="icon"> <span class="right"><a style="color: #fff;" target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=33010402003546">??????????????? 33010402003546???</a>
          ??2018 ??????????????? All Rights Reserved.</span>
      </p>
    </div>
  </footer>
</body>
</html>