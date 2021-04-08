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
<style type="text/css">
  #tort-info-table thead tr th{
    background-color: red;
  }
</style>
</head>
<body>
  <input id="external-url" type="hidden" value="${EXTERNAL_URL }">
  <input id="name" type="hidden" value="${name }">
  <jsp:include page="../widget/original-works/add-dialog.jsp" />
  <jsp:include page="../widget/original-works/add-tort-info.jsp" />
  <jsp:include page="../widget/original-works/view-credential-file-dialog.jsp" />
  <!--成功  -->
  <jsp:include page="../widget/original-works/success-dialog.jsp" />
  <div class='content-provider'>
    <jsp:include page="../widget/top-navbar.jsp" />

    <div class="container-fluid page-content">
      <div class="container open-service-frame">
        <div class="row container-page-content">
          <div class="col-lg-12 container-page-content-column">
            <div class="row">
              <div class="col-lg-12">
                <span class="content-title"><span style="font-size: 16px; width: 10px; background-color: #005E9E;">&nbsp;</span>&nbsp;管理原创内容</span>
              </div>
            </div>
            <div class="row mt-30">
              <div class="col-lg-1">
                <button type="button" class="btn btn-link color-btn mr-20 add-original-works" style="height: 30px; font-size: 12px;">立即添加</button>
              </div>
              <div class="col-lg-11 pt-10">
                <span>管理列表中的内容，将享受确权存证、侵权监测、维权索赔等原创保护服务</span>
              </div>
            </div>
            <div class="row mt-30">
              <div class="col-lg-12 text-center">
                <table id="original-works-table"></table>
              </div>
            </div>
            <div class="row mt-30 img-empty-list hidden">
              <div class="col-lg-12 text-center">
                <a class="add-original-works" href="javascript:void(0);"><img alt="" src="${EXTERNAL_URL }images/empty-table.png"></a>
              </div>
              <div class="col-lg-12 text-center mt-20">
                <span>您还没有添加任何作品</span>
              </div>
              <div class="col-lg-12 text-center mt-10">
                <span>把你需要保护的作品添加进来，开始保护自己的知识成果。</span>
              </div>
            </div>
            <div class="row mt-30" style="margin-top: 6px;">
              <div class="col-lg-12">
                <span>共 <label id="data-total">0</label> 条数据，总存证空间<label id="total_space">0 GB</label>，已使用 <label id="used-space">0 GB</label><%--，还可使用 <label id="free-space">0 GB</label> 存储空间--%>
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="container-fluid page-content">
      <div class="container open-service-frame">
        <div class="row container-page-content">
          <div class="col-lg-12 container-page-content-column">
            <div class="row">
              <div class="col-lg-12">
                <span class="content-title"><span style="font-size: 16px; width: 10px; background-color: #005E9E;">&nbsp;</span>&nbsp;管理维权内容</span>
              </div>
            </div>
            <div class="row mt-30">
              <div class="col-lg-1">
                <button type="button" class="btn btn-link color-btn mr-20 add-tort-info" style="height: 30px; font-size: 12px; background-color: red;">立即添加</button>
              </div>
              <div class="col-lg-11 pt-10">
                <span>以预猫平台所存的文件为维权依据，维权成功率与实际侵权判定有关。</span>
              </div>
            </div>
            <div class="row mt-30">
              <div class="col-lg-12 text-center">
                <table id="tort-info-table"  style="table-layout: fixed;"></table>
              </div>
              <div class="col-lg-12" style="margin-left: 5px;">
                <span>共 <label id="tort-total">0</label> 条数据
                </span>
              </div>
            </div>
            <div class="row mt-30 tort-info-empty-list hidden">
              <div class="col-lg-12 text-center mt-20">
                <span>您还没有添加任何侵权信息</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- 右侧悬浮意见反馈 -->
  <jsp:include page="../widget/opinion.jsp" />
  <!-- 弹出层 -->
  <jsp:include page="../widget/popup-overall.jsp" />

  <jsp:include page="../widget/footer-bottom.jsp" />
</body>
</html>
