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
<title>用户登录 - 预猫</title>
<link href='${EXTERNAL_URL }favicon.png' rel='shortcut icon' type='image/png'>
<link href="${EXTERNAL_URL }assets/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href='${EXTERNAL_URL }css/main.css' rel='stylesheet' type='text/css'>
<link href='${EXTERNAL_URL }css/regLogin.css' rel='stylesheet' type='text/css'>

<jsp:include page="/WEB-INF/jsp/widget/embedded/baidu-tongji-script.jsp" />
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src='${EXTERNAL_URL }assets/html5shiv/3.7.3/html5shiv.min.js' type='text/javascript'></script>
  <script src='${EXTERNAL_URL }assets/respond.js/1.4.2/respond.min.js' type='text/javascript'></script>
<![endif]-->
<script src='${EXTERNAL_URL }assets/html5shiv/3.7.3/html5shiv.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }assets/respond.js/1.4.2/respond.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }assets/jquery/1.12.4/jquery.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }assets/bootstrap/3.3.7/js/bootstrap.min.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/common.js' type='text/javascript'></script>
<script src='${EXTERNAL_URL }js/login.js' type='text/javascript'></script>
<style>
.login_content>img {
		margin: 30px 0px 0px 10%;
}

.login_content_in .form_box {
		top: 16px;
		width: 521px;
		height: 583px;
}

.login_content_in .form_box_in .tab_label {
		padding-top: 48px;
}

.login_content_in .form_box_in .tab-content {
		padding-top: 15px;
}

.login_content_in .form_box_in .form-footer .text-center {
		margin-top: 30px;
}

.login_content_in .form_box_in {
		width: 455px;
}

.login_content {
		height: 720px;
}
</style>
</head>
<body class="reglogin_page">
  <input id="external-url" type="hidden" value="${EXTERNAL_URL }">
  <input id="return-mapping" type="hidden" value="${return_mapping }">

  <!--
    <header>
        
    </header>
-->
  <div class="login_content">
  <img class="logo" src="${EXTERNAL_URL }images/evidence/logo.png">
    
    <div class="login_content_in">
      <div class="form_container">
        <div class="form_box">
          <div class="form_box_in">
            <form class="form_box_in login-dialog pb-20">
              <div class="tab_label">
                <label class="cur" id="1">个人用户<span></span></label>
                <label id="2">企业用户<span></span></label>
              </div>
              <div class="tab-content">
                <div role="tabpanel" class="tab-panel active" id="dialog-grpart">
                  <div class="form-group user-group">
                    <label for="dialog-login-gr-account" class="control-label">账号：</label> <input id="dialog-login-gr-account" type="text" class="form-control login-account"
                      placeholder="请填写用户名/手机号码/邮箱地址" autocomplete="off">
                  </div>
                  <div class="form-group pass-group mt-30">
                    <label for="dialog-login-gr-login-password" class="control-label">登录密码：</label> <input id="dialog-login-gr-login-password" type="password"
                      class="form-control login-login-password" placeholder="请填写登录密码" autocomplete="off">
                  </div>
                </div>
                <div role="tabpanel" class="tab-panel" id="dialog-qypart">
                  <div class="form-group user-group">
                    <label for="dialog-login-qy-account" class="control-label">账号：</label> <input id="dialog-login-qy-account" type="text" class="form-control login-account"
                      placeholder="请填写用户名/统一社会信用代码/组织机构代码" autocomplete="off">
                  </div>
                  <div class="form-group pass-group mt-30">
                    <label for="dialog-login-qy-login-password" class="control-label">登录密码：</label> <input id="dialog-login-qy-login-password" type="password"
                      class="form-control login-login-password" placeholder="请填写登录密码" autocomplete="off">
                  </div>
                </div>
              </div>
              <div class="form-footer mt-40">
                <a class="forget" href="${EXTERNAL_URL }user/forgetPass.html">忘记密码？</a>
                <button type="button" class="submit-login">登录</button>
                <div class="col-lg-12 text-center">
                  <span>还没有账号？</span><a class="im" href="${EXTERNAL_URL }register.html">立即注册</a>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
  <footer class="footer footer-bottom mt-20 pb-40">
    <div class='container'>
      <div class="row">
        <div class="col-lg-12 text-center">
          <p>杭州信拾贝网络科技有限公司为中国反侵权假冒创新战略联盟副理事长单位</p>
          <p class="copyright"><a style="color: #000;" href="//beian.miit.gov.cn" target="_blank">浙 ICP备 18028232号</a></p>
        </div>
      </div>
    </div>
  </footer>
</body>
</html>
