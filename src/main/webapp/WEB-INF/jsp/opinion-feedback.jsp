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
<title>意见反馈 - 预猫</title>
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
</head>
<body>
    <input id="external-url" type="hidden" value="${EXTERNAL_URL }">
    <input id="return-mapping" type="hidden" value="${return_mapping }">
    
    <!-- <form id="opinion-form">
                    标题:<input type="text" class="form-control opinion-title"><br>
                    内容:<textarea rows="4" cols="50" class="opinion-content"></textarea><br>
                    邮箱：<input type="text" class="opinion-email"><br>
                   手机号码：<input type="text" class="opinion-telphone"><br>
      <button id="keydown-focus-target" type="button" class="btn btn-link color-btn form-control submit-opinion" style="background-color: #005E9E; color: #FFFFFF; height: 42px;">提交意见</button>
    </form> -->
    <div class='content-provider'>
        <jsp:include page="widget/top-navbar.jsp">
            <jsp:param name="no_display_login" value="true" />
        </jsp:include>

        <div class="container-fluid page-content">
            <div class="container text-center login-frame">

                <div class="row container-page-content" >
                    <div class="col-lg-12 container-page-content-column" >
                        <div class="row">
                          <div class="col-lg-12"><span class="content-title" style="float: left;"><span style="font-size: 16px; width: 10px; background-color: #005E9E;">&nbsp;</span>&nbsp;用户意见反馈</span></div>
                        </div>
                        <div class="row" style="margin-left: 70px;">
                            <div class="col-md-9 row mt-30">
                                <form class="form-horizontal pb-20" id="opinion-form">
                                    <div class="form-group mt-30">
                                        <label for="dialog-add-original-works-creation-time" class="col-lg-3 control-label">标题&nbsp;*&nbsp;:</label>
                                        <div class="col-lg-9 pr-20">
                                            <input type="text" class="form-control opinion-title" placeholder="请填写反馈标题">
                                        </div>
                                    </div>
                    
                                    <div class="form-group mt-30">
                                        <label for="dialog-add-original-works-creation-time" class="col-lg-3 control-label">内容&nbsp;*&nbsp;:</label>
                                        <div class="col-lg-9 pr-20">
                                            <textarea rows="4" cols="50" class="form-control opinion-content" style="resize:none" placeholder="请填写内容"></textarea>
                                        </div>
                                    </div>
                    
                                    <div class="form-group mt-30">
                                        <label for="dialog-add-original-works-name" class="col-lg-3 control-label">邮箱：</label>
                                        <div class="col-lg-9 pr-20">
                                            <input type="text" class="form-control opinion-email" placeholder="请填写反馈邮箱（选填）">
                                        </div>
                                    </div>
                                    
                                    <div class="form-group mt-30">
                                        <label for="dialog-add-original-works-name" class="col-lg-3 control-label">联系方式：</label>
                                        <div class="col-lg-9 pr-20">
                                            <input type="text" class="form-control opinion-telphone" placeholder="请填写手机号/固话（选填）固话格式：xxxx-xxxxxxx">
                                        </div>
                                    </div>
                                    
                                    <div class="form-group mt-40">
                                        <div class="col-lg-12">
                                            <button type="button" class="btn btn-link color-btn form-control submit-opinion" style="width: 74%;margin-left:190px;" >提交反馈</button>
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
    <!-- 弹出层 -->
    <jsp:include page="widget/popup-overall.jsp" />
    
    <jsp:include page="widget/footer-bottom.jsp" />
</body>
</html>